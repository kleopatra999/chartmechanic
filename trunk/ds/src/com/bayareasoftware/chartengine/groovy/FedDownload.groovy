package com.bayareasoftware.chartengine.groovy

/**
 pull down all the sdmx files we can find from the Federal Reserve site

 Usage: groovy FedDownload.groovy [options]

 Options:
   -url                    starting URL (default: http://www.federalreserve.gov/datadownload/default.htm)
   -o, --outdir            output directory (default: "/tmp-data")
   -d, --debug             show debug information
   -h                      show this help message

 Examples:
   groovy FedDownload.groovy -url http://www.federalreserve.gov/datadownload/default.htm -o /tmp/fed-data

**/

import java.util.zip.ZipFile

class FedDownload {
    def url
    def outdir
    def verbose
    FedDownload(url, outdir, verbose) {
        this.url = url
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
    def List<String> findLinks(url, prefix) {
        p('checking links from ' + url)

        // find patterns of hrefs that start with prefix
        def pattern = "href=\"("+ prefix + ".*?)\""
        def body = new URL(url).text
        def match = (body =~ pattern)

        def urlRoot = url.substring(0,url.lastIndexOf('/'))
        // links is a list of all the fully qualified URLs that were first-level links
        def links = []
        match.each( {k,v -> links << urlRoot+'/'+v} )

        return links
    }

    /**
     extract files from a ZIP file
     expects ZIP to be flat, no directories
     // also, replace any ' & ' with ' &amp; ' as the Fed data has some uncleanliness
     // also, and fix the typo CL_UNT with CL_UNIT as the Fed data has some uncleanliness
     */
    def extractZipFile(File zipfile) {
        def zf = new ZipFile(zipfile)
        for (ze in zf.entries()) {
            p("extracting zip file named " + ze.getName())
            def fos = new FileOutputStream(new File(outdir,ze.getName()))
            zf.getInputStream(ze).eachLine {
                    // fix two kinds of typos we find in the data files
                    s -> fos << s.replace(' & ', ' &amp; ').replace("CL_UNT","CL_UNIT") << '\n'
            }
            fos.close()
        }
    }


    /** download zip files from url.  Tailored to the shape of the web pages at
     * federalreserve.gov
     */
    def download() {
        def ret = findLinks(url,"Choose.aspx")
        for (u in ret) {
            def links = findLinks(u,"Output.aspx")
            for (l in links) {
                // in case there are any embedded &amp;, replace them with plain ol &
                l = l.replace('&amp;','&')
                p(l)
                def url = new URL(l)
                def urlconn = url.openConnection()
                // name of the file is from the Content-Disposition in the header
                def headerFile = urlconn.getHeaderField("Content-Disposition");
                if (headerFile != null) {
                    def outfile = new File(outdir,urlconn.getHeaderField("Content-Disposition").split("=")[-1])
                    p("extracting zip stream: " + outfile)
                    def out = new BufferedOutputStream(new FileOutputStream(outfile))
                    out << url.openStream()
                    out.close()
                    extractZipFile(outfile)
                    outfile.delete()
                } else {
                    println  "****WARNING***** Problem downloading link at : " + l;
                }
            }
        }

    }

    static void main(args) {
//      parse the cmd-line arguments

        def cli = new CliBuilder(usage: 'groovy downloadFed.groovy [options]')
        cli.d(longOpt:'debug','Turn on debugging')
        cli.h(longOpt:'help','Help')
        cli.o(longOpt:'outdir',args:1,'Output Directory')
        cli.u(longOpt:'url',args:1,'URL from which to download')
        def options = cli.parse(args)

        def dir="/tmp/fed-data"
        def url="http://www.federalreserve.gov/datadownload/default.htm"
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
        if (options.u) {
            url = options.u
        }

//        println "dir = " + dir + " url = " + url + " debug = " + debug
        new FedDownload(url,dir,debug).download()
    }

}


