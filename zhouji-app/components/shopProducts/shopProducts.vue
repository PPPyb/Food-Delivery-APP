<template>
	<view class="self-adapt" @tap="viewMore()">
		<view class="product-box">
			<view class="product-img img-left">
				<image :src="item.examples[0]" mode="aspectFill"></image>
			</view>
			<view class="img-rignt-box">
				<view class="product-img img-right">
					<image :src="item.examples[1]" mode="aspectFill"></image>
				</view>
				<view class="product-img img-right">
					<image :src="item.examples[2]" mode="aspectFill"></image>
				</view>
			</view>
		</view>
		<view style="display: flex;justify-content: space-between;">
			<view class="product-title">{{ item.provider }}</view>
			<view class="product-title text-bottom">View more...</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				provider: "店铺自营",
				examples: [
					"../../static/img/shopExample/shopExample1.png",
					"../../static/img/shopExample/shopExample2.png",
					"../../static/img/shopExample/shopExample3.png"
				]
			};
		},
		props:{
			/* item example
				{
					id: 1,
					provider: "店铺自营",
					examples: [
						"../../static/img/shopExample/shopExample1.png",
						"../../static/img/shopExample/shopExample2.png",
						"../../static/img/shopExample/shopExample3.png"
					]
				}
			*/
			item: Object
		},
		methods: {
			viewMore: function(){
				uni.navigateTo({
					url:"/pages/shopitempick/shopitempick?shopid="+this.item.id
				})
			}
		}
	}
</script>

<style>
.product-title{
	padding: 0 20upx;
	color: #000000;
	font-size: 30upx;
}
.text-bottom{
	text-align: right;
	color: #000000 !important;
}
.product-img{
	background-color: #2C405A;
	margin: 10upx;
	border-radius: 20upx;
	overflow: hidden;
	box-shadow: 0 8upx 26upx 1upx #c5c5c5;
}
.product-img>image{
	width: 100%;
	height: 100%;
}
.product-box{
	display: flex;
	padding: 0 10upx;
}
.img-left{
	flex: 1;
	height: 200px;
}
.img-rignt-box{
	flex: 2;
	height: 210px;
	display: flex;
	flex-direction: column;
}
.img-right{
	flex: 1;
}
</style>
