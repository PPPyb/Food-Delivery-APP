<template>
<view>
<!-- phone top -->
<navbar :ableBack="true" :ableSet="true" :pic="4" :canvas_id="'shopcanvas'"></navbar>
<!-- pc top -->
<view v-show="pcshow" style="padding: 0 10px 10px 10px;">
	<uni-search-bar placeholder="Search other items in this shop" @confirm="pc_search" @cancel="pc_search" cancel-text="Search"/>
</view>
<view style="padding: 0 20px;">
<!-- page begin -->
<view style="display: flex; align-items: center;">
<!-- info box on the left (item img for pc) -->
<view style="width: 300px; height: 300px; margin-right: 20px;" v-show="pcshow">
	<image src="/static/img/shopExample/shopExample3.png" style="width: 100%;height: 100%;"></image>
</view>
<!-- info box on the right -->
<view style="flex: 1;">
	<!-- price bar -->
	<view style="display: flex; justify-content: space-between;align-items: center;">
		<view style="display: flex; align-items: center;">
			<view class="blue_white_box now_price unix_center_box">￥{{price[choice_index]}}.00</view>
			<view class="origin_price">￥{{goodsinfo.history}}.00</view>
		</view>
		<view style="color: #9b9ba5; font-size: 15px;" class="unix_center_box">{{goodsinfo.area}}</view>
	</view>
	<view style="font-size: 25px; line-height: 30px;margin-top: 8px;">
		{{goodsinfo.goodsname}}
	</view>
	<view class="unix_right_box" style="color: #9b9b9b;font-size: 15px;">
		Storage volume {{goodsinfo.storage}}
	</view>
	<!--detail list-->
	<view style="border: #8cdbed 4upx solid; border-radius: 10px; margin-top: 10px;">
		<view style="border-bottom: #8cdbed 3upx solid;" class="blue_list_items">
			<view style="float: left;" class="icon iconfont icon-qianbao1 my-icon-left"></view>
			<view>
				<picker @change="choicePickerChange" :value="choice_index" :range="choice" range-key="name">
					<view style="display: flex; align-items: center;justify-content: space-between;">
						<view class="list-middle-text">Size: {{choice[choice_index]}}</view>
						<view class="icon iconfont icon-zuojiantou myicon-right"></view>
					</view>
				</picker>
			</view>
		</view>
		<view style="border-bottom: #8cdbed 3upx solid;" class="blue_list_items">
			<view style="float: left;" class="icon iconfont icon-qianbao1 my-icon-left"></view>
			<view>
				<picker @change="servicePickerChange" :value="service_index" :range="service" range-key="name">
					<view style="display: flex; align-items: center;justify-content: space-between;">
						<view class="list-middle-text">Distribution mode: {{service[service_index].name}}</view>
						<view class="icon iconfont icon-zuojiantou myicon-right"></view>
					</view>
				</picker>
			</view>
		</view>
		<view style="border-bottom: #8cdbed 3upx solid; display: flex; align-items: center; justify-content: space-between;" class="blue_list_items">
			<view style="display: flex; align-items: center;">
				<view class="icon iconfont icon-qianbao1 my-icon-left"></view>
				<view class="list-middle-text">Num:</view>
			</view>
			<uni-number-box v-model="pick_num" :min="1"/>
		</view>
	</view>
	<!-- detail bar -->
	<view style="display: flex;justify-content: space-between;align-items: center;">
		<view style="font-size: 18px;">Delivery: ￥{{delivery[service_index]}}.00</view>
		<view v-show="!pcshow" class="icon iconfont icon-qianbao1" style="font-size: 30px; color: #8cdbed;"></view>
		<view v-show="pcshow" class="bottom-bar-in" style="margin: 15px 0;height: 40px;border-radius: 20px;padding: 0 20px;">
			<view @tap="addtocart">Add to Cart</view>
			<view style="border: 1px solid #FFFFFF; height: 20px;margin: 0 10px;"></view>
			<view @tap="buyit">Buy it</view>
		</view>
	</view>
</view>
</view>
<!-- detail bar -->
<view style="font-size: 22px;">Details</view>
<!-- info box end -->
<!-- 详情模块模板 -->
<view style="padding-bottom: 100px;">
	<image mode="widthFix"
	style="width: 100%;"
	:src="infoimg">
	</image>
</view>
<!-- page end -->
</view>
<!-- 下方悬浮框 -->
<view v-show="!pcshow" class="bottom-bar">
	<view class="bottom-bar-in">
		<view @tap="addtocart">Add to Cart</view>
		<view style="border: 1px solid #FFFFFF; height: 20px;"></view>
		<view @tap="buyit">Shopping Cart</view>
	</view>
</view>
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
			goodsid: 5,
			pcshow: false,
			choice_index: 0,
			choice: [
				'Small','Tall','Grande','Venti'
			],
			price: [],
			service_index: 0,
			service: [
				{name:'Normal'},{name:'Fast'},{name:'Rocket speed'}
			],
			delivery: [0,20,100],
			pick_num: 1,
			goodsinfo: {},
			infoimg: "/static/img/943daa4b37a245dbb873161f6623dca5.png"
		}
	},
	onLoad: function(options) {
		this.pcshow = uni.getStorageSync('pcshow');
		console.log("展示id为"+options.goodsid+"的商品");
		if(options.goodsid)
			this.goodsid = options.goodsid;
		this.get_info_backend();
	},
	methods: {
		async get_info_backend(){
			try{
				let [err,res] = await this.$myRequest.get('/shop/itemDetails',{goodsid:this.goodsid});
				this.goodsinfo = res.data;
				console.log(this.goodsinfo);
				this.price = this.goodsinfo.price.split(";");
				this.choice = this.goodsinfo.type.split(";");
				this.infoimg = this.config.staticUrl + this.goodsinfo.goodsinfo
			}catch(e){
				uni.showToast({
					title: "网络错误请重试",
					icon: 'none',
					duration: 1000
				});
			}
		},
		pc_search(){
			
		},
		addtocart(){
			let cartitem = {
				item_id: this.goodsinfo.goodsid,
				item_name: this.goodsinfo.goodsname,
				item_price: this.price[this.choice_index],
				item_num: this.pick_num,
				item_cover: this.config.staticUrl + this.goodsinfo.cover,
				item_color: this.choice[this.choice_index],
				delivery: this.delivery[this.service_index],
				choose: true
			};
			if(uni.getStorageSync("cart")){
				// 购物车不为空
				let cart = uni.getStorageSync("cart");
				let same = false;
				if(cart.shop==this.goodsinfo.shopname){
					// 同一商店有没有相同商品
					for(let i=0;i<cart.items.length;i++){
						if(this.goodsinfo.goodsname==cart.items[i].item_name&&this.choice[this.choice_index]==cart.items[i].item_color){
							same = true;
							cart.items[i].item_num+=this.pick_num;
							break;
						}
					}
					if(!same){
						cart.items.push(cartitem);
					}
					uni.setStorageSync("cart",cart);
					uni.showToast({
						title: "Add Successfully!",
						icon: 'none',
						duration: 1000
					});
				}else{
					// 不同商店
					uni.showToast({
						title: "Please finish the order in another shop first!",
						icon: 'none',
						duration: 1000
					});
				}
			}else{
				uni.setStorageSync("cart",{
					shop: this.goodsinfo.shopname,
					items: [cartitem]
				})
				uni.showToast({
					title: "Add Successfully!",
					icon: 'none',
					duration: 1000
				});
			}
			console.log(uni.getStorageSync("cart"));
			// uni.navigateTo({
			// 	url:"../../pages/shoppingCart/shoppingCart"
			// })
		},
		buyit(){
			uni.navigateTo({
				url: "/pages/shoppingCart/shoppingCart"
			})
		},
		choicePickerChange(e) {
			this.choice_index = e.detail.value;
		},
		servicePickerChange(e) {
			this.service_index = e.detail.value;
		}
	}
}
</script>

<style>
.unix_center_box{
	display: flex;
	align-items: center;
	justify-content: center;
}
.unix_left_box{
	display: flex;
	align-items: center;
}
.unix_right_box{
	display: flex;
	align-items: center;
	justify-content: flex-end;
}
.blue_white_box{
	background-color: #8cdbed;
	width: fit-content;
	color: #FFFFFF;
}
.now_price{
	padding: 0 15px;
	height: 4.5vh;
	border-radius: 2.5vh;
	font-size: 22px;
}
.origin_price{
	padding-left: 10px;
	color: #9b9ba2;
	font-size: 22px;
	text-decoration: line-through;
}
.blue_list_items{
	height: 45px;
	padding: 0 5px 0 10px;
}
.myicon-right{
	color: #8cdbed;
	font-size: 25px;
	font-weight: bolder;
}
.list-middle-text{
	font-size: 15px;
}
.my-icon-left{
	color: #8cdbed;
	font-size: 25px;
	padding-right: 5px;
}
.bottom-bar{
	width: 100vw;
	height: 7vh;
	position: fixed;
	top: 90vh;
}
.bottom-bar-in{
	margin: 0 20px;
	height: 100%;
	border-radius: 3.5vh;
	background-color: #8cdbed;
	display: flex;
	justify-content: space-around;
	align-items: center;
}
.bottom-bar-in>view{
	color: #FFFFFF;
	font-size: 20px;
}
</style>

