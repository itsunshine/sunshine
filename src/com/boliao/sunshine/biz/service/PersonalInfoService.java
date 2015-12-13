/**
 * 
 */
package com.boliao.sunshine.biz.service;

import com.boliao.sunshine.biz.model.Education;
import com.boliao.sunshine.biz.model.JobExperiences;
import com.boliao.sunshine.biz.model.PersonBasicInfo;
import com.boliao.sunshine.biz.model.ProjExperiences;

/**
 * @author liaobo.lb 用户信息服务类
 * 
 */
public interface PersonalInfoService {

	/**
	 * 用户信息注册服务
	 * 
	 * @param pBasicInfo
	 *            个人基本信息
	 */
	void registerPersonBasicInfo(PersonBasicInfo pBasicInfo);

	/**
	 * 教育信息注册服务
	 * 
	 * @param education
	 */
	void registerPersonEduInfo(Education education);

	/**
	 * 注册工作经历信息
	 * 
	 * @param jobEp
	 */
	void registerJobExperiences(JobExperiences jobEp);

	/**
	 * 注册工作经验
	 * 
	 * @param projExperiences
	 */
	void registerProExperiences(ProjExperiences projExperiences);

}
