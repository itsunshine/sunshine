/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.dao.JobDemandArtDao;
import com.boliao.sunshine.biz.model.JobDemandArt;
import com.boliao.sunshine.dao.AbstractDao;
import com.boliao.sunshine.util.CommonConstants;

/**
 * @author liaobo
 * 
 */
public class JobDemandArtDaoImpl extends AbstractDao<JobDemandArt> implements JobDemandArtDao {

	public static JobDemandArtDaoImpl jobDemandArtDaoImpl = new JobDemandArtDaoImpl();

	public static Logger logger = Logger.getLogger(JobDemandArtDaoImpl.class);

	private JobDemandArtDaoImpl() {
	};

	public static JobDemandArtDaoImpl getInstance() {
		if (jobDemandArtDaoImpl == null) {
			synchronized (JobDemandArtDaoImpl.class) {
				if (jobDemandArtDaoImpl == null) {
					jobDemandArtDaoImpl = new JobDemandArtDaoImpl();
				}
			}
		}
		return jobDemandArtDaoImpl;
	}

	@Override
	public boolean deleteById(long aticleId) {

		return false;
	}

	@Override
	public long insert(JobDemandArt jobDemandArt) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String insertSql = insertString(jobDemandArt, JobDemandArt.class);
		return this.execute(insertSql);
	}

	@Override
	public List<Long> insertBatch(List<JobDemandArt> jobDemandArts) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		List<String> sqlList = new ArrayList<String>();
		for (JobDemandArt jobDemandArt : jobDemandArts) {
			String insertSql = insertString(jobDemandArt, JobDemandArt.class);
			sqlList.add(insertSql);
		}
		List<Long> idList = this.executeBatch(sqlList);
		return idList;
	}

	@Override
	public boolean update(JobDemandArt article) {
		return false;
	}

	@Override
	public List<JobDemandArt> getPageArticle(String type, int max) {

		return null;
	}

	public List<JobDemandArt> getPageArticleDesc(String field, int start, int pageSize) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		// 记录开始，操作的时间
		long begin = System.currentTimeMillis();

		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "page_jobDemandArt_" + start + "_" + pageSize;
		List<JobDemandArt> articles = (List<JobDemandArt>) cacheUtil.getElement(CommonConstants.PAGE_CACHE_KEY, key);
		if (articles != null && articles.size() > 0) {
			long end = System.currentTimeMillis();
			logger.info("分页查询命中缓存，耗时：" + (end - begin) + " ms. the key is " + key);
			return articles;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("order by ").append(field).append(" desc limit ").append(start).append(",").append(pageSize);
		String selectStr = this.selectPagesString(JobDemandArt.class, sb.toString(), new String[] { "createTime", "location", "hrNumber", "companyName" });

		List<JobDemandArt> result = this.executeQueryPage(selectStr, JobDemandArt.class);

		long end = System.currentTimeMillis();
		logger.info("分页查询，耗时：" + (end - begin) + " ms. the key is " + key);
		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.PAGE_CACHE_KEY, key, result);

		return result;
	}

	public List<JobDemandArt> queryPageJobDesc(Map<String, String> whereFs, String field, int start, int pageSize) throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		// 记录开始，操作的时间
		long begin = System.currentTimeMillis();
		String cacheKey = "page_jobDemandArt_";

		for (String key : whereFs.keySet()) {
			cacheKey += key + "_" + whereFs.get(key) + "_";
		}

		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		cacheKey = cacheKey + start + "_" + pageSize;
		List<JobDemandArt> articles = (List<JobDemandArt>) cacheUtil.getElement(CommonConstants.PAGE_CACHE_KEY, cacheKey);
		if (articles != null && articles.size() > 0) {
			long end = System.currentTimeMillis();
			logger.info("分页查询命中缓存，耗时：" + (end - begin) + " ms. the key is " + cacheKey);
			return articles;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(constructWhereStr(whereFs));
		sb.append(" order by ").append(field).append(" desc limit ").append(start).append(",").append(pageSize);
		String selectStr = this.selectPagesString(JobDemandArt.class, sb.toString(), new String[] { "createTime", "location", "hrNumber", "companyName" });

		List<JobDemandArt> result = this.executeQueryPage(selectStr, JobDemandArt.class);

		long end = System.currentTimeMillis();
		logger.info("分页查询，耗时：" + (end - begin) + " ms. the key is " + cacheKey);
		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.PAGE_CACHE_KEY, cacheKey, result);

		return result;
	}

	@Override
	public int updateClickNumber(int articleId) {
		return 0;
	}

	@Override
	public JobDemandArt findArticleById(long id) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, SQLException {

		// 记录开始，操作的时间
		long begin = System.currentTimeMillis();
		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "con_jobDemandArt_" + id;
		JobDemandArt jobDemandArt = (JobDemandArt) cacheUtil.getElement(CommonConstants.CON_CACHE_KEY, key);
		if (jobDemandArt != null) {
			long end = System.currentTimeMillis();
			logger.info("根据id查询命中缓存，耗时：" + (end - begin) + " ms. the key is " + key);
			return jobDemandArt;
		}

		String whereSql = WHERE_PREFIX + id;
		String seletSql = this.selectString(JobDemandArt.class, whereSql);
		jobDemandArt = this.executeQuery(seletSql, JobDemandArt.class);

		long end = System.currentTimeMillis();
		logger.info("根据id查询，耗时：" + (end - begin) + " ms. the key is " + key);
		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.CON_CACHE_KEY, key, jobDemandArt);

		return jobDemandArt;
	}

	public static void main(String[] args) throws Exception {

		JobDemandArt jobDemandArt = new JobDemandArt();
		jobDemandArt.setTitle("testTitle");
		jobDemandArt.setContent("testContent");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE);
		String time = sdf.format(date);
		jobDemandArt.setCreateTime(time);
		jobDemandArt.setCompanyName("testCompanyName");
		// jobDemandArt.setDepartmentName("testDepartmentName");
		jobDemandArt.setSource("testUrl");
		jobDemandArt.setHrNumber(2);
		jobDemandArt.setEducation("testEducation");
		jobDemandArt.setJobTime("full time");
		jobDemandArt.setJobType(1);

		JobDemandArtDaoImpl a = new JobDemandArtDaoImpl();
		a.insert(jobDemandArt);
		/*
		 * List<JobDemandArt> pages = a.getPageArticleDesc("createTime", 0, 2);
		 * for (JobDemandArt jobDemandArt : pages) {
		 * System.out.println(jobDemandArt); }
		 */
		// JobDemandArt jobDemandArt = a.findArticleById(66);

	}

	@Override
	public long getTotalCount() {
		return this.getTotalCount(JobDemandArt.class, null);
	}

	@Override
	public long getTotalCount(Map<String, String> whereFs) {
		String whereStr = constructWhereStr(whereFs);
		return this.getTotalCount(JobDemandArt.class, whereStr);
	}

	/**
	 * 组装where字符串
	 * 
	 * @param whereFs
	 * @return
	 */
	private String constructWhereStr(Map<String, String> whereFs) {
		StringBuilder sb = new StringBuilder();
		sb.append(" where 1=1 ");
		for (String key : whereFs.keySet()) {
			sb.append(" and ").append(key).append("='").append(whereFs.get(key)).append("'");
		}
		return sb.toString();
	}

}
