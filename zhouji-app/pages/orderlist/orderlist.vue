<template>
<view class="tabs">
	<!-- top search bar -->
	<uni-nav-bar :fixed="false" color="#333333" background-color="#FFFFFF" left-icon="arrowleft" @clickLeft="goToShop">
		<view class="input-view">
			<uni-icons style="line-height: 30px;" type="search" size="22" color="#666666" />
			<input confirm-type="search" class="nav-bar-input" type="text" placeholder="Search for orders" @confirm="confirm" />
		</view>
	</uni-nav-bar>
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
				<ordercarditem :datainfo="item3" v-for="(item3, index3) in tab.list" :key="index3"></ordercarditem>
			</scroll-view>
		</swiper-item>
	</swiper>
<!-- page end -->
</view>
</template>
<script>
import ordercarditem from '../../components/ordercarditem/ordercarditem.vue'
export default {
	components:{
		ordercarditem: ordercarditem
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
			]
		}
	},
	async onLoad() {
		this.getorders();
	},
	methods: {
		confirm(){
			// 搜索
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
				// 查所有订单（订单id）
				let [err,res] = await this.$myRequest.post('/shop/orderlist/',
					JSON.stringify({token:uni.getStorageSync("token")})
				);
				
				// console.log(res)
				for(let i=0;i<res.data.length;i++){
					// 查所有订单详细信息（通过订单id）
					// console.log("id为"+res.data[i].orderid+"的订单信息");
					let [err2,res2] = await this.$myRequest.post('/order/getoneorder/',
						JSON.stringify({
							token:uni.getStorageSync("token"),
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
					title: "网络错误请重试",
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

