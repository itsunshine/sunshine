/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.dao.AticleDao;
import com.boliao.sunshine.biz.model.Article;
import com.boliao.sunshine.dao.AbstractDao;
import com.boliao.sunshine.util.CommonConstants;

/**
 * @author liaobo
 * 
 */
public class ArticleDaoImpl extends AbstractDao<Article> implements AticleDao {

	public static ArticleDaoImpl articleDaoImpl = new ArticleDaoImpl();

	public static Logger logger = Logger.getLogger(ArticleDaoImpl.class);

	private ArticleDaoImpl() {
	};

	public static ArticleDaoImpl getInstance() {
		if (articleDaoImpl == null) {
			synchronized (ArticleDaoImpl.class) {
				if (articleDaoImpl == null) {
					articleDaoImpl = new ArticleDaoImpl();
				}
			}
		}
		return articleDaoImpl;
	}

	@Override
	public boolean deleteById(long aticleId) {

		return false;
	}

	@Override
	public long insert(Article article) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String insertSql = insertString(article, Article.class);
		return this.execute(insertSql);
	}

	@Override
	public boolean update(Article article) {
		return false;
	}

	@Override
	public List<Article> getPageArticle(String type, int max) {

		return null;
	}

	public List<Article> getPageArticleDesc(String field, int start,
			int pageSize) throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		// 记录开始，操作的时间
		long begin = System.currentTimeMillis();

		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "page_article_" + start + "_" + pageSize;
		List<Article> articles = (List<Article>) cacheUtil.getElement(
				CommonConstants.PAGE_CACHE_KEY, key);
		if (articles != null && articles.size() > 0) {
			long end = System.currentTimeMillis();
			logger.info("分页查询命中缓存，耗时：" + (end - begin) + " ms. the key is "
					+ key);
			return articles;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("order by ").append(field).append(" desc limit ").append(
				start).append(",").append(pageSize);
		String selectStr = this.selectPagesString(Article.class, sb.toString());
		List<Article> result = this.executeQueryPage(selectStr, Article.class);

		long end = System.currentTimeMillis();
		logger.info("分页查询，耗时：" + (end - begin) + " ms. the key is " + key);
		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.PAGE_CACHE_KEY, key, result);

		return result;
	}

	@Override
	public int updateClickNumber(int articleId) {

		return 0;
	}

	@Override
	public Article findArticleById(long id) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, SQLException {

		// 记录开始，操作的时间
		long begin = System.currentTimeMillis();
		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "con_article_" + id;
		Article article = (Article) cacheUtil.getElement(
				CommonConstants.CON_CACHE_KEY, key);
		if (article != null) {
			long end = System.currentTimeMillis();
			logger.info("根据id查询命中缓存，耗时：" + (end - begin) + " ms. the key is "
					+ key);
			return article;
		}

		String whereSql = WHERE_PREFIX + id;
		String seletSql = this.selectString(Article.class, whereSql);
		article = this.executeQuery(seletSql, Article.class);

		long end = System.currentTimeMillis();
		logger.info("根据id查询，耗时：" + (end - begin) + " ms. the key is " + key);
		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.CON_CACHE_KEY, key, article);

		return article;
	}

	public static void main(String[] args) throws Exception {

		Article article = new Article();
		article.setTitle("testTitle");
		article.setContent("testContent");
		article.setTypeId(1);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE);
		String time = sdf.format(date);
		article.setCreateTime(time);
		article.setUpdateTime(time);
		article.setDeployTime(time);

		ArticleDaoImpl a = new ArticleDaoImpl();
		a.insert(article);
		/*
		 * List<Article> pages = a.getPageArticleDesc("createTime", 0, 2); for
		 * (Article article : pages) { System.out.println(article); }
		 */
		// Article article = a.findArticleById(66);

	}

	@Override
	public long getTotalCount() {
		return this.getTotalCount(Article.class, null);
	}

}
