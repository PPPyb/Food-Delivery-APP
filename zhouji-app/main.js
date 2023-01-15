import Vue from 'vue'
import App from './App'

// 配置全局请求函数
import myRequest from './common/request.js'
Vue.prototype.$myRequest = myRequest
// 配置管理
import config from 'common/config.js'
Vue.prototype.config = config
// 网络状态判断
import lib from 'common/lib.js'
Vue.prototype.lib = lib
// 用户信息验证
import LoginInControl from 'common/LoginInControl.js'
Vue.prototype.LoginInControl = LoginInControl
// AES加解密
import aes from 'common/aes.js'
Vue.prototype.AES = aes
// 引入uview-ui组件
import uView from "uview-ui";
Vue.use(uView);

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
