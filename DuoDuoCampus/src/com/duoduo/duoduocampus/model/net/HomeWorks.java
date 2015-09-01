package com.duoduo.duoduocampus.model.net;

import java.util.List;

import com.duoduo.duoduocampus.model.HomeWork;
import com.google.gson.annotations.SerializedName;

public class HomeWorks {
	@SerializedName("hasMore")
	public boolean hasMore;
	
	@SerializedName("limit")
	public int limit;
	
	@SerializedName("offset")
	public int offset;
	
	@SerializedName("totalCount")
	public int totalCount;
	
	@SerializedName("items")
	public List<HomeWork> items;
	
//	{
//		  "items" : [ {
//		    "workId" : 1,
//		    "createDate" : "2015-08-14T14:58:08.000",
//		    "course" : {
//		      "couId" : 1
//		    },
//		    "workContent" : "homeowrk test",
//		    "workAnalysis" : "homeowrk test",
//		    "workComment" : "homeowrk test"
//		  } ],
//		  "hasMore" : false,
//		  "limit" : 0,
//		  "offset" : 0,
//		  "totalCount" : 0
//		}
}
