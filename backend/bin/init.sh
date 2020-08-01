#!/bin/sh

/usr/local/bin/empty -f -i /tmp/input -o /tmp/output /opt/jboss/wildfly/bin/add-user.sh

sleep 2

echo b > /tmp/input
sleep 2
echo guest > /tmp/input
sleep 2
echo guest.guest.1 > /tmp/input
sleep 2
echo guest.guest.1 > /tmp/input
sleep 2
echo guest > /tmp/input
sleep 2
echo yes > /tmp/input
sleep 2
echo yes > /tmp/input
sleep 2



/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -c standalone-full-ha.xml
