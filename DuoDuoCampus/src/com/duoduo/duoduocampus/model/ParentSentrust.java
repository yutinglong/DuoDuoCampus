package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;


public class ParentSentrust {
	@SerializedName("peId")	
	public String peId;
	
	@SerializedName("student")	
	public Student student;
	
	@SerializedName("parentStr")	
	public String parentStr;
	
	@SerializedName("teacher")
	public Teacher teacher;
	
	@SerializedName("peContent")
	public String peContent;
}

