package com.sanerzone.smscenter.biz.cache;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.tfc.analysis.KWSeeker;
import com.tfc.analysis.entity.Keyword;

//关键字工具类
public class KeywordsHelper {
	
	private static KWSeeker kw1 = KWSeeker.getInstance();
	
	public static void clearAll(){
		kw1.clear();
	}
	
	/**
	 * 初始化关键字
	 * @param list String:keywords
	 */
	public static void init(List<String> list){
		if(list != null && list.size() > 0){
			for (String keywords : list) {
				put(keywords);
			}
		}
	}

	/**
	 * 缓存关键字
	 * @param value
	 */
	public static void put(String value){
		kw1.addWord(new Keyword(value));
	}
	
	/**
	 * 删除关键字
	 * @param value
	 */
	public static void del(String value){
		kw1.remove(value);
	}
	
	
	/**
	 * 是否包含非法内容
	 * @param content
	 * @return
	 */
	public static String keywords(String content){
	    StringBuffer strBuf = new StringBuffer();
	    Set<String> set = kw1.findWords(content);
	    
	    for (Iterator<String> iterator = set.iterator(); iterator.hasNext();){
	        strBuf.append(iterator.next()).append(",");
	    }
	    
	    if (strBuf.length() > 1){
	        return strBuf.substring(0, strBuf.length() -1);
	    }
		
		return "";
	}
	
	/**
	 * 匹配关键字
	 * @param keywords
	 * @param content
	 * @return
	 */
	public static boolean exits(String keywords,String content){
		if(StringHelper.isBlank(keywords))return true;
		String[] keywordArray = keywords.split(",");
		for (String key : keywordArray) {
			if(content.indexOf(key) >= 0)return true;
		}
		return false;
	}
	
	/**
	 * 敏感词是否存在
	 * @param keywords
	 * @return
	 */
	public static boolean exits(String keywords){
		Set<String> set = kw1.findWords(keywords);
		if(set != null && set.size() > 0)return true;
		return false;
	}
}
