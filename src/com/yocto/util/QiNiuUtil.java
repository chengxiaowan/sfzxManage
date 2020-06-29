package com.yocto.util;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiNiuUtil {
	// 密钥配置
	private static Auth auth = Auth.create(Const.QN_ACCESS_KEY, Const.QN_SECRET_KEY);

	public static String getUpToken(String bucketName) {
		return auth.uploadToken(bucketName);
		// String pfops = Const.QN_PIC_STYLE1 + "!" + key;
		// return auth.uploadToken(Const.QN_BUCKETNAME, null, 3600, new StringMap().putNotEmpty("persistentOps", pfops), true);
	}

	/**
	 * 上传文件，使用默认策略，只需要设置上传的空间名就可以了
	 * 
	 * @param filePath
	 *            本地文件路径
	 * @param key
	 *            上传到七牛的该文件的标识符
	 * @return
	 * @throws IOException
	 */
	public static boolean upload(String filePath, String key, String bucketName) throws IOException {
		try {
			UploadManager uploadManager = new UploadManager();// 创建上传对象

			Response res = uploadManager.put(filePath, key, getUpToken(bucketName));// 调用put方法上传
			// 打印返回的信息
			System.out.println(res.isOK() + "," + res.url() + "," + res.bodyString());
			return res.isOK();
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
			return false;
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            七牛资源url
	 * @return
	 */
	public static String download(String url) {
		String downloadRUL = auth.privateDownloadUrl(url);// 调用privateDownloadUrl方法生成下载链接
		System.out.println("downloadRUL:" + downloadRUL);
		return downloadRUL;
	}
}
