<template>
	<view>
		<view class="main" :style="'height:'+scrollH+'px'">
			<view class="empty">
				<image :src="src"></image>
			</view>
			<text class="text">{{text}}</text>
			<view class="loginbut" hover-class="animate__animated animate__pulse" @tap="goToOrder()">Go to Order</view>
		</view>
	</view>
</template>

<script>
	export default {
		props:{
			src:{
				type:String,
				default:'/static/img/empty.png'
			},
			text:{
				type:String,
				default:"You haven't order anything yet"
			},
			show:{
				type:Boolean,
				default:true
			}
		},	
		data() {
			return {
				scrollH:500,
			}
		},
		created() { //计算高度
			uni.getSystemInfo({
				success:res => {
					this.scrollH = res.windowHeight - uni.upx2px(82)
				}
			})
		},
		methods: {
			goToOrder(){
				uni.switchTab({
					url:"../../shop/shop"
				});
			}
		}
	}
</script>

<style lang="scss">
	.main{
		width: auto;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		.empty{
			height: 360rpx;
			width: 360rpx;
			background-color:#B2B2B2;
			border-radius: 100%;
			display: flex;
			align-items: center;
			justify-content: center;
			
			&>image{
				width: 220rpx;
				height: 220rpx;
			}
		}
		.text{
			margin-top: 50rpx;
			font-family: arial;
			font-size: 17px;
			font-weight: bold;
			margin-bottom: 5px;
			}
		.but{
			margin-top: 60rpx;
			background-color: #FF0000;
			color: #FFFFFF;
			height: 80rpx;
			width: 380rpx;
			line-height: 80rpx;
			text-align: center;
			border-radius: 50rpx;
		}
	}
	.loginbut{
		background-color: #689090;
		width: 400upx;
		height: 7vh;
		border-radius: 50upx;
		color: #FFFFFF;
		margin: 8px;
		display: flex;
		justify-content: center;
		align-items: center;
		font-size: 40upx;
	}
	
</style>
