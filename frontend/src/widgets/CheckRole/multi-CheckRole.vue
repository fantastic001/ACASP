<script>
import CheckRoleService from "./service";
import CheckRole from "./CheckRole.vue";


export default {
    name: "multi-CheckRole",
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
        CheckRoleService.list().then(response => this.items = response.data);
    },
    components: {
    	"CheckRole": CheckRole
    }
}
</script>

<template>
    <div class="multi-CheckRole"> 
      <CheckRole
      	v-for="item in items.filter(filter)"
      	:id="item.id"
      	:key="item.id"
        :CheckRole="item.id"
          />
    </div>

</template>

<style scoped> 



</style>