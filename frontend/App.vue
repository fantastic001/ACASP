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
  },
  mounted: function () {
  }
}

</script>


<template>
  <div id="app">
    <nav class="main-menu navbar navbar-dark bg-primary navbar-expand-lg">
	<div class="navbar-nav">
        <router-link to='/'>Home</router-link>

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
