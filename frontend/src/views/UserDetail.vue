<script>

import UserService from "../widgets/User/service"
import WidgetChat from "../widgets/Chat/WidgetChat.vue";

export default {
    name: "UserDetail",
    props: ["id"],
    data: function () {
            return {
		data: {},
		type: null,
		foreign_key: 0,
		role: localStorage.getItem("role"),
	    };
	},
    mounted: function () 
    {
    	
    },
    methods: {
        
    },
    components: {
        WidgetChat
    }
}
</script>

<template>
    <div class="User-detail"> 
        <WidgetChat :user="id" />
    </div>

</template>

<style scoped> 



</style>
