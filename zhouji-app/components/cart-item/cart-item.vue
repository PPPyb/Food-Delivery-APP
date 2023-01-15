<template>
<view>
<view class="cart_item_box">
	<view @tap="choose()" :class="[item.choose ? 'choose_activated' : 'choose_no' , 'choose_box']"></view>
	<!-- example image box -->
	<view style="width: 90px;height: 90px;border-radius: 10px;overflow: hidden;  margin-right: 10px;">
		<image :src="item.item_cover" style="height: 100%;width: 100%;" mode="aspectFill"></image>
	</view>
	<view style="display: flex;flex-direction: column;flex: 1;padding-right: 10px;">
		<view style="font-weight: bold;">{{ item.item_name }}</view>
		<view style="display: flex;justify-content: space-between;">
			<view style="color: #979797;">{{ item.item_color }}</view>
			<view style="">￥{{ item.item_price }}.00</view>
		</view>
		
		<view style="display: flex; justify-content: flex-end; margin-top: 10px;">
			<view class="choose_num_box">
				<view @tap="reduceNum()" class="unit_center_box" style="border-right: 1px solid #979797;width: 20px;">-</view>
				<view>{{ item.item_num }}</view>
				<view @tap="addNum()" class="unit_center_box" style="border-left: 1px solid #979797;width: 20px;">+</view>
			</view>
		</view>
	</view>
</view>
<!-- component end -->
</view>
</template>

<script>
	export default {
		name:"cart-item",
		props:{
			/* item example
				{
					item_id: "0100001",
					item_name: "Baozi Jiaozi",
					item_color: "Brown color",
					item_cover: "../../static/img/exampleForSocial.png",
					item_num: 1,
					choose: true
				}
			*/
			item: Object
		},
		data() {
			return {
				
			};
		},
		methods: {
			addNum(){
				this.item.item_num++;
			},
			reduceNum(){
				if(this.item.item_num!=1){
					this.item.item_num--;
				}
			},
			choose(){
				this.item.choose = !this.item.choose;
				this.$emit("update");
			}
		}
	}
</script>

<style scoped>
.choose_box{
	border-radius: 50%;
	margin: 0 10px 0 8px;
}
.choose_activated{
	width: 12px;
	height: 12px;
	border: 6px solid #8cdbed;
}
.choose_no{
	width: 22px;
	height: 22px;
	border: 1px solid #8cdbed;
}
.unit_center_box{
	display: flex;
	justify-content: center;
	align-items: center;
}
.cart_item_box{
	display: flex;
	align-items: center;
	height: 120px;
	width: 100%;
}
.choose_num_box{
	display: flex;
	justify-content: space-between;
	align-items: center; 
	width: 100px;
	border: 2px solid #979797; 
	border-radius: 8px;
}
.choose_num_box>view{
	font-size: 16px;
}
</style>
