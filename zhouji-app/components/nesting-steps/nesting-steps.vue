<template>
	<view v-if="options.length !== 0" class="nesting-steps">
		<text v-if="title!=''||null" class="nesting-steps-title">{{title}}</text>
		<view class="nesting-steps-content">
			<view class="nesting-steps-content-text-container">
				<view v-for="(item,index) in options" :key="index" class="nesting-steps-content-text">
					<view class="DISPLAY-FLEX">
						<view class="nesting-steps-content-container">
							<view class="nesting-steps-content-line-item">
								<view class="nesting-steps-content-check" v-if="index === active">
									<uni-icons :color="activeColor" type="circle-filled" size="14"></uni-icons>
								</view>
								<view :style="{backgroundColor:index<active?activeColor:deactiveColor}" class="nesting-steps-content-circle" v-else></view>
								<view class="nesting-steps-content-line nesting-steps-content-line--after"
								:style="{backgroundColor:index<active&&index!==options.length-1?activeColor:index===options.length-1?'transparent':deactiveColor}"></view>
							</view>
						</view>
						
						<view class="nesting-steps-content">
							<view class="nesting-steps-content-main">
								<text class="nesting-steps-content-main-title">{{item.stepName}}</text>
								<text class="nesting-steps-content-main-desc">{{item.time}}</text>
							</view>
							<!-- 二层嵌套 -->
							<Nesting-steps-item v-if="item.optionsItem" :optionItem="item.optionsItem.stepOptions" :active="item.optionsItem.active" :stepActive="active"></Nesting-steps-item>
						</view>
					</view>
				</view>
			</view>
			
		</view>
	</view>
</template>

<script>

	/**
	 * Nesting-Steps 嵌套步骤条
	 * @property {Number} active 当前步骤
	 * @property {String} activeColor 激活状态的颜色
	 * @property {String} deactiveColor 未激活状态的颜色
	 * @property {Array} options 数据源，格式为：[{stepName:'xxx',time:'xxx'},{stepName:'xxx',time:'xxx'}]
	 */

	import NestingStepsItem from "./nesting-steps-item.vue"

	export default {
		name: 'NestingSteps',
		components: {
			NestingStepsItem
		},
		props: {
			title: {
				type: String,
				default: ''
			},
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
				// 当前步骤列的下标
				type: Number,
				default: 0
			},
			options: {
				// 数据
				type: Array,
				default () {
					return []
				}
			}
		},
		data() {
			return {}
		}
	}
</script>

<style lang="scss" scoped>
	.DISPLAY-FLEX {
		display: flex;
	}
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
		background-color: white;
	}
	
	.nesting-steps-title {
		padding: 20px;
	}

	.nesting-steps-content {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row-reverse;
		margin-bottom: 5px;
	}

	.nesting-steps-content-text-container {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		flex: 1;
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
	
	.nesting-steps-content {
		display: flex;
		flex-direction: column;
		flex: 1;
	}
	
	.nesting-steps-content-main {
		display: flex;
		justify-content: space-between;
		padding-right: 20px;
	}

	.nesting-steps-content-main-title {
		font-size: $uni-font-size-base;
		line-height: 20px;
	}

	.nesting-steps-content-main-desc {
		font-size: $uni-font-size-sm;
		line-height: 20px;
	}

	.nesting-steps-content-container {
		/* #ifndef APP-NVUE */
		display: inline-flex;
		/* #endif */
		width: 40px;
		padding-left: 20px;
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
		border-radius: 100%;
		background-color: $uni-text-color-grey;
		margin: 5px 0px 5px 0px;
	}

	.nesting-steps-content-check {
		height: 14px;
		line-height: 14px;
		margin: 2px 0px;
	}
</style>
