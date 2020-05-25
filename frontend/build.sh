#!/bin/bash

THIS_DIR=$(dirname $0)
FRONTEND_DIR="$(readlink -f "$THIS_DIR")"
pushd . 
cd $FRONTEND_DIR
npm install
npm run-script build
cp -r index.html dist ../backend/ChatAPI-web/src/main/webapp/frontend/
popd