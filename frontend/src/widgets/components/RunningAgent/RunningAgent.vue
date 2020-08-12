<script>
import RunningAgentService from "./service";

export default {
    name: "RunningAgent",
    props: ["RunningAgent"],
    data: function () {
        return {
            data: {},
        }
    },
    mounted: function () 
    {
        this.data = this.RunningAgent;
    },
    methods: 
    {
    	deleteItem: function() 
	    {
		    RunningAgentService.delete(this.RunningAgent.name).then(response => {
            this.data = {};
            this.$emit("onStop");
          }
        );
	    }
    }
}
</script>

<template>
    <div class="widget-RunningAgent"> 
    <span class="badge badge-pill badge-info">RunningAgent: </span>

    <div class="card" style="width: 18rem;">
      <p>{{ data.name}}</p>
      <div class="card-body">
        <p class="card-text item-description">{{data.agent.id.type.fullName}}</p>
      </div>

    <div class="card-body">
      <button class="btn btn-danger" @click="deleteItem" v-if="true">Stop agent</button>
    </div>
    </div>
    </div>

</template>

<style scoped> 



</style>