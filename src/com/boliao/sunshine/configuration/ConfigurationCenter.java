/**
 * 
 */
package com.boliao.sunshine.configuration;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * properties配置中心
 * 
 * @author liaobo
 * 
 */
public class ConfigurationCenter {

	/** 异常日志收集器 */
	private static final Logger error = Logger.getLogger("ERROR");

	/** sunshine的配置中心 */
	private static Properties sunshineConfiguration = new Properties();

	/** lucene base dir */
	private static final String SEARCH_INDEX_PATH = "search_index_path";

	/***
	 * 初始化配置
	 */
	static {
		try {
			sunshineConfiguration.load(ConfigurationCenter.class.getClassLoader().getResourceAsStream("resources/application.properties"));
		} catch (IOException e) {
			error.error("初始化配置文件异常", e);
		}
	}

	/**
	 * 获得索引库的基本路径。
	 * 
	 * @return
	 */
	public static String getBaseSearchIndexDir() {
		return sunshineConfiguration.getProperty(SEARCH_INDEX_PATH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
