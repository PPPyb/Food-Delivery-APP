import config from "./config.js";
export default{
	config:{
		urlList: config.urlList,
		baseUrl: config.webUrl,
		data: {},
		method: "GET",
		dataType: "json"
	},
	request(options = {}){
		console.log(this.config.baseUrl + ' is working!');
		if(uni.getStorageSync('token')){
			options.header = options.header || {
				// 'Content-Type':'application/json;charset=UTF-8',
				'Content-Type':'application/x-www-form-urlencoded',
				'Authorization': uni.getStorageSync('token')
			};
		}else{
			options.header = options.header || {
				// 'Content-Type':'application/json;charset=UTF-8',
				'Content-Type':'application/x-www-form-urlencoded',
				'Authorization': 'none'
			};
		}
		options.method = options.method || this.config.method;
		options.dataType = options.dataType || this.config.dataType;
		options.url = this.config.baseUrl+options.url;
		return uni.request(options);
	},
	get(url,data,options={}){
		options.url = url;
		options.data = data;
		options.method = 'GET';
		return this.request(options);
	},
	post(url,data,options={}){
		options.url = url;
		options.data = data;
		options.method = 'POST';
		return this.request(options);
	}
}