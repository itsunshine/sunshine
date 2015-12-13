/**
 * 
 */
package com.boliao.sunshine.biz.service.impl;

import java.lang.reflect.InvocationTargetException;

import com.boliao.sunshine.biz.dao.impl.PersonalInfoDaoImpl;
import com.boliao.sunshine.biz.model.Education;
import com.boliao.sunshine.biz.model.JobExperiences;
import com.boliao.sunshine.biz.model.PersonBasicInfo;
import com.boliao.sunshine.biz.model.ProjExperiences;
import com.boliao.sunshine.biz.service.PersonalInfoService;

/**
 * @author liaobo.lb
 * 
 */
public class PersonalInfoServiceImpl implements PersonalInfoService {

	private static PersonalInfoService personalInfoService = new PersonalInfoServiceImpl();

	private final PersonalInfoDaoImpl personalInfoDao = PersonalInfoDaoImpl.getInstance();

	/**
	 * 构造方法私有化
	 */
	private PersonalInfoServiceImpl() {
	};

	/**
	 * 获取服务类实例的单例方法
	 * 
	 * @return
	 */
	public static PersonalInfoService getInstance() {
		if (personalInfoService == null) {
			synchronized (PersonalInfoServiceImpl.class) {
				if (personalInfoService == null) {
					personalInfoService = new PersonalInfoServiceImpl();
				}
			}
		}
		return personalInfoService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.PersonalInfoService#registerPersonBasicInfo
	 * (com.boliao.sunshine.biz.model.PersonBasicInfo)
	 */
	@Override
	public void registerPersonBasicInfo(PersonBasicInfo pBasicInfo) {
		try {
			personalInfoDao.createPersonalInfo(pBasicInfo);
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.PersonalInfoService#registerPersonEduInfo
	 * (com.boliao.sunshine.biz.model.Education)
	 */
	@Override
	public void registerPersonEduInfo(Education education) {
		personalInfoDao.createEducationInfo(education);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.PersonalInfoService#registerJobExperiences
	 * (com.boliao.sunshine.biz.model.JobExperiences)
	 */
	@Override
	public void registerJobExperiences(JobExperiences jobEp) {
		try {
			personalInfoDao.createJobExperiences(jobEp);
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.service.PersonalInfoService#registerProExperiences
	 * (com.boliao.sunshine.biz.model.ProjExperiences)
	 */
	@Override
	public void registerProExperiences(ProjExperiences projExperiences) {
		try {
			personalInfoDao.createProExperiences(projExperiences);
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
	}

}
