<template>
	<view>
		<view :style="{ height: statusBarHeight }" class="uni-status-bar"></view>
		<view class="mainBox">
			<view class="left icon iconfont icon-arrowLeft topicon" @tap="back"></view>
			<view class="middle" @tap="change">{{ mood }}</view>
			<view class="right" @tap="ok">创建</view>
		</view>
	</view>
</template>

<script>
	var statusBarHeight = uni.getSystemInfoSync().statusBarHeight + 'px';
	let moodList = ["景点模块", "周边模块", "自定义模块"];
	export default {
		name: 'UniStatusBar',
		data() {
			return {
				statusBarHeight: statusBarHeight,
				mood: "请选择模块类型"
			}
		},
		methods:{
			back(){
				uni.navigateBack({
					delta: 1
				})
			},
			change(){
				uni.showActionSheet({
					itemList: moodList,
					success: (res) => {
						this.mood = moodList[res.tapIndex];
					}
				});
			},
			ok(){
				this.$emit('ok', this.mood);
			}
		}
	}
</script>

<style scoped>
.uni-status-bar {
	height: 20px;
}
.mainBox{
	display: flex;
	justify-content: space-between;
}
.left,.right,.middle{
	display: flex;
	align-items: center;
	justify-content: center;
}
.left{
	width: 120upx;
	font-size: 50upx;
}
.middle{
	font-size: 40upx;
}
.right{
	width: 120upx;
	font-size: 40upx;
}
</style>
