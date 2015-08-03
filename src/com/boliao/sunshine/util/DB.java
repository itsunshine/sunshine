package com.boliao.sunshine.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * 数据库工具类
 * 
 * @author liaobo
 * 
 */
public class DB {
	static BasicDataSource ds = null;

	static Logger loger = Logger.getLogger(DB.class);

	public static void initDataSource() {
		ds = new BasicDataSource();
		ds.setMaxIdle(1000);
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/sunshine?useUnicode=true&characterEncoding=UTF-8");
		ds.setUsername("root");
		ds.setPassword("liaobo626");
		ds.setMaxActive(50);
		ds.setInitialSize(20);
		ds.setMinIdle(20);
		ds.setMaxIdle(30);
	}

	/** 新浪空间的数据库信息 */
	// public static void initDataSource() {
	// ds = new BasicDataSource();
	// ds.setDriverClassName("com.mysql.jdbc.Driver");
	// ds
	// .setUrl("jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_joke123?useUnicode=true&characterEncoding=UTF-8");
	// ds.setUsername("okkzzw342x");
	// ds.setPassword("2kykjy0hllzwmi3x5jhw4kimmhk5i2xi0my534il");
	// ds.setMaxActive(20);
	// ds.setInitialSize(20);
	// ds.setMinIdle(10);
	// ds.setMaxIdle(10);
	// }
	/** JHOST空间的数据库信息 */
	// public static void initDataSource() {
	// ds = new BasicDataSource();
	// ds.setDriverClassName("com.mysql.jdbc.Driver");
	// ds
	// .setUrl("jdbc:mysql://localhost/udb_liaobo626?useUnicode=true&characterEncoding=UTF-8");
	// ds.setUsername("liaobo626");
	// ds.setPassword("Lb+syj1986");
	// ds.setMaxActive(20);
	// ds.setInitialSize(20);
	// ds.setMinIdle(10);
	// ds.setMaxIdle(10);
	// }

	public static Connection getConn() {
		Connection conn = null;
		try {
			if (ds == null) {
				synchronized (DB.class) {
					if (ds == null) {
						initDataSource();
					}
				}
			}
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static PreparedStatement prepare(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try {
			if (conn != null) {
				pstmt = conn.prepareStatement(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static PreparedStatement prepare(Connection conn, String sql, int autoGenereatedKeys) {
		PreparedStatement pstmt = null;
		try {
			if (conn != null) {
				pstmt = conn.prepareStatement(sql, autoGenereatedKeys);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static Statement getStatement(Connection conn) {
		Statement stmt = null;
		try {
			if (conn != null) {
				stmt = conn.createStatement();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}

	/*
	 * public static ResultSet getResultSet(Connection conn, String sql) {
	 * Statement stmt = getStatement(conn); ResultSet rs = getResultSet(stmt,
	 * sql); close(stmt); return rs; }
	 */

	public static ResultSet getResultSet(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			if (stmt != null) {
				rs = stmt.executeQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void executeUpdate(Statement stmt, String sql) {
		try {
			if (stmt != null) {
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
				loger.info("线程池的链接数有：" + ds.getNumIdle() + "个");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
