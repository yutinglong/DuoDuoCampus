package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;


public class StudentAppraisal {
	@SerializedName("appraisalId")	
	public String appraisalId;
	
	@SerializedName("student")	
	public Student student;
	
	@SerializedName("createDate")	
	public String createDate;
	
	@SerializedName("teacher")	
	public Teacher teacher;
	
	@SerializedName("appraisalContent")	
	public String appraisalContent;
}
