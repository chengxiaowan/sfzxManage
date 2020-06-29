package com.yocto.controller.business;

import net.sf.json.JSONObject;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yocto.controller.base.BaseController;
import com.yocto.thirdParty.qiniu.AuthException;
import com.yocto.thirdParty.qiniu.Base64Coder;
import com.yocto.thirdParty.qiniu.Mac;
import com.yocto.thirdParty.qiniu.PutPolicy;
import com.yocto.util.Const;
import com.yocto.util.PathUtil;
import com.yocto.util.QiNiuUtil;

@Controller
@RequestMapping(value = "/qiniu")
public class QiNiuController extends BaseController {
	/**
	 * 获取七牛上传token
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUpToken")
	@ResponseBody
	public String getUpToken() {
		String uptoken = null;
		try {
			JSONObject json = new JSONObject();
			json.put("token", QiNiuUtil.getUpToken(Const.QN_BUCKETNAME));
			// System.out.println(">>>>>>>>>" + json.toString());

			Mac mac = new Mac(Const.QN_ACCESS_KEY, Const.QN_SECRET_KEY);
			PutPolicy putPolicy = new PutPolicy(Const.QN_BUCKETNAME);
			// 可以根据自己需要设置过期时间,sdk默认有设置，具体看源码
			// putPolicy.expires = getDeadLine();
			putPolicy.returnUrl = PathUtil.PathAddress() + "/qiniu/qiNiuCallback";
			putPolicy.returnBody = "{\"name\": $(fname),\"size\": \"$(fsize)\",\"w\": \"$(imageInfo.width)\",\"h\": \"$(imageInfo.height)\",\"key\":$(etag)}";

			uptoken = putPolicy.token(mac);
		} catch (AuthException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return uptoken;
		// return json.toString();
	}

	/**
	 * 获取七牛上传token
	 * 
	 * @return
	 */
	@RequestMapping(value = "/qiNiuCallback")
	@ResponseBody
	public String qiNiuCallback() {
		JSONObject json = new JSONObject();
		try {
			String upload_ret = this.getRequest().getParameter("upload_ret");
			org.json.JSONObject callback = new org.json.JSONObject(Base64Coder.decode(upload_ret));
			if (callback.has("error")) {
				json.put("state", callback.get("error"));
			} else {
				json.put("original", callback.get("name"));
				// json.put("url", callback.get("key")+"-v001");
				json.put("url", callback.get("key"));
				json.put("state", "SUCCESS");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
