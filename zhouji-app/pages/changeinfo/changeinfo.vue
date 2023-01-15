<template>
<view>
	<navbar :ableBack="true" :ableSet="true" :pic="3" :canvas_id="'signupscanvas'"></navbar>
	<view class="main-box">
		<view class="input_tag">Phone number:</view>
		<view class="no_input">
			<input @focus="clearWarn" type="text" v-model="tel" placeholder="Please input your phone number"/>
		</view>
		<view class="input_tag">Address:<label class="warning_lab">{{ address_warning }}</label></view>
		<view class="no_input">
			<input @focus="clearWarn" type="text" v-model="address1" placeholder="Please input your common address"/>
		</view>
		<view class="input_tag">Spare Address:</view>
		<view class="no_input">
			<input @focus="clearWarn" type="text" v-model="address2" placeholder="Please input your alternate address"/>
		</view>
		<view class="input_tag">Username:<label class="warning_lab">{{ name_warning }}</label></view>
		<view class="no_input">
			<input @focus="clearWarn" type="text" maxlength="6" v-model="name" placeholder="Please input your prefer name"/>
		</view>
		<view class="no_loginBut" @tap="changeInfo" style="margin: 20px 0;">
			<view class="no_But">
				Edit information
			</view>
		</view>
		<view class="input_tag">Old password:</view>
		<view class="no_input">
			<input @focus="clearWarn" type="password" v-model="pass" placeholder="Input the old password"/>
		</view>
		<view class="input_tag">New password:</view>
		<view class="no_input">
			<input @focus="clearWarn" type="password" v-model="a_pass" placeholder="Input the new password"/>
		</view>
		<view class="err_massage">{{err_info}}</view>
		<view class="no_loginBut" @tap="changePass" style="margin-bottom: 40px;">
			<view class="no_But">
				Change password
			</view>
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
				tel:"",
				address1: "",
				address2: "",
				name:"",
				pass:"",
				a_pass:"",
				err_info:"",
				address_warning: "",
				name_warning: ""
			}
		},
		async onShow(){
			try{
				let req = {
					token: uni.getStorageSync("token")
					// token: "1234"
				}
				let [err,res] = await this.$myRequest.post('/user/getinfo/',JSON.stringify(req));
				console.log(res)
				if(res.data.code===1000){
					this.tel = res.data.user.phonenum;
					this.name = res.data.user.username;
					let address = res.data.user.address.split(";");
					// console.log(address);
					this.address1 = address[0];
					if(address[1]){
						this.address2 = address[1];
					}else{
						this.address2 = "";
					}
				}else{
					uni.showToast({
						title: res.data.info,
						icon: 'none',
						duration: 1000
					});
				}
			}catch(e){
				// uni.showToast({
				// 	title: "网络错误",
				// 	icon: 'none',
				// 	duration: 1000
				// });
			}
		},
		methods: {
			async changeInfo(){
				let reg_tel = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
				if(this.tel===""){
					this.err_info = "Phone number is required!";
				}else if(this.name===""){
					this.err_info = "Username is required!";
				}else if(this.address1===""){
					this.err_info = "Input at least one address!";
				}else if(!reg_tel.test(this.tel)){
					this.tel = "";
					this.err_info = "Input the right phone number!";
				}else{
					this.clearWarn();
					// 这里添加后端请求
					// console.log(uni.getStorageSync("token"));
					let req = JSON.stringify({
						token: uni.getStorageSync("token"),
						tel: this.tel,
						address1: this.address1,
						address2: this.address2,
						name: this.name
					});
					let [err,res] = await this.$myRequest.post('/user/changeinfo/',req);
					//------
					try{
						uni.showToast({
							title: res.data.info,
							duration: 1000
						});
					}catch(e){
						uni.showToast({
							title: "Network Error, Please try later!",
							icon: 'none',
							duration: 1000
						});
					}	
				}
			},
			clearWarn(){
				this.email_warning = this.code_warning = this.err_info = "";
			},
			async changePass(){
				let [err,res] = await this.$myRequest.post('/user/changepass/',JSON.stringify({
						token: uni.getStorageSync("token"),
						oldpassword: this.pass,
						newpassword: this.a_pass
					}));
				//------
				console.log(res)
				try{
					uni.showToast({
						title: res.data.info,
						icon: 'none',
						duration: 3000
					});
				}catch(e){
					uni.showToast({
						title: "Network Error, Please try later!",
						icon: 'none',
						duration: 1000
					});
				}
			}
		}
	}
</script>

<style>
.main-box{
	padding: 0 20px;
	max-width: 800px;
}
.no_input{
	display: flex;
	align-items: center;
	justify-content: center;
	height: 5.5vh;
	border: 2px solid #bed2d1;
	border-radius: 10px;
	padding: 0 10px;
}
.input_tag{
	font-size: 30upx;
}
.no_input>input{
	width: 100%;
	height: 60upx;
}
.err_massage{
	color: red;
	height: 50upx;
	font-size: 30upx;
}
.no_loginBut{
	height: 100upx;
	display: flex;
	justify-content: center;
	padding-top: 10upx;
}
.no_But{
	background-color: #689090;
	width: 450upx;
	height: 7vh;
	border-radius: 50upx;
	color: #FFFFFF;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 40upx;
}
.warning_lab{
	margin-left: 20upx;
	color: red;
}
</style>
