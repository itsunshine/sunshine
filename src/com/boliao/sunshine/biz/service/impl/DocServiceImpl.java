/**
 * 
 */
package com.boliao.sunshine.biz.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.boliao.sunshine.biz.dao.DocDao;
import com.boliao.sunshine.biz.dao.impl.DocDaoImpl;
import com.boliao.sunshine.biz.model.Doc;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.service.DocService;
import com.boliao.sunshine.biz.service.SearchService;

/**
 * 技术文章，服务类
 * 
 * @author liaobo
 */
public class DocServiceImpl implements DocService {

	private final DocDao docDao;
	private final SearchService searchService;

	static DocServiceImpl articleServiceImpl = new DocServiceImpl();

	private DocServiceImpl() {
		this.docDao = DocDaoImpl.getInstance();
		searchService = SearchServiceImpl.getInstance();
	}

	public static DocServiceImpl getInstance() {
		if (articleServiceImpl == null) {
			synchronized (DocServiceImpl.class) {
				if (articleServiceImpl == null)
					articleServiceImpl = new DocServiceImpl();
			}
		}
		return articleServiceImpl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boliao.sunshine.biz.service.ArticleService#findArticleById(long)
	 */
	@Override
	public Doc findDocById(long id) {
		Doc doc = null;
		try {
			doc = docDao.findDocById(id);
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
		return doc;
	}

	@Override
	public PageBase<Doc> getPageDocDesc(String field, PageBase<Doc> page) {
		List<Doc> results = null;
		try {
			long totalCount = docDao.getTotalCount();
			if (totalCount == 0)
				return null;
			page.setTotalCount(totalCount);

			// 如果页码大于总页码，则设置页码为最后一页
			if (page.getPageNo() >= page.getTotalPage())
				page.setPageNo(page.getTotalPage());

			results = docDao.getPageDocDesc(field, page.getStart(), page
					.getPageSize());
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

	@Override
	public boolean insertDoc(Doc doc) {
		try {
			long id = docDao.insert(doc);
			doc.setId(id);
			searchService.indexContext(doc);
			return true;
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

}
