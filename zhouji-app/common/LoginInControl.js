export default {
	// 用户token
	token: "12321321",
	// 用户信息
	userinfo: false,
	// 验证加跳转函数
	JudgeNavigate(toWhere, type){
		// 权限验证
		if (!this.token){
			// 验证不通过重定向到登录页
			uni.navigateTo({
				url:"/pages/index/index.vue"
			});
		}else{
			// 跳转
			switch (type){
				case "navigateTo":
				uni.navigateTo(toWhere);
					break;
				case "redirectTo":
				uni.redirectTo(toWhere);
					break;
				case "reLaunch":
				uni.reLaunch(toWhere);
					break;
				case "switchTab":
				uni.switchTab(toWhere);
					break;
			}
		}	
	}
}