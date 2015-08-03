/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.dao.JobCityDao;
import com.boliao.sunshine.biz.model.JobCity;
import com.boliao.sunshine.dao.AbstractDao;

/**
 * @author liaobo
 * 
 */
public class JobCityDaoImpl extends AbstractDao<JobCity> implements JobCityDao {

	public static JobCityDaoImpl jobDemandArtDaoImpl = new JobCityDaoImpl();

	public static Logger logger = Logger.getLogger(JobCityDaoImpl.class);

	private JobCityDaoImpl() {
	};

	public static JobCityDaoImpl getInstance() {
		if (jobDemandArtDaoImpl == null) {
			synchronized (JobCityDaoImpl.class) {
				if (jobDemandArtDaoImpl == null) {
					jobDemandArtDaoImpl = new JobCityDaoImpl();
				}
			}
		}
		return jobDemandArtDaoImpl;
	}

	@Override
	public List<JobCity> queryAllCity() {
		String queryStr = "select id,location from t_jobCity";
		List<JobCity> cityList = this.executeQueryPage(queryStr, JobCity.class);
		return cityList;
	}

	public static void main(String[] args) throws Exception {
		JobCityDao dao = new JobCityDaoImpl();
		System.out.println(dao.queryAllCity());
	}

}
