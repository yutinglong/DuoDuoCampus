package com.duoduo.duoduocampus.msg;


import java.util.HashMap;

public class DataSet {

	private HashMap<Object, Object> data = new HashMap<Object, Object>();
	private Object reciver;
 	
	public Object getReciver() {
		return reciver;
	}

	public void setReciver(Object reciver) {
		this.reciver = reciver;
	}
	
	public void put(Object key,Object value)
	{
		data.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> cls,String key)
	{
		Object obj = data.get(key);
 		if(obj != null && (obj.getClass() == cls || cls.isInstance(obj)) )
			return (T) obj;
		
		return null;
	}

	public Object get(String key)
	{
		return data.get(key);
	}

	public void recycle()
	{
		reciver = null;
		data.clear();
	}

	public String getString(String key) {
		String result = get(String.class, key);
 		return result == null?null:result;
	}

	public int getInt(String key) {
		Integer result = get(Integer.class,key);
		return result == null?0:result;
	}

	public int getInt(String key, int defaultVal) {
		Integer result = get(Integer.class,key);
		return result == null?defaultVal:result;
	}
	
	public float getFloat(String key) {
		Float result = get(Float.class,key);
		return result == null?0:result;
	}
	
	public float getFloat(String key, float defaultVal) {
		Float result = get(Float.class,key);
		return result == null?defaultVal:result;
	}	
	
	public long getLong(String key) {
		Long result = get(Long.class, key);
		return result == null?0:result;
	}

	public boolean getBoolean(String key) {
		Boolean result = get(Boolean.class, key);
 		return result == null?false:result;
	}

	public String[] getStringArray(String key) {
		String[] array = get(String[].class, key);
		return array == null?null:array;
	}

	public boolean containsKey(Object key)
	{
		return data.containsKey(key) && data.get(key)!=null;
	}
}
