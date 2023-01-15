<template>
<view class="tabs">
	<!-- top changebutton -->
	<button style="width: 100%;" @click="open">{{shops[currentShop].name}}</button>
	<uni-popup ref="popup" type="left">
		<view style="width: 80vw;height: 100vh;background-color: #FFF;">
			<uni-list>
			    <uni-list-item :title="shop.name" :note="shop.info" clickable  @click="shopListClick(index)" v-for="(shop, index) in shops" :key="index"></uni-list-item>
			</uni-list>
		</view>
	</uni-popup>
	<!-- top scroll bar -->
	<scroll-view class="scroll-h" :scroll-x="true" :show-scrollbar="false" :scroll-into-view="scrollInto">
		<view v-for="(tab,index) in tabBars" :key="index" class="uni-tab-item" :id="tab.id" :data-current="index" @click="ontabtap">
			<text class="uni-tab-item-title" :class="tabIndex==index ? 'uni-tab-item-title-active' : ''">{{tab.name}}</text>
		</view>
	</scroll-view>
	<view class="line-h"></view>
	<!-- main swiper -->
	<swiper :current="tabIndex" style="flex: 1;" :duration="300" @change="ontabchange">
		<swiper-item v-for="(tab,index1) in tabBars" :key="index1">
			<!-- 每块的滚动列表 -->
			<scroll-view scroll-y style="height: 100%;background-color: #eeeeee;">
				<ordercarditem :datainfo="item3" v-for="(item3, index3) in tab.list" :key="index3" v-show="item3.shopname==shops[currentShop].name"></ordercarditem>
			</scroll-view>
		</swiper-item>
	</swiper>
	<!-- 弹出输入框 -->
	<uni-popup ref="popupaaa" type="dialog">
	    <uni-popup-dialog mode="input" placeholder="Password" title="Please input Admin password" :before-close="true" @close="close" @confirm="confirm"></uni-popup-dialog>
	</uni-popup>
<!-- page end -->
</view>
</template>
<script>
import ordercarditem2 from '../../components/ordercarditem2/ordercarditem2.vue'
export default {
	components:{
		ordercarditem: ordercarditem2
	},
	data() {
		return {
			tabIndex: 0,
			scrollInto: "",
			tabBars: [
				{name: 'All',id: 'All', list:[]},
				{name: 'Not Paid',id: 'NotPaid', list:[]},
				{name: 'Checkout success',id: 'Checkoutsuccess', list:[]},
				{name: 'Delivering',id: 'Delivering', list:[]},
				{name: 'Order complete',id: 'Ordercomplete', list:[]}
			],
			shops: [
				{id: 0, name:"ShuTan Sichuan cuisine",info:"Every day food, gourmet beauty carved."},
				{id: 1, name:"Gll Wonton",info:"Care for your heart and stomach, so that you eat more flavor."},
				{id: 2, name:"Peng sauce milk tea",info:"Every point, every point of happiness."},
				{id: 3, name:"Time Cake",info:"The day of the night! Midnight snack."},
				{id: 4, name:"Chinese Food",info:"Spend the same money, eat a better meal."},
				{id: 5, name:"Home of the World",info:"What you want, l know."}
			],
			currentShop: 0
		}
	},
	async onLoad() {
		
		this.getorders();
	},
	onReady() {
		// 管理员账号密码
		this.$refs.popupaaa.open();
		this.$refs.popup.open('left');
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
		close() {
			
		},
		confirm(value) {
			if(value=="pass"){
				this.$refs.popupaaa.close();
			}
		},
		goToShop(){
			uni.navigateBack()
		},
		ontabtap(e) {
			let index = e.target.dataset.current || e.currentTarget.dataset.current;
			this.switchTab(index);
		},
		ontabchange(e) {
			let index = e.target.current || e.detail.current;
			this.switchTab(index);
		},
		switchTab(index) {
			if (this.tabIndex === index) return;
			this.tabIndex = index;
			this.scrollInto = this.tabBars[index].id;
		},
		async getorders(){
			try{
				// 查所有订单
				let [err,res] = await this.$myRequest.post('/shop/allorder/',{});
				
				// console.log(res)
				for(let i=0;i<res.data.length;i++){
					// 查所有订单详细信息（通过订单id）
					// console.log("id为"+res.data[i].orderid+"的订单信息");
					let [err2,res2] = await this.$myRequest.post('/order/oneorder/',
						JSON.stringify({
							orderid: res.data[i].orderid
						})
					);
					
					let example = {
						shopname: res2.data.goods_collection[0].shopname,
						state: res2.data.state,
						cover: this.config.staticUrl + res2.data.goods_collection[0].cover,
						goodsname: res2.data.goods_collection[0].goodsname,
						order_time: res2.data.order_time,
						total_price: res2.data.total_price,
						orderid: res.data[i].orderid
					}
					this.tabBars[0].list.push(example);
					for(let k=1;k<this.tabBars.length;k++){
						if(this.tabBars[k].name == res2.data.state){
							this.tabBars[k].list.push(example);
						}
					}
				}
			}catch(e){
				console.log(e);
				uni.showToast({
					title: "Network Error, Please try again!",
					icon: 'none',
					duration: 1000
				});
			}
		}
	}
}
</script>

<style>
/* #ifndef APP-PLUS */
page {
	width: 100%;
	min-height: 100%;
	display: flex;
}
/* #endif */

/* 顶输入框 */
.input-view {
	display: flex;
	flex-direction: row;
	flex: 1;
	background-color: #f8f8f8;
	height: 30px;
	border-radius: 15px;
	padding: 0 15px;
	flex-wrap: nowrap;
	margin: 7px 0;
	line-height: 30px;
}
.nav-bar-input {
	height: 30px;
	line-height: 30px;
	width: 370rpx;
	padding: 0 5px;
	font-size: 14px;
	background-color: #f8f8f8;
}
/* top tab */
.tabs {
	flex: 1;
	display: flex;
	flex-direction: column;
	overflow: hidden;
	background-color: #ffffff;
	height: 100vh;
}

.scroll-h {
	width: 750rpx;
	width:100%;
	height: 100rpx;
	flex-direction: row;
	white-space: nowrap;
}

.line-h {
	height: 1rpx;
	background-color: #cccccc;
}

.uni-tab-item {
	display: inline-block;
	flex-wrap: nowrap;
	padding-left: 34rpx;
	padding-right: 34rpx;
}

.uni-tab-item-title {
	color: #555;
	font-size: 30rpx;
	height: 80rpx;
	line-height: 80rpx;
	flex-wrap: nowrap;

	white-space: nowrap;

}

.uni-tab-item-title-active {
	color: #f6d200;
}

</style>