#!/bin/sh

set -x 

BACKEND_DIR="$(readlink -f $(dirname $0))"
echo $BACKEND_DIR
pushd .
cd "$BACKEND_DIR"
APPNAME="$(ls -d *-ear | sed "s/-ear//g")"
popd

DOCKER_OPTIONS=""
for t in ejb ear web; do 
	DOCKER_OPTIONS="${DOCKER_OPTIONS} -v $BACKEND_DIR/$APPNAME-$t:/usr/src/mymaven/$APPNAME-$t"
done
docker run \
	-it --rm \
	--name prodex_api_build \
	-v "$BACKEND_DIR/pom.xml":/usr/src/mymaven/pom.xml \
	${DOCKER_OPTIONS} \
	-v "$BACKEND_DIR/.m2":/root/.m2 \
	-w /usr/src/mymaven \
	maven mvn clean package


if [ $? = 0 ]; then 
	echo "Successfully build $APPNAME"
fi

rm -rf "$BACKEND_DIR/target" 
mkdir -p "$BACKEND_DIR/target" 
cp -r "$BACKEND_DIR/$APPNAME-ear/target/$APPNAME-ear.ear" "$BACKEND_DIR/target"

