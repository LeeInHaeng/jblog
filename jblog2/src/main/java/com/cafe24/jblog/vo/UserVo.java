package com.cafe24.jblog.vo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserVo {

	@NotEmpty
	@Length(min=1, max=50)
	@Email
	private String id;
	
	@NotEmpty
	@Length(min=1, max=50)
	private String name;
	
	@NotEmpty
	@Length(min=1, max=64)
	private String pass;
	private String regDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", pass=" + pass + ", regDate=" + regDate + "]";
	}
	
}
