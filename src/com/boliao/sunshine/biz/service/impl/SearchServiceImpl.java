package com.boliao.sunshine.biz.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.dao.JobDemandArtDao;
import com.boliao.sunshine.biz.dao.impl.JobDemandArtDaoImpl;
import com.boliao.sunshine.biz.model.BaseModel;
import com.boliao.sunshine.biz.model.JobDemandArt;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.service.SearchService;
import com.boliao.sunshine.biz.utils.LogUtil;
import com.boliao.sunshine.util.TextFileIndexer;

public class SearchServiceImpl implements SearchService {

	/** 错误日志 */
	private static final Logger errLog = Logger.getLogger(LogUtil.ERROR);

	TextFileIndexer textFileIndexer;
	JobDemandArtDao jobDemandArtDao;

	static SearchServiceImpl searchServiceImpl = new SearchServiceImpl();

	private SearchServiceImpl() {
		textFileIndexer = TextFileIndexer.getInstance();
		jobDemandArtDao = JobDemandArtDaoImpl.getInstance();
	}

	public static SearchServiceImpl getInstance() {
		if (searchServiceImpl == null) {
			synchronized (SearchServiceImpl.class) {
				if (searchServiceImpl == null)
					searchServiceImpl = new SearchServiceImpl();
			}

		}
		return searchServiceImpl;
	}

	@Override
	public void indexContext(Object... obj) {
		try {
			textFileIndexer.index(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PageBase<BaseModel> searchIndex(String keyWords, String type, PageBase<BaseModel> page) {
		List<BaseModel> result = textFileIndexer.search(keyWords, type, page);
		page.setResults(result);
		return page;
	}

	@Override
	public PageBase<JobDemandArt> searchIndex(String keyWords, String type, int pageNo) {
		PageBase<JobDemandArt> page = new PageBase<JobDemandArt>();
		page.setPageNo(pageNo);
		List<Long> idList = textFileIndexer.searchSocailJobDemand(keyWords, type, page);
		List<JobDemandArt> arts = new ArrayList<JobDemandArt>(idList.size());
		for (Long id : idList) {
			JobDemandArt jobDemandArt = null;
			try {
				jobDemandArt = jobDemandArtDao.findArticleById(id);
			} catch (SecurityException e) {
				LogUtil.error(errLog, "全文搜索工作内容时，抛了异常，请排查", e);
			} catch (IllegalArgumentException e) {
				LogUtil.error(errLog, "全文搜索工作内容时，抛了异常，请排查", e);
			} catch (NoSuchMethodException e) {
				LogUtil.error(errLog, "全文搜索工作内容时，抛了异常，请排查", e);
			} catch (IllegalAccessException e) {
				LogUtil.error(errLog, "全文搜索工作内容时，抛了异常，请排查", e);
			} catch (InvocationTargetException e) {
				LogUtil.error(errLog, "全文搜索工作内容时，抛了异常，请排查", e);
			} catch (InstantiationException e) {
				LogUtil.error(errLog, "全文搜索工作内容时，抛了异常，请排查", e);
			} catch (SQLException e) {
				LogUtil.error(errLog, "全文搜索工作内容时，抛了异常，请排查", e);
			}
			arts.add(jobDemandArt);
		}
		page.setResults(arts);
		return page;
	}

}
