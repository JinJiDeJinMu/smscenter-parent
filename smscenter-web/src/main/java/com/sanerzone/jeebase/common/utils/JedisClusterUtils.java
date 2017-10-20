/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.jeebase.common.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sanerzone.jeebase.common.config.Global;
import com.sohu.tv.builder.ClientBuilder;

import redis.clients.jedis.JedisCluster;

public class JedisClusterUtils {

	private static Logger logger = LoggerFactory.getLogger(JedisClusterUtils.class);
	private static JedisCluster redisCluster = getJedisInstance();
	public static final String KEY_PREFIX = Global.getConfig("redis.keyPrefix");
	
	public static JedisCluster getJedisInstance(){
		if(redisCluster == null ) {
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			redisCluster = ClientBuilder.redisCluster(Long.parseLong(Global.getConfig("redis.appid"))).setJedisPoolConfig(poolConfig)
					.setConnectionTimeout(1000).setSoTimeout(1000).setMaxRedirections(5).build();
		}
		return redisCluster;
	}
	
//	public static void set(String key,String value,int cacheSeconds){
//		getJedisInstance().set(key, value);
//		if (cacheSeconds != 0) {
//			getJedisInstance().expire(key, cacheSeconds);
//		}
//	}
//	
//	public static void hset(String key, String field,String value){
//		getJedisInstance().hset(key, field, value);
//	}
//	
//	public static int getInt(String key){
//		String value = getJedisInstance().get(key);
//		if(StringUtils.isBlank(value))return 0;
//		return Integer.valueOf(value);
//	}
//	
//	public static String get(String key){
//		return getJedisInstance().get(key);
//	}
//	
//	public static String hget(String key,String field){
//		return getJedisInstance().hget(key, field);
//	}
//	
//	public static long incr(String key,int cacheSeconds){
//		long l = getJedisInstance().incr(key);
//		if(cacheSeconds !=0)getJedisInstance().expire(key, cacheSeconds);
//		return l;
//	}
//	
//	public static long incr(String key){
//		long l = getJedisInstance().incr(key);
//		return l;
//	}
//	
//	public static long incrBy(String key, long integer)
//	{
//		long result = 0;
//		
//		try
//		{
//			result = getJedisInstance().incrBy(key, integer);
//		}
//		catch(Exception e)
//		{
//			result = 0;
//			logger.error("{}", e);
//		}
//		
//		return result;
//	}
//	
//	public static long decrBy(String key, long integer)
//	{
//		return getJedisInstance().decrBy(key, integer);
//	}
//	
//	public static void decr(String key){
//		getJedisInstance().decr(key);
//	}
//	
//	public static void del(String key){
//		getJedisInstance().del(key);
//	}
//	
//	
//	/**
//	 * 获取byte[]类型Key
//	 * @param key
//	 * @return
//	 */
//	public static byte[] getBytesKey(Object object){
//		if(object instanceof String){
//    		return StringUtils.getBytes((String)object);
//    	}else{
//    		return ObjectUtils.serialize(object);
//    	}
//	}
//	
//	/**
//	 * Object转换byte[]类型
//	 * @param key
//	 * @return
//	 */
//	public static byte[] toBytes(Object object){
//    	return ObjectUtils.serialize(object);
//	}
//
//	/**
//	 * byte[]型转换Object
//	 * @param key
//	 * @return
//	 */
//	public static Object toObject(byte[] bytes){
//		return ObjectUtils.unserialize(bytes);
//	}

}
