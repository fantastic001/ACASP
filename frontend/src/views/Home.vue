<script>


import AgentClass from "../widgets/components/AgentClass/multi-AgentClass.vue"
export default {
    name: "Home",
    data: function () {
            return {
		data: {},
	    };
	},
    methods: {
    	
	},
    components: {
		AgentClass 
    }
}
</script>

<template>
<div>
Home page
<AgentClass />
</div>
</template>

<style scoped> 

.search-toggle {
	margin: 10px;
}

.search-part {
	background-color: #ddd;
	padding: 10px;
	margin: 10px;
}

</style>
