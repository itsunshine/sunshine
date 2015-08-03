/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.boliao.sunshine.biz.dao.AnswerDao;
import com.boliao.sunshine.biz.model.Answer;
import com.boliao.sunshine.dao.AbstractDao;
import com.boliao.sunshine.util.CommonConstants;

/**
 * answerDao
 * 
 * @author liaobo
 */
public class AnswerDaoImpl extends AbstractDao<Answer> implements AnswerDao {

	protected static final String WHERE_PREFIX = "where questionId = ";

	public static AnswerDaoImpl answerDaoImpl = new AnswerDaoImpl();

	private AnswerDaoImpl() {
	};

	public static AnswerDaoImpl getInstance() {
		if (answerDaoImpl == null) {
			synchronized (AnswerDaoImpl.class) {
				if (answerDaoImpl == null) {
					answerDaoImpl = new AnswerDaoImpl();
				}
			}
		}
		return answerDaoImpl;
	}

	@Override
	public boolean deleteById(long aticleId) {

		return false;
	}

	@Override
	public long insert(Answer answer) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String insertSql = insertString(answer, Answer.class);
		return this.execute(insertSql);
	}

	@Override
	public boolean update(Answer answer) {
		return false;
	}

	@Override
	public List<Answer> getPageAnswer(String type, int max) {

		return null;
	}

	public List<Answer> getPageAnswerDesc(String field, int start, int pageSize)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "page_answer_" + start + "_" + pageSize;
		List<Answer> answers = (List<Answer>) cacheUtil.getElement(
				CommonConstants.PAGE_CACHE_KEY, key);
		if (answers != null && answers.size() > 0)
			return answers;

		StringBuilder sb = new StringBuilder();
		sb.append("order by ").append(field).append(" desc limit ").append(
				start).append(",").append(pageSize);
		String selectStr = this.selectPagesString(Answer.class, sb.toString());
		List<Answer> result = this.executeQueryPage(selectStr, Answer.class);

		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.PAGE_CACHE_KEY, key, result);
		return result;
	}

	@Override
	public int updateClickNumber(int answerId) {

		return 0;
	}

	@Override
	public Answer findAnswerById(long id) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, SQLException {

		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "con_answer_" + id;
		Answer answer = (Answer) cacheUtil.getElement(
				CommonConstants.CON_CACHE_KEY, key);
		if (answer != null)
			return answer;

		String whereSql = WHERE_PREFIX + id;
		String seletSql = this.selectString(Answer.class, whereSql);
		answer = this.executeQuery(seletSql, Answer.class);

		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.CON_CACHE_KEY, key, answer);
		return answer;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * Answer answer = new Answer(); answer.setContent("testContent"); Date
		 * date = new Date(); SimpleDateFormat sdf = new
		 * SimpleDateFormat(DATE_FORMATE); String time = sdf.format(date);
		 * answer.setCreateTime(time); answer.setUpdateTime(time);
		 * answer.setAnswerId(71L);
		 */
		AnswerDaoImpl a = new AnswerDaoImpl();
		// a.insert(answer);
		// List<Answer> pages = a.getPageAnswerDesc("createTime",0, 2);
		Answer answer = a.findAnswerById(71);
		System.out.println(answer);
		// Answer answer = a.findAnswerById(66);

	}

	@Override
	public long getTotalCount() {
		return this.getTotalCount(Answer.class, null);
	}

	@Override
	public Answer findAnswerByQuestionId(long questionId) {
		Answer answer = null;
		try {
			answer = findAnswerById(questionId);
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
		return answer;
	}

}
