<template>
<view>
<view style="box-shadow: darkgrey 3px 0px 13px 0px;height: 100vh;margin-right: 5px;position: fixed;min-width: 200px;width: 18vw;">
	<view style="display: flex;align-items: center;padding-left: 20px;min-width: 200px;margin-top: 10px;">
		<image style="width: 80px;height: 80px;" src="../../../static/logo.png"></image>
		<view style="font-size: 20px;">HOWAREYOU</view>
	</view>
	<view>
		<uni-collapse accordion>
			<uni-collapse-item title="User">
				<uni-list-item  title="Login" @click="login" clickable></uni-list-item>
				<uni-list-item  title="Signup for free" @click="signup" clickable></uni-list-item>
				<uni-list-item  title="Forget password" @click="forgetpass" clickable></uni-list-item>
				<uni-list-item  title="User information" @click="userinfo" clickable></uni-list-item>
				<uni-list-item  title="Change information" @click="changeinfo" clickable></uni-list-item>
				<uni-list-item  title="Logout" @click="logout" clickable></uni-list-item>
			</uni-collapse-item>
			<uni-collapse-item title="Shop">
				<uni-list-item  title="View All" @click="shopall" clickable></uni-list-item>
				<uni-list-item  :title="item.name" @click="shopitem(index)" clickable v-for="(item, index) in shops_info" :key="index"></uni-list-item>
			</uni-collapse-item>
			<uni-collapse-item title="Orders">
				<uni-list-item  title="Shopping Cart" @click="shoppingcart" clickable></uni-list-item>
				<uni-list-item  title="Check Orders" @click="orders" clickable></uni-list-item>
			</uni-collapse-item>
			<uni-collapse-item title="About">
				<uni-list-item  title="XingZhouRen" @click="about" clickable></uni-list-item>
				<uni-list-item  title="Join us" ></uni-list-item>
				<uni-list-item  title="Contribute" ></uni-list-item>
			</uni-collapse-item>
		</uni-collapse>
	</view>
</view>
</view>
</template>

<script>
	export default {
		data() {
			return {
				shops_info: [
					{id: 0,name:"ShuTan Sichuan cuisine",info:"Every day food, gourmet beauty carved."},
					{id: 1, name:"Gll Wonton",info:"Care for your heart and stomach, so that you eat more flavor."},
					{id: 2, name:"Peng sauce milk tea",info:"Every point, every point of happiness."},
					{id: 3, name:"Time Cake",info:"The day of the night! Midnight snack."},
					{id: 4, name:"Chinese Food",info:"Spend the same money, eat a better meal."},
					{id: 5, name:"Home of the World",info:"What you want, l know."}
				]
			}
		},
		methods: {
			login(){
				uni.navigateTo({
					url: "/pages/login/login"
				})
			},
			signup(){
				uni.navigateTo({
					url: "/pages/signup/signup"
				})
			},
			forgetpass(){
				uni.navigateTo({
					url: '/pages/forgetPass/forgetPass'
				})
			},
			userinfo(){
				uni.switchTab({
					url: "/pages/index/index"
				})
			},
			changeinfo(){
				uni.navigateTo({
					url: "/pages/changeinfo/changeinfo"
				})
			},
			logout(){
				uni.showModal({
					title: "Tip",
					content: "Are you sure to logout?",
					success: (e) => {
						if (e.confirm) {
							// 后端请求
							try{
								let [err,res] = this.$myRequest.get('/user/logout',{token: uni.getStorageSync("token")});
							}catch(e){
								console.log(e);
								console.log("logout fail");
							}
							uni.clearStorageSync();
							uni.navigateTo({
								url:"/pages/login/login"
							})
						}
					}
				})
			},
			shopall(){
				uni.switchTab({
					url: "/pages/shop/shop"
				})
			},
			shopitem(index){
				uni.navigateTo({
					url:"/pages/shopitempick/shopitempick?shopid="+index
				})
			},
			shoppingcart(){
				uni.navigateTo({
					url: "/pages/shoppingCart/shoppingCart"
				})
			},
			orders(){
				uni.navigateTo({
					url: "/pages/orderlist/orderlist"
				})
			},
			about(){
				uni.navigateTo({
					url: "/pages/settings/settings"
				})
			}
		}
	}
</script>

<style scoped>
.content {
	padding: 10px 20px;
	font-size: 15px;
	background-color: #f9f9f9;
	color: #666;
}
</style>
