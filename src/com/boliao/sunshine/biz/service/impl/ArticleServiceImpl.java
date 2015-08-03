/**
 * 
 */
package com.boliao.sunshine.biz.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.boliao.sunshine.biz.dao.AticleDao;
import com.boliao.sunshine.biz.dao.impl.ArticleDaoImpl;
import com.boliao.sunshine.biz.model.Article;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.service.ArticleService;
import com.boliao.sunshine.biz.service.SearchService;

/**
 * 技术文章，服务类
 * 
 * @author liaobo
 */
public class ArticleServiceImpl implements ArticleService {

	private final AticleDao articleDao;
	private final SearchService searchService;

	static ArticleServiceImpl articleServiceImpl = new ArticleServiceImpl();

	private ArticleServiceImpl() {
		this.articleDao = ArticleDaoImpl.getInstance();
		searchService = SearchServiceImpl.getInstance();
	}

	public static ArticleServiceImpl getInstance() {
		if (articleServiceImpl == null) {
			synchronized (ArticleServiceImpl.class) {
				if (articleServiceImpl == null)
					articleServiceImpl = new ArticleServiceImpl();
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
	public Article findArticleById(long id) {
		Article article = null;
		try {
			article = articleDao.findArticleById(id);
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
		return article;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.ArticleService#getPageArticleDesc(java
	 * .lang.String, com.boliao.sunshine.biz.model.PageBase)
	 */
	@Override
	public PageBase<Article> getPageArticleDesc(String field,
			PageBase<Article> page) {
		List<Article> results = null;
		try {
			long totalCount = articleDao.getTotalCount();
			if (totalCount == 0)
				return null;
			page.setTotalCount(totalCount);

			// 如果页码大于总页码，则设置页码为最后一页
			if (page.getPageNo() >= page.getTotalPage())
				page.setPageNo(page.getTotalPage());

			results = articleDao.getPageArticleDesc(field, page.getStart(),
					page.getPageSize());
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
	public boolean insertArticle(Article article) {
		try {
			long id = articleDao.insert(article);
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

}
