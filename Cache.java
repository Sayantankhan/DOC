/*
* Written By Sayantan Khan
* Guava 25.0-jre
*/
package com.application.caching.JavaCaching.cache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class Cache {
	
	private final static String TIME_TOKEN = "Time_Token";

	public static  LoadingCache<String,String> create_cache_configuration(long duration_second) {
		// create a cache for the application
		LoadingCache<String, String> keyToken_cache = CacheBuilder.newBuilder().maximumSize(3)
				.expireAfterWrite(duration_second, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
					@Override
					public String load(String token_name) throws Exception {
						if(token_name.equalsIgnoreCase(TIME_TOKEN))
							return getEmployerToken();
						return null;
					}
				});
		
		return keyToken_cache;
	}
	
	private static String getEmployerToken() {
		System.out.println("got a hit");
		Calendar cal = Calendar. getInstance();
		Date date=cal. getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate=dateFormat.format(date);
		return formattedDate;
	}

}
