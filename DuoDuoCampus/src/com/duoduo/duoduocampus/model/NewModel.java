package com.duoduo.duoduocampus.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class NewModel {
	@SerializedName("hasMore")
	public boolean hasMore;
	
	@SerializedName("limit")
	public int limit;
	
	@SerializedName("offset")
	public int offset;
	
	@SerializedName("totalCount")
	public int totalCount;
	
	@SerializedName("items")
	public List<News> items;
	
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
	}
	
	public class NewType {
		@SerializedName("typeId")	
		public String typeId;
	}
	
	
//	{
//		  "items" : [ {
//		    "newId" : 1,
//		    "newTitle" : "newtitle",
//		    "newDate" : "2015-08-14T14:58:33.000",
//		    "newContent" : "newcontent",
//		    "newType" : {
//		      "typeId" : 1
//		    },
//		    "newFor" : 1
//		  } ],
//		  "hasMore" : false,
//		  "limit" : 0,
//		  "offset" : 0,
//		  "totalCount" : 0
//		}
}
