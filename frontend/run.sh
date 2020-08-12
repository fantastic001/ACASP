#!/bin/sh

THIS_DIR=$(dirname $0)
FRONTEND_DIR="$(readlink -f "$THIS_DIR")"
pushd . 
cd $FRONTEND_DIR
npm install
npm run-script build
popd

docker run -it --rm \
	--name acasp_frontend \
	--link at_acasp_deploy \
	--volume "$FRONTEND_DIR/dist":/usr/share/nginx/html/frontend/dist \
	--volume "$FRONTEND_DIR/index.html":/usr/share/nginx/html/frontend/index.html \
	--volume "$FRONTEND_DIR/nginx/":/etc/nginx/ \
	--publish 666:80 \
	nginx
