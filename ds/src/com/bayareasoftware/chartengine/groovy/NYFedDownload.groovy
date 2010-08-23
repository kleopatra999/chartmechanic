package com.bayareasoftware.chartengine.groovy

/**
 pull down all the sdmx files we can find from the NY Federal Reserve site

 Usage: groovy NYFedDownload.groovy [options]

 Options:
   -o, --outdir            output directory (default: "/tmp-data")
   -d, --debug             show debug information
   -h                      show this help message

 Examples:
   groovy NYFedDownload.groovy -o /tmp/fed-data

**/

import java.util.zip.ZipFile

class NYFedDownload {
    def outdir
    def verbose
    NYFedDownload(outdir, verbose) {
        this.outdir = outdir
        this.verbose = verbose
        def f = new File(outdir)
        if (!f.isDirectory()) {
            f.mkdir()
        }
    }

    def p(s) {
        if (verbose) {
            println(s)
        }
    }

/**
 given a URL, look in that page for all first-level links
 that start with prefix and return a list of qualified URLs
 **/
    def List<String> findLinks(urlString, prefix) {
        p('checking links from ' + urlString)

        // find patterns of hrefs that start with prefix
        def pattern = "href=\"("+ prefix + ".*?)\""
        def url = new URL(urlString)
        def body = url.text
        def match = (body =~ pattern)

        def urlRoot = urlString.substring(0,urlString.lastIndexOf('/')+1)
        if (prefix.startsWith('/')) {
            urlRoot = urlString.substring(0,urlString.indexOf(url.getPath()))
        }
        //println "urlRoot = " + urlRoot
        // links is a list of all the fully qualified URLs that were first-level links
        def links = []
        match.each ({
            // if the regexp matched value starts with /, skip it so we don't end up
            // with double slashes
            k,v -> links << urlRoot+v
        })
        return links
    }

    /** 
     extract files from a ZIP file
     expects ZIP to be flat, no directories
     // also, replace any ' & ' with ' &amp; ' as the Fed data has some uncleanliness
     */
    def extractZipFile(File zipfile) {
    	def zf = new ZipFile(zipfile)
    	for (ze in zf.entries()) {
    	    p("extracting zip file named " + ze.getName())
            def fos = new FileOutputStream(new File(outdir,ze.getName()))
            zf.getInputStream(ze).eachLine {
                s -> fos << s.replace(' & ', ' &amp; ') << '\n'
            }
            fos.close()
        }     
    }
    
    /**
     * download a single file from URL u to a file 
     */
    def downloadSingleFile(String u, File outfile) {
        try {
            def url = new URL(u)
            p('downloading ' + u + ' to ' + outfile)
            def out = new BufferedOutputStream(new FileOutputStream(outfile))
            out << url.openStream()
            out.close()
        } catch (FileNotFoundException fne) {
            println("file: " + u + " not found because of " + fne)
            fne.printStackTrace()
        }
    }
    
    /** download files from links that contain the prefix string
     *  from the page referred to by the URL.
     */
    def downloadFiles(urlString,prefix="/xml/data/") {
        def ret = findLinks(urlString,prefix)
        for (u in ret) {
            downloadSingleFile(u,new File(outdir,u.split("/")[-1]))
        }

    }
    
    static void main(args) {
//      parse the cmd-line arguments

        def cli = new CliBuilder(usage: 'groovy downloadFed.groovy [options]')
        cli.d(longOpt:'debug','Turn on debugging')
        cli.h(longOpt:'help','Help')
        cli.o(longOpt:'outdir',args:1,'Output Directory')
        def options = cli.parse(args)

        def dir="/tmp/nyfed-data"
        def debug = false

        if (options.h) { 
            cli.usage()
            System.exit(0)
        }
        if (options.d) {
            debug = true
        }
        if (options.o) {
            dir = options.o
        }

        NYFedDownload nyf = new NYFedDownload(dir,debug)

        ["fx.html","gsds.html","gsds_transactions.html","gsds_finance.html","gsds_fails.html"].each {
            f -> nyf.downloadFiles("http://www.newyorkfed.org/xml/"+f);
        }

        ["structures/FXStructure.xml", 
         "structures/PDStructure.xml",
         "schemas/FX_Utility.xsd",
         "schemas/PD_Utility.xsd",
         "schemas/RateBaseUtility.xsd",
         "schemas/FFTargetUtility.xsd",
         "codelists/FXCodelists.xml",
         "codelists/FFCodelists.xml",
         "codelists/PDCodelists.xml",
         "codelists/RatesCodelists.xml",
         "codelists/GeneralCodelists.xml"].each {
            f -> nyf.downloadSingleFile("http://www.newyorkfed.org/xml/"+f,
                    new File(dir,f.split('/')[1]))
        }

        nyf.downloadFiles("http://www.newyorkfed.org/xml/fedfunds.html")

        ["http://www.unece.org/etrades/unedocs/repository/codelists/xml/CountryCode.xsd",
         "http://www.unece.org/etrades/unedocs/repository/codelists/xml/CurrencyCodeList.xml"].each {
            f -> nyf.downloadSingleFile(f, new File(dir,f.split('/')[-1]))
        }
        

    }
 }


