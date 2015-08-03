/**
 * 
 */
package com.boliao.sunshine.biz.service;

import java.util.List;

import com.boliao.sunshine.biz.model.JobCorporation;

/**
 * @author liaobo
 * 
 */
public interface JobCorperationService {
	/**
	 * 根据id查找城市对象
	 * 
	 * @param id
	 * @return
	 */
	public JobCorporation findJobCityById(long id);

	/**
	 * 查询出所有的城市
	 * 
	 * @return
	 */
	public List<JobCorporation> queryAllJobCorporation();

}
