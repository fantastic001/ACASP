<script>
import CheckRoleService from "./service";

export default {
    name: "CheckRole",
    props: ["CheckRole"],
    data: function () {
        return {
            data: {},
        }
    },
    mounted: function () 
    {
        CheckRoleService.get(this.CheckRole).then(response => this.data = response.data);
    },
    methods: 
    {
    	deleteItem: function() 
	{
		CheckRoleService.delete(this.CheckRole).then(response => this.data = {});
	}
	// add additional methods here
    }
}
</script>

<template>
    <div class="widget-CheckRole"> 
    <span class="badge badge-pill badge-info">CheckRole: </span>

    <div class="card" style="width: 18rem;">
      <p>TOP</p>
      <div class="card-body">
        <h5 class="card-title"></h5>
        <p class="card-text item-description"></p>
      </div>

    
     <ul class="list-group list-group-flush">
        <li class="list-group-item"></li>
     </ul>


    <div class="card-body">
      <button class="btn btn-danger" @click="deleteItem" v-if="true">Delete</button>
      <button class="btn btn-primary" @click="orderItem" v-if="true">Button</button>
    </div>
    </div>
    </div>

</template>

<style scoped> 



</style>