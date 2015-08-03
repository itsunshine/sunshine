/**
 * 
 */
package com.boliao.sunshine.biz.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.dao.JobCityDao;
import com.boliao.sunshine.biz.dao.impl.JobCityDaoImpl;
import com.boliao.sunshine.biz.model.JobCity;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.service.JobCityService;
import com.boliao.sunshine.biz.utils.LogUtil;

/**
 * 工作所在城市，服务类
 * 
 * @author liaobo
 */
public class JobCityServiceImpl implements JobCityService {

	/** 错误日志记录器 */
	private final Logger errLog = Logger.getLogger(LogUtil.ERROR);

	private final JobCityDao jobCityDao;

	static JobCityServiceImpl jobCityServiceImpl = new JobCityServiceImpl();

	private JobCityServiceImpl() {
		this.jobCityDao = JobCityDaoImpl.getInstance();
	}

	public static JobCityServiceImpl getInstance() {
		if (jobCityServiceImpl == null) {
			synchronized (JobCityServiceImpl.class) {
				if (jobCityServiceImpl == null)
					jobCityServiceImpl = new JobCityServiceImpl();
			}
		}
		return jobCityServiceImpl;
	}

	@Override
	public JobCity findJobCityById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageBase<JobCity> getPageJobDemandArtDesc(String field, PageBase<JobCity> page) {
		return null;
	}

	@Override
	public List<JobCity> queryAllJobCity() {

		return jobCityDao.queryAllCity();
	}
}
