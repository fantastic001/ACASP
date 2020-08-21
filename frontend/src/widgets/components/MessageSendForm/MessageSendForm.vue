<script>
import MessageSendFormService from "./service";

export default {
    name: "MessageSendForm",
    data: function () {
        return {
            data:{
				"performative": null, 
				"sender":  {
						name: "",
						hostAlias: "",
						type: {
							module:"frontend",
							name: "",
						}
					}, 
				"receivers": [], 
				"replyTo":null, 
				"content": "PING", 
				"contentObj": null, 
				"userArgs": null, 
				"language": null, 
				"encoding": null, 
				"orthology": null, 
				"protocol": null, 
				"conversationId": null, 
				"replyWith": null, 
				"inReplyTo": null, 
				"replyBy": null
			},
			receiversList: "",

        };
    },
    methods: {
    	submit: function() 
		{
			this.data.receivers = this.receiversList.split(" ").map( x => {
					return {
						name: "",
						hostAlias: "",
						type: {
							module: x.split(".").slice(0,-1).join("."),
							name: x.split(".").reverse()[0],
						}
					}
				}
			);
			MessageSendFormService.submit(this.data).then(response => {
				if (response.data.code == 0) this.data.success = true;
				else this.data.success = false;

			});
		}
    }
}
</script>

<template>
    <div class="form-MessageSendForm"> 
        <div class="success-box" v-if="success">Message has been sent</div>
	<div v-if="!success"> 
		
		
		<p>
		<input type="text" class="form-control" placeholder="Receivers (separated by space)" v-model="receiversList" />
		</p>
		
		<p>
		<input type="text" class="form-control" placeholder="Message" v-model="data.content" />
		</p>
		

		<button type="button" class="btn btn-primary btn-lg btn-block" v-on:click="submit">Submit</button>
	</div>
    </div>

</template>

<style scoped> 

.form-MessageSendForm {

}

.success-box 
{
	backgrund-color: #dfd;
	color: #0f0;
	padding: 5px;
}

</style>