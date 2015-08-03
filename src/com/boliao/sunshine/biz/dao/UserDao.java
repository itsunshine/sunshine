/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.lang.reflect.InvocationTargetException;

import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.User;

/**
 * @author liaobo.lb
 * 
 */
public interface UserDao {

	User getUser(Long id) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	User getUser(String username) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	long createUser(User user) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void updateUser(User user);

	void deleteUser(Long userId);

	PageBase<User> getUserPage(PageBase<User> page);

	long registerUser(User user) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

}
