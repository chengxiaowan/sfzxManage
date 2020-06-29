package com.yocto.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import com.yocto.controller.base.BaseController;
import com.yocto.service.system.sysProperties.ISysPropertiesService;
import com.yocto.util.TextUtil;
import com.yocto.util.context.SpringContextUtils;

/**
 * 邮件发送器
 * 
 * @author
 */
public class SimpleMailSender extends BaseController {
	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public boolean sendTextMail(MailSenderInfo mailInfo) throws Exception {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		logBefore(logger, "构造一个发送邮件的session");
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		mailMessage.setRecipient(Message.RecipientType.TO, to);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// 设置邮件消息的主要内容
		String mailContent = mailInfo.getContent();
		mailMessage.setText(mailContent);
		// 发送邮件
		Transport.send(mailMessage);
		logBefore(logger, "发送成功！");
		return true;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public boolean sendHtmlMail(MailSenderInfo mailInfo) throws Exception {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipient(Message.RecipientType.TO, to);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);

		// ============添加附件=======================
		String filePath = mailInfo.getPath();
		if (TextUtil.isNotNull(filePath)) {
			String[] filePathArr = filePath.split(",");
			if (null != filePathArr && filePathArr.length > 0) {
				for (int i = 0; i < filePathArr.length; i++) {
					// 创建一新的MimeBodyPart
					MimeBodyPart mdp = new MimeBodyPart();
					// 得到文件数据源
					FileDataSource fds = new FileDataSource(filePathArr[i]);
					// 得到附件本身并至入BodyPart
					mdp.setDataHandler(new DataHandler(fds));
					// BASE64Encoder enc = new BASE64Encoder();
					// 得到文件名同样至入BodyPart
					// String fileName = MimeUtility.encodeText(fds.getName());
					String fileName = MimeUtility.encodeText(mailInfo.getAttachFileNames()[i]);
					fileName = fileName.replaceAll("\r", "").replaceAll("\n", "");
					mdp.setFileName(fileName);
					// mdp.setFileName("=?GBK?B?"+enc.encode(fds.getName().getBytes())+"?=");
					mainPart.addBodyPart(mdp);
				}
			}
		}

		// 将MiniMultipart对象设置为邮件内容
		mailMessage.setContent(mainPart);
		// 发送邮件
		Transport.send(mailMessage);
		return true;
	}

	/*
	 * @title:标题
	 * @content:内容
	 * @type:类型,1:文本格式;2:html格式
	 * @tomail:接收的邮箱
	 */
	public boolean sendMail(String title, String content, String type, String toAddress, String[] attchNames, String path) throws Exception {
		ISysPropertiesService sysPropertiesService = SpringContextUtils.getBean("sysPropertiesService", ISysPropertiesService.class);
		if (null != sysPropertiesService) {
			String mailServerHost = sysPropertiesService.getSysPropertiesByKey("mailServerHostKey");
			String mailServerPort = sysPropertiesService.getSysPropertiesByKey("mailServerPortKey");
			String userName = sysPropertiesService.getSysPropertiesByKey("mailUserNameKey");
			String password = sysPropertiesService.getSysPropertiesByKey("mailPasswordKey");
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setMailServerHost(mailServerHost);
			mailInfo.setMailServerPort(mailServerPort);
			mailInfo.setValidate(true);
			mailInfo.setUserName(userName);
			mailInfo.setPassword(password);// 您的邮箱密码
			mailInfo.setFromAddress(userName);
			mailInfo.setToAddress(toAddress);
			mailInfo.setSubject(title);
			mailInfo.setContent(content);
			mailInfo.setAttachFileNames(attchNames);
			mailInfo.setPath(path);
			SimpleMailSender sms = new SimpleMailSender();
			if ("1".equals(type)) {
				return sms.sendTextMail(mailInfo);// 发送文体格式
			} else if ("2".equals(type)) {
				return sms.sendHtmlMail(mailInfo);// 发送html格式
			}
		}
		// 这个类主要来发送邮件

		return false;
	}

	/**
	 * @param SMTP
	 *            邮件服务器
	 * @param PORT
	 *            端口
	 * @param EMAIL
	 *            本邮箱账号
	 * @param PAW
	 *            本邮箱密码
	 * @param toEMAIL
	 *            对方箱账号
	 * @param TITLE
	 *            标题
	 * @param CONTENT
	 *            内容
	 * @param TYPE
	 *            1：文本格式;2：HTML格式
	 */
	public static void sendEmail(String SMTP, String PORT, String EMAIL, String PAW, String toEMAIL, String TITLE, String CONTENT, String TYPE) throws Exception {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(SMTP);
		mailInfo.setMailServerPort(PORT);
		mailInfo.setValidate(true);
		mailInfo.setUserName(EMAIL);
		mailInfo.setPassword(PAW);
		mailInfo.setFromAddress(EMAIL);
		mailInfo.setToAddress(toEMAIL);
		mailInfo.setSubject(TITLE);
		mailInfo.setContent(CONTENT);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		if ("1".equals(TYPE)) {
			sms.sendTextMail(mailInfo);
		} else {
			sms.sendHtmlMail(mailInfo);
		}

	}

	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("itfather@1b23.com");
		mailInfo.setPassword("tttt");// 您的邮箱密码
		mailInfo.setFromAddress("itfather@1b23.com");
		mailInfo.setToAddress("653589048@qq.com");
		mailInfo.setSubject("设置邮箱标题");
		mailInfo.setContent("设置邮箱内容");
		// 这个类主要来发送邮件
		// SimpleMailSender sms = new SimpleMailSender();
		// sms.sendTextMail(mailInfo);//发送文体格式
		// sms.sendHtmlMail(mailInfo);//发送html格式
	}

}
