package com.duoduo.duoduocampus.msg;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;

import com.duoduo.duoduocampus.msg.Pools.Pool;

public class AndroidMessenger extends Messenger {

	private SparseArray<List<MessageReciever>> recievers = new SparseArray<List<MessageReciever>>();
	private DataSetPool pool = new DataSetPool();
	private Handler handler;

	public AndroidMessenger() {
		handler = new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(Message msg) {
				List<MessageReciever> list = recievers.get(msg.what);
				DataSet set = (DataSet) msg.obj;
				if (list != null)
				{
					for (MessageReciever messageReciever : list) 
					{
  						if (set.getReciver() == null || messageReciever.getContext() == set.getReciver() || messageReciever == set.getReciver())
 						{
 							messageReciever.handleMessage(msg.what, set);
 						}
					}					
				}
				pool.release(set);
  			}
		};
	}
 	
	@Override
	public void addListener(MessageReciever reciever, int msgType) {
		if (recievers.indexOfKey(msgType)>=0)
			recievers.get(msgType).add(reciever);
		else {
			List<MessageReciever> list = new ArrayList<MessageReciever>(10);
			list.add(reciever);
			recievers.put(msgType, list);
		}
	}

	@Override
	public void addListener(MessageReciever reciever, int... msgTypes) {
		for (int type : msgTypes) {
			addListener(reciever, type);
		}
	}

	@Override
	public void sendMessage(int msgType, Object... objects) {
 		Message msg = Message.obtain();
		DataSet data = pool.acquire();
		msg.obj = data;
		msg.what = msgType;
		for (int i = 0; i < objects.length; i += 2) {
			data.put( objects[i], objects[i+1]);
		}
		handler.sendMessage(msg);
	}

	@Override
	public void sendMessageTo(Context context, int msgType, Object... objects) {
		if(context == null)
			return ;
		Message msg = Message.obtain();
		DataSet data = pool.acquire();
		data.setReciver(context);
		msg.obj = data;
		msg.what = msgType;
 		for (int i = 0; i < objects.length; i += 2) {
			data.put( objects[i], objects[i+1]);
		}
		handler.sendMessage(msg);
	}
 
	@Override
	public void sendMessageTo(MessageReciever reciever, int msgType, Object... objects) {
		if(reciever == null)
			return ;
		Message msg = Message.obtain();
		DataSet data = pool.acquire();
		data.setReciver(reciever);
		msg.obj = data;
		msg.what = msgType;
		for (int i = 0; i < objects.length; i += 2) {
			data.put( objects[i], objects[i+1]);
		}
		handler.sendMessage(msg);		
	}
	
	@Override
	public synchronized void removeListener(Context context) {
 		for (int index = 0; index < recievers.size(); index++) {
			int key = recievers.keyAt(index);
			List<MessageReciever> list = recievers.get(key);
			for (int i = list.size() - 1; i >= 0; i--) {
				if (list.get(i).getContext() == context)
				{
					list.remove(i);
 				}	
			}
		}
	}

	@Override
	public void postTask(Runnable task, long delay) {
		handler.postDelayed(task, delay);
	}

	@Override
	public void cancelTask(Runnable task) {
		if (task != null)
			handler.removeCallbacks(task);
	}

	@Override
	public synchronized void removeListener(MessageReciever reciever) {
		for (int index = 0; index < recievers.size(); index++) {
			int key = recievers.keyAt(index);
			List<MessageReciever> list = recievers.get(key);
			for (int i = list.size() - 1; i >= 0; i--) {
				if (list.get(i) == reciever)
				{
					list.remove(i);
 				}	
			}
		}	
	}

	@Override
	public void removeListener(int msgType) {
		recievers.remove(msgType);
 	}
	
	class DataSetPool implements Pool<DataSet>
	{
		private List<DataSet> list = Collections.synchronizedList(new ArrayList<DataSet>());
		
		@Override
		public DataSet acquire() {
			if(list.isEmpty())
				return new DataSet();
			return list.remove(0);
		}

		@Override
		public boolean release(DataSet instance) {
			instance.recycle();
			return list.add(instance);
		}
	}
}
