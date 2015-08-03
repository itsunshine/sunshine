/**
 * 
 */
package com.boliao.sunshine.biz.service.impl;

import java.lang.reflect.InvocationTargetException;

import com.boliao.sunshine.biz.dao.UserDao;
import com.boliao.sunshine.biz.dao.impl.UserDaoImpl;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.User;
import com.boliao.sunshine.biz.service.UserService;

/**
 * @author liaobo.lb
 * 
 */
public class UserServiceImpl implements UserService {

	private static UserService userService = new UserServiceImpl();

	private final UserDao userDao = UserDaoImpl.getInstance();

	private UserServiceImpl() {
	};

	public static UserService getInstance() {
		if (userService == null) {
			synchronized (userService) {
				if (userService == null) {
					userService = new UserServiceImpl();
				}
			}
		}
		return userService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.UserService#createUser(javax.xml.registry
	 * .infomodel.User)
	 */
	@Override
	public boolean createUser(User user) {
		try {
			userDao.createUser(user);
			return true;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.UserService#deleteUser(java.lang.Long)
	 */
	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boliao.sunshine.biz.service.UserService#getUser(java.lang.Long)
	 */
	@Override
	public User getUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.UserService#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String username) {
		try {
			User user = userDao.getUser(username);
			return user;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.UserService#getUserPage(com.boliao.sunshine
	 * .biz.model.PageBase)
	 */
	@Override
	public PageBase<User> getUserPage(PageBase<User> page) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.UserService#getUserPage(com.boliao.sunshine
	 * .biz.model.PageBase, java.lang.Long)
	 */
	@Override
	public PageBase<User> getUserPage(PageBase<User> page, Long siteId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.UserService#getUserPage(com.boliao.sunshine
	 * .biz.model.PageBase, java.lang.String)
	 */
	@Override
	public PageBase<User> getUserPage(PageBase<User> page, String startWith) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.UserService#registerUser(javax.xml.registry
	 * .infomodel.User)
	 */
	@Override
	public void registerUser(User user) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.UserService#updateUser(javax.xml.registry
	 * .infomodel.User)
	 */
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub

	}

}
