<template>
<view class="outer">
	<view class="PicBarWithImg">
		<view class="top-icon icon iconfont icon-arrowLeft" style="color: rgba(255,255,255,0);"></view>
		<view class="middle-box">
			<view class="img-box">
				<image src="../../static/userExample.jpg"></image>
			</view>
			<view style="font-size: 50upx;">Traveller</view>
			<view>Following 3 | Follower 120</view>
		</view>
		<view class="top-icon icon iconfont icon-icon_fuben" @tap="goToWrite()"></view>
		
	</view>
	<view class="background-img">
		<canvas style="width: 100%; height: 100%;background-color: rgba(0,0,0,0);" canvas-id="firstCanvas" id="firstCanvas"></canvas>
	</view>
</view>
</template>
<script scoped>
	export default {
		name:"NavBar",
		props:{
			
		},
		data() {
			return {
				
			}
		},
		mounted: function(){
			var width;
			var height;
			uni.getSystemInfo({
			    success: function (res) {
			        width = res.windowWidth;
			        height = 0.2 * res.windowHeight;
			    }
			});
			var ctx = uni.createCanvasContext('firstCanvas', this); 
			
			var boHeight = height / 10;
			var posHeight = height / 1.4;
			var step = 0;
			var colors = [
				"rgba(255,255,255, 0.4)",
				"rgba(255,255,255, 0.3)",
				"rgba(255,255,255, 1)"];
			
			var timer1 = setInterval(function(){
				ctx.clearRect(0,0,width,height);
				step += 5; 
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
			goToWrite(){
				uni.navigateTo({
					url:"../../pages/write/write"
				})
			}
		}
	}
</script>
<style scoped>
.outer{
	background-image: url(../../static/topimg/Socialtop2.jpg);
	background-size: cover;
	background-repeat: no-repeat;
	height: 38vh;
}
.PicBarWithImg{
	width: 100%;
	display: flex;
	justify-content: space-between;
}
.PicBarWithImg>view{
	margin-top: 60upx;
}
.middle-box{
	margin-top: 100upx !important;
	display: flex;
	flex-direction: column;
	align-items: center;
}
.middle-box>view{
	font-size: 32upx;
	color: #FFFFFF;
}
.img-box{
	width: 180upx;
	height: 180upx;	
	border-radius: 50%;
	overflow: hidden;
}
/* view{
	border: 1px solid #000000;
} */
.img-box>image{
	width: 100%;
	height: 100%;
}
.top-icon{
	font-size: 80upx;
	color: #FFFFFF;
	padding: 0 20upx 0 20upx;
}
.background-img{
	top: 18vh;
	width: 100%;
	height: 21vh;
	position: absolute;
/* 	border: 1px solid black; */
}
</style>
