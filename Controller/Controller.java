package Controller;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;


import com.opencsv.exceptions.CsvException;

import model.FileIndexer;
import model.FileSearcher;

public class Controller {
	private FileIndexer indexer;
	private FileSearcher searcher;
	private Document[] searchResultDocs;
	public Controller() throws IOException, CsvException {
		indexer=new FileIndexer();
		searcher=new FileSearcher();
	}
	
	
	
	public void search(String query) throws ParseException, IOException {
		
		ArrayList<Document> searchResultsDocsList=searcher.search(query);
		searchResultDocs=new Document[searchResultsDocsList.size()];
		searchResultDocs=searchResultsDocsList.toArray(searchResultDocs);
		displaySearchResults();
	}
	
	public void setResultBtnsText() {
		
	}
	
	
	
	private  void displaySearchResults() throws IOException {
		// 4. display results
        System.out.println("Found " + searchResultDocs.length + " hits.");
        for(int i=0;i<searchResultDocs.length;++i) {
            System.out.println((i + 1) + ". " + searchResultDocs[i].get("title") + "\t" + searchResultDocs[i].get("release_year"));
        }
	}
//	
	public static void main(String[] args) throws IOException, CsvException,ParseException{
		
		Controller controller= new Controller();
		controller.search("Naruto");
	}
	
	
	
}
