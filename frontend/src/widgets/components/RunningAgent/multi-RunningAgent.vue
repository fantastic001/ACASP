<script>
import RunningAgentService from "./service";
import RunningAgent from "./RunningAgent.vue";


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