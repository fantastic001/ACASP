#!/bin/bash

THIS_DIR=$(readlink -f $(dirname $0))

TARGET_PATH="$THIS_DIR/target"

mkdir -p $THIS_DIR/data

docker run \
    --hostname api.atacasp \
    --name at_acasp_deploy \
    --rm \
    -v "$TARGET_PATH":/opt/jboss/wildfly/standalone/deployments/ \
    -p 5544:8080 \
    -p 9990:9990 \
    -v $THIS_DIR/standalone-full-ha.xml:/opt/jboss/wildfly/standalone/configuration/standalone-full-ha.xml \
    -v $THIS_DIR/external:/opt/jboss/wildfly/standalone/lib/ext/ \
    -v $THIS_DIR/data:/opt/data/ \
    -it \
    -v $THIS_DIR/bin/:/usr/local/bin/ \
    jboss/wildfly:18.0.1.Final /usr/local/bin/init.sh
