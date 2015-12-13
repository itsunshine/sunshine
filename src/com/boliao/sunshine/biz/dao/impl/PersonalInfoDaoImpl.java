/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.lang.reflect.InvocationTargetException;

import com.boliao.sunshine.biz.dao.PersonalInfoDao;
import com.boliao.sunshine.biz.model.Education;
import com.boliao.sunshine.biz.model.JobExperiences;
import com.boliao.sunshine.biz.model.PersonBasicInfo;
import com.boliao.sunshine.biz.model.ProjExperiences;
import com.boliao.sunshine.dao.AbstractDao;

/**
 * @author liaobo.lb
 * 
 */
public class PersonalInfoDaoImpl extends AbstractDao<PersonBasicInfo> implements PersonalInfoDao {

	public static PersonalInfoDaoImpl personalInfoDaoImpl = new PersonalInfoDaoImpl();

	private final static String WHERE_USERNAME = "where email=";

	private PersonalInfoDaoImpl() {
	};

	public static PersonalInfoDaoImpl getInstance() {
		if (personalInfoDaoImpl == null) {
			synchronized (PersonalInfoDaoImpl.class) {
				if (personalInfoDaoImpl == null) {
					personalInfoDaoImpl = new PersonalInfoDaoImpl();
				}
			}
		}
		return personalInfoDaoImpl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.dao.PersonalInfoDao#createPersonalInfo(com.boliao
	 * .sunshine.biz.model.PersonBasicInfo)
	 */
	@Override
	public long createPersonalInfo(PersonBasicInfo personBasicInfo) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		String insertSql = insertString(personBasicInfo, PersonBasicInfo.class);
		return this.execute(insertSql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.dao.PersonalInfoDao#createEducationInfo(com.boliao
	 * .sunshine.biz.model.Education)
	 */
	public long createEducationInfo(Education edu) {
		try {
			String insertSql = insertString(edu, Education.class);
			return this.execute(insertSql);
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
		return 0;
	}

	public static void main(String[] args) throws Exception {
		PersonalInfoDaoImpl personalInfoDao = PersonalInfoDaoImpl.getInstance();
		// PersonBasicInfo personBasicInfo = new PersonBasicInfo();
		// personBasicInfo.setEmail("liaobo2@163.com");
		// personBasicInfo.setUsername("廖博");
		// personBasicInfo.setBirthDay("1986-06-26");
		// personBasicInfo.setMarriage(0);
		// personBasicInfo.setSex(0);
		// personBasicInfo.setSkillDesc("java 擅长者");
		// personalInfoDao.createPersonalInfo(personBasicInfo);
		// userDao.createUser(user);
		// User user = userDao.getUser("username");
		// System.out.println(personBasicInfo);
		Education edu = new Education();
		edu.setEndTime("1986-06-26");
		edu.setStartTime("1989-06-26");
		edu.setSchool("长江大学");
		edu.setMayor("计算机科学与技术");
		edu.setProDesc("学号c++走遍天下都不怕");
		personalInfoDao.createEducationInfo(edu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.dao.PersonalInfoDao#createJobExperiences(com.
	 * boliao.sunshine.biz.model.JobExperiences)
	 */
	@Override
	public long createJobExperiences(JobExperiences jobEp) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		String insertSql = insertString(jobEp, JobExperiences.class);
		return this.execute(insertSql);
	}

	@Override
	public long createProExperiences(ProjExperiences projExperiences) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		String insertSql = insertString(projExperiences, ProjExperiences.class);
		return this.execute(insertSql);
	}

}
