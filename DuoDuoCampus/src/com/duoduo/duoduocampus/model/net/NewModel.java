package com.duoduo.duoduocampus.model.net;

import java.util.List;

import com.duoduo.duoduocampus.model.News;
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
