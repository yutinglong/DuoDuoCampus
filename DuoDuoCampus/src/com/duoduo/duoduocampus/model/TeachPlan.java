package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;


public class TeachPlan {
	@SerializedName("tpId")
	public String tpId;
	
	@SerializedName("course")
	public Course course;
	
	@SerializedName("teacher")
	public Teacher teacher;
	
	@SerializedName("classInfo")
	public ClassInfo classInfo;
	
	@SerializedName("tpContent")
	public String tpContent;
	
	@SerializedName("classHour")
	public String classHour;
	
	@SerializedName("tpStatus")
	public String tpStatus;
}
