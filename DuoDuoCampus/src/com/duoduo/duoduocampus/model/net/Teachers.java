package com.duoduo.duoduocampus.model.net;

import java.util.List;

import com.duoduo.duoduocampus.model.Teacher;
import com.google.gson.annotations.SerializedName;

public class Teachers {
	@SerializedName("hasMore")
	public boolean hasMore;
	
	@SerializedName("limit")
	public int limit;
	
	@SerializedName("offset")
	public int offset;
	
	@SerializedName("totalCount")
	public int totalCount;
	
	@SerializedName("items")
	public List<Teacher> items;
	
//	{
//		  "items" : [ {
//		    "teaId" : 1,
//		    "teaName" : "TeaName",
//		    "teaSchool" : {
//		      "schID" : 1
//		    },
//		    "username" : "admin",
//		    "gender" : "å¥³",
//		    "education" : "0",
//		    "dgree" : "ç¡å£«",
//		    "title" : "ææ"
//		  } ],
//		  "hasMore" : false,
//		  "limit" : 0,
//		  "offset" : 0,
//		  "totalCount" : 0
//		}
}
