/**
 * 
 */
package com.boliao.sunshine.biz.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.dao.JobCorporationDao;
import com.boliao.sunshine.biz.dao.impl.JobCorperationDaoImpl;
import com.boliao.sunshine.biz.model.JobCorporation;
import com.boliao.sunshine.biz.service.JobCorperationService;
import com.boliao.sunshine.biz.utils.LogUtil;

/**
 * 工作所在城市，服务类
 * 
 * @author liaobo
 */
public class JobCorperationServiceImpl implements JobCorperationService {

	/** 错误日志记录器 */
	private final Logger errLog = Logger.getLogger(LogUtil.ERROR);

	private final JobCorporationDao jobCorporationDao;

	static JobCorperationServiceImpl jobCityServiceImpl = new JobCorperationServiceImpl();

	private JobCorperationServiceImpl() {
		this.jobCorporationDao = JobCorperationDaoImpl.getInstance();
	}

	public static JobCorperationServiceImpl getInstance() {
		if (jobCityServiceImpl == null) {
			synchronized (JobCorperationServiceImpl.class) {
				if (jobCityServiceImpl == null)
					jobCityServiceImpl = new JobCorperationServiceImpl();
			}
		}
		return jobCityServiceImpl;
	}

	@Override
	public JobCorporation findJobCityById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobCorporation> queryAllJobCorporation() {

		return jobCorporationDao.queryAllCorporation();
	}
}
