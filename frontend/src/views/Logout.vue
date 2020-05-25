<script>
import LoginService from "../widgets/Auth/service";



export default {
    name: "LogOut",
    data: function () {
            return {
		data: {}
	    };
	},
    mounted: function () 
    {
		LoginService.logout();
		localStorage.setItem("user", "");
		localStorage.setItem("role", "NOT_LOGGED");
		this.$store.commit("login", {user: "", role: "NOT_LOGGED"});
		this.$router.push("/");
    },
}
</script>

<template>

<p>Logging out...</p>

</template>

<style scoped> 



</style>
