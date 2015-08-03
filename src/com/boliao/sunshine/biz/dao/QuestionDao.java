/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.boliao.sunshine.biz.model.Question;

/**
 * @author liaobo
 *
 */
public interface QuestionDao {
	public long insert(Question question)  throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException ;
	public List<Question> getPageQuestion(String type, int max);
	public Question findQuestionById(long id) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException;
	public boolean update(Question question);
	public boolean deleteById(long questionId);
	public List<Question> getPageQuestionDesc(String field, int start, int pageSize) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
	public long getTotalCount();
	/**
	 * 更新点击量，即在原来点击量的基础上，增加一次点击
	 * @param questionId
	 */
	public int updateClickNumber(int questionId);
}
