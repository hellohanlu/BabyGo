package com.hl.http;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {
	
	private static SharedPreferences getSharedPreferences(Context context)
	{
		 SharedPreferences sharedpreferences=context.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		
		 return sharedpreferences;	
	}
	public static void saveString(Context context,String key,String value)
	{
	     SharedPreferences sharedPreferences=getSharedPreferences(context);
	     sharedPreferences.edit().putString( key, value).commit();
	}
    public static String getString(Context context,String key)
    {
		return getSharedPreferences(context).getString(key , "");
    	 	
    }
}