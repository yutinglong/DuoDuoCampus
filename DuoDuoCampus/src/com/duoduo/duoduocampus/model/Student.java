package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;


public class Student {
	@SerializedName("stuId")	
	public String stuId;
	
	@SerializedName("stuAge")	
	public int stuAge;
	
	@SerializedName("stuClassInfo")
	public StuClassInfo stuClassInfo;
	
	@SerializedName("teacher")
	public Teacher teacher;
}
