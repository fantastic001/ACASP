<script>
import UserService from "./service";
import WidgetUserSingle from "./WidgetUserSingle.vue";


export default {
    name: "WidgetUserMulti",
    props: {
        filter: {
            type: Function,
            default: (x => true)
        },
	search: {
		type: String
	}
    },
    data: function () {
        return {
            items: []
        }
    },
    watch: {
	search: function(criteria, oldCriteria) {
		console.log("Searching for " + criteria);
        	UserService.list().then(response => this.items = response.data.filter(user => this.search.split(' ').filter(x => x != '').filter(x => ((user.username)).includes(x)).length > 0));
	}
    },
    mounted: function () 
    {
        UserService.list().then(response => this.items = response.data);
    },
    components: {
    	"WidgetUserSingle": WidgetUserSingle
    }
}
</script>

<template>
    <div class="WidgetUserSingle"> 
      <WidgetUserSingle
      	v-for="item in items.filter(filter)"
      	:id="item.username"
      	:key="item.username"
        :user="item.username"
          />
    </div>

</template>

<style scoped> 



</style>
