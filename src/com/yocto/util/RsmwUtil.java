package com.yocto.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cc.rssoft.rsmw.sdk.RsmwServer;
import cc.rssoft.rsmw.sdk.command.response.GetMobileInfoResponse;
import cc.rssoft.rsmw.sdk.command.response.OriginateResponse;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RsmwUtil {

	private static String rsmwUrl = "http://localhost:18080/rsmw/api/2.0/";

	private static String apiSecret = "pA55w0rd";

	private static int connectTimeout = 2000;
	private static int readTimeout = 2000;

	private static RsmwServer rsmwSdk = new RsmwServer(rsmwUrl, apiSecret, connectTimeout, readTimeout);

	// 发起呼叫的方法返回callUuid
	public static String call(int absoluteTimeout, String landline, String srcGateway, String srcAccessNumber, String srcAnnounceMediaUrl, String dst, String dstGateway, String dstAccessNumber,
			String dstAnnounceMediaUrl) {
		String callUuid = "";
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<Map<Object, Object>>() {
			}.getType();
			if (landline.length() == 11&&!"0".equals(landline.substring(0,1))) {
				GetMobileInfoResponse getMobileInfoResponse = rsmwSdk.getMobileInfo(landline);
				String jsonString = getMobileInfoResponse.toJsonString();
				Map<Object, Object> map1 = gson.fromJson(jsonString, type);
				if (null != map1.get("city")) {
					if (!"上海".equals(map1.get("city").toString())) {
						landline = "0" + landline;
					}
				}
			}
			if (dst.length() == 11&&!"0".equals(dst.substring(0,1))) {
				GetMobileInfoResponse getMobileInfoResponse = rsmwSdk.getMobileInfo(dst);
				String jsonString = getMobileInfoResponse.toJsonString();
				Map<Object, Object> map1 = gson.fromJson(jsonString, type);
				if (null != map1.get("city")) {
					if (!"上海".equals(map1.get("city").toString())) {
						dst = "0" + dst;
					}
				}
			}
			OriginateResponse originateResponse = rsmwSdk.originate(absoluteTimeout, landline, srcGateway, srcAccessNumber, srcAnnounceMediaUrl, dst, dstGateway, dstAccessNumber, dstAnnounceMediaUrl);
			String jsonString = originateResponse.toJsonString();
			Map<Object, Object> map1 = gson.fromJson(jsonString, type);
			if (null != map1.get("callUuid")) {
				callUuid = map1.get("callUuid").toString();
			}
			System.out.println(map1.get("callUuid"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return callUuid;
	}

	public static void main(String[] args) {
		GetMobileInfoResponse getMobileInfoResponse = rsmwSdk.getMobileInfo("18706119437");
		String jsonString = getMobileInfoResponse.toJsonString();
		List<String> s = new ArrayList<String>();
		s.add(jsonString);
		s.add(jsonString);
		String string = s.toString();
		@SuppressWarnings("unchecked")
		ArrayList<Map<String, Object>> parseObject = JSON.parseObject(string, ArrayList.class);
		System.out.println(parseObject);
	}
}
