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
	
	public String getStatusStr() {
		String result = "未知";
		if ("1".equals(tpStatus)) {
			result = "已开课";
		}
		else if ("2".equals(tpStatus)) {
			result = "已停课";
		}
		return result;
	}
}
