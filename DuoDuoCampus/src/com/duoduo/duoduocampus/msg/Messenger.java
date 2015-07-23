package com.duoduo.duoduocampus.msg;

import android.content.Context;

public abstract class Messenger {
	
	public abstract void addListener(MessageReciever reciever,int msgType);
	
	public abstract void addListener(MessageReciever reciever,int... msgTypes);
	
	public abstract void sendMessage(int msgType,Object... objects);
	
	public abstract void removeListener(Context context);
	
	public abstract void removeListener(MessageReciever reciever);
	
	public abstract void removeListener(int msgType);
	
	public abstract void postTask(Runnable task,long delay);
	
	public abstract void cancelTask(Runnable task);
	
	public abstract void sendMessageTo(Context context,int msgType, Object... objects);
	
	public abstract void sendMessageTo(MessageReciever reciever,int msgType, Object... objects);
	
	private static Messenger messeger;
	
	public static Messenger getInstance() {
		return messeger == null ? (messeger = new AndroidMessenger()):messeger; 
	} 
	
 	public static int MSG_DRAWER_CHANGE;
}
