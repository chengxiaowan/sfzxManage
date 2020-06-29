package com.yocto.util;

import java.util.HashMap;
import java.util.Map;

import cn.jiguang.commom.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.yocto.service.system.sysProperties.ISysPropertiesService;
import com.yocto.util.context.SpringContextUtils;

/**
 * 说明：极光推送工具类
 * 
 * @version
 */
public class JPushClientUtil {

	/**
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒)。
	 */
	private static long timeToLive = 60 * 60 * 24;

	public static final int MAX = Integer.MAX_VALUE;

	public static final int MIN = (int) MAX / 2;

	/**
	 * 
	 * @param title
	 *            标题
	 * @param alert
	 *            内容
	 * @param extra
	 * @param apnsProduction
	 * @return
	 */
	public static PushResult sendPushAll(String title, String alert, Map<String, String> extra, boolean apnsProduction) {
		PushResult result = null;
		try {
			PushPayload payload = PushPayload
					.newBuilder()
					.setPlatform(Platform.android_ios())
					// 设置接受的平台
					.setAudience(Audience.all())
					// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
					.setNotification(
							Notification.newBuilder().addPlatformNotification(AndroidNotification.newBuilder().setAlert(alert).setTitle(title).addExtras(extra).build())
									.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).setBadge(0).setSound("").setAlert(alert).addExtras(extra).build()).build())
					.setOptions(Options.newBuilder().setApnsProduction(true).setTimeToLive(timeToLive).build()).build();
			System.out.println("sendPushAll.pushNotifiton：" + payload.toString());

			ISysPropertiesService sysPropertiesService = SpringContextUtils.getBean("sysPropertiesService", ISysPropertiesService.class);
			if (null != sysPropertiesService) {
				String appKey = sysPropertiesService.getSysPropertiesByKey("jg.appKey");
				String masterSecret = sysPropertiesService.getSysPropertiesByKey("jg.masterSecret");
				if (TextUtil.isNotNull(appKey) && TextUtil.isNotNull(masterSecret)) {
					ClientConfig config = ClientConfig.getInstance();
					config.setPushHostName("https://api.jpush.cn");
					config.setTimeToLive(timeToLive); // one day
					JPushClient jpush = new JPushClient(masterSecret, appKey, null, config); // JPush client
					result = jpush.sendPush(payload);
				}
			}
		} catch (APIConnectionException e) {
			System.out.println("Connection error. Should retry later. " + e.getMessage());
			return null;
		} catch (APIRequestException e) {
			System.out.println("Error response from JPush server. Should review and fix it. " + e.getMessage());
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * 向android和ios客户端发送推送消息
	 * 
	 * @param registrationId
	 *            设备标识。一次推送最多 1000 个。
	 * @param title
	 *            通知标题
	 * @param alert
	 *            通知内容
	 * @param extra
	 *            扩展字段,这里自定义 JSON 格式的 Key/Value 信息，以供业务使用
	 * @param apnsProduction
	 *            True 表示推送生产环境，False 表示要推送开发环境
	 * @return
	 */
	public static PushResult sendPush(String registrationId, String title, String alert, Map<String, String> extra, boolean apnsProduction) {
		PushResult result = null;
		try {
			PushPayload payload = PushPayload
					.newBuilder()
					.setPlatform(Platform.android_ios())
					.setAudience(Audience.registrationId(registrationId))
					.setNotification(
							Notification.newBuilder().addPlatformNotification(AndroidNotification.newBuilder().setAlert(alert).setTitle(title).addExtras(extra).build())
									.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).setBadge(0).setSound("").setAlert(alert).addExtras(extra).build()).build())
					.setOptions(Options.newBuilder().setApnsProduction(true).setTimeToLive(timeToLive).build()).build();
			System.out.println("sendPush.pushNotifiton：" + payload.toString());

			ISysPropertiesService sysPropertiesService = SpringContextUtils.getBean("sysPropertiesService", ISysPropertiesService.class);
			if (null != sysPropertiesService) {
				String appKey = sysPropertiesService.getSysPropertiesByKey("jg.appKey");
				String masterSecret = sysPropertiesService.getSysPropertiesByKey("jg.masterSecret");
				if (TextUtil.isNotNull(appKey) && TextUtil.isNotNull(masterSecret)) {
					ClientConfig config = ClientConfig.getInstance();
					config.setPushHostName("https://api.jpush.cn");
					config.setTimeToLive(timeToLive); // one day
					JPushClient jpush = new JPushClient(masterSecret, appKey, null, config); // JPush client
					result = jpush.sendPush(payload);
				}
			}
		} catch (APIConnectionException e) {
			System.out.println("Connection error. Should retry later. " + e.getMessage());
			return null;
		} catch (APIRequestException e) {
			System.out.println("Error response from JPush server. Should review and fix it. " + e.getMessage());
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			String registrationId = "1517bfd3f7c6acec368";// 设备标识。一次推送最多 1000 个。
			String title = "推送测试标题 - 标题";
			String content = "看到信息了么，看到就推送成功了！";
			Map<String, String> extra = new HashMap<String, String>();// 这里自定义 JSON 格式的 Key/Value 信息，以供业务使用。
			extra.put("flag", "1");
			extra.put("url", "http://www.baidu.com");
			System.out.println("Got result - " + sendPush(registrationId, title, content, extra, false));
			System.out.println("Got result - " + sendPushAll(title, content, extra, false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 处理超过1000的数据(异步推送)
	// registrationId的格式如:"a,b,c,d,e,"
	public static void ansySendPush(final String registrationId, final String title, final String content, final Map<String, String> extra) {
		new Thread(new Runnable() {
			String registrationId_ = registrationId.substring(0, registrationId.length() - 1);

			@Override
			public void run() {
				try {
					String[] registrationIds = registrationId_.split(",");
					if (registrationIds.length > 1000) {
						String registrationId1 = "";
						for (int i = 0; i < registrationIds.length; i++) {
							if (i % 1000 == 0 && 0 != i) {
								registrationId1 = registrationId1.substring(0, registrationId1.length() - 1);
								JPushClientUtil.sendPush(registrationId1, title, content, extra, false);
								registrationId1 = "";
							}
							registrationId1 = registrationId1 + registrationIds[i] + ",";
						}
						if (TextUtil.isNotNull(registrationId1)) {
							registrationId1 = registrationId1.substring(0, registrationId1.length() - 1);
							JPushClientUtil.sendPush(registrationId1, title, content, extra, false);
						}
					} else {
						System.out.println("===");
						System.out.println("Got result - " + JPushClientUtil.sendPush(registrationId_, title, content, extra, false));
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Thread.currentThread().interrupt();
				}
			}
		}).start();

	}

	/**
	 * 保持 sendNo 的唯一性是有必要的
	 */
	public static int getRandomSendNo() {
		return (int) (MIN + Math.random() * (MAX - MIN));
	}
}
