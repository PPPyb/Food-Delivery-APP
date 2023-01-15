<template>
<view>
<navbar :ableBack="true" :ableSet="true" :pic="2" :canvas_id="'shopcanvas'"></navbar>
<view style="padding: 0 20px;">
<!-- page begin -->	
<view class="unit_center_box" style="font-size: 20px;">Shopping Cart</view>
<view class="unit_right_box" style="font-size: 18px; margin: 0 10px;">
	<view @tap="clearAll()">Clear All</view>
	<view>
		<checkbox @tap="selectAll()" :checked="chooseAll"></checkbox>
		Select all
	</view>
</view>
<!-- items list box -->
<view class="cart_list_box">
	<view style="display: flex;align-items: center;margin-left: 8px;font-size: 20px;color: #979797;">
		<view class="icon iconfont icon-sousuo" style="font-size: 30px;margin-right: 5px;color: #8cdbed;"></view>
		{{shopname}}
	</view>
	<!-- item box -->
	<cartItem v-bind:item="item" v-for="(item,key) in data" :key="key" v-on:update="updateSelect"></cartItem>
</view>
<view style="height: 12vh;"></view>
<!-- page end -->
</view>
<!-- 下方悬浮框 -->
<view class="bottom-bar" v-show="!pcshow">
	<view class="inside-bottom-bar" @tap="buy">Buy</view>
</view>
<view class="bottom-bar-pc" v-show="pcshow">
	<view class="inside-bottom-bar-pc" @tap="buy">Buy</view>
</view>
</view>
</template>

<script>
import NavBar from "../../components/NavBarPro/NavBarPro.vue";
import cartItem from "../../components/cart-item/cart-item";
export default {
	components:{
		navbar: NavBar,
		cartItem: cartItem
	},
	data() {
		return {
			chooseAll: false,
			shopname: "There is Nothing",
			data: [],
			pcshow: false
		}
	},
	onLoad() {
		if(uni.getStorageSync("cart")){
			this.shopname = uni.getStorageSync("cart").shop;
			this.data = uni.getStorageSync("cart").items;
		}
		this.pcshow = uni.getStorageSync('pcshow');
	},
	onHide(){
		// 推出页面时保存购物车内容
		console.log("Cart saved");
		if(uni.getStorageSync("cart")){
			uni.setStorageSync("cart",{
				shop: uni.getStorageSync("cart").shop,
				items: this.data
			})
		}
	},
	methods: {
		updateSelect(){
			// 当下方选择按钮点击时取消全选状态
			this.chooseAll = false;
		},
		selectAll(){
			this.chooseAll = !this.chooseAll;
			for(var i=0;i<this.data.length;i++){
				this.data[i].choose = this.chooseAll;
			}
		},
		clearAll(){
			uni.showModal({
				title: "提示",
				content: "Are you sure to clear all?",
				success: (e) => {
					if (e.confirm) {
						uni.removeStorageSync("cart");
						this.shopname = "There is Nothing";
						this.data = [];
					}
				}
			})
		},
		buy(){
			// 下单
			uni.navigateTo({
				url: "/pages/confirmorder/confirmorder"
			})
		}
	}
}
</script>

<style>
/* view{
	border: 1px solid black;
} */
.unit_center_box{
	display: flex;
	justify-content: center;
	align-items: center;
}
.unit_right_box{
	display: flex;
	justify-content: space-between;
	align-items: center;
}
.cart_list_box{
	height: fit-content;
	display: flex;
	border: 2px solid #8cdbed;
	border-radius: 25px;
	margin-top: 10px;
	flex-direction: column;
}
.bottom-bar{
	width: 100%;
	height: 7vh;
	top: 90vh;
	position: fixed;
	display: flex;
	justify-content: center;
}
.inside-bottom-bar{
	width: 90%;
	border-radius: 3.5vh;
	background-color: #8cdbed;
	display: flex;
	justify-content: center;
	align-items: center;
	color: #FFFFFF;
	font-size: 20px;
}
.bottom-bar-pc{
	width: 200px;
	height: 7vh;
	top: 90vh;
	right: 20px;
	position: fixed;
	display: flex;
	justify-content: center;
}
.inside-bottom-bar-pc{
	width: 100%;
	border-radius: 3.5vh;
	background-color: #8cdbed;
	display: flex;
	justify-content: center;
	align-items: center;
	color: #FFFFFF;
	font-size: 20px;
}
</style>
