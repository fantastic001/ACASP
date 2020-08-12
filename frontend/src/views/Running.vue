<script>


import Runningagent from "../widgets/components/RunningAgent/multi-RunningAgent.vue"
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
		Runningagent
    }
}
</script>

<template>
<div>
Running agents on current connected node
<Runningagent />
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
