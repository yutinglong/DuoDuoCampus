package com.duoduo.duoduocampus.model.net;

import java.util.List;

import com.duoduo.duoduocampus.model.School;
import com.google.gson.annotations.SerializedName;

public class Schools {
	@SerializedName("hasMore")
	public boolean hasMore;
	
	@SerializedName("limit")
	public int limit;
	
	@SerializedName("offset")
	public int offset;
	
	@SerializedName("totalCount")
	public int totalCount;
	
	@SerializedName("items")
	public List<School> items;
}
