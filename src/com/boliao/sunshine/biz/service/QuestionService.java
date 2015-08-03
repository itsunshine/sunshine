/**
 * 
 */
package com.boliao.sunshine.biz.service;

import com.boliao.sunshine.biz.model.Answer;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.Question;

/**
 * @author liaobo
 * 
 */
public interface QuestionService {
	public Question findQuestionById(long id);

	public PageBase<Question> getPageQuestionDesc(String field,
			PageBase<Question> page);

	public boolean insertQuestion(Question question, Answer answer);

	public Answer findAnswerByQuestionId(long questionId);
}
