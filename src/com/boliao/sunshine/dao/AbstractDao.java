/**
 * 
 */
package com.boliao.sunshine.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.utils.LogUtil;
import com.boliao.sunshine.util.CacheUtil;
import com.boliao.sunshine.util.DB;
import com.boliao.sunshine.util.StrUtil;

/**
 * @author liaobo
 * 
 */
public class AbstractDao<T> {

	/** 错误日志记录器 */
	public static Logger errorLog = Logger.getLogger(LogUtil.ERROR);

	/** 日志记录器工具 */
	public static Logger logger = Logger.getLogger(AbstractDao.class);

	protected static final String TABLE_PREFIX = "t_";

	protected static final String WHERE_PREFIX = "where id = ";

	protected static final String DATE_FORMATE = "yyyy-MM-dd hh:mm:ss";

	public CacheUtil cacheUtil = CacheUtil.getInstance();

	/**
	 * 之查出域对象的id和title信息
	 * 
	 * @param beanClazz
	 * @param whereStr
	 * @return 分页对象集合
	 */
	public String selectPagesString(Class beanClazz, String whereStr) {
		String tableName = this.TABLE_PREFIX + beanClazz.getSimpleName().toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("select id, title from ").append(tableName).append(" ").append(whereStr);

		return sb.toString();
	}

	/**
	 * 查出值对象的分页信息，包括值对象的fields的值
	 * 
	 * @param beanClazz
	 * @param whereStr
	 * @param fileds
	 * @return
	 */
	public String selectPagesString(Class beanClazz, String whereStr, String[] columns) {
		if (columns == null) {
			return selectPagesString(beanClazz, whereStr);
		}
		String tableName = this.TABLE_PREFIX + beanClazz.getSimpleName().toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("select id, title, ");
		for (String colum : columns) {
			sb.append(colum).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ").append(tableName).append(" ").append(whereStr);

		return sb.toString();
	}

	public String selectString(Class beanClazz, String whereStr) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		List<Field> fields = getFields(beanClazz);
		String tableName = this.TABLE_PREFIX + beanClazz.getSimpleName().toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		for (Field f : fields) {
			String name = f.getName();
			sb.append(name).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(" from ").append(tableName).append(" ").append(whereStr);
		return sb.toString();
	}

	public long getTotalCount(Class beanClazz, String whereStr) {
		String tableName = this.TABLE_PREFIX + beanClazz.getSimpleName().toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ").append(tableName).append(StringUtils.defaultIfBlank(whereStr, ""));
		return queryNumber(sb.toString());
	}

	/**
	 * 获得所有的属性
	 * 
	 * @param t
	 * @param beanClazz
	 * @return
	 */
	public List<Field> getFields(Class beanClazz) {
		List<Field> fields = new ArrayList<Field>();
		for (Class clazz = beanClazz; clazz != Object.class; clazz = clazz.getSuperclass()) {
			Field[] fs = clazz.getDeclaredFields();
			for (Field f : fs) {
				fields.add(f);
			}

		}

		return fields;
	}

	@SuppressWarnings( { "static-access", "unchecked" })
	public String insertString(T t, Class beanClazz) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<Field> fields = getFields(beanClazz);

		String tableName = this.TABLE_PREFIX + beanClazz.getSimpleName().toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ").append(tableName).append(" (");
		StringBuilder values = new StringBuilder();
		values.append(" values(");
		boolean strFlag = false;
		boolean booleanFlag = false;
		boolean mapFlag = false;
		for (Field f : fields) {
			f.setAccessible(true);
			String name = f.getName();
			Class propertyType = f.getType();
			String getMethod = null;
			String upFirstCharName = StrUtil.upFirstChar(name);
			if (propertyType.isAssignableFrom(Boolean.class)) {
				getMethod = "get" + upFirstCharName;
				booleanFlag = true;
			} else if (propertyType.isAssignableFrom(String.class)) {
				getMethod = "get" + upFirstCharName;
				strFlag = true;
			} else if (propertyType.isAssignableFrom(HashMap.class)) {
				getMethod = "get" + upFirstCharName;
				mapFlag = true;
			} else {
				getMethod = "get" + upFirstCharName;
			}
			Method method = beanClazz.getMethod(getMethod, null);
			Object obj = method.invoke(t, null);
			if (obj != null) {
				if (strFlag) {
					values.append("'").append(obj.toString()).append("',");
				} else if (booleanFlag) {
					if (StringUtils.equals("true", obj.toString())) {
						values.append("1").append(",");
					} else {
						values.append("0").append(",");
					}
				} else if (mapFlag) {
					Map<String, String> mapField = (Map<String, String>) obj;
					if (mapField.size() == 0)
						continue;
					JSONObject jsonObject = JSONObject.fromObject(obj);
					values.append("'").append(jsonObject.toString()).append("',");
				} else {
					values.append(obj.toString()).append(",");
				}
				sb.append(name).append(",");
			}
			booleanFlag = false;
			strFlag = false;
			mapFlag = false;

		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(")");
		values.deleteCharAt(values.lastIndexOf(","));
		sb.append(" ").append(values).append(")");
		return sb.toString();
	}

	/**
	 * 执行sql的方法
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public long execute(String sql) {
		Connection con = DB.getConn();
		Statement statement = DB.getStatement(con);
		long id = 0;
		ResultSet rs = null;
		try {
			boolean result = statement.execute(sql);
			rs = statement.getGeneratedKeys();
			while (rs != null && rs.next()) {
				id = rs.getLong(1);
			}
			return id;
		} catch (Exception e) {
			LogUtil.error(errorLog, "执行sql时出错，请检查sql：" + sql, e);
		} finally {
			DB.close(rs);
			DB.close(statement);
			DB.close(con);
		}
		return id;

	}

	/**
	 * 批量执行sql语句
	 * 
	 * @param sqlList
	 */
	public List<Long> executeBatch(List<String> sqlList) {
		Connection con = DB.getConn();
		Statement statement = DB.getStatement(con);
		ResultSet rs = null;
		List<Long> list = new ArrayList<Long>();
		try {
			for (String sql : sqlList) {
				statement.addBatch(sql);
			}
			statement.executeBatch();
			rs = statement.getGeneratedKeys();
			while (rs != null && rs.next()) {
				list.add(rs.getLong(1));
			}
		} catch (Exception e) {
			LogUtil.error(errorLog, "批量执行sql时出错，请检查sql", e);
		} finally {
			DB.close(rs);
			DB.close(statement);
			DB.close(con);
		}
		return list;
	}

	private T asseblePages(ResultSet rs, Class<T> beanClazz) throws InstantiationException, IllegalAccessException, SQLException, IllegalArgumentException,
			InvocationTargetException, SecurityException, NoSuchMethodException {
		T o = beanClazz.newInstance();
		String[] pageFields = new String[] { "Id", "Title" };
		for (String field : pageFields) {
			String setMethod = "set" + field;
			Method method = null;
			if (field.equals("Id")) {
				method = beanClazz.getMethod(setMethod, Long.class);
				method.invoke(o, rs.getLong(field));
			} else {
				if (rs.getString(field) == null) {
					continue;
				}
				method = beanClazz.getMethod(setMethod, String.class);
				method.invoke(o, rs.getString(field));
			}

		}
		return o;
	}

	private T assemble(ResultSet rs, Class<T> beanClazz, List<Field> fields) throws InstantiationException, IllegalAccessException, SQLException, IllegalArgumentException,
			InvocationTargetException, SecurityException, NoSuchMethodException {
		T o = beanClazz.newInstance();
		for (Field f : fields) {
			f.setAccessible(true);
			String name = f.getName();
			Class propertyType = f.getType();
			String setMethod = null;
			String upFirstCharName = StrUtil.upFirstChar(name);
			setMethod = "set" + upFirstCharName;
			if (propertyType.isAssignableFrom(Boolean.class)) {
				Integer value = rs.getInt(name);
				if (value.equals(1)) {
					Method method = beanClazz.getMethod(setMethod, Boolean.class);
					method.invoke(o, true);
				} else {
					Method method = beanClazz.getMethod(setMethod, Boolean.class);
					method.invoke(o, false);
				}

			} else if (propertyType.isAssignableFrom(String.class)) {
				String value = rs.getString(name);
				Method method = beanClazz.getMethod(setMethod, String.class);
				method.invoke(o, value);
			} else if (propertyType.isAssignableFrom(Integer.class)) {
				Integer value = rs.getInt(name);
				Method method = beanClazz.getMethod(setMethod, Integer.class);
				method.invoke(o, value);
			} else if (propertyType.isAssignableFrom(Long.class)) {
				Long value = rs.getLong(name);
				Method method = beanClazz.getMethod(setMethod, Long.class);
				method.invoke(o, value);
			}
		}
		return o;
	}

	// 真正执行分页查询。
	public List<T> executeQueryPage(String sql, Class<T> beanClazz) {
		logger.info("接收到分页查询sql：" + sql);
		Connection con = DB.getConn();
		Statement stmt = DB.getStatement(con);
		ResultSet rs = null;
		try {
			rs = DB.getResultSet(stmt, sql);
			List<T> result = new ArrayList<T>();
			List<Field> fields = getFields(beanClazz, sql);
			while (rs.next()) {
				// T o = asseblePages(rs, beanClazz);
				T o = assemble(rs, beanClazz, fields);
				result.add(o);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(con);
		}
		return null;
	}

	public long queryNumber(String sql) {
		logger.info("接收到count查询sql：" + sql);
		Connection con = DB.getConn();
		Statement stmt = DB.getStatement(con);
		ResultSet rs = null;
		try {
			rs = DB.getResultSet(stmt, sql);
			long count = 0;
			while (rs.next()) {
				count = rs.getLong(1);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(con);
		}
		return 0;
	}

	public T executeQuery(String sql, Class<T> beanClazz) {
		Connection con = DB.getConn();
		Statement stmt = DB.getStatement(con);
		ResultSet rs = null;
		try {
			rs = DB.getResultSet(stmt, sql);
			T o = null;
			List<Field> fields = getFields(beanClazz, sql);
			while (rs.next()) {
				o = assemble(rs, beanClazz, fields);
			}
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(con);
		}
		return null;

	}

	/**
	 * 根据sql语句里的clumn，过滤无用的fields
	 * 
	 * @param beanClazz
	 * @param sql
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private List<Field> getFields(Class<T> beanClazz, String sql) throws InstantiationException, IllegalAccessException {
		List<Field> fields = getFields(beanClazz);
		List<Field> results = new ArrayList<Field>();
		// 根据sql查询语句，获取feild信息
		sql = sql.substring(sql.indexOf("select") + "select".length());
		sql = sql.substring(0, sql.indexOf("from"));
		String[] fs = sql.split(",");
		Set<String> fsArray = new HashSet<String>(fs.length);
		for (String f : fs) {
			fsArray.add(StringUtils.trim(f));
		}
		for (Field f : fields) {
			f.setAccessible(true);
			String name = f.getName();
			if (!fsArray.contains(name)) {
				continue;
			}
			results.add(f);
		}
		return results;

	}

}
