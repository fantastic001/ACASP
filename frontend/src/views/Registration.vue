<script>


import UserService from "../widgets/User/service"
import WidgetUserSingle from "../widgets/User/WidgetUserSingle.vue"
import WidgetUserSingleEdit from "../widgets/User/WidgetUserSingleEdit.vue"
import WidgetUserMulti from "../widgets/User/WidgetUserMulti.vue"
import WidgetUserNew from "../widgets/User/WidgetUserNew.vue"


export default {
    name: "Registration",
    data: function () {
            return {
		data: {}
	    };
	},
    mounted: function () 
    {
    },
    methods: {
    },
    components: {
        
        
        "WidgetUserSingle": WidgetUserSingle,
        "WidgetUserNew": WidgetUserNew,
        "WidgetUserSingleEdit": WidgetUserSingleEdit,
        "WidgetUserMulti": WidgetUserMulti
    }
}
</script>

<template>
    <div class="User-detail"> 
        <WidgetUserNew />

        
        
    </div>

</template>

<style scoped> 



</style>
