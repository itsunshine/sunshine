/**
 * 
 */
package com.boliao.sunshine.biz.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.boliao.sunshine.biz.model.Answer;
import com.boliao.sunshine.biz.model.Article;
import com.boliao.sunshine.biz.model.JobDemandArt;
import com.boliao.sunshine.biz.model.Question;
import com.boliao.sunshine.biz.service.ArticleService;
import com.boliao.sunshine.biz.service.JobHRService;
import com.boliao.sunshine.biz.service.QuestionService;
import com.boliao.sunshine.biz.service.impl.ArticleServiceImpl;
import com.boliao.sunshine.biz.service.impl.JobHRServiceImpl;
import com.boliao.sunshine.biz.service.impl.QuestionServiceImpl;
import com.boliao.sunshine.biz.utils.FileUtils;
import com.boliao.sunshine.biz.utils.KenZip;
import com.boliao.sunshine.servlet.BaseServlet;
import com.boliao.sunshine.util.CacheUtil;
import com.boliao.sunshine.util.CommonConstants;

/**
 * @author liaobo
 * 
 */
public class SpiderFileProcessor {

	/** 文章服务对象 */
	private final ArticleService articleService;

	/** 问题服务 */
	private final QuestionService questionService;

	private final JobHRService jobHRService;

	/** 单实例对象 */
	private static SpiderFileProcessor processor = new SpiderFileProcessor();

	// 图片解压后的路径
	private final String imageDir = /* "D:/Tomcat/apache-tomcat-6.0.32/webapps/sunshine_new/images/" */
	BaseServlet.realPath + File.separator + "images" + File.separator;

	/**
	 * 构造方法
	 */
	private SpiderFileProcessor() {
		articleService = ArticleServiceImpl.getInstance();
		questionService = QuestionServiceImpl.getInstance();
		jobHRService = JobHRServiceImpl.getInstance();
	}

	/**
	 * 获得单实例 processor方法
	 * 
	 * @return
	 */
	public static SpiderFileProcessor getInstance() {
		if (processor == null) {
			synchronized (SpiderFileProcessor.class) {
				if (processor == null) {
					processor = new SpiderFileProcessor();
				}
			}
		}
		return processor;
	}

	/**
	 * 处理文章内容
	 * 
	 * @param articleFilePath
	 * @param imagePath
	 */
	public void dealWithArticle(File articleFilePath, File imagePath) {
		FileInputStream fr = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			fr = new FileInputStream(articleFilePath);
			isr = new InputStreamReader(fr, "gbk");
			br = new BufferedReader(isr);
			String line = br.readLine();
			while (line != null) {
				line = line.replace("'", "&apos;");
				JSONObject obj = JSONObject.fromObject(line);
				Article article = (Article) JSONObject.toBean(obj, Article.class);
				articleService.insertArticle(article);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void dealWithJobArt(File filePath) {
		FileInputStream fr = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			fr = new FileInputStream(filePath);
			isr = new InputStreamReader(fr, "gbk");
			br = new BufferedReader(isr);
			String line = br.readLine();
			List<JobDemandArt> jobDemandArtList = new ArrayList<JobDemandArt>(100);
			while (line != null) {
				line = line.replace("'", "&apos;");
				try {
					JSONObject obj = JSONObject.fromObject(line);
					JobDemandArt jobDemandArt = (JobDemandArt) JSONObject.toBean(obj, JobDemandArt.class);
					jobDemandArtList.add(jobDemandArt);
					// 对数据做批量插入
					if (jobDemandArtList.size() == 100) {
						jobHRService.insertBatch(jobDemandArtList);
						jobDemandArtList.clear();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				line = br.readLine();
			}
			jobHRService.insertBatch(jobDemandArtList);
			jobDemandArtList.clear();
			CacheUtil.getInstance().clearCache(CommonConstants.PAGE_CACHE_KEY);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 处理问答内容
	 * 
	 * @param filePath
	 */
	public void dealWithQuestion(File filePath) {
		FileInputStream fr = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			fr = new FileInputStream(filePath);
			isr = new InputStreamReader(fr, "gbk");
			br = new BufferedReader(isr);
			String line = br.readLine();
			while (line != null) {
				line = line.replace("'", "&apos;");
				JSONArray array = JSONArray.fromObject(line);
				JSONObject obj = array.getJSONObject(0);
				Question question = (Question) JSONObject.toBean(obj, Question.class);
				obj = array.getJSONObject(1);
				Answer answer = (Answer) JSONObject.toBean(obj, Answer.class);
				questionService.insertQuestion(question, answer);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 处理图片
	 * 
	 * @param imagePath
	 */
	public void dealWithImages(File imagePath) {
		try {
			KenZip.unZip(imagePath.getAbsolutePath(), imageDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对上传的文件做处理，处理完后 删除
	 * 
	 * @param uploadeDir
	 */
	public void dealWithFile(String uploadDir) {
		TaskThread t = new TaskThread(uploadDir);
		new Thread(t).start();
	}

	public static void main(String[] args) {
		SpiderFileProcessor processor = SpiderFileProcessor.getInstance();
		File file = new File("D:/Tomcat/apache-tomcat-6.0.32/webapps/sunshine_new/spider/");
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.getName().endsWith("article")) {
				processor.dealWithArticle(f, null);
			} else if (f.getName().endsWith("question")) {
				processor.dealWithQuestion(f);
			} else {
				processor.dealWithImages(f);
			}
		}

	}

	/**
	 * 任务线程 负责将上传目录下的文件 进行入库
	 * 
	 * @author liaobo
	 * 
	 */
	class TaskThread implements Runnable {
		String uploadDir;

		public TaskThread(String uploadDir) {
			this.uploadDir = uploadDir;
		}

		@Override
		public void run() {
			File f = new File(uploadDir);
			if (f.getName().endsWith("article")) {
				processor.dealWithArticle(f, null);
			} else if (f.getName().endsWith("question")) {
				processor.dealWithQuestion(f);
			} else if (f.getName().endsWith("jobDemandArt")) {
				processor.dealWithJobArt(f);
			} else {
				processor.dealWithImages(f);
			}

			// 处理完成之后 删除 文件
			FileUtils.deleteDir(f.getAbsolutePath());
		}
	}

}
