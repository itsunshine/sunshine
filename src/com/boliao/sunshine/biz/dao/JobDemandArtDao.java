/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.boliao.sunshine.biz.model.JobDemandArt;

/**
 * @author liaobo
 * 
 */
public interface JobDemandArtDao {
	public long insert(JobDemandArt jobDemandArt) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;

	public List<JobDemandArt> getPageArticle(String type, int max);

	public JobDemandArt findArticleById(long id) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, SQLException;

	public boolean update(JobDemandArt jobDemandArt);

	public boolean deleteById(long articleId);

	/**
	 * 根据field 做查询后的排序
	 * 
	 * @param field
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<JobDemandArt> getPageArticleDesc(String field, int start, int pageSize) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException;

	/**
	 * 根据where条件的fields，进行数据查询的方法
	 * 
	 * @param whereFs
	 * @param field
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<JobDemandArt> queryPageJobDesc(Map<String, String> whereFs, String field, int start, int pageSize) throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	/**
	 * 默认的总数查询方法
	 * 
	 * @return
	 */
	public long getTotalCount();

	/**
	 * 根据whereStr 查询总数
	 * 
	 * @param whereStr
	 * @return
	 */
	public long getTotalCount(Map<String, String> whereFs);

	/**
	 * 更新点击量，即在原来点击量的基础上，增加一次点击
	 * 
	 * @param articleId
	 */
	public int updateClickNumber(int articleId);

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
	public List<Long> insertBatch(List<JobDemandArt> jobDemandArts) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException;
}
