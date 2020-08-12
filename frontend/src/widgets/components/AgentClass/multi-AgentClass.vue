<script>
import AgentClassService from "./service";
import AgentClass from "./AgentClass.vue";


export default {
    name: "multi-AgentClass",
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
        AgentClassService.list().then(response => this.items = response.data);
    },
    components: {
    	"AgentClass": AgentClass
    }
}
</script>

<template>
    <div class="multi-AgentClass"> 
      <AgentClass
      	v-for="item in items.filter(filter)"
      	:id="item.id"
      	:key="item.id"
        :AgentClass="item"
          />
    </div>

</template>

<style scoped> 



</style>