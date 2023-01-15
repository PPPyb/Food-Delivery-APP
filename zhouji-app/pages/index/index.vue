<template>
	<view>
		<!-- 顶部图组件 -->
		<navbar :ableLogout="true" :ableSet="true" :pic="1" :canvas_id="'personalcanvas'"></navbar>
		<!-- 请在此处继续 -->
		<view class="info">
			<view class="pic">
				<image src="../../static/userExample.jpg" class="round_pic" alt="头像"></image>
			</view>
			<view>
				<view style="font-size: 50upx;">{{ user_name }}</view>
				<view class="vip">{{ user_type }}</view>
			</view>
		</view>
		<view class="pay">
			<view class="icon iconfont icon-qianbao1 myicon-top" @tap="showMoney"></view>
			<view class="record">{{ money_lab }}</view>
		</view>
		<view class="tips">
			<view class="lines" @click="changeinfo">
				<view class="my-icon">
					<image src="../../static/img/user/user_icon (7).jpg" mode=""></image>
				</view>
				<view class="words"><view>Edit information</view><view></view></view>
				<view class="icon iconfont icon-zuojiantou myicon-right"></view>
			</view>
			<view class="lines lines1" @tap="pushpay">
				<view class="my-icon">
					<image src="../../static/img/user/user_icon (1).jpg" mode=""></image>
				</view>
				<view class="words"><view>Pay</view><view></view></view>
				<view class="icon iconfont icon-zuojiantou myicon-right"></view>
			</view>
		</view>
		<view class="tips" >
			<view class="lines" @click="gotocart">
				<view class="my-icon">
					<image src="../../static/img/user/user_icon (2).jpg" mode=""></image>
				</view>
				<view class="words"><view>Shopping Cart</view><view></view></view>
				<view class="icon iconfont icon-zuojiantou myicon-right"></view>
			</view>
			<view class="lines" @click="gotoorders">
				<view class="my-icon">
					<image src="../../static/img/user/user_icon (3).jpg" mode=""></image>
				</view>
				<view class="words"><view>Purchase Orders</view><view></view></view>
				<view class="icon iconfont icon-zuojiantou myicon-right"></view>
			</view>
			<view class="lines lines1" @click="gotoorders">
				<view class="my-icon">
					<image src="../../static/img/user/user_icon (4).jpg" mode=""></image>
				</view>
				<view class="words"><view>All Orders</view><view></view></view>
				<view class="icon iconfont icon-zuojiantou myicon-right"></view>
			</view>
		</view>
		<!-- <view class="tips">
			<view class="lines">
				<view class="my-icon">
					<image src="../../static/img/user/user_icon (5).jpg" mode=""></image>
				</view>
				<view class="words"><view>收藏</view><view>Collevtion</view></view>
				<view class="icon iconfont icon-zuojiantou myicon-right"></view>
			</view>
			<view class="lines lines1">
				<view class="my-icon">
					<image src="../../static/img/user/user_icon (6).jpg" mode=""></image>
				</view>
				<view class="words"><view>浏览记录</view><view>History</view></view>
				<view class="icon iconfont icon-zuojiantou myicon-right"></view>
			</view>
		</view> -->
		<uni-popup ref="popup" type="dialog">
		    <uni-popup-dialog mode="input" placeholder="Enter the number here" title="Enter the recharge number" :before-close="true" @confirm="chongqian" @close="close"></uni-popup-dialog>
		</uni-popup>
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
			user_name: "",
			user_tel: "13022226666",
			user_email: "aaa@b.com",
			user_address1: "拉尼亚凯亚超星系团-室女座星系团-本星系群-银河系-猎户座旋臂-太阳系-地球",
			user_address2: "拉尼亚凯亚超星系团-室女座星系团-本星系群-银河系-猎户座旋臂-太阳系-火星",
			user_type: "VIP",
			money_lab: "Click to show money"
		}
	},
	onLoad() {
		
	},
	async onShow(){
		// 登陆验证token
		// if(true){
			// console.log(uni.getStorageSync('token'));
		if(uni.getStorageSync('token')){
			try{
				let req = {
					// token: "123"
					token: uni.getStorageSync('token')
				}
				let [err,res] = await this.$myRequest.post('/user/getinfo/',JSON.stringify(req));
				console.log(res)
				if(res.data.code===1000){
					//uni.setStorageSync("userinfo", this.user);
					console.log(res.data.user);
					this.user_name = res.data.user.username;
					this.user_tel = res.data.user.phonenum;
					this.user_email = res.data.user.email;
					
					let address = res.data.user.address.split(";");
					this.user_address1 = address[0];
					if(address[1]){
						this.user_address2 = address[1];
					}else{
						this.user_address2 = "";
					}
				}else{
					// uni.navigateTo({
					// 	url:"../login/login"
					// })
				}
			}catch(e){
				// uni.showToast({
				// 	title: "网络错误",
				// 	icon: 'none',
				// 	duration: 1000
				// });
			}
		}else{
			uni.navigateTo({
				url:"../login/login"
			})
		}
	},
	methods: {
		showMoney(){
			if(uni.getStorageSync("money")){
				this.money_lab = "￥" + uni.getStorageSync("money") + ".00";
			}else{
				this.money_lab = "￥0.00";
			}
		},
		changeinfo(){
			uni.navigateTo({
				url: "/pages/changeinfo/changeinfo"
			})
		},
		gotoorders(){
			uni.navigateTo({
				url: "/pages/orderlist/orderlist"
			})
		},
		gotocart(){
			uni.navigateTo({
				url: "/pages/shoppingCart/shoppingCart"
			})
		},
		close() {
			this.$refs.popup.close();
		},
		chongqian(value){
			let money = 0;
			if(uni.getStorageSync("money")){
				money =  parseInt(uni.getStorageSync("money"));
			}
			money += parseInt(value);
			uni.setStorageSync("money", money);
			this.showMoney();
			this.$refs.popup.close();
		},
		pushpay(){
			console.log("打开充钱窗口");
			this.$refs.popup.open();
		}
	}
}
</script>
<style>
/* view{
	border: 1px solid #000000;
} */
.info{
	margin: 0 auto;
	width: 80%;
	height: 10vh;
	display: flex;
	margin-bottom: 2vh;
	padding-left: 50upx;
}
.pic{
	overflow: hidden;
	height: 10vh;
	width: 10vh;
	margin-right: 40upx;
}
.round_pic{
	height: 100%;
	width: 100%;
	border-radius: 50%;
}
.vip{
	flex: 3;
	font-size: 40upx;
	line-height: 30upx;
	color: orange;
}
.ff{
	display: flex;
	flex: 2;
	justify-content: space-between;
	align-items: center;
}
.ff>view{
	width: 200upx;
	font-size: 34upx;
}
.pay{
	background-color: orange;
	height: 8vh;
	width: 80%;
	margin: 0 auto;
	justify-content: center;
	display: flex;
	margin-top: 2vh;
	border-radius: 20upx;
}
.record{
	height: 100%;
	display: flex;
	padding-left: 20upx;
	flex: 2;
	align-items: center;
	font-size: 40upx;
	color: white;
	line-height: 40upx;
}
.tips{
	border: 2upx solid orange;
	width: 80%;
	border-radius: 20upx;
	margin: 0 auto;
	margin-top: 2vh;
}
.lines{
	width: 100%;
	height: 4.5vh;
	border-bottom: 1upx solid orange;
	display: flex;
}
.lines1{
	border-bottom: 0!important;
}
.words{
	text-align: left;
	padding-left: 20upx;
	flex: 1;
	display: flex;
	align-items: center;
	justify-content: space-between;
}
.words>view{
	font-size: 32upx;
}
.my-icon{
	width: 4vh;
	height: 4vh;
	border-radius: 20upx;
	overflow: hidden;
	padding: 2upx 10upx;
	align-items: center;
	justify-content: center;
}
.my-icon>image{
	width: 100%;
	height: 100%;
}
.myicon-top{
	padding-left: 20upx;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 55upx;
	color: #FFFFFF;
}
.myicon-right{
	font-size: 40upx;
	color: orange;
	padding: 0 20upx;
	line-height: 50upx;
	display: flex;
	align-items: center;
}
</style>
