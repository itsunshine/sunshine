package com.boliao.sunshine.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

import com.boliao.sunshine.biz.model.Answer;
import com.boliao.sunshine.biz.model.Article;
import com.boliao.sunshine.biz.model.BaseModel;
import com.boliao.sunshine.biz.model.Doc;
import com.boliao.sunshine.biz.model.JobDemandArt;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.Question;
import com.boliao.sunshine.biz.utils.FileUtils;
import com.boliao.sunshine.biz.utils.LogUtil;
import com.boliao.sunshine.configuration.ConfigurationCenter;
import com.boliao.sunshine.servlet.BaseServlet;

/**
 * 
 * @author liaobo.lb
 * 
 */
public class TextFileIndexer {

	/** 错误日志记录器 */
	private final static Logger errLog = Logger.getLogger(LogUtil.ERROR);

	/** 日志记录器 */
	private final static Logger logger = Logger.getLogger(TextFileIndexer.class.getSimpleName());

	private final IKAnalyzer analyzer = new IKAnalyzer();

	/** 取得配置里的路径 */
	String baseDirPath = ConfigurationCenter.getBaseSearchIndexDir();
	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static TextFileIndexer textFileIndexer = new TextFileIndexer();

	private final static Map<String, String> pathMap = new HashMap<String, String>();

	// 所有的索引字段
	private final static String CONTENT_FIELD = "content";
	private final static String ANSWER_FIELD = "answer";
	private final static String TITLE_FIELD = "title";
	private final static String TYPE_FIELD = "type";
	private final static String ID_FIELD = "id";
	private final static String LOCATION = "location";
	private final static String TIME = "time";

	// 返回内容的最多字数
	private final static int SIZE = 20;

	static {
		pathMap.put(Article.class.getSimpleName(), "article");
		pathMap.put(Question.class.getSimpleName(), "qa");
		pathMap.put(Doc.class.getSimpleName(), "doc");
		pathMap.put(BaseServlet.HR_KEY, "hr");

	}

	private TextFileIndexer() {
	};

	/**
	 * 获取索引工具类实例
	 * 
	 * @return
	 */
	public static TextFileIndexer getInstance() {
		if (textFileIndexer == null) {
			synchronized (TextFileIndexer.class) {
				if (textFileIndexer == null) {
					textFileIndexer = new TextFileIndexer();
				}
			}
		}
		return textFileIndexer;
	}

	public void index(Object... obj) throws Exception {
		if (obj[0] instanceof Article) {
			indexArticle((Article) obj[0]);
		} else if (obj[0] instanceof Question) {
			indexQuestion((Question) obj[0], (Answer) obj[1]);
		} else if (obj[0] instanceof Doc) {
			indexDoc((Doc) obj[0]);
		} else if (obj[0] instanceof JobDemandArt) {
			indexSocialJobDemand((JobDemandArt) obj[0]);
		}

	}

	private Document indexBase(IndexWriter indexWriter, BaseModel model) {
		synchronized (this) {

			// 增加document到索引去
			Document document = new Document();
			String content = ParseUtils.htmlRemoveTag(model.getContent());
			Field fieldContent = new Field(CONTENT_FIELD, content, Field.Store.YES, Field.Index.ANALYZED);
			String title = ParseUtils.htmlRemoveTag(model.getTitle());
			Field fieldTitle = new Field("title", title, Field.Store.YES, Field.Index.ANALYZED);
			Field fieldId = new Field("id", model.getId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED);
			document.add(fieldContent);
			document.add(fieldTitle);
			document.add(fieldId);
			return document;
		}
	}

	/**
	 * 用于对Doc建索引
	 * 
	 * @param article
	 * @param type
	 * @throws Exception
	 */
	private void indexDoc(Doc doc) throws Exception {

		synchronized (this) {
			File indexDir = getIndexFileDir(Doc.class.getSimpleName(), true);
			if (indexDir == null) {
				return;
			}

			FSDirectory dir = FSDirectory.open(indexDir);
			IndexWriter indexWriter = new IndexWriter(dir, analyzer, true, MaxFieldLength.UNLIMITED);
			// 增加document到索引去
			Document document = indexBase(indexWriter, doc);
			document.removeField(CONTENT_FIELD);
			Field fieldType = new Field(TYPE_FIELD, BaseServlet.DOC_KEY, Field.Store.YES, Field.Index.NO);
			document.add(fieldType);
			indexWriter.addDocument(document);
			// optimize()方法是对索引进行优化
			indexWriter.optimize();
			indexWriter.close();
		}
	}

	/**
	 * 用于对Question建索引
	 * 
	 * @param question
	 * @param folder
	 * @throws Exception
	 */
	private void indexQuestion(Question question, Answer answer) throws Exception {

		synchronized (this) {
			File indexDir = getIndexFileDir(Question.class.getSimpleName(), true);
			if (indexDir == null) {
				return;
			}
			FSDirectory dir = FSDirectory.open(indexDir);
			IndexWriter indexWriter = new IndexWriter(dir, analyzer, false, MaxFieldLength.UNLIMITED);

			// 增加document到索引去
			Document document = indexBase(indexWriter, question);
			String content = ParseUtils.htmlRemoveTag(answer.getContent());
			Field fieldAnswer = new Field("answer", content, Field.Store.YES, Field.Index.ANALYZED);
			Field fieldType = new Field(TYPE_FIELD, BaseServlet.QUE_KEY, Field.Store.YES, Field.Index.NO);
			document.add(fieldType);
			document.add(fieldAnswer);
			indexWriter.addDocument(document);
			// optimize()方法是对索引进行优化
			indexWriter.optimize();
			indexWriter.close();
		}

	}

	/**
	 * 用于对Article建索引
	 * 
	 * @param article
	 * @param type
	 * @throws Exception
	 */
	private void indexArticle(Article article) throws Exception {

		synchronized (this) {
			File indexDir = getIndexFileDir(Article.class.getSimpleName(), true);
			if (indexDir == null) {
				return;
			}

			FSDirectory dir = FSDirectory.open(indexDir);
			IndexWriter indexWriter = new IndexWriter(dir, analyzer, false, MaxFieldLength.UNLIMITED);
			// 增加document到索引去
			Document document = indexBase(indexWriter, article);
			Field fieldType = new Field(TYPE_FIELD, BaseServlet.ART_KEY, Field.Store.YES, Field.Index.NO);
			document.add(fieldType);
			indexWriter.addDocument(document);
			// optimize()方法是对索引进行优化
			indexWriter.optimize();
			indexWriter.close();
		}

	}

	/**
	 * 社招职位，索引建立
	 * 
	 * @param jda
	 * @throws Exception
	 */
	private void indexSocialJobDemand(JobDemandArt jda) throws Exception {
		try {
			rwl.writeLock().lock();
			File indexDir = getIndexFileDir(BaseServlet.HR_KEY, true);
			if (indexDir == null) {
				return;
			}

			FSDirectory dir = FSDirectory.open(indexDir);
			IndexWriter indexWriter = null;
			if (indexDir.list().length != 0) {
				indexWriter = new IndexWriter(dir, analyzer, false, MaxFieldLength.UNLIMITED);
			} else {
				indexWriter = new IndexWriter(dir, analyzer, true, MaxFieldLength.UNLIMITED);
			}
			// 增加document到索引去
			Document document = new Document();
			String content = ParseUtils.htmlRemoveTag(jda.getContent());
			Field contentField = new Field(CONTENT_FIELD, content, Field.Store.NO, Field.Index.ANALYZED);
			Field title = new Field(TITLE_FIELD, jda.getTitle(), Field.Store.NO, Field.Index.ANALYZED);
			Field location = new Field(LOCATION, jda.getLocation(), Field.Store.NO, Field.Index.ANALYZED);
			Field id = new Field(ID_FIELD, String.valueOf(jda.getId()), Field.Store.YES, Field.Index.NO);
			Field time = new Field(TIME, jda.getCreateTime(), Field.Store.NO, Field.Index.NOT_ANALYZED);
			document.add(contentField);
			document.add(title);
			document.add(location);
			document.add(id);
			document.add(time);
			indexWriter.addDocument(document);
			// optimize()方法是对索引进行优化
			indexWriter.optimize();
			indexWriter.close();
		} finally {
			rwl.writeLock().unlock();
		}

	}

	/**
	 * 获得索引目录，路径
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	private File getIndexFileDir(String name, boolean isCreate) throws IOException {
		String folder = pathMap.get(name);

		if (StringUtils.isNotBlank(folder)) {
			String path = baseDirPath + File.separator + folder;
			File indexDir = null;
			if (FileUtils.isExists(path)) {
				FileUtils.createDir(baseDirPath + File.separator + folder);
				indexDir = new File(baseDirPath + File.separator + folder);
			} else if (!FileUtils.isExists(path) && isCreate) {
				FileUtils.createDir(baseDirPath + File.separator + folder);
				indexDir = new File(baseDirPath + File.separator + folder);
			}
			return indexDir;
		}
		return null;
	}

	/**
	 * 根据传入的搜索类型，获取searcher;
	 * 
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public MultiSearcher getSearcher(String type) throws IOException {
		MultiSearcher searcher = null;
		if (StringUtils.isBlank(type)) {
			Set<String> keySet = pathMap.keySet();
			String[] keys = keySet.toArray(new String[0]);
			FSDirectory[] dirs = new FSDirectory[keys.length];
			List<IndexSearcher> searList = new ArrayList<IndexSearcher>();
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];
				File indexDir = getIndexFileDir(key, false);
				if (indexDir != null) {
					dirs[i] = FSDirectory.open(indexDir);
					IndexSearcher indexSearcher = new IndexSearcher(dirs[i]);
					searList.add(indexSearcher);
				}
			}
			searcher = new MultiSearcher(searList.toArray(new IndexSearcher[0]));
			return searcher;
		} else {
			IndexSearcher indexSearcher = null;
			File indexDir = getIndexFileDir(type, false);
			if (indexDir != null) {
				FSDirectory dir = FSDirectory.open(indexDir);
				indexSearcher = new IndexSearcher(dir);
			}
			searcher = new MultiSearcher(indexSearcher);
			return searcher;
		}
	}

	/**
	 * 搜索社招职位
	 * 
	 * @param searchStr
	 * @param type
	 * @param page
	 * @return
	 */
	public List<Long> searchSocailJobDemand(String searchStr, String type, PageBase<JobDemandArt> page) {
		List<Long> idList = null;
		try {
			rwl.readLock().lock();
			idList = new ArrayList<Long>();

			BooleanQuery bq = this.generateBooleanQuery(searchStr);
			LogUtil.info(logger, "搜索条件Query:" + bq.toString());
			// 先按记录的得分排序,然后再按记录的发布时间倒序
			Sort sort = new Sort(new SortField[] { new SortField(null, SortField.SCORE, false), new SortField(TIME, SortField.STRING, true) });

			MultiSearcher searcher = getSearcher(type);
			searcher.setSimilarity(new IKSimilarity());
			TopDocs topDocs = searcher.search(bq, null, SIZE, sort);
			ScoreDoc[] ds = topDocs.scoreDocs;
			page.setTotalCount(Long.valueOf(ds.length));
			if (page.getTotalPage() < page.getPageNo()) {
				page.setPageNo(page.getTotalPage());
			}
			int start = page.getStart();
			int end = page.getPageSize() + start;
			for (int i = start; i < ds.length && i < end; i++) {
				ScoreDoc tScore = ds[i];
				int docId = tScore.doc;
				Document tDoc = searcher.doc(docId);
				String id = tDoc.get(ID_FIELD);
				idList.add(Long.valueOf(id));
			}
			searcher.close();
			return idList;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(errLog, "根据type: " + type + " 和 searchStr：" + searchStr + " 进行搜索时，发生异常", e);
		} finally {
			rwl.readLock().unlock();
		}
		return idList;
	}

	/**
	 * 生成boolean类型的查询query
	 * 
	 * @param keyWords
	 * @return
	 * @throws IOException
	 */
	private BooleanQuery generateBooleanQuery(String keyWords) throws IOException {
		String fiels[] = { TITLE_FIELD, ID_FIELD, LOCATION, CONTENT_FIELD, TIME };

		BooleanQuery bq = new BooleanQuery();
		for (int i = 0; i < fiels.length; i++) {

			IKSegmentation se = new IKSegmentation(new StringReader(keyWords), true);
			Lexeme le = null;

			while ((le = se.next()) != null) {
				String tKeyWord = le.getLexemeText();
				String tFeild = fiels[i];
				TermQuery tq = new TermQuery(new Term(fiels[i], tKeyWord));

				if (tFeild.equals(TITLE_FIELD)) { // 在Name这一个Field需要给大的比重
					tq.setBoost(100.0f);
				} else if (tFeild.equals(LOCATION)) {
					tq.setBoost(200.0f);
				} else {
					tq.setBoost(0.0f); // 其他的不需要考滤
				}

				bq.add(tq, BooleanClause.Occur.SHOULD); // 关键字之间是 "或" 的关系
			}
		}
		return bq;
	}

	public List<BaseModel> search(String searchStr, String type, PageBase<BaseModel> page) {
		try {
			synchronized (this) {
				List<BaseModel> resultList = new ArrayList<BaseModel>();
				int start = page.getStart();
				int end = page.getPageSize() + start;
				String fiels[] = { TITLE_FIELD, CONTENT_FIELD, ANSWER_FIELD };
				Query bq = IKQueryParser.parseMultiField(fiels, searchStr);
				MultiSearcher searcher = getSearcher(type);
				searcher.setSimilarity(new IKSimilarity());

				TopDocs topDocs = searcher.search(bq, SIZE);
				ScoreDoc[] ds = topDocs.scoreDocs;
				page.setTotalCount(Long.valueOf(ds.length));

				// 下面的代码进行高亮的显示
				SimpleHTMLFormatter formater = new SimpleHTMLFormatter("<b><font color='red'/>", "</font></b>");
				Highlighter lighter = new Highlighter(formater, new QueryScorer(bq));
				SimpleFragmenter fragmenter = new SimpleFragmenter(50);
				lighter.setTextFragmenter(fragmenter);

				for (int i = start; i < ds.length && i < end; i++) {
					ScoreDoc tScore = ds[i];
					int docId = tScore.doc;
					Document tDoc = searcher.doc(docId);
					String con = tDoc.get(CONTENT_FIELD);
					String content = getSearchedValue(CONTENT_FIELD, con, lighter);
					String title = tDoc.get(TITLE_FIELD);
					String t = getSearchedValue(TITLE_FIELD, title, lighter);
					String answer = tDoc.get(ANSWER_FIELD);
					if (StringUtils.isEmpty(content) && StringUtils.isNotBlank(answer)) {

						content = getSearchedValue(ANSWER_FIELD, answer, lighter);
					}
					String id = tDoc.get(ID_FIELD);
					String source = tDoc.get(TYPE_FIELD);
					BaseModel model = new BaseModel();
					model.setId(Long.valueOf(id));
					model.setContent(content);
					model.setTitle(t);
					model.setSource(source);
					resultList.add(model);
				}
				searcher.close();
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private String getSearchedValue(String field, String content, Highlighter lighter) throws IOException, InvalidTokenOffsetsException {
		TokenStream tokenStream = analyzer.tokenStream(CONTENT_FIELD, new StringReader(content));
		String result = lighter.getBestFragment(tokenStream, content);
		if (StringUtils.isBlank(result)) {
			if (StringUtils.length(content) > SIZE) {
				result = StringUtils.substring(content, 0, SIZE);
			} else {
				result = content;
			}

		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		// TextFileIndexer indexer = TextFileIndexer.getInstance();
		// Article article = new Article();
		// article.setId(8799909888L);
		// article.setTitle("只是想测试一下");
		// article.setContent("<br>真的只想测试一下内容</br>");
		// indexer.index(article);
		//
		// PageBase<BaseModel> page = new PageBase<BaseModel>();
		// page.setPageNo(1);
		// List<BaseModel> result = indexer.search("测试", null, page);
		// System.out.println(result);

		TextFileIndexer indexer = TextFileIndexer.getInstance();
		JobDemandArt article = new JobDemandArt();
		article.setId(111111111111L);
		article.setTitle("只是想测试杭州java一下");
		article.setContent("<br>真的只想测试一下hadoop内容,会java更好</br>");
		article.setLocation("郑州");
		article.setCreateTime("2015-03-01");
		indexer.index(article);
		PageBase<JobDemandArt> page = new PageBase<JobDemandArt>();
		page.setPageNo(1);
		List<Long> result = indexer.searchSocailJobDemand("郑州", "hr", page);
		System.out.println(result);

		article = new JobDemandArt();
		article.setId(22222222222222222L);
		article.setTitle("只是想测试java一下");
		article.setContent("<br>真的只想测试一下内容,会java更好</br>");
		article.setLocation("杭州");
		article.setCreateTime("2015-03-01");
		indexer.index(article);

		page = new PageBase<JobDemandArt>();
		page.setPageNo(1);
		result = indexer.searchSocailJobDemand("郑州", "hr", page);
		System.out.println(result);

		// testIndex();

	}

	public static void testIndex() throws Exception {
		File indexDir = new File("testIndex");
		if (indexDir == null) {
			return;
		}
		IKAnalyzer analyzer = new IKAnalyzer();
		FSDirectory dir = FSDirectory.open(indexDir);
		IndexWriter indexWriter = new IndexWriter(dir, analyzer, true, MaxFieldLength.UNLIMITED);
		// 增加document到索引去
		Document document = new Document();
		String content = ParseUtils.htmlRemoveTag("这是一段测试的文字内容，只是用一下");
		Field fieldContent = new Field(CONTENT_FIELD, content, Field.Store.NO, Field.Index.ANALYZED);
		Field fieldType = new Field("id", "1234567890", Field.Store.YES, Field.Index.NO);
		document.add(fieldContent);
		document.add(fieldType);
		indexWriter.addDocument(document);
		// optimize()方法是对索引进行优化
		indexWriter.optimize();
		indexWriter.close();

		String fiels[] = { CONTENT_FIELD, "id" };
		Query bq = IKQueryParser.parseMultiField(fiels, "文字内容");
		IndexSearcher indexSearcher = new IndexSearcher(dir);
		MultiSearcher searcher = new MultiSearcher(indexSearcher);
		searcher.setSimilarity(new IKSimilarity());

		TopDocs topDocs = searcher.search(bq, SIZE);
		ScoreDoc[] ds = topDocs.scoreDocs;
		for (int i = 0; i < ds.length; i++) {
			ScoreDoc tScore = ds[i];
			int docId = tScore.doc;
			Document tDoc = searcher.doc(docId);
			String con = tDoc.get(CONTENT_FIELD);
			System.out.println(con);
			String id = tDoc.get("id");
			System.out.println(id);
		}
	}

}