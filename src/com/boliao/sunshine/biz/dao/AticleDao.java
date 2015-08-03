/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.boliao.sunshine.biz.model.Article;

/**
 * @author liaobo
 *
 */
public interface AticleDao {
	public long insert(Article article)  throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException ;
	public List<Article> getPageArticle(String type, int max);
	public Article findArticleById(long id) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException;
	public boolean update(Article article);
	public boolean deleteById(long aticleId);
	public List<Article> getPageArticleDesc(String field, int start, int pageSize) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
	public long getTotalCount();
	/**
	 * 更新点击量，即在原来点击量的基础上，增加一次点击
	 * @param articleId
	 */
	public int updateClickNumber(int articleId);
}
