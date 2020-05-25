<script>
import UserService from "./service";

export default {
    name: "WidgetUserNew",
    data: function () {
        return {
            data: {
	    	username: null,
		password: null,
		email: null
	    },
	    success: false,

        };
    },
    methods: {
    	submit: function() 
		{
			UserService.create(this.data).then(response => {
				console.log("User created: ");
				console.log(response.data);
				this.success = true
				this.user = response.data.id;
			});
		},
    }
}
</script>

<template>
    <div class="widget-user-new"> 
        <div v-if="this.success">Creation successful</div>
	<div v-if="!this.success"> 
		<p>
		<label>Username</label>
		<input type="text" v-model="data.username" />
		</p>
		
		<p>
		<label>Password</label>
		<input type="password" v-model="data.password" />
		</p>
		

		<p><button v-on:click="submit">Submit</button></p>
	</div>

    </div>

</template>

<style scoped> 



</style>
