package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;


public class StuClassInfo {
	@SerializedName("classInfoId")	
	public String classInfoId;
	
	@SerializedName("grade")	
	public String grade;
	
	@SerializedName("school")	
	public School school;
}

