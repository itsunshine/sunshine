/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.boliao.sunshine.biz.dao.DocDao;
import com.boliao.sunshine.biz.model.Doc;
import com.boliao.sunshine.dao.AbstractDao;
import com.boliao.sunshine.util.CommonConstants;

/**
 * @author liaobo
 * 
 */
public class DocDaoImpl extends AbstractDao<Doc> implements DocDao {

	public static DocDaoImpl docDaoImpl = new DocDaoImpl();

	private DocDaoImpl() {
	};

	public static DocDaoImpl getInstance() {
		if (docDaoImpl == null) {
			synchronized (DocDaoImpl.class) {
				if (docDaoImpl == null) {
					docDaoImpl = new DocDaoImpl();
				}
			}
		}
		return docDaoImpl;
	}

	@Override
	public boolean deleteById(long aticleId) {

		return false;
	}

	@Override
	public long insert(Doc doc) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String insertSql = insertString(doc, Doc.class);
		return this.execute(insertSql);
	}

	@Override
	public boolean update(Doc doc) {
		return false;
	}

	@Override
	public List<Doc> getPageDoc(String type, int max) {

		return null;
	}

	public List<Doc> getPageDocDesc(String field, int start, int pageSize)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "page_doc_" + start + "_" + pageSize;
		List<Doc> docs = (List<Doc>) cacheUtil.getElement(
				CommonConstants.PAGE_CACHE_KEY, key);
		if (docs != null && docs.size() > 0)
			return docs;

		StringBuilder sb = new StringBuilder();
		sb.append("order by ").append(field).append(" desc limit ").append(
				start).append(",").append(pageSize);
		String selectStr = this.selectPagesString(Doc.class, sb.toString());
		List<Doc> result = this.executeQueryPage(selectStr, Doc.class);

		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.PAGE_CACHE_KEY, key, result);
		return result;
	}

	@Override
	public int updateClickNumber(int docId) {

		return 0;
	}

	@Override
	public Doc findDocById(long id) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, SQLException {

		// 先从查缓存里面取数据，如果取不到，再从数据库里面查出来
		String key = "con_doc_" + id;
		Doc doc = (Doc) cacheUtil
				.getElement(CommonConstants.CON_CACHE_KEY, key);
		if (doc != null)
			return doc;

		String whereSql = WHERE_PREFIX + id;
		String seletSql = this.selectString(Doc.class, whereSql);
		doc = this.executeQuery(seletSql, Doc.class);

		// 新查出来的数据放入缓存
		cacheUtil.putCache(CommonConstants.CON_CACHE_KEY, key, doc);
		return doc;
	}

	public static void main(String[] args) throws Exception {
		Doc doc = new Doc();
		doc.setTitle("JDK5/JAVA5 API 在线文档");
		doc.setContent("/docs/java/index.html");
		doc.setTypeId(1);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE);
		String time = sdf.format(date);
		doc.setCreateTime(time);
		doc.setUpdateTime(time);
		DocDaoImpl a = new DocDaoImpl();
		a.insert(doc);
		// List<Doc> pages = a.getPageDocDesc("createTime", 0, 2);
		// for (Doc doc : pages) {
		// System.out.println(doc);
		// }
		// Doc doc = a.findDocById(66);

	}

	@Override
	public long getTotalCount() {
		return this.getTotalCount(Doc.class, null);
	}

}
