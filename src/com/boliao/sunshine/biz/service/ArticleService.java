/**
 * 
 */
package com.boliao.sunshine.biz.service;

import com.boliao.sunshine.biz.model.Article;
import com.boliao.sunshine.biz.model.PageBase;

/**
 * @author liaobo
 * 
 */
public interface ArticleService {
	/**
	 * 根据id查找文章对象
	 * 
	 * @param id
	 * @return
	 */
	public Article findArticleById(long id);

	/**
	 * 分页查询文章对象
	 * 
	 * @param field
	 * @param page
	 * @return
	 */
	public PageBase<Article> getPageArticleDesc(String field,
			PageBase<Article> page);

	/**
	 * 插入文章对象
	 * 
	 * @param article
	 * @return
	 */
	public boolean insertArticle(Article article);
}
