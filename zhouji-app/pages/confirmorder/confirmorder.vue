<template>
<view>
<navbar :ableBack="true" :ableSet="true" :pic="2" :canvas_id="'shopcanvas'"></navbar>
<view style="padding: 0 20px 10vh 20px;">
<!-- page begin -->	
<view class="unit_center_box" style="font-size: 20px;">Confirm the order</view>
<!-- 彭，请从这里开始 -->
<!-- 上框 -->
<view class="cart_list_box">
	<view style="display: flex;justify-content: space-between;align-items: center;">
		<view style="display: flex;justify-content: center;align-items: center;">
			<uni-icons type="location-filled" size="60" color="#8cdbed"></uni-icons>
		</view>
		<view style="flex: 1;">
			<picker :value="address_index" :range="array" range-key="name" @change="addressPickerChange">
				<view style="display: flex;justify-content: space-between;align-items: center;">
					<view>
						<view class="list-middle-text" style="font-weight: bold;">{{data.username}}</view>
						<view class="list-middle-text">{{data.phonenum}}</view>
						<view class="list-middle-text">{{address}}</view>
					</view>
					<view class="icon iconfont icon-zuojiantou myicon-right"></view>
				</view>
			</picker>
		</view>
	</view>
</view>
<!-- 下框 -->
<view class="cart_list_box" style="padding-bottom: 18px;">
	<view style="display: flex;align-items: center;height: 80upx;">
		<uni-icons type="location-filled" size="30" color="#8cdbed"></uni-icons>
		<view class="list-middle-text">
			{{shop}}
		</view>
	</view>
	<!-- Shop item model -->
	<orderitem :goods="item" v-for="(item,index) in goods" :key="index"></orderitem>
	<!-- Shop item model -->
	<view style="padding: 10px 10px 5px 10px;">
		<view style="display: flex;justify-content:space-between;">
			<view class="list-middle-text">
				Courier fees:
			</view>
			<view class="list-middle-text">
				￥{{orderdata.courier_fee}}.00
			</view>
		</view>
		<view style="display: flex;justify-content:space-between;">
			<view class="list-middle-text">
				Total price:
			</view>	
			<view class="list-middle-text">
				￥{{orderdata.total_price}}.00
			</view>
		</view>
		<view class="list-middle-text" style="color: #007AFF;">
			Can not exchange it unless it is damaged
		</view>
		<view class="list-middle-text">
			Order notes:
		</view>
	</view>
	<view style="padding: 0 3px 0 8px;">
		<uni-easyinput v-model="orderdata.comment" placeholder="Please input your request"></uni-easyinput>
	</view>
</view>
<!-- page end -->
</view>
<!-- 下方悬浮框 -->
<view class="bottom-bar" @tap="buy">
	Buy it
</view>
<!-- 弹出输入框 -->
<uni-popup ref="popup" type="dialog">
    <uni-popup-dialog mode="input" placeholder="New address here" title="Input your new address" :before-close="true" @close="close" @confirm="confirm"></uni-popup-dialog>
</uni-popup>
</view>
</template>

<script>
import NavBar from "../../components/NavBarPro/NavBarPro.vue";
import confirmorderitem from "../../components/confirmorderitem/confirmorderitem.vue";
export default {
	components:{
		navbar: NavBar,
		orderitem: confirmorderitem
	},
	data() {
		return {
			address_index: 0,
			array: [
				{name:''},
				{name:''}, 
				{name:'Add new address'}
			],
			address: "",
			data: {
				username: "TTTT",
				phonenum: "13066668888",
				address1: "address1",
				address2: "address2"
			},
			orderdata: {
				courier_fee: "9",
				total_price: "1000",
				comment:""
			},
			goods:[
				{
					goodsid: 1,
					cover: "../../static/img/exampleForSocial.png",
					goodsname: "One Apple Two Apple Three Apple Four",
					goodsinfo: "Green",
					price: "100",
					goodsnum: 2,
					shopname: "NBC",
					area: "Beijing"
				}
			],
			shop: "Beijing"
		}
	},
	async onLoad() {
		// 获取user数据
		let req = {
			token: uni.getStorageSync('token')
		}
		let [err,res] = await this.$myRequest.post('/user/getinfo/',JSON.stringify(req));
		
		this.data.username = res.data.user.username;
		this.data.phonenum = res.data.user.phonenum;
		let address = res.data.user.address.split(";");
		this.data.address1 = address[0];
		if(address[1]){
			this.data.address2 = address[1];
		}else{
			this.data.address2 = "";
		}
		// 获取购物车数据
		console.log(uni.getStorageSync("cart"));
		this.goods = uni.getStorageSync("cart").items;
		this.shop = uni.getStorageSync("cart").shop;
		let courier_max = 0;
		let total_fee = 0;
		for(let i=0;i<this.goods.length;i++){
			if(this.goods[i].delivery>courier_max){
				courier_max = this.goods[i].delivery;
			}
			total_fee += parseInt(this.goods[i].item_price)*this.goods[i].item_num;
		}
		this.orderdata.courier_fee = courier_max;
		this.orderdata.total_price = total_fee;
		// 调整数据显示格式
		this.address = this.data.address1;
		this.array[0].name = this.data.address1;
		this.array[1].name = this.data.address2;
		this.data.phonenum = this.data.phonenum.slice(0,3)+"****"+this.data.phonenum.slice(-4);
	},
	methods: {
		addressPickerChange(e) {
			this.address_index = e.detail.value;
			if(this.address_index==2){
				this.$refs.popup.open();
			}else{
				this.address = this.array[this.address_index].name;
			}
		},
		close() {
			this.address_index = 0;
			this.address = this.array[0].name;
			this.$refs.popup.close();
		},
		confirm(value) {
			// 输入框的值
			console.log(value);
			if(value!=""){
				this.address = value;
			}
			this.$refs.popup.close();
		},
		async buy(){
			let req = {
				token: uni.getStorageSync('token'),
				total_price: this.orderdata.total_price,
				comment: this.orderdata.comment,
				address: this.address,
				collection:[]
			}
			for(let i=0;i<this.goods.length;i++){
				let item = {
					goodsid: this.goods[i].item_id,
					goodsinfo: this.goods[i].item_color + ";" + this.goods[i].item_price,
					goodsnum: this.goods[i].item_num
				}
				req.collection.push(item);
			}
			console.log(req);
			let [err,res] = await this.$myRequest.post('/order/add/',JSON.stringify(req));
			console.log(res);
			if(res.data.code==1000){
				uni.navigateTo({
					url: "/pages/orderlist/orderlist"
				});
				uni.removeStorageSync("cart");
			}else{
				uni.showToast({
				    title: "Network error, please try later!",
					icon: "none",
				    duration: 2000
				});
			}
			
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
.cart_list_box{
	height: fit-content;
	display: flex;
	border: 2px solid #8cdbed;
	border-radius: 25px;
	margin: 10px 0 20px 0;
	flex-direction: column;
	padding: 10px 10px;
}
.bottom-bar{
	width: 150px;
	height: 7vh;
	position: fixed;
	top: 90vh;
	right: 20px;
	
	border-radius: 3.5vh;
	background-color: #8cdbed;
	display: flex;
	justify-content: space-evenly;
	align-items: center;
	color: #FFFFFF;
	font-size: 20px;
}
.my-icon-left{
	color: #8cdbed;
	font-size: 25px;
	padding-right: 5px;
}
.myicon-right{
	color: #8cdbed;
	font-size: 25px;
	font-weight: bolder;
}
.blue_list_items{
	height: 5.5vh;
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 0 5px 0 10px;
}
</style>