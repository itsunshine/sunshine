package com.boliao.sunshine.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheUtil {
	private Cache contentCache = null;
	private Cache pagesCache = null;
	private Cache userCache = null;

	private final Map<String, Cache> cacheMap = new HashMap<String, Cache>();

	private static CacheUtil cacheUtil = new CacheUtil();

	public static CacheUtil getInstance() {
		if (cacheUtil == null) {
			cacheUtil = new CacheUtil();
		}
		return cacheUtil;
	}

	private CacheUtil() {
		URL url = CacheUtil.class.getResource("/resources/ehcache.xml");
		InputStream inputStream = null;
		try {
			inputStream = url.openStream();
			CacheManager manager = CacheManager.create(inputStream);
			contentCache = manager.getCache("articleCache");
			pagesCache = manager.getCache("pagesCache");
			userCache = manager.getCache("registeredUserCache");
			if (contentCache != null && pagesCache != null && userCache != null) {
				cacheMap.put(CommonConstants.CON_CACHE_KEY, contentCache);
				cacheMap.put(CommonConstants.PAGE_CACHE_KEY, pagesCache);
				cacheMap.put(CommonConstants.RGT_USER_KEY, userCache);

			} else {
				throw new RuntimeException("Initial cache error, please check your ehcache.xml");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Object getElement(String cacheKey, String key) {
		Cache cache = cacheMap.get(cacheKey);
		Element value = cache.get(key);
		if (value != null) {
			Object obj = value.getValue();
			return obj;
		}
		return null;
	}

	public void putCache(String cacheKey, String key, Object obj) {
		Element element = new Element(key, obj);
		Cache cache = cacheMap.get(cacheKey);
		cache.put(element);
	}

	public void removeCache(String cacheKey, String key) {
		Cache cache = cacheMap.get(cacheKey);
		cache.remove(key);
	}

	// 清楚掉cache中的数据
	public void clearCache(String cacheKey) {
		Cache cache = cacheMap.get(cacheKey);
		cache.removeAll();
	}

	public static void main(String[] args) {
		CacheManager manager = CacheManager.create("D:/workspace/sunshine_new/src/resources/ehcache.xml");
		String[] names = manager.getCacheNames();

		for (String name : names) {
			System.out.println(name);
			Cache cache = manager.getCache(name);
			System.out.println(cache.getSize());
		}
		Cache cache = manager.getCache("aaa");
		System.out.println(cache.getSize());
	}
}
