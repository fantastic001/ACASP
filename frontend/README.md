Building
=================

Run:

	npm install 
	npm run-script build


Running
=========

It is recommended to use dedicated Docker container. Docker container will build and run frontend, just run:

	./run.sh

Now you can access http://localhost:666/frontend/

NOTE: for this container to work, you will need to run backend container as well (backend/run.sh)

If you want to run it manually, there are few things you need to consider:

1. backend and frontend have to be hosted by the same web server (to avoid cross origin protection)
	+ Provided container uses Nginx server which hosts frontend index.html and dist/ directory and  redirects other urls to backend, please check `nginx/nginx.conf` for sample configuration.
2. check src/config.js to configure frontend to access websocket properly (change localhost:666 to address of hosting)
