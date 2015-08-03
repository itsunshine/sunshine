/**
 * 
 */
package com.boliao.sunshine.biz.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.dao.JobCorporationDao;
import com.boliao.sunshine.biz.model.JobCorporation;
import com.boliao.sunshine.dao.AbstractDao;

/**
 * @author liaobo
 * 
 */
public class JobCorperationDaoImpl extends AbstractDao<JobCorporation> implements JobCorporationDao {

	public static JobCorperationDaoImpl jobDemandArtDaoImpl = new JobCorperationDaoImpl();

	public static Logger logger = Logger.getLogger(JobCorperationDaoImpl.class);

	private JobCorperationDaoImpl() {
	};

	public static JobCorperationDaoImpl getInstance() {
		if (jobDemandArtDaoImpl == null) {
			synchronized (JobCorperationDaoImpl.class) {
				if (jobDemandArtDaoImpl == null) {
					jobDemandArtDaoImpl = new JobCorperationDaoImpl();
				}
			}
		}
		return jobDemandArtDaoImpl;
	}

	@Override
	public List<JobCorporation> queryAllCorporation() {
		String queryStr = "select id,name from t_jobCorporation";
		List<JobCorporation> cityList = this.executeQueryPage(queryStr, JobCorporation.class);
		return cityList;
	}

	public static void main(String[] args) throws Exception {
		JobCorporationDao dao = new JobCorperationDaoImpl();
		System.out.println(dao.queryAllCorporation());
	}

}
