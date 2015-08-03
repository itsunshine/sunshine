/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.boliao.sunshine.biz.model.Doc;

/**
 * @author liaobo
 *
 */
public interface DocDao {
	public long insert(Doc doc)  throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException ;
	public List<Doc> getPageDoc(String type, int max);
	public Doc findDocById(long id) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException;
	public boolean update(Doc doc);
	public boolean deleteById(long docId);
	public List<Doc> getPageDocDesc(String field, int start, int pageSize) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
	public long getTotalCount();
	/**
	 * 更新点击量，即在原来点击量的基础上，增加一次点击
	 * @param docId
	 */
	public int updateClickNumber(int docId);
}
