<template>
	<view>
		<view style="padding: 10px 20px;background-color: #FFFFFF;margin: 10px 10px;border-radius: 10px;">
			<view style="display: flex;justify-content: space-between;align-items: center;">
				<view style="font-size: 16px;font-weight: bold;color: #555;">{{datainfo.shopname}}</view>
				<view style="font-size: 14px;color: #666;">{{datainfo.state}}</view>
			</view>
			<view style="height: 2px;background-color: #f8f8f8; margin: 10px 0;"></view>
			<view style="display: flex;">
				<view style="width: 80px;height: 80px;border-radius: 10px;overflow: hidden;">
					<image :src="datainfo.cover" style="width: 100%;height: 100%;" mode="aspectFill"></image>
				</view>
				<view style="flex: 1;margin-left: 10px;">
					<view class="order-item">{{datainfo.goodsname}} ...</view>
					<view class="order-item">Time: {{datainfo.order_time}}</view>
				</view>
			</view>
			<view style="display: flex;justify-content: space-between;align-items: flex-end;">
				<view class="order-item" style="font-size: 20px;">Total: ￥{{datainfo.total_price}}.00</view>
				<button class="btn bg-yellow" style="margin: 0;" @click="check">Check</button>
			</view>
		</view>
	</view>
</template>

<script>
export default {
	name:"ordercarditem",
	props:{
		datainfo: Object
	},
	data() {
		return {
			
		};
	},methods:{
		check(){
			uni.navigateTo({
				url: "/pages/userordertrace/userordertrace?orderid=" + this.datainfo.orderid
			})
		}
	}
}
</script>

<style scoped>
/* 按钮 */
.btn{
	padding: 5px 14px;
	border: 1px solid #cdcdcd;
	font-size: 14px;
	color: #555;
	border-radius: 10px;
	transition: all 0.2s;
	background: #fff;
}

.bg-yellow{
	background: linear-gradient(100deg, #FFEB3B, #FFC107);
	border-color: #ffe635;
	font-weight: bold;
}
.bg-yellow:active{
	background: linear-gradient(100deg, #e8d536, #dda809);
}
.order-item{
	color: #787878;
}
</style>
