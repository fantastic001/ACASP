const WebSocket = require('ws');

// const ws = new WebSocket('ws://localhost:5544/ChatAPI-web/websocket/login');
const ws = new WebSocket('ws://localhost:666/ChatAPI-web/websocket/login');


ws.on('open', function open() {
  ws.send('something');
  console.log("Connection opened");
});

ws.on('message', function incoming(data) {
  console.log(data);
});
