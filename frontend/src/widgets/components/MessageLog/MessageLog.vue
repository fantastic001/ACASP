<script>
import { WS_URL } from "./../../../config";


export default {
    name: "MessageLog",
    props: ["MessageLog"],
    data: function () {
        return {
            data: [],
        }
    },
    mounted: function () 
    {
        // Create WebSocket connection.
        this.socket = new WebSocket(WS_URL + '/websocket/');
        this.socket.component = this;
        // Listen for messages
        this.socket.addEventListener('message', function (event) {
            console.log('Message from server ', event.data);
            if (event.data.startsWith("MSG")) {
                var log = event.data;
                this.component.data.push(log);
            }
        });

    },
}
</script>

<template>
    <div class="widget-MessageLog"> 
        <p v-for="msg in this.data" :key="msg.replace(/ /g, '_') + Math.random()" :id="msg.replace(/ /g, ' ') + Math.random()">
            {{msg.substring(0, 100)}}
        </p>
    </div>

</template>

<style scoped> 



</style>