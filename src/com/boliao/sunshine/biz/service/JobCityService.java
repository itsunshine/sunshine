/**
 * 
 */
package com.boliao.sunshine.biz.service;

import java.util.List;

import com.boliao.sunshine.biz.model.JobCity;
import com.boliao.sunshine.biz.model.PageBase;

/**
 * @author liaobo
 * 
 */
public interface JobCityService {
	/**
	 * 根据id查找城市对象
	 * 
	 * @param id
	 * @return
	 */
	public JobCity findJobCityById(long id);

	/**
	 * 分页查询城市对象
	 * 
	 * @param field
	 * @param page
	 * @return
	 */
	public PageBase<JobCity> getPageJobDemandArtDesc(String field, PageBase<JobCity> page);

	/**
	 * 查询出所有的城市
	 * 
	 * @return
	 */
	public List<JobCity> queryAllJobCity();

}
