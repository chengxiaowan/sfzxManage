package com.yocto.util.cache;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;
import com.yocto.util.context.SpringContextUtils;

/**
 * cache 缓存管理器
 * 
 * @author wxh
 * @version [版本号, 2016-05-26]
 */
public class CacheManage {
	/**
	 * memcache缓存对象
	 */
	@Autowired
	private MemCachedClient memcachedClient;

	/**
	 * 缓存管理 单例对象
	 */
	protected static CacheManage sm;

	public synchronized static CacheManage getInstance() {
		if (sm == null) {
			sm = new CacheManage();
			// 实例化memcachedClient
			if (null == sm.memcachedClient) {
				sm.memcachedClient = SpringContextUtils.getBean("memcachedClient", MemCachedClient.class);
			}
		}
		return sm;
	}

	/**
	 * 从缓存中删除该对象
	 * 
	 * @param key
	 *            缓存中存储key值
	 */
	public void dropCache(String key) {
		memcachedClient.delete(key);
	}

	/**
	 * 添加对象到缓存
	 * 
	 * @param key
	 *            缓存中存储key值
	 * @param Object
	 *            key对应值value
	 * @param limit
	 *            过期时间 0:表示使用默认时间
	 */
	public void addCache(String key, Object o, long limit) {
		if (limit == 0) {
			memcachedClient.set(key, o);
		} else
			memcachedClient.set(key, o, new Date(limit));
	}

	/**
	 * 若原对象存在，则 替换原有对象到缓存
	 * 
	 * @param key
	 *            缓存中存储key值
	 * @param Object
	 *            key对应值value
	 * @param expireTime
	 *            过期时间
	 */
	public void replaceCache(String key, Object o, long limit) {
		memcachedClient.replace(key, o, new Date(limit));
	}

	/**
	 * 根据key,读取缓存对象
	 * 
	 * @param key
	 */
	public Object getCacheByKey(String key) {
		if (memcachedClient.keyExists(key)) {
			return memcachedClient.get(key);
		} else
			return null;
	}

	/**
	 * 根据一个或多个key，清空缓存
	 * 
	 * @param keys
	 *            key集合
	 */
	public void clearCacheByKeys(String[] keys) {
		if (null != keys && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				memcachedClient.delete(keys[i]);
			}
		}
	}

	/**
	 * cache中是否含有key
	 * 
	 * @param key
	 * @return
	 */
	public boolean keyExists(String key) {
		return memcachedClient.keyExists(key);
	}

}
