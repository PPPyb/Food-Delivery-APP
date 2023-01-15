<template>
	<view>
		<navbar :ableBack="false" :ableSet="true" :pic="4" :canvas_id="'shopcanvas'"></navbar>
		<view class="search-input-box">
			<input type="text" value="" placeholder="Search for anything you want" placeholder-class="my-placeholder-s"/>
			<view class="icon iconfont icon-sousuo" style="padding-left: 5px;"></view>
		</view>
		<view class="top-text-bar">
			<view class="top-text-s" @click="changeShow(true)">
				Shops
				<view class="text-underline" v-show="controlShow"></view>
			</view>
			<view class="top-text-s" @click="changeShow(false)">
				Items
				<view class="text-underline" v-show="!controlShow"></view>
			</view>
		</view>
		<!-- shops -->
		<view class="souvenit-s" v-show="controlShow" style="display: flex; flex-wrap: wrap;">
			<shopproducts :item="item" v-for="(item,index) in itemexample" :key="index"></shopproducts>
		</view>
		<!-- items -->
		<view class="local-products-s" v-show="!controlShow">
			<shopitem :goods="item" v-for="(item, index) in foodlist" :key="index"></shopitem>
			<!-- 占位块儿 --><view style="height: 10vh;"></view>
		</view>
	</view>
</template>

<script>
import shopitem from "../../components/shopitem/shopitem.vue";
import shopProducts from "../../components/shopProducts/shopProducts.vue";
import NavBar from "../../components/NavBarPro/NavBarPro.vue";
export default {
	components:{
		navbar: NavBar,
		shopproducts: shopProducts,
		shopitem: shopitem
	},
	data() {
		return {
			controlShow: true,
			itemexample: [
				{id:0,provider: "ShuTan Sichuan cuisine",examples: [
					"../../static/img/shopExample/shopExample1.png",
					"../../static/img/shopExample/shopExample2.png",
					"../../static/img/shopExample/shopExample3.png"
				]},
				{id:1,provider: "Gll Wonton",examples: [
					"../../static/img/shopExample/shopExample1.png",
					"../../static/img/shopExample/shopExample2.png",
					"../../static/img/shopExample/shopExample3.png"
				]},
				{id:2,provider: "Peng sauce milk tea",examples: [
					"../../static/img/shopExample/shopExample1.png",
					"../../static/img/shopExample/shopExample2.png",
					"../../static/img/shopExample/shopExample3.png"
				]},
				{id:3,provider: "Time Cake",examples: [
					"../../static/img/shopExample/shopExample1.png",
					"../../static/img/shopExample/shopExample2.png",
					"../../static/img/shopExample/shopExample3.png"
				]},
				{id:4,provider: "Chinese Food",examples: [
					"../../static/img/shopExample/shopExample1.png",
					"../../static/img/shopExample/shopExample2.png",
					"../../static/img/shopExample/shopExample3.png"
				]},
				{id:5,provider: "Home of the World",examples: [
					"../../static/img/shopExample/shopExample1.png",
					"../../static/img/shopExample/shopExample2.png",
					"../../static/img/shopExample/shopExample3.png"
				]}
			],
			foodlist:[
				{
					goodsid: 1,
					goodsname: "Apple Apple Apple Apple",
					type: "red;yellow;green",
					area: "Beijing",
					price: "69",
					history: "99",
					storage: 987,
					goodsinfo: "",
					cover: "../../static/img/exampleForSocial.png",
					shopname: "Gll Wonton",
					praise: 256,
					trample: 33
				},
				{
					goodsid: 1,
					goodsname: "Apple Apple Apple Apple",
					type: "red;yellow;green",
					area: "Beijing",
					price: "69",
					history: "99",
					storage: 987,
					goodsinfo: "",
					cover: "../../static/img/exampleForSocial.png",
					shopname: "Gll Wonton",
					praise: 256,
					trample: 33
				}
			]
		}
	},
	async onShow() {
		// 获取shops
		for(let i=0;i<this.itemexample.length;i++){
			try{
				let req = {
					// token: "123"
					shopname: this.itemexample[i].provider
				}
				let [err,res] = await this.$myRequest.post('/shop/getshop',JSON.stringify(req));
				let covers = res.data.cover
				for(let j=0;j<covers.length;j++){
					covers[j] = this.config.staticUrl + covers[j]
				}
				this.itemexample[i].examples = covers;
			}catch(e){
				console.log(e)
				uni.showToast({
					title: "网络错误",
					icon: 'none',
					duration: 1000
				});
			}
		}
		// 获取items
		try{
			let [err,res] = await this.$myRequest.get('/shop/itemlist',{});
			let fooddata = res.data;
			for(let i=0;i<fooddata.length;i++){
				fooddata[i].cover = this.config.staticUrl + fooddata[i].cover;
				fooddata[i].price = fooddata[i].price.split(";")[0];
			}
			this.foodlist = res.data
		}catch(e){
			console.log(e)
			uni.showToast({
				title: "网络错误",
				icon: 'none',
				duration: 1000
			});
		}
	},
	methods: {
		changeShow(s){
			this.controlShow = s;
		}
	}
}
</script>

<style>
.search-input-box{
	width: 82%;
	height: 80upx;
	border-radius: 50upx;
	border: 2upx solid #8cdbed;
	display: flex;
	align-items: center;
	padding: 0 20px;
	padding-top: 0;
	margin: 5px auto 0 auto;
}
.my-placeholder-s{
	color: #8cdbed;
}
.search-input-box>input{
	flex: 1;
}
.search-input-box>view{
	color: #8cdbed;
	font-size: 45upx;
}
.top-text-bar{
	width: 80%;
	margin: 0 auto;
	display: flex;
	justify-content: space-around;
}
.top-text-s{
	display: flex;
	flex-direction: column;
	align-items: center;
	font-size: 40upx;
	line-height: 40upx;
	padding: 30upx 0;
}
.text-underline{
	background-color: #8cdbed;
	width: 60upx;
	height: 10upx;
	border-radius: 5upx;
}
.souvenit-s{
	height: fit-content;
}
.local-products-s{
	height: 500upx;
}
</style>
