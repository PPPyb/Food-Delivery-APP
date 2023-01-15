<template>
	<view>
		<view id="canvasBox" v-show="!pc">
			<image src="../../static/loginimg.jpg" class="top-img"></image>
			<canvas style="width: 100%; height: 40vh;background-color: rgba(255,255,255,0);" canvas-id="firstCanvas" id="firstCanvas"></canvas>
		</view>
		<view style="height: 180px; width: 100%; margin-bottom: 50px;" v-show="pc">
			<image src="../../static/pctop.png" style="height: 100%; width: 100%;"></image>
		</view>
		<view class="title-pic">
			<image src="/static/img/zhouji.png" mode="aspectFit"></image>
		</view>
		<view class="u-title">HOWAREYOU</view>
		<view class="input-box">
			<view class="my-input">
				<view class="icon iconfont icon-user myicon"></view>
				<input type="text" v-model="username" placeholder="Username" placeholder-class="my-placeholder"/>
			</view>
			<view class="my-input">
				<view class="icon iconfont icon-lock myicon"></view>
				<input type="password" v-model="password" placeholder="Password" placeholder-class="my-placeholder"/>
			</view>
		</view>
		<view class="other-log-in" v-if="false">
			<view class="icon iconfont icon-qq" @tap="otherLogIn()"></view>
			<view class="icon iconfont icon-weixin" @tap="otherLogIn()"></view>
			<view class="icon iconfont icon-weibo" @tap="otherLogIn()"></view>
			<view class="icon iconfont icon-zhifubao" @tap="otherLogIn()"></view>
		</view>
		<view class="loginbut" hover-class="animate__animated animate__pulse" @tap="dologin">Login</view>
		<view class="forget">
			<view @tap="forgetPass()">Forget password</view><view @tap="signIn">Sign up</view>
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
				username: '',
				password: '',
				pc: false
			}
		},
		onReady: function(){
			var width;
			var height;
			uni.getSystemInfo({
			    success: function (res) {
			        width = res.windowWidth;
			        height = 0.4 * res.windowHeight;
			    }
			});
			var ctx = uni.createCanvasContext('firstCanvas'); 
			
			var boHeight = height / 10;
			var posHeight = height / 1.2;
			var step = 0;
			var colors = [
				"rgba(255,255,255, 0.4)",
				"rgba(255,255,255, 0.3)",
				"rgba(255,255,255, 1)"];
			
			// var colors = [
			// 	"rgba(255,0,0, 0.4)",
			// 	"rgba(255,0,0, 0.3)",
			// 	"rgba(255,0,0, 1)"];
			var timer1 = setInterval(function(){
				ctx.clearRect(0,0,width,height);
				step += 3; 
				//画3个不同颜色的矩形 
				for(var j=2;j>=0;j--){
					var offset = -j*10;
					ctx.fillStyle = colors[j];
					var angle = (step+j*40)*Math.PI/180; 
					var deltaHeight = Math.sin(angle) * boHeight;
					var deltaHeightRight = Math.cos(angle) * boHeight; 
					ctx.beginPath();
					ctx.moveTo(0, posHeight+deltaHeight-offset)
					ctx.bezierCurveTo(width/2, posHeight+deltaHeight-boHeight-offset, width / 2, posHeight+deltaHeightRight-boHeight-offset, width, posHeight+deltaHeightRight-offset); 
					ctx.lineTo(width, height-offset); 
					ctx.lineTo(0, height-offset); 
					ctx.lineTo(0, posHeight+deltaHeight-offset); 
					ctx.closePath(); 
					ctx.fill();
				}
				ctx.draw();
			},50);
 
		},
		onLoad() {
			this.pc = uni.getStorageSync('pcshow');
		},
		methods: {
			async dologin(){
				// let a = this.AES.Encrypt("Jane");
				// console.log(a);
				// let b = this.AES.Decrypt('eGViRHZYUzZwSkNWeVJsNXNuWHE2dz09');
				// console.log(b);
				try{
					let aaa = {
						username: this.AES.Encrypt(this.username),
						password: this.AES.Encrypt(this.password)
					}
					let [err,res] = await this.$myRequest.post('/user/login',JSON.stringify(aaa));
					console.log(res.data);
					let err_message = res.data.info;
					if(res.data.code===1000){
						uni.setStorageSync('token', res.data.token);
						// console.log(this.$myStorage.getStorage('token'));
						uni.switchTab({
						    url: '/pages/index/index'
						});
					}else{
						uni.showToast({
						    title: err_message,
							icon: "none",
						    duration: 2000
						});
					}
				}catch(err){
					console.log(err);
					uni.showToast({
					    title: "Server Error, Please try later!",
						icon: "none",
					    duration: 2000
					});
				}
				
			},
			signIn(){
				uni.navigateTo({
					url:"../signup/signup"
				});
			},
			forgetPass(){
				uni.navigateTo({
					url:"../forgetPass/forgetPass"
				});
			},
			otherLogIn(){
				uni.showToast({
				    title: "暂不支持此登陆方式",
					icon: "none",
				    duration: 2000
				});
			}
		}
	}
</script>
<style>
.title-pic{
	width: 100%;
	height: 14vh;
	display: flex;
	justify-content: center;
	/*border: 1px solid black;*/
}
.title-pic>image{
	height: 14vh;
}
.u-title{
	display: flex;
	width: 100%;
	height: 8vh;
	justify-content: center;
	font-size: 50upx;
}
.input-box{
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
}
.my-input{
	width: 85%;
	max-width: 500px;
	height: 5.5vh;
	border: 4upx solid #bed2d1;
	border-radius: 20upx;
	margin-bottom: 3vh;
	display: flex;
}
.my-input>input{
	width: 80%;
	height: 100%;	
}
.my-placeholder{
	color: #bed2d1;
}
.myicon{
	/* width: 60upx; */
	font-size: 60upx;
	display: flex;
	align-items: center;
	padding: 0 20upx 0 10upx;
	color: #bed2d1;
}
.other-log-in{
	display: flex;
	width: 100%;
	height: fit-content;
	justify-content: center;
	margin-bottom: 2.2vh;
}
.other-log-in>view{
	width: 150upx;
	height: fit-content;
	font-size: 50upx;
	display: flex;
	justify-content: center;
	color: #689090;
}
.loginbut{
	background-color: #689090;
	width: 400upx;
	height: 7vh;
	border-radius: 50upx;
	color: #FFFFFF;
	margin: 0 auto;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 40upx;
}
.forget{
	margin-top: 20upx;
	display: flex;
	justify-content: center;
	font-size: 30upx;
}
.forget>view:first-of-type{
	margin-right: 20upx;
}
/* view{
	border: 1upx solid black;
	color: #689090;
} */


.info{
	display: flex;
	margin: 0 auto;
	border: white;
	width: 80%;
	justify-content: center;
	height: 10vh;
}
.pic{
	overflow: hidden;
	height: 10vh;
	width: 10vh;
	margin-right: 2vw;
}
.word{
	padding-top: 10upx;
	display: flex;
	flex-direction: column;
	height: 80%;
}
.round_pic{
	height: 100%;
	width: 100%;
	border-radius: 50%;
	
}
.id{
	font-size: 40upx;
	text-align: left;
	display: flex;
	flex: 3;
	width: 100%;
	align-items: center;
}
.vip{
	text-align: right;
	flex: 3;
	width: 100%;
	font-size: 30upx;
	line-height: 30upx;
	color: orange;
}
.ff{
	text-align: center;
	display: flex;
	flex: 2;
	width: 100%;
	align-items: center;
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
	flex: 2;
	align-items: center;
	text-align: left;
	color: white;
	line-height: 40upx;
}
.tips{
	border: 2upx solid orange;
	width: 80%;
	border-radius: 20upx;
	margin: 0 auto;
	margin-top: 3vh;
}
.lines{
	width: 100%;
	height: 4.5vh;
	border-bottom: 1upx solid orange;
	display: flex;
	align-items: center;
}
.lines1{
	border-bottom: 0!important;
}
.words{
	text-align: left;
	width: 70%;
	display: flex;
	align-items: center;
}
.my-icon{
	width: 4vh;
	height: 4vh;
	border-radius: 20upx;
	overflow: hidden;
	padding: 0upx 10upx;

	align-items: center;
	display: flex;
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
	padding-left: 50upx;
	line-height: 50upx;
	display: flex;
	align-items: center;
}
#canvasBox{
	width: 100%; 
	height: 40vh;
	position: relative;
}
.top-img{
	width: 100%;
	height: 100%;
	overflow: hidden;
}
#firstCanvas{
	position: absolute;
	bottom: 0;
}
</style>