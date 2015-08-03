/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import com.boliao.sunshine.biz.dao.ArticleTypeDao;
import com.boliao.sunshine.biz.model.ArticleType;
import com.boliao.sunshine.dao.AbstractDao;

/**
 * @author liaobo
 *
 */
public class ArticleTypeDaoImpl extends AbstractDao<ArticleType> implements
		ArticleTypeDao {

	/* (non-Javadoc)
	 * @see com.boliao.sunshine.biz.dao.ArticleTypeDao#findArticleTypeById(long)
	 */
	@Override
	public ArticleType findArticleTypeById(long id) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, SQLException {
		String whereStr = this.WHERE_PREFIX + id;
		String selectStr = this.selectString(ArticleType.class, whereStr);
		ArticleType type = this.executeQuery(selectStr, ArticleType.class);
		return type;
	}

	@Override
	public long insert(ArticleType articleType) throws Exception{
		String insertSelect = this.insertString(articleType, ArticleType.class);
		return this.execute(insertSelect);
	}
	
	public static void main(String[] args) throws Exception {
		ArticleType type = new ArticleType();
		type.setType("testType");
		ArticleTypeDaoImpl a = new ArticleTypeDaoImpl();
		a.insert(type);
		type = a.findArticleTypeById(1);
		System.out.println(type);
	}

}
