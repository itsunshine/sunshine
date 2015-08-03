/**
 * 
 */
package com.boliao.sunshine.biz.service;

import com.boliao.sunshine.biz.model.Answer;
import com.boliao.sunshine.biz.model.Doc;
import com.boliao.sunshine.biz.model.PageBase;

/**
 * @author liaobo
 *
 */
public interface DocService {
	public Doc findDocById(long id);
	public PageBase<Doc> getPageDocDesc(String field, PageBase<Doc> page);
	public boolean insertDoc(Doc doc);
}
