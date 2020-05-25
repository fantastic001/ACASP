#!/bin/bash

THIS_DIR=$(readlink -f $(dirname $0))

TARGET_PATH="$THIS_DIR/target"


docker run --rm \
    --hostname api.atchat \
    --name at_chat_deploy \
    -v "$TARGET_PATH":/opt/jboss/wildfly/standalone/deployments/ \
    -p 5544:8080 \
    -p 9990:9990 \
    -it \
    jboss/wildfly /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0
