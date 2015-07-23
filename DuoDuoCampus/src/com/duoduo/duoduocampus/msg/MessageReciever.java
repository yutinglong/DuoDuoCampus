package com.duoduo.duoduocampus.msg;

import android.content.Context;

public interface MessageReciever {
	
	public Context getContext();
	
	public void handleMessage(int msgType,DataSet data);
}
