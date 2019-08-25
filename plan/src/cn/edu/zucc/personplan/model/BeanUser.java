package cn.edu.zucc.personplan.model;

import java.util.Date;

public class BeanUser {
	public static BeanUser currentLoginUser=null;
	private String userId;
	private String pwd;
	private Date registerDate;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
}