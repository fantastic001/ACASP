<script>
import UserService from "./service";

export default {
    name: "WidgetUserSingle",
    props: ["user"],
    data: function () {
        return {
            data: {
		id: null
	    }
        }
    },
    mounted: function () 
    {
        // UserService.get(this.user).then(response => this.data = response.data);
    }
}
</script>

<template>
    <div class="widget-user-single"> 


<div class="card" style="width: 18rem;">
  <div class="card-header">
        <router-link :to="'users/' + user">{{ user }}</router-link>
  </div>
  <div class="card-body">
  </div>
</div>

    </div>

</template>

<style scoped> 



</style>
