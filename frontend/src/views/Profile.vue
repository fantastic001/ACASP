<script>



export default {
    name: "Profile",
    data: function () {
            return {
		data: {},
	    };
	},
    mounted: function () 
    {
    	this.$router.push("/users/" + localStorage.getItem("user_id"));
    },
    methods: {
    },
    components: {
        
    }
}
</script>

<template>
    <div class="Buyer-detail"> 
        
    </div>

</template>

<style scoped> 



</style>
