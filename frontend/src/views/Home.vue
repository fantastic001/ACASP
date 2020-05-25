<script>


import WidgetUserMulti from "../widgets/User/WidgetUserMulti.vue"
import WidgetChat from "../widgets/Chat/WidgetChat.vue"


export default {
    name: "Home",
    data: function () {
            return {
		data: {},
		role: "",
		search: false,
		create: false,
		search_data: {
			name_contains: "",
			description_contains: "",
			min_price: 0,
			max_price: 100000
		}
	    };
	},
    mounted: function () 
    {
    	this.role = localStorage.getItem("role");
    },
    methods: {
    	filter: function(item) {
		return item.name.includes(this.search_data.name_contains) && 
			item.description.includes(this.search_data.description_contains) &&
			item.price >= this.search_data.min_price && 
			item.price <= this.search_data.max_price;
	},
	submit_search: function() {
		this.$refs.items.reload();
	}
    },
    components: {
		WidgetChat
        
    }
}
</script>

<template>
<div>
Home page
<WidgetChat user="all"/>
</div>
</template>

<style scoped> 

.search-toggle {
	margin: 10px;
}

.search-part {
	background-color: #ddd;
	padding: 10px;
	margin: 10px;
}

</style>
