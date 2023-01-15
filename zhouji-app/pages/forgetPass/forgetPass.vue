<template>
<view>
	<navbar :ableBack="true" :ableSet="true" :pic="3" :canvas_id="'aaa'"></navbar>
	<view class="main-box">
		<view class="input_tag">Please enter your registered email address:<label class="warning_lab">{{ email_warning }}</label></view>
		<view class="no_input">
			<input @focus="clearWarn" type="text" v-model="email" placeholder="Email"/>
		</view>
		<view class="input_tag">Email code:<label class="warning_lab">{{ code_warning }}</label></view>
		<view class="no_input" style="display: flex;border: 0;padding: 0;">
			<view class="no_input" style="flex: 1;">
				<input @focus="clearWarn" type="text" maxlength="6" v-model="code" placeholder="Email code"/>
			</view>
			<view style="width: 150px;height: 100%;margin-left: 20upx;border-radius: 30upx;font-size: 35upx;" class="no_But" @tap="judgeCode" :class="{no_But_disable:disable}">{{ but_counting }}</view>
		</view>
		<view class="input_tag">New Password:</view>
		<view class="no_input">
			<input @focus="clearWarn" type="password" v-model="pass" placeholder="Enter your new password"/>
		</view>
		<view class="input_tag">New Password again:</view>
		<view class="no_input">
			<input @focus="clearWarn" type="password" v-model="a_pass" placeholder="Enter your new password again"/>
		</view>
		<view class="err_massage">{{err_info}}</view>
		<view class="no_loginBut" @tap="judgeSignin">
			<view class="no_But">
				Change Password
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
				pass:"",
				a_pass:"",
				err_info:"",
				email: "",
				code: "",
				email_warning: "",
				code_warning: "",
				but_counting: "Get Code",
				disable: false,
				counter: 60
			}
		},
		methods: {
			async judgeSignin(){
				if(this.email===""||this.code===""||this.pass===""){
					this.err_info = "Information is not complete!";
				}else if(this.pass!==this.a_pass){
					this.pass = this.a_pass ="";
					this.err_info = "Two password are not the same!";
				}else if(this.code.length!==6){
					this.code_warning = "Verification code is incorrect!";
				}else{
					// 这里添加后端请求
					let [err,res] = await this.$myRequest.post('/user/findpass/',JSON.stringify({
							email: this.email,
							code: this.code,
							newpass: this.pass
						}));
					console.log(res)
					//------
					this.clearWarn();
					uni.showToast({
					    title: res.data.msg,
					    duration: 2000
					});
				}
			},
			clearWarn(){
				this.email_warning = this.code_warning = this.err_info = "";
			},
			async judgeCode(){
				if(this.disable){
					uni.showToast({
					    title: "Please try in "+String(this.counter)+" seconds",
						icon:'none',
					    duration: 1000
					});
				}else{
					let reg_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
					if(!reg_email.test(this.email)){
						this.email_warning = "Please input the right email address!";
					}else{
						// 后端请求
						this.disable = true;
						let that = this;
						that.counter = 60;
						that.but_counting = String(that.counter);
						let timer = setInterval(function(){
							that.counter--;
							that.but_counting = String(that.counter);
							if(that.counter===0){
								that.but_counting = "Get Code";
								that.disable = false;
								clearInterval(timer);//停止计时器
							}
						},1000);
						let [err,res] = await this.$myRequest.get('/user/signup',{email:this.email});
						//------
						console.log(res.data)
						uni.showToast({
						    title: res.data.msg,
						    duration: 2000
						});		
					}
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
	width: 400upx;
	height: 7vh;
	border-radius: 50upx;
	color: #FFFFFF;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 40upx;
}
.no_But_disable{
	background-color: #D3D3D3;
}
.warning_lab{
	margin-left: 20upx;
	color: red;
}
</style>
