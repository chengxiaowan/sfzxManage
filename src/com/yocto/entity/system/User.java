package com.yocto.entity.system;

import com.yocto.entity.Page;

/**
 * 
 * 类名称：用户 类描述：
 * 
 * @author 作者单位： 联系方式： 创建时间：2014年6月28日
 * @version 1.0
 */
public class User {
	private String USER_ID; // 用户id
	private String USERNAME; // 用户名
	private String PASSWORD; // 密码
	private String NAME; // 姓名
	private String RIGHTS; // 权限
	private String ROLE_ID; // 角色id
	private String LAST_LOGIN; // 最后登录时间
	private String IP; // 用户登录ip地址
	private String STATUS; // 状态
	private Role role; // 角色对象
	private Page page; // 分页对象
	private String SKIN; // 皮肤
	private String QQ;
	private String weChatId;
	private String pic;
	private String qnKey;
	private String originalPicName;
	private String PHONE;
	private String EMAIL;
	private String BZ;
	private String isQuit;
	private String quitTime;
	private String NUMBER;
	private String type;
	private String isUrl;// 是否允许访问外网url 0:是,1:否

	public String getSKIN() {
		return SKIN;
	}

	public void setSKIN(String sKIN) {
		SKIN = sKIN;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getRIGHTS() {
		return RIGHTS;
	}

	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}

	public String getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}

	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getQnKey() {
		return qnKey;
	}

	public void setQnKey(String qnKey) {
		this.qnKey = qnKey;
	}

	public String getOriginalPicName() {
		return originalPicName;
	}

	public void setOriginalPicName(String originalPicName) {
		this.originalPicName = originalPicName;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
	}

	public String getNUMBER() {
		return NUMBER;
	}

	public void setNUMBER(String nUMBER) {
		NUMBER = nUMBER;
	}

	public String getWeChatId() {
		return weChatId;
	}

	public void setWeChatId(String weChatId) {
		this.weChatId = weChatId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsQuit() {
		return isQuit;
	}

	public void setIsQuit(String isQuit) {
		this.isQuit = isQuit;
	}

	public String getQuitTime() {
		return quitTime;
	}

	public void setQuitTime(String quitTime) {
		this.quitTime = quitTime;
	}

	public String getIsUrl() {
		return isUrl;
	}

	public void setIsUrl(String isUrl) {
		this.isUrl = isUrl;
	}

}
