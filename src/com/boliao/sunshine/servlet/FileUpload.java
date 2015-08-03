package com.boliao.sunshine.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.processor.SpiderFileProcessor;
import com.boliao.sunshine.biz.utils.FileUtils;
import com.boliao.sunshine.biz.utils.LogUtil;

public class FileUpload extends HttpServlet {

	/**
	 * versionId
	 */
	private static final long serialVersionUID = -757704620674769704L;

	/** 日志收集器 */
	private final static Logger logger = Logger.getLogger(FileUpload.class);
	/** 搜集错误日志 */
	private final Logger errLog = Logger.getLogger(LogUtil.ERROR);

	private final String tempPath = BaseServlet.realPath + File.separator + "tmp" + File.separator;
	private File tempFile = null;

	/**
	 * 爬虫文件处理器
	 */
	private SpiderFileProcessor spiderFileProcessor;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(4096);
		try {

			// the location for saving data that is larger than
			// getSizeThreshold()
			factory.setRepository(tempFile);

			ServletFileUpload upload = new ServletFileUpload(factory);
			// maximum size before a FileUploadException will be thrown
			upload.setSizeMax(1000000000);

			List fileItems = upload.parseRequest(req);
			// assume we know there are two files. The first file is a small
			// text file, the second is unknown and is written to a file on
			// the server
			Iterator iter = fileItems.iterator();
			// 过滤掉的文件类型
			String[] errorType = { ".exe", ".com", ".cgi", ".jsp" };
			String uploadPath = null;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// 忽略其他不是文件域的所有表单信息
				if (!item.isFormField()) {
					String name = item.getName();
					long size = item.getSize();
					if ((name == null || name.equals("")) && size == 0)
						continue;
					for (int temp = 0; temp < errorType.length; temp++) {
						if (name.endsWith(errorType[temp])) {
							throw new IOException(name + ": wrong type");
						}
					}
					uploadPath = BaseServlet.realPath + File.separator + "spider" + File.separator + name; // 用于存放上传文件的目录
					FileUtils.createFile(uploadPath);
					// 保存上传的文件到指定的目录
					// 在下文中上传文件至数据库时，将对这里改写
					item.write(new File(uploadPath));

				}
			}
			out.write("success");
			out.flush();
			spiderFileProcessor.dealWithFile(uploadPath);
			return;
		} catch (IOException e) {
			errLog.error("进行文件上传时，发生IO异常，请排查原因", e);
		} catch (FileUploadException e) {
			errLog.error("进行文件上传时，异常，请排查原因", e);
		} catch (Exception e) {
			errLog.error("进行文件上传时，异常，请排查原因", e);
		}
		out.write("error");
		out.flush();
		return;
	}

	@Override
	public void init() throws ServletException {
		try {
			FileUtils.createFile(tempPath);
			tempFile = new File(tempPath);
			super.init();
			spiderFileProcessor = SpiderFileProcessor.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
