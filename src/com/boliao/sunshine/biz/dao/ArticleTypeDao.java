/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import com.boliao.sunshine.biz.model.ArticleType;

/**
 * @author liaobo
 *
 */
public interface ArticleTypeDao {
	public ArticleType findArticleTypeById(long id) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException;
	public long insert(ArticleType articleType) throws Exception;
}
