package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;


import com.opencsv.exceptions.CsvException;

import model.FileIndexer;
import model.FileSearcher;

public class Controller {
	private FileIndexer indexer;
	private FileSearcher searcher;
	private Document[] searchResultDocs;

	
	
	private int lastPageIndex;
	private int currentPageIndex;
	
	
	public Controller() throws IOException, CsvException {
		indexer=new FileIndexer();
		searcher=new FileSearcher();
		
	}
	
	
	
	public void search(String query) throws ParseException, IOException {
		
		ArrayList<Document> searchResultsDocsList=searcher.search(query);
//		System.out.println("Found "+searchResultsDocsList.size()+"Results");
		searchResultDocs=new Document[searchResultsDocsList.size()];
		searchResultDocs=searchResultsDocsList.toArray(searchResultDocs);
		System.out.println("Total Pages:"+calculateResultPages());
		lastPageIndex=calculateResultPages()-1;
		System.out.println("LastPage index:"+lastPageIndex);
//		displaySearchResults();
	}
	
	
	
	public void setFirstSearchResultBtnsText(ArrayList<JButton> resultButtons) {
		currentPageIndex=0;
		getPageResults(currentPageIndex, resultButtons);
		
	}

	
	private void getPageResults(int page,ArrayList<JButton> resultButtons) {
		int start=page*10;
		int stop=calculateStopRes(start);
		for(int i=start;i<stop;i++) {
			JButton btn=resultButtons.get(i%10);

			btn.setText(searchResultDocs[i].get("title") + " " + searchResultDocs[i].get("release_year"));
			btn.setVisible(true);
		}	
	}
	
	private int calculateStopRes(int start){

		if(currentPageIndex==lastPageIndex && searchResultDocs.length%10!=0) {
			return start+searchResultDocs.length%10-1;
		}
		return start+10;
		
	}
	
	
	public void setNextSearchResultBtnsText(ArrayList<JButton> resultButtons) {
	  currentPageIndex+=1;
	  System.out.println("current page:"+currentPageIndex);
	  System.out.println("last page:"+lastPageIndex);
	  getPageResults(currentPageIndex,resultButtons);
	  
	
}
	public void setPreviousSearchResultBtnsText(ArrayList<JButton> resultButtons) {
		  currentPageIndex-=1;
		  getPageResults(currentPageIndex,resultButtons);
		  
		
	}

	public boolean checkForNextRemainingResults() {
		return !(currentPageIndex==lastPageIndex);
	}

	public boolean checkForPreviousRemainingResults() {
		return !(currentPageIndex==0);
	}


	private int calculateResultPages() {
		if(searchResultDocs.length%10==0) {
			return (int) Math.min(Math.ceil(searchResultDocs.length/10),10);
		}
		return (int) Math.min(Math.ceil(searchResultDocs.length/10)+1,10);
		
	}
	
	
	private  void displaySearchResults() throws IOException {
		// 4. display results
        System.out.println("Found " + searchResultDocs.length + " hits.");
        for(int i=0;i<searchResultDocs.length;++i) {
            System.out.println((i + 1) + ". " + searchResultDocs[i].get("title") + "\t" + searchResultDocs[i].get("release_year"));
        }
	}
//	
	public void sortByTitle(ArrayList<JButton> resultButtons) {

        // create string array called names
        Document temp;
        for (int i = 0; i <  searchResultDocs.length; i++) {
            for (int j = i + 1; j <  searchResultDocs.length; j++) {
                // to compare one document with other documents using title
                if (searchResultDocs[i].get("title").compareTo(searchResultDocs[j].get("title")) > 0) {
                    // swapping
                    temp = searchResultDocs[i];
                    searchResultDocs[i] = searchResultDocs[j];
                    searchResultDocs[j] = temp;
                }
            }
        }
        setFirstSearchResultBtnsText(resultButtons);
        
	}
	
	public void sortByYear(ArrayList<JButton> resultButtons) {

        // create string array called names
        Document temp;
        for (int i = 0; i <  searchResultDocs.length; i++) {
            for (int j = i + 1; j <  searchResultDocs.length; j++) {
                // to compare one document with other documents using title
                if (Integer.valueOf(searchResultDocs[i].get("release_year")) > Integer.valueOf(searchResultDocs[j].get("release_year"))) {
                    // swapping
                    temp = searchResultDocs[i];
                    searchResultDocs[i] = searchResultDocs[j];
                    searchResultDocs[j] = temp;
                }
            }
        }
        setFirstSearchResultBtnsText(resultButtons);
        
	}
//	
	
	public static void main(String[] args) throws IOException, CsvException,ParseException{
		
		Controller controller= new Controller();
		controller.search("Naruto");
	}
	
	
	
}
