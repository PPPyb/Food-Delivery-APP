const CryptoJS = require('crypto-js');  //引用AES源码js
    
const key = CryptoJS.enc.Latin1.parse("U0CgGQFpJt2ZpGuR");  //十六位十六进制数作为密钥
const iv = CryptoJS.enc.Latin1.parse('gzAbpbVaSRA0zLZV');   //十六位十六进制数作为密钥偏移量

//解密方法
function Decrypt(word) {
	var decrypted = CryptoJS.AES.decrypt(atob(word), key, {
		iv: iv, 
		padding: CryptoJS.pad.ZeroPadding,
	});
	return decrypted.toString(CryptoJS.enc.Utf8);
}

//加密方法
function Encrypt(word) {
	// let srcs = CryptoJS.enc.Utf8.parse(word);
	let encrypted = CryptoJS.AES.encrypt(word, key, { 
		iv: iv, 
		mode: CryptoJS.mode.CBC, 
		padding: CryptoJS.pad.ZeroPadding
	});
	return btoa(encrypted.toString());
}

export default {
	Decrypt,
	Encrypt
}