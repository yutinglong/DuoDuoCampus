package com.duoduo.duoduocampus.model.net;

import java.util.List;

import com.duoduo.duoduocampus.model.ClassInfo;
import com.google.gson.annotations.SerializedName;


public class ClassInfos {
	@SerializedName("hasMore")
	public boolean hasMore;
	
	@SerializedName("limit")
	public int limit;
	
	@SerializedName("offset")
	public int offset;
	
	@SerializedName("totalCount")
	public int totalCount;
	
	@SerializedName("items")
	public List<ClassInfo> items;
	
//	{
//		  "items" : [ {
//		    "classInfoId" : 1,
//		    "classInfoName" : "classinfo1",
//		    "grade" : 1,
//		    "school" : {
//		      "schID" : 1
//		    },
//		    "teacher" : {
//		      "teaId" : 1,
//		      "teaSchool" : {
//		        "schID" : 0
//		      }
//		    }
//		  } ],
//		  "hasMore" : false,
//		  "limit" : 0,
//		  "offset" : 0,
//		  "totalCount" : 0
//		}
}
