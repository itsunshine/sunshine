/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.dao.QuestionDao;
import com.boliao.sunshine.biz.model.Question;
import com.boliao.sunshine.dao.AbstractDao;
import com.boliao.sunshine.util.CommonConstants;

/**
 * @author liaobo
 * 
 */
public class QuestionDaoImpl extends AbstractDao<Question> implements
		QuestionDao {

	public static QuestionDaoImpl questionDaoImpl = new QuestionDaoImpl();

	// 日志记录器
	static Logger logger = Logger.getLogger(QuestionDaoImpl.class);

	private QuestionDaoImpl() {
	};

	public static QuestionDaoImpl getInstance() {
		if (questionDaoImpl == null) {
			synchronized (QuestionDaoImpl.class) {
				if (questionDaoImpl == null) {
					questionDaoImpl = new QuestionDaoImpl();
				}
			}
		}
		return questionDaoImpl;
	}

	@Override
	public boolean deleteById(long aticleId) {

		return false;
	}

	@Override
	public long insert(Question question) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String insertSql = insertString(question, Question.class);
		System.out.println("insertSql : " + insertSql);
		return this.execute(insertSql);
	}

	@Override
	public boolean update(Question question) {
		return false;
	}

	@Override
	public List<Question> getPageQuestion(String type, int max) {

		return null;
	}

	public List<Question> getPageQuestionDesc(String field, int start,
			int pageSize) throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		// 记录开始，操作的时间
		long begin = System.currentTimeMillis();

		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "page_question_" + start + "_" + pageSize;
		List<Question> questions = (List<Question>) cacheUtil.getElement(
				CommonConstants.PAGE_CACHE_KEY, key);
		if (questions != null && questions.size() > 0) {
			long end = System.currentTimeMillis();
			logger.info("分页查询命中缓存，耗时：" + (end - begin) + " ms. the key is "
					+ key);
			return questions;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("order by ").append(field).append(" desc limit ").append(
				start).append(",").append(pageSize);
		String selectStr = this
				.selectPagesString(Question.class, sb.toString());
		List<Question> result = this
				.executeQueryPage(selectStr, Question.class);

		long end = System.currentTimeMillis();
		logger.info("分页查询，耗时：" + (end - begin) + " ms. the key is " + key);
		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.PAGE_CACHE_KEY, key, result);
		return result;
	}

	@Override
	public int updateClickNumber(int questionId) {

		return 0;
	}

	@Override
	public Question findQuestionById(long id) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, SQLException {

		// 记录开始，操作的时间
		long begin = System.currentTimeMillis();
		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "con_question_" + id;
		Question question = (Question) cacheUtil.getElement(
				CommonConstants.CON_CACHE_KEY, key);
		if (question != null) {
			long end = System.currentTimeMillis();
			logger.info("根据id查询命中缓存，耗时：" + (end - begin) + " ms. the key is "
					+ key);
			return question;
		}

		String whereSql = WHERE_PREFIX + id;
		String seletSql = this.selectString(Question.class, whereSql);
		question = this.executeQuery(seletSql, Question.class);

		long end = System.currentTimeMillis();
		logger.info("根据id查询，耗时：" + (end - begin) + " ms. the key is " + key);
		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.CON_CACHE_KEY, key, question);
		return question;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * Question question = new Question(); question.setTitle("testTitle");
		 * question.setContent("testContent"); question.setTypeId(1); Date date
		 * = new Date(); SimpleDateFormat sdf = new
		 * SimpleDateFormat(DATE_FORMATE); String time = sdf.format(date);
		 * question.setCreateTime(time); question.setUpdateTime(time);
		 */
		QuestionDaoImpl a = new QuestionDaoImpl();
		// a.insert(question);
		List<Question> pages = a.getPageQuestionDesc("createTime", 0, 2);
		for (Question question : pages) {
			System.out.println(question);
		}
		// Question question = a.findQuestionById(66);

	}

	@Override
	public long getTotalCount() {
		return this.getTotalCount(Question.class, null);
	}

}
