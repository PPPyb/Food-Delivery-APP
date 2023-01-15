<template>
<view>
	<view class="blue_list_items" style="height:80px;display: flex;padding: 10px;" @tap="gotodetail">
		<view style="width: 80px;height: 80px;border-radius: 10px;overflow: hidden;">
			<image :src="goods.item_cover" style="width: 100%;height: 100%;" mode="aspectFill"></image>
		</view>
		<view style="flex: 1;padding-left: 10px;">
			<view class="list-middle-text" style="font-size: 30upx; font-weight: bold;">
				{{goods.item_name}}
			</view>
			<view style="display: flex;justify-content: space-between;">
				<view class="list-middle-text">{{goods.item_color}}</view>
				<view>￥{{goods.item_price}}.00 X{{goods.item_num}}</view>
			</view>
		</view>
	</view>
</view>
</template>

<script>
	export default {
		name:"confirmorderitem",
		props:{
			goods: Object
		},
		data() {
			return {
				
			};
		},
		methods: {
			gotodetail(){
				uni.navigateTo({
					url: "../../pages/shoppingDetails/shoppingDetails?goodsid=" + this.goods.item_id
				})
			}
		}
	}
</script>

<style scoped>
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
