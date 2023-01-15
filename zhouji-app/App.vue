<script>
	export default {
		onLaunch() {
			console.log('App Launch');
			// 判断网络状态
			this.lib.NetWork.On();
		},
		onShow() {
			// 手机端还是电脑端判断
			var windowWidth = 0;
			uni.getSystemInfo({
			    success: (res) => {
					windowWidth = res.windowWidth;
			    }
			});
			uni.setStorageSync('pcshow',(windowWidth>800));
			if(windowWidth>800){
				console.log("App show on pc");
			}else{
				console.log("App show on mobile");
			}
			// 检查服务器状态
			let list = this.config.urlList;
			for(let i=0;i<list.length;i++){
				 uni.request({
					url: list[i] + '/test/',
					data: {
						text: 'test'
					},
					success: (res) => {
						// console.log(res.data);
						if(res.data.info=='ok'){
							console.log(list[i] + " working well");
							this.$myRequest.config.baseUrl = list[i];
						}
					}
				});
			}
			// end check
		},
		onHide() {
			console.log('App Hide')
		}
	}
</script>
<style>	
	/* @import "uview-ui/theme.scss"; */
/* 	@import "uview-ui/index.scss"; */
	/* hello.js库 */
	@import './common/uni.css';
	/* 图标库 */
	@import './common/iconfont.css';
	/* 引入animate动画库 */
	@import './common/animate.min.css';
	/*每个页面公共css */
	*{
		/* font-family: Courier; */
		
	}
	/*pc隐藏tabbar*/
	.uni-app--showleftwindow + .uni-tabbar-bottom {
		display: none;
	}
</style>
