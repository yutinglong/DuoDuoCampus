package com.duoduo.duoduocampus.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class HomeWork implements Serializable {
	private static final long serialVersionUID = -3962205462102753577L;

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
