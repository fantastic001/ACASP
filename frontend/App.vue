<script>

export default {
  name: "App",
  data: function () {
	return {
		data: {
			user: localStorage.getItem("user"),
			role: localStorage.getItem("role")
		}
	}
  },
  components: {
  },
  methods:{
	  logOut: function() 
		{
			AuthService.logout().then(response => {
				window.location.href = "/frontend/";
				loocalStorage.setItem("user", "");
				loocalStorage.setItem("role", "");
			});
		}
  },
  mounted: function () {
	this.$store.subscribe((mutation, state) => {
		console.log("Logged as: ", state.user, " with role: ", state.role);
		console.log(this);
		this.data.user = state.user;
		this.data.role = state.role;
		console.log("State changed");
	})
  }
}

</script>


<template>
  <div id="app">
    <nav class="main-menu navbar navbar-dark bg-primary navbar-expand-lg">
	<div class="navbar-nav">
        <router-link to='/'>Home</router-link>

        <router-link v-if='data.role == "NOT_LOGGED"' to='/login'>Login</router-link>
        <router-link v-if='data.role == "NOT_LOGGED"' to='/registration'>Registration</router-link>

        <router-link v-if="data.role != 'NOT_LOGGED'" to='/logout'>Log out</router-link>
        <router-link v-if="data.role == 'LOGGED'" to='/profile'>Profile</router-link>
		<router-link to="/users">Users</router-link>
	</div>
	
	<p class="my-2 my-lg-0" v-if="data.role != 'NOT_LOGGED'">Logged as: {{ data.user }}</p>


    </nav>
    <div class="view"><router-view /> </div>
  </div>
</template>

<style scoped>

.view {
	margin: 20px;
}

.main-menu a
{
	color: white;
	padding: 10px;
}
.main-menu a:hover 
{
	text-decoration: none;
	display: block;
	background: #bbbbff;
}

.main-menu p
{
	color: white;
}
</style>
