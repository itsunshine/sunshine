package com.boliao.sunshine.biz.service;

import com.boliao.sunshine.biz.model.BaseModel;
import com.boliao.sunshine.biz.model.JobDemandArt;
import com.boliao.sunshine.biz.model.PageBase;

public interface SearchService {
	public void indexContext(Object... obj);

	public PageBase<BaseModel> searchIndex(String keyWords, String type, PageBase<BaseModel> page);

	public PageBase<JobDemandArt> searchIndex(String keyWords, String type, int pageNo);
}
