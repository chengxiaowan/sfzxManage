package com.yocto.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载文件 创建人： 创建时间：2014年12月23日
 * 
 * @version
 */
public class FileDownload {

	/**
	 * @param response
	 * @param filePath
	 *            //文件完整路径(包括文件名和扩展名)
	 * @param fileName
	 *            //下载后看到的文件名
	 * @return 文件名
	 */
	public static void fileDownload(final HttpServletResponse response, HttpServletRequest request, String filePath, String fileName) throws Exception {
		if (TextUtil.isNotNull(filePath) && TextUtil.isNotNull(fileName)) {
			OutputStream outputStream = null;
			try {
				byte[] data = FileUtil.toByteArray2(filePath);
				// System.out.println("2:" + fileName);
				String userAgent = request.getHeader("User-Agent");
				if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				} else {
					// 非IE浏览器的处理：
					fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				}
				// System.out.println("3:" + fileName);
				response.reset();
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
				response.addHeader("Content-Length", "" + data.length);
				response.setContentType("application/octet-stream;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				outputStream = new BufferedOutputStream(response.getOutputStream());
				outputStream.write(data);
				outputStream.flush();
				outputStream.close();
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != outputStream) {
					outputStream.close();
					outputStream = null;
				}
			}
		}
	}

	/**
	 * 将下载文件名转化支持中文的字符串
	 * 
	 * @param String
	 * @return
	 */
	public static String toDownloadString(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
}
