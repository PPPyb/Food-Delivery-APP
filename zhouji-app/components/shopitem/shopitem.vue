<template>
<view>
	<view class="blue_list_items" style="height:80px;display: flex;padding: 10px;" @tap="gotodetail">
		<view style="width: 80px;height: 80px;border-radius: 10px;overflow: hidden;">
			<image :src="goods.cover" style="width: 100%;height: 100%;" mode="aspectFill"></image>
		</view>
		<view style="flex: 1;padding-left: 10px;height: 80px; display: flex;flex-direction: column;justify-content: space-between;">
			<view class="list-middle-text" style="font-size: 30upx; font-weight: bold;">
				{{goods.goodsname}}
			</view>
			<view>{{goods.area}} - {{goods.shopname}}</view>
			<view style="display: flex;justify-content: space-between;">
				<view>{{goods.type}}</view>
				<view>￥{{goods.price}}.00</view>
			</view>
		</view>
	</view>
</view>
</template>

<script>
	export default {
		name:"shopitem",
		props:{
			goods: Object
		},
		data() {
			return {
				
			};
		},
		methods: {
			gotodetail(){
				console.log(this.goods.goodsid)
				uni.navigateTo({
					url: "../../pages/shoppingDetails/shoppingDetails?goodsid=" + this.goods.goodsid
				})
			}
		}
	}
</script>

<style scoped>
	/* view{
		border: 1px solid black;
	} */
.blue_list_items{
	height: 5.5vh;
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 0 5px 0 10px;
}
.list-middle-text{
	
}
</style>