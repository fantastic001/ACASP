<script>


import MessageLog from "../widgets/components/MessageLog/MessageLog.vue"
export default {
    name: "Messages",
    data: function () {
            return {
		data: {},
	    };
	},
    methods: {
    	
	},
    components: {
		MessageLog 
    }
}
</script>

<template>
<div>
<MessageLog />
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
