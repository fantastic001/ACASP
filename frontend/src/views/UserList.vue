<script>


import WidgetUserMulti from "../widgets/User/WidgetUserMulti.vue"

export default {
    name: "UserList",
    data: function () {
            return {
		data: {},
		search: "",

	    };
	},
    mounted: function () 
    {
    },
    methods: {
    },
    components: {
        WidgetUserMulti,
    }
}
</script>

<template>
    <div class="user-list"> 
    	<input v-model="search" placeholder="Search users..." />	
        <WidgetUserMulti :search="search" />
    </div>

</template>

<style scoped> 



</style>
