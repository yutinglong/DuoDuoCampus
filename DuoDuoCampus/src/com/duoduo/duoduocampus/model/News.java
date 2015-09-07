package com.duoduo.duoduocampus.model;

import com.google.gson.annotations.SerializedName;


public class News {
	@SerializedName("newId")
	public String newId;
	
	@SerializedName("newTitle")
	public String newTitle;
	
	@SerializedName("newDate")
	public String newDate;
	
	@SerializedName("newContent")
	public String newContent;
	
	@SerializedName("newFor")
	public String newFor;
	
	@SerializedName("newType")
	public NewType newType;
	
	@SerializedName("imgUrl")
	public String imgUrl;
}
