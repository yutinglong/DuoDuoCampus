package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;


public class Teacher {
	@SerializedName("teaId")
	public String teaId;
	
	@SerializedName("teaName")
	public String teaName;
	
	@SerializedName("teaSchool")
	public School teaSchool;
	
	@SerializedName("username")
	public String username;
	
	@SerializedName("gender")
	public String gender;
	
	@SerializedName("education")
	public String education;
	
	@SerializedName("dgree")
	public String dgree;
	
	@SerializedName("title")
	public String title;
}
