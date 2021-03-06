#!/bin/bash

THIS_DIR=$(readlink -f $(dirname $0))

TARGET_PATH="$THIS_DIR/target"

NODE_HOSTNAME="node$(date +%s).api.atacasp"
NODE_ID="$NODE_HOSTNAME"
NODE_BIND_PORT=$(ls -ld $TARGET_PATH* | wc -l)
NODE_BIND_PORT=$((5545 + $NODE_BIND_PORT))

if [ -d "$TARGET_PATH-worker-$NODE_ID" ]; then 
    rm -rf "$TARGET_PATH-worker-$NODE_ID"

fi
mkdir -p "$TARGET_PATH-worker-$NODE_ID"
cp $TARGET_PATH/*.ear "$TARGET_PATH-worker-$NODE_ID"


mkdir -p  $THIS_DIR/data-$NODE_BIND_PORT

NODE_ALIAS=$NODE_HOSTNAME
echo "Bind port: $NODE_BIND_PORT"
docker run --rm \
    --hostname $NODE_HOSTNAME \
    --link  at_acasp_deploy \
    -e NODE_HOSTNAME="172.17.0.1" \
    -e NODE_PORT="$NODE_BIND_PORT" \
    -e NODE_ALIAS=$NODE_ALIAS \
    -e masterHostname="http://api.atacasp:8080/ACASPAPI-web/rest" \
    -v "$TARGET_PATH-worker-$NODE_ID":/opt/jboss/wildfly/standalone/deployments/ \
    -p $NODE_BIND_PORT:8080 \
    -v $THIS_DIR/standalone-full-ha.xml:/opt/jboss/wildfly/standalone/configuration/standalone-full-ha.xml \
    -v $THIS_DIR/data-$NODE_BIND_PORT:/opt/data/ \
    -it \
    -v $THIS_DIR/bin/:/usr/local/bin/ \
    jboss/wildfly:18.0.1.Final /usr/local/bin/init.sh
