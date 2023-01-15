<template>
	<view class="outerBox-s">
		<view class="user-info-s">
			<view class="img-pic-box">
				<image src="../../static/userExample.jpg" mode=""></image>
			</view>
			<view class="user-info-text">
				<text>Traveller.\n 2020.4.16 from GuangZahou</text>
			</view>
		</view>
		<view class="post-text-s">
			Dimsum in Guangzhou is sooooooo delicious!!
		</view>
		<view class="share-img">
			<image src="../../static/img/exampleForSocial.png" mode="aspectFill"></image>
		</view>
		<view class="bottom-but-s">
			<view class="icon iconfont icon-share">11</view>
			<view class="icon iconfont icon-icon_huifu-xian"><text> </text>10</view>
			<view class="icon-box">
				<view class="icon iconfont praise-icon" :class="starClass" @click="changeS()"></view>
				<text> {{starNums}}</text>
			</view>
			
			
		</view>
	</view>
</template>

<script scoped>
	export default {
		data() {
			return {
				star: false,
				starNums: 998,
				starClass:"icon-praise1"
			};
		},
		methods: {
			changeS: function(){
				if(!this.star){
					this.star = true;
					this.starNums++;
					var that = this;
					var num = 2;
					var timer1 = setInterval(function(){
						that.starClass = "red icon-praise"+num;
						num++;
						// console.log(num);
						if(num>9){
							clearInterval(timer1);
						}
					},30);
				}	
			}
		}
	}
</script>

<style scoped>
.outerBox-s{
	width: 90%;
	padding: 20upx 5%;
}
.user-info-s{
	display: flex;
}
.img-pic-box{
	width: 120upx;
	height: 120upx;
	border-radius: 50%;
	overflow: hidden;
}
.img-pic-box>image{
	width: 100%;
	height: 100%;
}
.user-info-text{
	padding-top: 20upx;
	padding-left: 20upx;
	font-size: 35upx;
	line-height: 45upx;
}
.post-text-s{
	font-size: 35upx;
	line-height: 45upx;
	padding: 10upx 0;
}
.share-img{
	height: 400upx;
	overflow: hidden;
}
.share-img>image{
	height: 100%;
}
.bottom-but-s{
	display: flex;
	justify-content: space-between;	
}
.bottom-but-s>view{
	font-size: 35upx;
}
.praise-icon{
	line-height: 50upx;
	font-size: 50upx;
}
.red{
	color: #c30d23;
}
.icon-box{
	display: flex;
}
.icon-box>text{
	font-size: 35upx;
}
</style>
