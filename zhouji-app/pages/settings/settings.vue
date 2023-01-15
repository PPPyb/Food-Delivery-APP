<template>	
	<view>
		<navbar :ableBack="true" :pic="3" :canvas_id="'settingscanvas'"></navbar>
		<view class="list_title">About</view>
		<view class="list_text">Design and Complete by Group 10</view>
		<view class="list_text">Lu Hao</view>
		<view class="list_text">Nan Jing</view>
		<view class="list_text">Huo Jiaxin</view>
		<view class="list_text">Peng Yibo</view>
		<view class="list_text">Guo Jie</view>
		<view class="list_text">Zheng Yucheng</view>
		<view class="list_title">Group 10 appreciate your support</view>
	</view>
</template>

<script>
	import NavBar from "../../components/NavBarPro/NavBarPro.vue";
	export default {
		components:{
			navbar: NavBar
		},
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style>
.list_text{
	padding-left: 20upx;
	font-size: 40upx;
}
.list_title{
	padding-left: 20upx;
	font-size: 50upx;
}
</style>
