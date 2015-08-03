/**
 * 
 */
package com.boliao.sunshine.biz.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.boliao.sunshine.biz.dao.AnswerDao;
import com.boliao.sunshine.biz.dao.impl.AnswerDaoImpl;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.Answer;
import com.boliao.sunshine.biz.service.AnswerService;
import com.boliao.sunshine.biz.service.SearchService;

/**
 * 技术文章，服务类
 * @author liaobo
 */
public class AnswerServiceImpl implements AnswerService {
	
	private AnswerDao answerDao;
	private SearchService searchService;
	
	static AnswerServiceImpl articleServiceImpl = new AnswerServiceImpl();
	private AnswerServiceImpl(){
		this.answerDao = AnswerDaoImpl.getInstance();
		searchService = SearchServiceImpl.getInstance();
	}
	 
	public static AnswerServiceImpl getInstance(){
		if(articleServiceImpl == null){
			synchronized(AnswerServiceImpl.class){
				if(articleServiceImpl == null)
					articleServiceImpl = new AnswerServiceImpl();
			}
		}
		return articleServiceImpl;
	}
	

	/* (non-Javadoc)
	 * @see com.boliao.sunshine.biz.service.ArticleService#findArticleById(long)
	 */
	@Override
	public Answer findAnswerById(long id) {
		Answer article = null;
		try {
			article = answerDao.findAnswerById(id);
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

	@Override
	public PageBase<Answer> getPageAnswerDesc(String field, PageBase<Answer> page) {
		List<Answer> results = null;
		try {
			long totalCount = answerDao.getTotalCount();
			if(totalCount == 0)
				return null;
			page.setTotalCount(totalCount);
			
			//如果页码大于总页码，则设置页码为最后一页
			if(page.getPageNo()>= page.getTotalPage())
				page.setPageNo(page.getTotalPage());
			
			results = answerDao.getPageAnswerDesc(field, page.getStart(), page.getPageSize());
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
	public boolean insertAnswer(Answer answer) {
		try {
			answerDao.insert(answer);
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
