package model;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
public class FileIndexer {
	private  IndexWriter writer;
	private  String file="netflix_titles.csv";
	private  String indexPath="Index";
	private  Analyzer analyzer;
	private  Directory dir;
	private String[] fieldsArr= {"title","director","cast","country","release_year","listed_in","description"};
//	Fields that we are going to keep
	private  HashMap<String, Integer> fields = new HashMap<String, Integer>(){{ put("title",2);put("director",3);put("cast",4);
	put("country",5);put("release_year",7);put("listed_in",10);put("description",11);}};


	public FileIndexer() throws IOException, CsvException {
		createAnalyzer();
		createWriter();
		createIndexIfNotExists();
//		we close the writer here 
//		otherwise it causes problems!!!!!
		writer.close();
		
	}

	
	private  void createAnalyzer() {
		analyzer=new StandardAnalyzer();
	}
	

	private  void createWriter() throws IOException {
		 dir = FSDirectory.open( Paths.get(indexPath) );
		 //IndexWriter Configuration
		 IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		 //IndexWriter writes new index files to the directory
		 writer = new IndexWriter(dir, iwc);
	}
	
	private  void createIndexIfNotExists() throws IOException, CsvException {
		File directory= new File(Paths.get(indexPath).toString());
		String[] files = directory.list();
		if(directory.isDirectory() && files.length==1) {
			createIndex();
		}
	
	}
	
	private   void createIndex() throws IOException, CsvException
	{
		List<String[]> csvData=readAllDataAtOnce();
		
		int size=csvData.size(); 
		for(int i=0; i<size; i++)
		{
			String[] row=csvData.get(i);
			writer.addDocument(createDocument(row));
		}
		
	}
	
	
	private  List<String[]> readAllDataAtOnce()  throws IOException, CsvException
	{
		List<String[]> csvData = new ArrayList<>();
		CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
		try(CSVReader reader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(csvParser).withSkipLines(1).build())
		{																	
			csvData = reader.readAll();
		}
		return csvData;

	}
	
	
	
	private  Document createDocument(String [] row)
	{
		Document document = new Document();
		int i=0;
		for(String field: fields.keySet()) {
			
			document.add(new TextField(field, row[fields.get(field)], Field.Store.YES));
		}

		return document;
	}
	
	
	
	public  ScoreDoc[] search(String queryStr) throws ParseException, IOException {
		Query simpleQuery= new MultiFieldQueryParser(fieldsArr, analyzer).parse(queryStr);
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc [] hits=searcher.search(simpleQuery, 50, Sort.RELEVANCE).scoreDocs;
		// 4. display results
        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("title") + "\t" + d.get("release_year"));
        }
        reader.close();
        
        return hits;
	}
	

//
	public static void main(String[] args) throws IOException, CsvException,ParseException{
//		String indexPath="Indexes";
//		
//		  Directory dir = FSDirectory.open( Paths.get(indexPath) );
//        
//        //analyzer with the default stop words
//        Analyzer analyzer = new StandardAnalyzer();
//        //IndexWriter Configuration
//        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
//        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//        //IndexWriter writes new index files to the directory
//        writer = new IndexWriter(dir, iwc);
//        
//        FileIndexer indexer=new FileIndexer(writer);
////        writer.close();
//        
//        String query="naruto";
//        indexer.search(query,analyzer);
//		
		FileIndexer indexer=new FileIndexer();
		indexer.search("Kirsten Johnson");
//		dir.close();
	}

}
