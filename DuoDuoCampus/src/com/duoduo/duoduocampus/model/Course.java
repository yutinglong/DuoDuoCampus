package com.duoduo.duoduocampus.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Course implements Serializable {
	private static final long serialVersionUID = -9185681243605761006L;

	@SerializedName("couId")
	public String couId;

	@SerializedName("couName")
	public String couName;

	@SerializedName("couDesc")
	public String couDesc;
}
