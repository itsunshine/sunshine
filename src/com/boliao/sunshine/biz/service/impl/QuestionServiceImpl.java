/**
 * 
 */
package com.boliao.sunshine.biz.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.boliao.sunshine.biz.dao.AnswerDao;
import com.boliao.sunshine.biz.dao.QuestionDao;
import com.boliao.sunshine.biz.dao.impl.AnswerDaoImpl;
import com.boliao.sunshine.biz.dao.impl.QuestionDaoImpl;
import com.boliao.sunshine.biz.model.Answer;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.Question;
import com.boliao.sunshine.biz.service.QuestionService;
import com.boliao.sunshine.biz.service.SearchService;

/**
 * 技术文章，服务类
 * 
 * @author liaobo
 */
public class QuestionServiceImpl implements QuestionService {

	private final QuestionDao questionDao;
	private final AnswerDao answerDao;
	private final SearchService searchService;

	static QuestionServiceImpl articleServiceImpl = new QuestionServiceImpl();

	private QuestionServiceImpl() {
		this.questionDao = QuestionDaoImpl.getInstance();
		answerDao = AnswerDaoImpl.getInstance();
		searchService = SearchServiceImpl.getInstance();
	}

	public static QuestionServiceImpl getInstance() {
		if (articleServiceImpl == null) {
			synchronized (QuestionServiceImpl.class) {
				if (articleServiceImpl == null)
					articleServiceImpl = new QuestionServiceImpl();
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
	public Question findQuestionById(long id) {
		Question article = null;
		try {
			article = questionDao.findQuestionById(id);
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
	public PageBase<Question> getPageQuestionDesc(String field,
			PageBase<Question> page) {
		List<Question> results = null;
		try {
			long totalCount = questionDao.getTotalCount();
			if (totalCount == 0)
				return null;
			page.setTotalCount(totalCount);

			// 如果页码大于总页码，则设置页码为最后一页
			if (page.getPageNo() >= page.getTotalPage())
				page.setPageNo(page.getTotalPage());

			results = questionDao.getPageQuestionDesc(field, page.getStart(),
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

	@Override
	public boolean insertQuestion(Question question, Answer answer) {
		try {
			long id = questionDao.insert(question);
			question.setId(id);
			answer.setQuestionId(id);
			answerDao.insert(answer);
			searchService.indexContext(question, answer);
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

	@Override
	public Answer findAnswerByQuestionId(long questionId) {
		Answer answer = answerDao.findAnswerByQuestionId(questionId);
		return answer;
	}

}
