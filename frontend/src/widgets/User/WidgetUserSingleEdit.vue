<script>
import UserService from "./service";

export default {
    name: "WidgetUserSingleEdit",
    props: ["user"],
    data: function () {
        return {
            data: {}
        }
    },
    mounted: function () 
    {
        UserService.get(this.user).then(response => this.data = response);
    }
}
</script>

<template>
    <div class="widget-user-single-edit"> 
        
    </div>

</template>

<style scoped> 

</style>