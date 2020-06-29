package com.yocto.websockect;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SystemWebSocketHandler implements WebSocketHandler {

	private static final ArrayList<WebSocketSession> users;
	static {
		users = new ArrayList<WebSocketSession>();
	}

	public SystemWebSocketHandler() {

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 一个客户端连接断开时关闭
		users.remove(session);
		if (session.isOpen()) {
			System.out.println("====sesison存在，session关闭===");
			session.close();
		}
		System.out.println("突然断电1");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		users.add(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		TextMessage tm = new TextMessage(message.getPayload().toString());
		session.sendMessage(tm);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
		// 消息传输出错时调用
		users.remove(session);
	}

	/*public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : users) {
			if (user.isOpen()) {
				try {
					user.sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}*/

	public void sendMessageToUser(String userId, TextMessage message) {
		/*for (WebSocketSession user : users) {
			if (user.getAttributes().get("userId").equals(userId)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);
					}
				} catch (IOException e) {
				}
				break;
			}
		}*/
		int j = 0;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getAttributes().get("userId").equals(userId)) {
				j = i;
			}
		}
		if (j < users.size() && null != users.get(j)) {
			if (users.get(j).isOpen()) {
				try {
					users.get(j).sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
