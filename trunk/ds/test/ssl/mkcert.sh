#!/bin/sh

# Generates a self-signed certificate.
# Edit dovecot-openssl.cnf before running this.

OPENSSL=${OPENSSL-openssl}
SSLDIR=${SSLDIR-.}
OPENSSLCONFIG=${OPENSSLCONFIG-sf-openssl.cnf}

CERTDIR=$SSLDIR
KEYDIR=$SSLDIR

CERTFILE=$CERTDIR/server.crt
KEYFILE=$KEYDIR/server.key

if [ -f $CERTFILE ]; then
  echo "$CERTFILE already exists, won't overwrite"
  exit 1
fi

if [ -f $KEYFILE ]; then
  echo "$KEYFILE already exists, won't overwrite"
  exit 1
fi

$OPENSSL req -new -x509 -nodes -config $OPENSSLCONFIG -out $CERTFILE -keyout $KEYFILE -days 2000 || exit 2
#chown root:root $CERTFILE $KEYFILE
#chmod 0600 $CERTFILE $KEYFILE
echo 
$OPENSSL x509 -subject -fingerprint -noout -in $CERTFILE || exit 2
