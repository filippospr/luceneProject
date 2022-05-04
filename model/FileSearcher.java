package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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
import org.apache.lucene.search.spell.LevenshteinDistance;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.opencsv.exceptions.CsvException;

public class FileSearcher {
	private  IndexReader reader;
	private  IndexSearcher searcher;
	private  ScoreDoc[] hits;
	private  IndexWriter writer;
	private  ArrayList<Document> searchResultDocs;
	private  String indexPath="Index";
	private  Analyzer analyzer;
	private  Directory dir;
	
	
	private  String[] fieldsArr= {"title","director","cast","country","release_year","listed_in","description"};
	private  ArrayList<String> searchHistory;
	private  File searchHistoryFile;
	private  String searchHistoryFileName="SearchHistory.txt";
	
	public FileSearcher() throws IOException {
		createAnalyzer();
		createWriter();
		searchHistory=new ArrayList<String>();
		searchResultDocs=new ArrayList<Document>();
		loadSearchHistory();
		createSpellIndex();
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
	
	private  void loadSearchHistory() throws IOException {
		searchHistoryFile=new File("SearchHistory.txt");
		if(!searchHistoryFile.exists()) {
			searchHistoryFile.createNewFile();
			return;
		}
		FileReader fr = new FileReader(searchHistoryFile);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
			searchHistory.add(line);
		}
		br.close();
		
	}
	
	public  ArrayList<Document> search(String query) throws ParseException, IOException {
		addToSearchHistory(query);
		MultiFieldQueryParser queryParser= new MultiFieldQueryParser(fieldsArr, analyzer);
		Query simpleQuery= queryParser.parse(query);
		reader = DirectoryReader.open(dir);
		searcher = new IndexSearcher(reader);
		hits=searcher.search(simpleQuery, 100, Sort.RELEVANCE).scoreDocs;
//		displaySearchResults();
		createsearchResultDocs(hits);
        return searchResultDocs;
	}	
	

	
	private  void addToSearchHistory(String query) throws IOException {
		searchHistory.add(query);
		 try {
			FileWriter fileWriter = new FileWriter(searchHistoryFileName,true);
			fileWriter.write(query+"\n");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private  void displaySearchResults() throws IOException {
		// 4. display results
        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("title") + "\t" + d.get("release_year"));
        }
	}
	
	private  void createsearchResultDocs(ScoreDoc[] hits) throws IOException {
		   for(int i=0;i<hits.length;++i) {
	            int docId = hits[i].doc;
	            Document d = searcher.doc(docId);
	            searchResultDocs.add(d);
	        }
	}
	
	private  void spellcheck(String query) throws IOException {
		 String spellCheckDir = "SpellingIndex";
		 String wordToRespell = query;
		 Directory dir = FSDirectory.open(Paths.get(spellCheckDir));
		 
		 SpellChecker spell = new SpellChecker(dir);
		 spell.setStringDistance(new LevenshteinDistance());
		 
		 String[] suggestions = spell.suggestSimilar(wordToRespell, 5);
		 
		 System.out.println(suggestions.length +" suggestions for '" +
		 wordToRespell + "':");
		 
		 for(String suggestion : suggestions){
		   System.out.println(" " + suggestion);
		 }
		
	}
	
	private  void createSpellIndex() throws IOException {
		 
		 String spellCheckDir = "SpellingIndex";
		 String indexDir = "Index";
		 String indexField = "title";
		 
		 Directory dir = FSDirectory.open( Paths.get(spellCheckDir));
		 SpellChecker spell = new SpellChecker(dir);
		 Directory dir2 = FSDirectory.open(Paths.get(indexDir));
		 IndexReader r = DirectoryReader.open(dir2);
		 try {
			 IndexWriterConfig config = new IndexWriterConfig(new KeywordAnalyzer());
			 spell.indexDictionary(
					 new LuceneDictionary(r, indexField),config, false);
		 } finally {
			 r.close();
		 }
		 dir.close();
		 dir2.close();
	}
//	
	public static void main(String[] args) throws IOException, CsvException,ParseException{
//		String indexPath="Indexes";
//		
//		Directory dir = FSDirectory.open( Paths.get(indexPath) );
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
//		indexer.createIndex();
////        writer.close();
        FileSearcher searcher = new FileSearcher();
        String query="Gail Willumsen";
        searcher.search(query);
//       
////      Check Spell correction
//        searcher.spellcheck("Jow");
 	}
//	
//	
	
}
