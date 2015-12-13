/**
 * 
 */
package com.boliao.sunshine.biz.dao;

import java.lang.reflect.InvocationTargetException;

import com.boliao.sunshine.biz.model.Education;
import com.boliao.sunshine.biz.model.JobExperiences;
import com.boliao.sunshine.biz.model.PersonBasicInfo;
import com.boliao.sunshine.biz.model.ProjExperiences;

/**
 * @author liaobo.lb 用户信息dao
 * 
 */
public interface PersonalInfoDao {

	/**
	 * 在数据库中，创建一条用户基本信息记录
	 * 
	 * @param personBasicInfo
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	long createPersonalInfo(PersonBasicInfo personBasicInfo) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException;

	/**
	 * 创建教育经历记录
	 * 
	 * @param edu
	 * @return
	 */
	public long createEducationInfo(Education edu);

	/**
	 * 创建工作经历记录
	 * 
	 * @param jobEp
	 * @return
	 */
	public long createJobExperiences(JobExperiences jobEp) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException;

	/**
	 * 创建工作经验记录
	 * 
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public long createProExperiences(ProjExperiences projExperiences) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException;

}
