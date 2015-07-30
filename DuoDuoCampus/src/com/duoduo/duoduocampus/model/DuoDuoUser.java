package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;

public class DuoDuoUser {
	@SerializedName("username")
	public String username;
	
	@SerializedName("password")
	public String password;
	
	@SerializedName("realName")
	public String realName;
	
	@SerializedName("birthday")
	public String birthday;
	
	@SerializedName("gender")
	public String gender;
	
	@SerializedName("registerDate")
	public String registerDate;
	
	@SerializedName("lastLoadDate")
	public String lastLoadDate;
	
	@SerializedName("authority")
	public String authority;
}
