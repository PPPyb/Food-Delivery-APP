<template>
<view>
<!-- page top -->
<navbar :ableBack="true" :ableSet="true" :pic="4" :canvas_id="'shopcanvas'"></navbar>
<!-- main page -->
<view style="padding: 0 10px;">
	<button @click="open">{{shops[currentShop].name}}</button>
	<uni-popup ref="popup" type="left">
		<view style="width: 80vw;height: 100vh;background-color: #FFF;">
			<uni-list>
			    <uni-list-item :title="shop.name" :note="shop.info" clickable  @click="shopListClick(index)" v-for="(shop, index) in shops" :key="index"></uni-list-item>
			</uni-list>
		</view>
	</uni-popup>
	<shopitem :goods="foods[currentShop][index]" v-for="(item, index) in foods[currentShop]" :key="index"></shopitem>
	<!-- 占位块儿 --><view style="height: 10vh;"></view>
</view>
<!-- page end -->
</view>
</template>

<script>
	import shopitem from "../../components/shopitem/shopitem.vue";
	import NavBar from "../../components/NavBarPro/NavBarPro.vue";
	export default {
		components:{
			navbar: NavBar,
			shopitem: shopitem
		},
		data() {
			return {
				shops: [
					{id: 0, name:"ShuTan Sichuan cuisine",info:"Every day food, gourmet beauty carved."},
					{id: 1, name:"Gll Wonton",info:"Care for your heart and stomach, so that you eat more flavor."},
					{id: 2, name:"Peng sauce milk tea",info:"Every point, every point of happiness."},
					{id: 3, name:"Time Cake",info:"The day of the night! Midnight snack."},
					{id: 4, name:"Chinese Food",info:"Spend the same money, eat a better meal."},
					{id: 5, name:"Home of the World",info:"What you want, l know."}
				],
				currentShop: 0,
				foods: [[],[],[],[],[],[]]
			}
		},
		onLoad: function(options) {
			console.log("展示商店"+options.shopid+options.shopname+"的商品");
			if(options.shopid){
				this.currentShop = options.shopid;
			}
			this.send();
		},
		methods: {
			open(){
				this.$refs.popup.open('left');
			},
			shopListClick(shopid){
				// console.log(shopid);
				this.currentShop = shopid;
				this.$refs.popup.close('left');
			},
			async send(){
				// 获取items
				try{
					let [err,res] = await this.$myRequest.get('/shop/itemlist',{});
					let fooddata = res.data;
					for(let i=0;i<fooddata.length;i++){
						fooddata[i].cover = this.config.staticUrl + fooddata[i].cover;
						fooddata[i].price = fooddata[i].price.split(";")[0];
						for(let j=0;j<this.shops.length;j++){
							if(fooddata[i].shopname==this.shops[j].name){
								this.foods[this.shops[j].id].push(fooddata[i])
							}
						}
					}
					
				}catch(e){
					console.log(e)
					uni.showToast({
						title: "网络错误",
						icon: 'none',
						duration: 1000
					});
				}
			}
		}
	}
</script>

<style>
button{
	/* background-color: #8cdbed;
	color: #FFFFFF; */
}
</style>
