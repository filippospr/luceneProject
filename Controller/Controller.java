package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import com.opencsv.exceptions.CsvException;

import view.movieInfoDialog;
import model.FileIndexer;
import model.FileSearcher;
import model.QuerySpellChecker;

public class Controller 
{
	private FileIndexer indexer;
	private FileSearcher searcher;
	
	private QuerySpellChecker queryspellchecker;
	
	private Document[] searchResultDocs;
	
	private int lastPageIndex;
	private int currentPageIndex;
	
	
	public Controller() throws IOException, CsvException 
	{
		indexer=new FileIndexer();
		searcher=new FileSearcher();
		queryspellchecker=new QuerySpellChecker();
	}
		
	public void search(String query) throws ParseException, IOException 
	{
		ArrayList<Document> searchResultsDocsList=searcher.search(query);
		searchResultDocs=new Document[searchResultsDocsList.size()];
		searchResultDocs=searchResultsDocsList.toArray(searchResultDocs);
		lastPageIndex=calculateResultPages()-1;
	}
	
	public boolean noResultsGiven() 
	{
		return searchResultDocs.length==0;
	}
	
	public void setFirstSearchResultBtnsText(ArrayList<JButton> resultButtons,JFrame parentFrame) 
	{
		currentPageIndex=0;
		getPageResults(currentPageIndex, resultButtons,parentFrame);	
	}

	private void getPageResults(int page,ArrayList<JButton> resultButtons,JFrame parentFrame) 
	{
		int start=page*10;
		int stop=calculateStopRes(start);

		for(int i=start;i<stop;i++) 
		{
			JButton btn=resultButtons.get(i%10);
			btn.setText(searchResultDocs[i].get("title") + " " + searchResultDocs[i].get("release_year"));
			Document d=searchResultDocs[i];
			btn.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					String movieContents=createMovieContents(d);
					new movieInfoDialog(parentFrame,movieContents,d.get("title"));
				}
			});
			btn.setVisible(true);
		}	
	}
	
	private int calculateStopRes(int start)
	{
		if(currentPageIndex==lastPageIndex && searchResultDocs.length%10!=0) 
		{
			return start+searchResultDocs.length%10;
		}
		return start+10;	
	}

	private String createMovieContents(Document doc) 
	{
		return "Title:"+doc.get("title")+"  "+doc.get("release_year")+"\n"+"Director:"+doc.get("director")+"\n"+"Cast:"+doc.get("cast")+"\n"+"Country:"+doc.get("country")+"  "
				+"Genre:"+doc.get("listed_in")+"\n"+"Description:"+doc.get("description")+"\n";
	}
	
	public void setNextSearchResultBtnsText(ArrayList<JButton> resultButtons,JFrame parentFrame) 
	{
		currentPageIndex+=1;
		getPageResults(currentPageIndex,resultButtons,parentFrame);
	}
	
	public void setPreviousSearchResultBtnsText(ArrayList<JButton> resultButtons,JFrame parentFrame) 
	{
		currentPageIndex-=1;
		getPageResults(currentPageIndex,resultButtons,parentFrame);
	}
	
	public void setSuggestionBtnsText(ArrayList<JButton> suggestionBtns,String query) throws IOException 
	{
		String [] suggestions=queryspellchecker.suggestSearchQuery(query);
		for(int i=0;i<suggestions.length;i++) 
		{
			JButton suggestionBtn=suggestionBtns.get(i);
			suggestionBtn.setText(suggestions[i]);
			suggestionBtn.setVisible(true);
		}
	}

	public boolean checkForNextRemainingResults() 
	{
		return !(currentPageIndex==lastPageIndex);
	}

	public boolean checkForPreviousRemainingResults() 
	{
		return !(currentPageIndex==0);
	}

	private int calculateResultPages() 
	{
		if(searchResultDocs.length%10==0) 
		{
			return (int) Math.min(Math.ceil(searchResultDocs.length/10),10);
		}
		return (int) Math.min(Math.ceil(searchResultDocs.length/10)+1,10);	
	}

	public void sortByTitle(ArrayList<JButton> resultButtons,JFrame parentFrame) 
	{
        // create string array called names
        Document temp;
        for (int i = 0; i <  searchResultDocs.length; i++) 
        {
            for (int j = i + 1; j <  searchResultDocs.length; j++) 
            {
                // to compare one document with other documents using title
                if (searchResultDocs[i].get("title").compareTo(searchResultDocs[j].get("title")) > 0) 
                {
                    // swapping
                    temp = searchResultDocs[i];
                    searchResultDocs[i] = searchResultDocs[j];
                    searchResultDocs[j] = temp;
                }
            }
        }
        setFirstSearchResultBtnsText(resultButtons,parentFrame);
	}
	
	public void sortByYear(ArrayList<JButton> resultButtons,JFrame parentFrame) 
	{
        // create string array called names
        Document temp;
        for (int i = 0; i <  searchResultDocs.length; i++) 
        {
            for (int j = i + 1; j <  searchResultDocs.length; j++) 
            {
                // to compare one document with other documents using year
                if (Integer.valueOf(searchResultDocs[i].get("release_year")) > Integer.valueOf(searchResultDocs[j].get("release_year"))) 
                {
                    // swapping
                    temp = searchResultDocs[i];
                    searchResultDocs[i] = searchResultDocs[j];
                    searchResultDocs[j] = temp;
                }
            }
        }
        setFirstSearchResultBtnsText(resultButtons,parentFrame);    
	}

	public static void main(String[] args) throws IOException, CsvException,ParseException
	{
		Controller controller= new Controller();
		controller.search("Naruto");	
	}

}
