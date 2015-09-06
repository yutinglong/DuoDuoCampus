package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;


public class ExamResult {

	@SerializedName("resultId")
	public String resultId;
	
	@SerializedName("createDate")
	public String createDate;
	
	@SerializedName("course")
	public Course course;
	
	@SerializedName("classInfo")
	public ClassInfo classInfo;
	
	@SerializedName("student")
	public Student student;
	
	@SerializedName("result")
	public String result;
	
	@SerializedName("rank")
	public String rank;
}
