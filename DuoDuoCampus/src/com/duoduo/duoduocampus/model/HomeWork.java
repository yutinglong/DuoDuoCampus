package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;

public class HomeWork {
	@SerializedName("workId")
	public String workId;
	
	@SerializedName("createDate")
	public String createDate;
	
	@SerializedName("workAnalysis")
	public String workAnalysis;
	
	@SerializedName("workContent")
	public String workContent;
	
	@SerializedName("workComment")
	public String workComment;
	
	@SerializedName("course")
	public Course course;
}
