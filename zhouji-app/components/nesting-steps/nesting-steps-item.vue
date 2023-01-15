<template>
	<view class="nesting-steps">
		<view class="nesting-steps-content">
			<view class="nesting-steps-content-text-container">
				<view v-for="(item,index) in optionItem" :key="index" class="nesting-steps-content-text">
					<view class="nesting-steps-content-wrap">
						<view class="nesting-steps-content-container" v-if="optionItem.length > 1">
							<view class="nesting-steps-content-line-item">
								<view class="nesting-steps-content-circle" v-if="index > active-1" :style="{backgroundColor:deactiveColor}"></view>
								<view class="nesting-steps-content-circle" v-if="index < active-1" :style="{backgroundColor:activeColor}"></view>
								<view class="nesting-steps-content-check" v-if="index === active-1">
									<uni-icons :color="activeColor" type="circle-filled" size="14"></uni-icons>
								</view>
								<view class="nesting-steps-content-line nesting-steps-content-line--after"
								:style="{backgroundColor:index===optionItem.length-1?'transparent':index < active-1?activeColor:deactiveColor}"></view>
							</view>
						</view>

						
						<view style="display: flex;flex-direction: column;flex:1">
							<view style="display: flex; justify-content: space-between; padding-right: 20px;">
								<text class="nesting-steps-content-title">{{item.stepName}}</text>
								<text class="nesting-steps-content-desc">
									<text v-if="index < active-1" class="success-tip">已完成</text>
									<text v-if="index === active-1" class="now-tip">进行中</text>
								</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>

	/**
	 * Nesting-Steps-Item 嵌套步骤条-内层
	 * @property {Number} active 当前内层步骤
	 * @property {Number} stepActive 当前总层步骤
	 * @property {String} activeColor 激活状态的颜色
	 * @property {String} deactiveColor 未激活状态的颜色
	 * @property {Array} optionItem 数据源，格式为：[{stepName:'xxx',time:'xxx'},{stepName:'xxx',time:'xxx'}]
	 */

	export default {
		name: 'NestingStepsItem',
		props: {
			activeColor: {
				// 激活状态颜色
				type: String,
				default: '#2c8afb'
			},
			deactiveColor: {
				// 未激活状态颜色
				type: String,
				default: '#DDDDDD'
			},
			active: {
				// 当前内层步骤
				type: Number,
				default: 0
			},
			stepActive: {
				// 当前总层步骤
				type: Number,
				default: 0
			},
			optionItem: {
				type: Array,
				default () {
					return []
				}
			} // 数据
		},
		data() {
			return {}
		}
	}
</script>

<style lang="scss" scoped>
	.success-tip {color: #5ABB3C;}
	.now-tip {color: #2c8afb;}
	.nesting-steps {
		/* #ifndef APP-NVUE */
		display: flex;
		width: 100%;
		/* #endif */
		/* #ifdef APP-NVUE */
		flex: 1;
		/* #endif */
		flex-direction: column;
	}

	.nesting-steps-content {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row-reverse;
	}

	.nesting-steps-content-text-container {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		flex: 1;
		padding-top: 5px;
	}

	.nesting-steps-content-text {
		padding: 2px 0px;
		// border-bottom-style: solid;
		// border-bottom-width: 1px;
		// border-bottom-color: $uni-border-color;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
	}

	.nesting-steps-content-title {
		font-size: $uni-font-size-base;
		text-align: left;
		margin-bottom: 10rpx;
		line-height: 18px;
	}

	.nesting-steps-content-desc {
		font-size: $uni-font-size-sm;
		line-height: 18px;
	}
	
	.nesting-steps-content-wrap {
		display: flex;
	}

	.nesting-steps-content-container {
		/* #ifndef APP-NVUE */
		display: inline-flex;
		/* #endif */
		width: 30px;
		flex-direction: column;
	}

	.nesting-steps-content-line-item {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		flex: 1;
		align-items: center;
		justify-content: center;
	}

	.nesting-steps-content-line {
		width: 1px;
		background-color: $uni-text-color-grey;
	}

	.nesting-steps-content-line--after {
		flex: 1;
		transform: translate(0px, 1px);
	}

	.nesting-steps-content-line--before {
		height: 6px;
		transform: translate(0px, -1px);
	}

	.nesting-steps-content-circle {
		width: 7px;
		height: 7px;
		border-radius: 100px;
		background-color: $uni-text-color-grey;
		margin: 4px 0px 5px 0px;
	}

	.nesting-steps-content-check {
		height: 14px;
		line-height: 14px;
		margin: 2px 0px;
	}
</style>
