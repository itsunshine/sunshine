/**
 * 
 */
package com.boliao.sunshine.biz.service;

import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.Answer;

/**
 * @author liaobo
 *
 */
public interface AnswerService {
	public Answer findAnswerById(long id);
	public PageBase<Answer> getPageAnswerDesc(String field, PageBase<Answer> page);
	public boolean insertAnswer(Answer answer);
}
