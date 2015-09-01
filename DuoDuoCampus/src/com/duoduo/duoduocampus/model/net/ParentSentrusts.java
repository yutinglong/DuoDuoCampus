package com.duoduo.duoduocampus.model.net;

import java.util.List;

import com.duoduo.duoduocampus.model.ParentSentrust;
import com.google.gson.annotations.SerializedName;

public class ParentSentrusts {
	@SerializedName("hasMore")
	public boolean hasMore;
	
	@SerializedName("limit")
	public int limit;
	
	@SerializedName("offset")
	public int offset;
	
	@SerializedName("totalCount")
	public int totalCount;
	
	@SerializedName("items")
	public List<ParentSentrust> items;
	
	
//	{
//		  "items" : [ {
//		    "peId" : 1,
//		    "student" : {
//		      "stuId" : 1,
//		      "stuAge" : 0,
//		      "stuClassInfo" : {
//		        "classInfoId" : 0,
//		        "grade" : 0,
//		        "school" : {
//		          "schID" : 0
//		        },
//		        "teacher" : {
//		          "teaId" : 0,
//		          "teaSchool" : {
//		            "schID" : 0
//		          }
//		        }
//		      }
//		    },
//		    "parentStr" : "ç¸ç¸",
//		    "teacher" : {
//		      "teaId" : 1,
//		      "teaSchool" : {
//		        "schID" : 0
//		      }
//		    },
//		    "peContent" : "fortest"
//		  } ],
//		  "hasMore" : false,
//		  "limit" : 0,
//		  "offset" : 0,
//		  "totalCount" : 0
//		}
}
