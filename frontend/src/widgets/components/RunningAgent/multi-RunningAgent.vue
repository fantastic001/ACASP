<script>
import RunningAgentService from "./service";
import RunningAgent from "./RunningAgent.vue";

import { WS_URL } from "./../../../config";

export default {
    name: "multi-RunningAgent",
    props: {
        filter: {
            type: Function,
            default: (x => true)
        }
    },
    data: function () {
        return {
            items: []
        }
    },
    mounted: function () 
    {
        RunningAgentService.list().then(response => this.items = response.data);
        // Create WebSocket connection.
        this.socket = new WebSocket(WS_URL + '/websocket/');
        this.socket.component = this;
        // Connection opened
        this.socket.addEventListener('open', function (event) {
            socket.send('Hello Server!');
        });
        // Listen for messages
        this.socket.addEventListener('message', function (event) {
            console.log('Message from server ', event.data);
            if (event.data.startsWith("LOGIN") || event.data.startsWith("LOGOUT")) {
                console.log("reloading...");
                this.component.reload();
            }
        });

    },
    methods: {
        reload: function () {
            RunningAgentService.list().then(response => this.items = response.data);
        }
    },
    components: {
    	"RunningAgent": RunningAgent
    }
}
</script>

<template>
    <div class="multi-RunningAgent"> 
      <RunningAgent
      	v-for="item in items.filter(filter)"
      	:id="item.id"
      	:key="item.id"
        :RunningAgent="item"
        @onStop="reload"
          />
    </div>

</template>

<style scoped> 



</style>