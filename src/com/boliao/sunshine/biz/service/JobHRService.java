/**
 * 
 */
package com.boliao.sunshine.biz.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.boliao.sunshine.biz.model.JobDemandArt;
import com.boliao.sunshine.biz.model.PageBase;

/**
 * @author liaobo
 * 
 */
public interface JobHRService {
	/**
	 * 根据id查找文章对象
	 * 
	 * @param id
	 * @return
	 */
	public JobDemandArt findJobDemandArtById(long id);

	/**
	 * 分页查询工作职责对象
	 * 
	 * @param field
	 * @param page
	 * @return
	 */
	public PageBase<JobDemandArt> getPageJobDemandArtDesc(String field, PageBase<JobDemandArt> page);

	/**
	 * 分页查询工作职责对象
	 * 
	 * @param whereFs
	 * @param field
	 * @param page
	 * @return
	 */
	public PageBase<JobDemandArt> queryPageJobDesc(Map<String, String> whereFs, String field, PageBase<JobDemandArt> page);

	/**
	 * 插入文章对象
	 * 
	 * @param jobDemandArt
	 * @return
	 */
	public boolean insertJobDemandArt(JobDemandArt jobDemandArt);

	/**
	 * 批量插入接口
	 * 
	 * @param jobDemandArts
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void insertBatch(List<JobDemandArt> jobDemandArts);
}
