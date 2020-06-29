package com.yocto.interceptor.shiro;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;

import com.yocto.entity.system.User;
import com.yocto.service.system.user.UserManager;
import com.yocto.util.PageData;
import com.yocto.websockect.SystemWebSocketHandler;

/**
 * @author 2015-3-6
 */
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private SessionDAO sessionDAO;

	@Resource(name = "userService")
	private UserManager userService;

	@Resource(name = "systemWebSocketHandler")
	private SystemWebSocketHandler systemWebSocketHandler;

	/*
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken aToken) throws AuthenticationException {
		/*String username = (String) aToken.getPrincipal(); // 得到用户名
		String password = new String((char[]) aToken.getCredentials()); // 得到密码
		if (null != username && null != password) {
			return new SimpleAuthenticationInfo(username, password, getName());
		} else {
			return null;
		}*/
		try {
			UsernamePasswordToken token = (UsernamePasswordToken) aToken;
			String username = token.getUsername();
			PageData pd = new PageData();
			pd.put("USERNAME", username);
			// 判断用户名是否存在
			pd = userService.findByUsername(pd);
			User user = new User();
			user.setUSER_ID(pd.getString("USER_ID"));
			user.setUSERNAME(pd.getString("USERNAME"));
			user.setPASSWORD(pd.getString("PASSWORD"));
			user.setNAME(pd.getString("NAME"));
			user.setRIGHTS(pd.getString("RIGHTS"));
			user.setROLE_ID(pd.getString("ROLE_ID"));
			user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
			user.setIP(pd.getString("IP"));
			user.setSTATUS(pd.getString("STATUS"));
			user.setPHONE(pd.getString("PHONE"));
			user.setIsUrl(pd.get("isUrl").toString());
			// 声明一个user用来获取sessionDao中的user
			String userSession = null;

			// 获取在线的session
			Collection<Session> sessionCollection = sessionDAO.getActiveSessions();
			for (Session session : sessionCollection) {
				// session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY); 获取simpleAuthenticationInfo的第一个参数的值
				if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) != null) {
					// 根据session build出一个subject
					Subject subject = new Subject.Builder().session(session).buildSubject();
					// 拿到这个登陆的对象
					userSession = (String) subject.getPrincipal();
					// 判断她的code和我现在登陆的code是否一致 （code是我在数据库里面设置一个标识码 采用uuid 保证唯一性 这里你可以id）
					if (user.getUSERNAME().equals(userSession)) {
						// 两者一致的时候，设置这个session的失效时间 （0：立刻）
						systemWebSocketHandler.sendMessageToUser(user.getUSER_ID(), new TextMessage("exit"));
						session.setTimeout(0);
						subject.logout();
						break;
					}
				}
			}
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUSERNAME(), new String((char[]) aToken.getCredentials()), this.getName());
			return authenticationInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("========2");
		return null;
	}

}
