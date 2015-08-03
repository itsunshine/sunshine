/**
 * 
 */
package com.boliao.sunshine.biz.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.dao.JobDemandArtDao;
import com.boliao.sunshine.biz.dao.impl.JobDemandArtDaoImpl;
import com.boliao.sunshine.biz.model.JobDemandArt;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.service.JobHRService;
import com.boliao.sunshine.biz.service.SearchService;
import com.boliao.sunshine.biz.utils.LogUtil;

/**
 * 技术文章，服务类
 * 
 * @author liaobo
 */
public class JobHRServiceImpl implements JobHRService {

	/** 错误日志记录器 */
	private final Logger errLog = Logger.getLogger(LogUtil.ERROR);

	private final JobDemandArtDao jobDemandArtDao;
	private final SearchService searchService;

	static JobHRServiceImpl jobHRServiceImpl = new JobHRServiceImpl();

	private JobHRServiceImpl() {
		this.jobDemandArtDao = JobDemandArtDaoImpl.getInstance();
		searchService = SearchServiceImpl.getInstance();
	}

	public static JobHRServiceImpl getInstance() {
		if (jobHRServiceImpl == null) {
			synchronized (JobHRServiceImpl.class) {
				if (jobHRServiceImpl == null)
					jobHRServiceImpl = new JobHRServiceImpl();
			}
		}
		return jobHRServiceImpl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boliao.sunshine.biz.service.ArticleService#findArticleById(long)
	 */
	@Override
	public JobDemandArt findJobDemandArtById(long id) {
		JobDemandArt jobDemandArt = null;
		try {
			jobDemandArt = jobDemandArtDao.findArticleById(id);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jobDemandArt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.ArticleService#getPageArticleDesc(java
	 * .lang.String, com.boliao.sunshine.biz.model.PageBase)
	 */
	@Override
	public PageBase<JobDemandArt> getPageJobDemandArtDesc(String field, PageBase<JobDemandArt> page) {
		List<JobDemandArt> results = null;
		try {
			long totalCount = jobDemandArtDao.getTotalCount();
			if (totalCount == 0)
				return null;
			page.setTotalCount(totalCount);

			// 如果页码大于总页码，则设置页码为最后一页
			if (page.getPageNo() >= page.getTotalPage())
				page.setPageNo(page.getTotalPage());

			results = jobDemandArtDao.getPageArticleDesc(field, page.getStart(), page.getPageSize());
			page.setResults(results);
			page.setTotalCount(totalCount);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.ArticleService#getPageArticleDesc(java
	 * .lang.String, com.boliao.sunshine.biz.model.PageBase)
	 */
	@Override
	public PageBase<JobDemandArt> queryPageJobDesc(Map<String, String> whereFs, String field, PageBase<JobDemandArt> page) {
		List<JobDemandArt> results = null;
		try {
			long totalCount = jobDemandArtDao.getTotalCount(whereFs);
			if (totalCount == 0) {
				page.setTotalCount(totalCount);
				return page;
			}
			page.setTotalCount(totalCount);

			// 如果页码大于总页码，则设置页码为最后一页
			if (page.getPageNo() >= page.getTotalPage())
				page.setPageNo(page.getTotalPage());

			results = jobDemandArtDao.queryPageJobDesc(whereFs, field, page.getStart(), page.getPageSize());
			page.setResults(results);
			page.setTotalCount(totalCount);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.ArticleService#insertArticle(com.boliao
	 * .sunshine.biz.model.Article)
	 */
	@Override
	public boolean insertJobDemandArt(JobDemandArt article) {
		try {
			long id = jobDemandArtDao.insert(article);
			article.setId(id);
			searchService.indexContext(article);
			return false;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void insertBatch(List<JobDemandArt> jobDemandArts) {
		try {
			List<Long> ids = jobDemandArtDao.insertBatch(jobDemandArts);
			for (int i = 0; i < jobDemandArts.size(); i++) {
				JobDemandArt art = jobDemandArts.get(i);
				long id = ids.get(i);
				art.setId(id);
				searchService.indexContext(art);
			}
		} catch (SecurityException e) {
			LogUtil.error(errLog, "批量插入工作内容的时候出现异常，或者建立工作内容索引的时候，出现了异常，请排查", e);
		} catch (IllegalArgumentException e) {
			LogUtil.error(errLog, "批量插入工作内容的时候出现异常，或者建立工作内容索引的时候，出现了异常，请排查", e);
		} catch (NoSuchMethodException e) {
			LogUtil.error(errLog, "批量插入工作内容的时候出现异常，或者建立工作内容索引的时候，出现了异常，请排查", e);
		} catch (IllegalAccessException e) {
			LogUtil.error(errLog, "批量插入工作内容的时候出现异常，或者建立工作内容索引的时候，出现了异常，请排查", e);
		} catch (InvocationTargetException e) {
			LogUtil.error(errLog, "批量插入工作内容的时候出现异常，或者建立工作内容索引的时候，出现了异常，请排查", e);
		}

	}
}
