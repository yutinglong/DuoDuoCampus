package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;


public class ClassInfo {
	@SerializedName("classInfoId")
	public String classInfoId;
	
	@SerializedName("classInfoName")
	public String classInfoName;
	
	@SerializedName("grade")
	public String grade;
	
	@SerializedName("school")
	public School school;
	
	@SerializedName("teacher")
	public Teacher teacher;
}
