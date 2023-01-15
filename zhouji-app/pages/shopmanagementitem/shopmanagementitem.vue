<template>
<view>
<!-- page begin -->
<navbar :ableBack="true" :ableSet="true" :pic="2" :canvas_id="'shopcanvas'"></navbar>
<!-- main box -->
<view class="cart_list_box" style="padding-bottom: 18px;">
	<view style="display: flex;align-items: center;height: 80upx;">
		<uni-icons type="location-filled" size="30" color="#8cdbed"></uni-icons>
		<view class="list-middle-text">
			{{shoparea}} - {{shopname}}
		</view>
	</view>
	<!-- Shop item model -->
	<orderitem :goods="item" v-for="(item, index) in goods" :key="index"></orderitem>
	<!-- Shop item model -->
	
	<!-- order info -->
	<view style="padding: 10px 10px 5px 10px;">
		<view style="display: flex;justify-content:space-between;">
			<view class="list-middle-text">Total price:</view>
			<view class="list-middle-text">￥{{orderdata.total_price}}.00</view>	
		</view>
		<view style="display: flex;justify-content:space-between;">
			<view class="list-middle-text">Ordered Time:</view>
			<view class="list-middle-text">{{orderdata.order_time}}</view>	
		</view>
		<view style="display: flex;justify-content:space-between;">
			<view class="list-middle-text">Arrival Time:</view>
			<view class="list-middle-text">{{orderdata.arrive_time}}</view>	
		</view>
		<view class="list-middle-text">Address:</view>
		<view class="list-middle-text">{{orderdata.address}}</view>	
		<view class="list-middle-text" style="color: #007AFF;">
			Can not exchange it unless it is damaged
		</view>
		<view class="list-middle-text">
			Order notes: {{orderdata.comment}}
		</view>
	</view>
</view>
<!-- 步骤条 -->
<view style="margin: 0 10px 0 0;">
	<Nesting-Steps title="" :options="buzhou" :active="current_state"></Nesting-Steps>
</view>
<!-- 按钮们 -->
<view style="padding: 0 20px;">
	<button v-show="state_data_num==0" @click="buttonpush(0)">For Free</button>
	<button v-show="state_data_num==1" @click="buttonpush(1)">Send out</button>
	<button v-show="state_data_num==2" @click="buttonpush(2)">Order complete</button>
</view>
<!-- 占位块儿 --><view style="height: 10vh;"></view>
<uni-popup id="popupMessage" ref="popupMessage" type="message">
	<uni-popup-message :type="msgType" :message="message" :duration="2000"></uni-popup-message>
</uni-popup>
<!-- page end -->
</view>
</template>

<script>
import NavBar from "../../components/NavBarPro/NavBarPro.vue";
import orderitem from "../../components/orderitem/orderitem.vue";
import NestingSteps from "../../components/nesting-steps/nesting-steps.vue";
	export default {
		components:{
			NestingSteps: NestingSteps,
			navbar: NavBar,
			orderitem: orderitem
		},
		data() {
			return {
				buzhou: [
					{stepName:'Not Paid', time:''},
					{stepName:'Checkout success', time:'', optionsItem: {
						stepOptions: [
							{stepName:'Rider is arriving', time:''},
							{stepName:'Delivering', time:''},
						],
						active: 0
					}},
					{stepName:'Arrive at the destination', time:''},
					{stepName:'Order complete', time:''}
				],
				current_state: 0,
				state_data: [
					{name:"Not Paid",active:0,active_l:0},
					{name:"Checkout success",active:1,active_l:1},
					{name:"Delivering",active:1,active_l:2},
					{name:"Arrive at the destination",active:2,active_l:3},
					{name:"Order complete",active:3,active_l:3}
				],
				state_data_num: 0,
				shopname: "AAA",
				shoparea: "Beijing",
				orderdata: {
					courier_fee: "9",
					total_price: "1000",
					arrive_time: "3 hours",
					comment:"",
					orderid: 0
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
				msgType: 'error',
				message: '错误',
				// 测试用，删除
				orderid: 3
			}
		},
		async onLoad(options) {
			if(options.orderid){
				this.orderid = options.orderid;
			}
			try{
				let [err2,res2] = await this.$myRequest.post('/order/oneorder/',
					JSON.stringify({
						orderid: this.orderid
					})
				);
				this.orderdata = res2.data;
				this.goods = res2.data.goods_collection;
				this.shopname = this.goods[0].shopname;
				this.shoparea = this.goods[0].area;
				for(let i=0;i<this.goods.length;i++){
					this.goods[i].cover = this.config.staticUrl + this.goods[i].cover;
					let a = this.goods[i].goodsinfo.split(";");
					this.goods[i].price = this.goods[i].goodsinfo.split(";")[1];
					this.goods[i].goodsinfo = this.goods[i].goodsinfo.split(";")[0];
				}
				// 订单状态
				for(let i=0;i<this.state_data.length;i++){
					if(this.state_data[i].name==res2.data.state){
						this.state_data_num = i;break;
					}
				}
				console.log("当前订单状态: " + this.orderdata.state + this.state_data_num);
			}catch(e){
				console.log(e);
			}
			this.refresh_state();
		},
		methods: {
			refresh_state(){
				this.current_state = this.state_data[this.state_data_num].active;
				this.buzhou[1].optionsItem.active = this.state_data[this.state_data_num].active_l;
				if(this.orderdata.order_time){
					this.buzhou[1].time = this.orderdata.order_time;
				}else{
					this.buzhou[1].time = "";
				}
				if(this.orderdata.arrive_time){
					this.buzhou[2].time = this.orderdata.arrive_time;
					this.buzhou[3].time = this.orderdata.arrive_time;
				}else{
					this.buzhou[2].time = "";
					this.buzhou[3].time = "";
				}
				if(this.orderdata.deliveryid){
					this.buzhou[1].optionsItem.stepOptions[1].stepName = this.orderdata.deliveryid;
				}else{
					this.buzhou[1].optionsItem.stepOptions[1].stepName = "Delivering";
				}
			},
			buttonpush(index){
				if(index===0){
					// pay
					this.changeState("Checkout success");
					this.msgType = "success";
					this.message = "Payment Success, "+this.orderdata.total_price+" yuan was deducted";
					this.$refs.popupMessage.open();
					this.state_data_num = 1;
					this.refresh_state();
				}else if(index===1){
					this.changeState("Delivering");
					this.msgType = "success";
					this.message = "Call Success";
					this.$refs.popupMessage.open();
					this.state_data_num = 2;
					this.refresh_state();
				}else if(index===2){
					this.changeState("Order complete");
					this.msgType = "success";
					this.message = "Confirm Success";
					this.$refs.popupMessage.open();
					this.state_data_num = 4;
					this.refresh_state();
				}else{
					// 这种情况不会发生
					console.log("???");
				}
			},
			async changeState(state){
				let [err,res] = await this.$myRequest.post('/order/changestate/',
					JSON.stringify({
						token: uni.getStorageSync("token"),
						orderid: this.orderid,
						state: state
					})
				);
				console.log(res);
			}
		}
	}
</script>

<style>
.cart_list_box{
	height: fit-content;
	display: flex;
	border: 2px solid #8cdbed;
	border-radius: 25px;
	margin: 10px 20px 20px 20px;
	flex-direction: column;
	padding: 0 10px;
}
</style>
