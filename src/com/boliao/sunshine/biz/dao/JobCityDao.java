/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.util.List;

import com.boliao.sunshine.biz.model.JobCity;

/**
 * @author liaobo
 * 
 */
public interface JobCityDao {

	/**
	 * 从数据库中查询所有的城市地址
	 * 
	 * @return
	 */
	public List<JobCity> queryAllCity();
}
