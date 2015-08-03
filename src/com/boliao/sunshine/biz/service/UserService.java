/**
 * 
 */
package com.boliao.sunshine.biz.service;

import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.User;

/**
 * @author liaobo.lb
 * 
 */
public interface UserService {

	User getUser(Long userId);

	User getUser(String username);

	boolean createUser(User user);

	void updateUser(User user);

	void deleteUser(Long userId);

	PageBase<User> getUserPage(PageBase<User> page);

	PageBase<User> getUserPage(PageBase<User> page, Long siteId);

	PageBase<User> getUserPage(PageBase<User> page, String startWith);

	void registerUser(User user);

}
