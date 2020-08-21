<script>


import MessageSendForm from "../widgets/components/MessageSendForm/MessageSendForm.vue"
export default {
    name: "Send message",
    data: function () {
            return {
		data: {},
	    };
	},
    methods: {
    	
	},
    components: {
		MessageSendForm 
    }
}
</script>

<template>
<div>
Send message to agents
<MessageSendForm />
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
