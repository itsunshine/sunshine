/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.util.List;

import com.boliao.sunshine.biz.model.JobCorporation;

/**
 * @author liaobo
 * 
 */
public interface JobCorporationDao {

	/**
	 * 从数据库中查询所有的公司
	 * 
	 * @return
	 */
	public List<JobCorporation> queryAllCorporation();
}
