<template>
<view>
	<view class="NavBar canvasBox" v-show="!pcshow">
		<image :src="picClass" class="top-img-new"></image>
		<view class="topbar">
			<view>
				<view class="icon iconfont icon-arrowLeft topicon" @tap="back" v-if="ableBack"></view>
				<view class="icon iconfont icon-arrowLeft topicon" @tap="logout" v-if="ableLogout" style="display: flex;">
					<view style="display: flex; align-items: center; font-size: 35upx;">Logout</view>
				</view>
			</view>
			<view>
				<view class="icon iconfont icon-shezhi topicon" @tap="goToSetting" v-if="ableSet"></view>
			</view>
		</view>
		<canvas class="background-img" style="background-color: rgba(0,0,0,0);" :canvas-id="canvas_id"></canvas>
	</view>
	<view style="height: 200px; width: 100%; margin-bottom: 20px;" v-show="pcshow">
		<image src="../../static/pctop.png" style="height: 100%; width: 100%;"></image>
	</view>
</view>
</template>
<script>
	export default {
		name:"NavBar",
		props:{
			ableBack: Boolean,
			ableSet: Boolean,
			ableLogout: Boolean,
			pic: Number,
			canvas_id: String
		},
		data() {
			return {
				picClass: "../../static/topimg/Top1.jpg",
				pcshow: false
			}
		},
		mounted: function(){
			this.pcshow = uni.getStorageSync("pcshow");
			if(this.pic){
				// console.log(this.pic)
				let pic = uni.createSelectorQuery().in(this).select("#canvasBox")
				switch(this.pic){
					case 1: this.picClass = "../../static/topimg/Top1.jpg";break;
					case 2: this.picClass = "../../static/topimg/Top2.jpg";break;
					case 3: this.picClass = "../../static/topimg/Top3.jpg";break;
					case 4: this.picClass = "../../static/topimg/Top4.png";break;
				}
			}
			var width;
			var height;
			uni.getSystemInfo({
			    success: function (res) {
			        width = res.windowWidth;
			        height = 0.18 * res.windowHeight;
			    }
			});
			var ctx = uni.createCanvasContext(this.canvas_id, this); 
			var boHeight = height / 10;
			var posHeight = height / 1.4;
			var step = 0;
			var colors = [
				"rgba(255,255,255, 0.4)",
				"rgba(255,255,255, 0.3)",
				"rgba(255,255,255, 1)"];
			var timer1 = setInterval(function(){
				ctx.clearRect(0,0,width,height);
				step+=5; 
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
		methods: {
			back(){
				uni.navigateBack({
					delta: 1
				})
			},
			goToSetting(){
				uni.navigateTo({
					url:"../../pages/settings/settings"
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
								url:"../../pages/login/login"
							})
						}
					}
				})
			}
		}
	}
</script>
<style scoped>
.canvasBox{
	background-size: cover;
	position: relative;
	border-bottom: 10px;
	overflow: hidden;
}
.top-img-new{
	width: 100%;
	position: absolute;
	top: 0;
	z-index: -1;
}
.background-img{
	position: absolute;
	bottom: 0;
	width: 100%;
	height: 18vh;
}
.topicon{
	color: #FFFFFF;
	font-size: 50upx;
}
.topbar{
	min-height: 90upx;
	display: flex;
	justify-content: space-between;
	padding: 70upx 50upx 0 30upx;
}
.NavBar{
	width: 100%;
	height: 30vh; 
}
</style>