<script>
import AgentClassService from "./service";

export default {
    name: "AgentClass",
    props: ["AgentClass"],
    data: function () {
        return {
            data: {},
            name: ""
        }
    },
    mounted: function () 
    {
        this.data = this.AgentClass;
    },
    methods: {
        run: function() {
            AgentClassService.run(this.data.fullName, this.name).then(response => {
                console.log("Agent running");
            });
        }
    }
}
</script>

<template>
    <div class="widget-AgentClass"> 
    <span class="badge badge-pill badge-info">AgentClass: </span>

    <div class="card" style="width: 18rem;">
      <p>Agent class</p>
      <div class="card-body">
        <h5 class="card-title">{{ data.fullName}}</h5>
        <input placeholder="New Agent Instance Name" v-model="name">
      </div>


    <div class="card-body">
      <button class="btn btn-primary" v-if="this.name != ''" @click="run">Run new agent instance</button>
    </div>
    </div>
    </div>

</template>

<style scoped> 



</style>