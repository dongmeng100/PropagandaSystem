package com.propaganda.bean;

public class User {
	private String username;
	private String passWord;
	private String Authority;
	private String apart; //≤ø√≈Ω÷µ¿
	
	public String getApart() {
		return apart;
	}
	public void setApart(String apart) {
		this.apart = apart;
	}

	
    public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getAuthority() {
		return Authority;
	}
	public void setAuthority(String authority) {
		Authority = authority;
	}
}
