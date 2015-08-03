/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.boliao.sunshine.biz.model.Answer;

/**
 * @author liaobo
 * 
 */
public interface AnswerDao {
	public long insert(Answer answer) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException;

	public List<Answer> getPageAnswer(String type, int max);

	public Answer findAnswerById(long id) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, SQLException;

	public boolean update(Answer answer);

	public boolean deleteById(long answerId);

	public List<Answer> getPageAnswerDesc(String field, int start, int pageSize)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException;

	public long getTotalCount();

	/**
	 * 根据问题Id查询答案
	 * 
	 * @param questionId
	 * @return
	 */
	public Answer findAnswerByQuestionId(long questionId);

	/**
	 * 更新点击量，即在原来点击量的基础上，增加一次点击
	 * 
	 * @param answerId
	 */
	public int updateClickNumber(int answerId);
}
