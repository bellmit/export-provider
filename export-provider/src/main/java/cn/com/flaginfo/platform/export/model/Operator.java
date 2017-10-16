package cn.com.flaginfo.platform.export.model;


/**
 * 用户信息dto
 * @author Rain
 *
 */
public class Operator {
	
	private String spId;
	private String userId;
	private String userName;
	private String ip;
	private String roleType;
	
	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "UserInfo [spId="+spId+", userId=" + userId + ", userName=" + userName + ", loginIp=" + ip + "]";
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
}
