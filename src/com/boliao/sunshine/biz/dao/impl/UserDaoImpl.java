/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.lang.reflect.InvocationTargetException;

import com.boliao.sunshine.biz.dao.UserDao;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.User;
import com.boliao.sunshine.dao.AbstractDao;

/**
 * @author liaobo.lb
 * 
 */
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

	public static UserDaoImpl userDaoImpl = new UserDaoImpl();

	private final static String WHERE_USERNAME = "where email=";

	private UserDaoImpl() {
	};

	public static UserDaoImpl getInstance() {
		if (userDaoImpl == null) {
			synchronized (userDaoImpl) {
				if (userDaoImpl == null) {
					userDaoImpl = new UserDaoImpl();
				}
			}
		}
		return userDaoImpl;
	}

	@Override
	public long createUser(User user) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String insertSql = insertString(user, User.class);
		return this.execute(insertSql);
	}

	public static void main(String[] args) throws Exception {
		UserDaoImpl userDao = UserDaoImpl.getInstance();
		User user = new User();
		user.setAddress("testAddress");
		user.setUserName("username2");
		user.setEmail("liaobo2@163.com");
		user.setBirthDay("1986-09-09");
		user.setPassword("liaobo626");
		userDao.createUser(user);
		// User user = userDao.getUser("username");
		System.out.println(user);
	}

	@Override
	public void deleteUser(Long userId) {

	}

	@Override
	public User getUser(Long id) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String whereSql = WHERE_PREFIX + id;
		String seletSql = this.selectString(User.class, whereSql);
		User user = this.executeQuery(seletSql, User.class);
		return user;
	}

	@Override
	public User getUser(String username) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String whereSql = WHERE_USERNAME + "'" + username + "'";
		String seletSql = this.selectString(User.class, whereSql);
		User user = this.executeQuery(seletSql, User.class);
		return user;
	}

	@Override
	public PageBase<User> getUserPage(PageBase<User> page) {
		return null;
	}

	@Override
	public long registerUser(User user) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return createUser(user);
	}

	@Override
	public void updateUser(User user) {

	}

}
