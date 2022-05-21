package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;


public class HistorySearcher {
	private HashMap<String,Integer> wordCount;
	
	private  void readSearchHistory() throws IOException 
	{
		wordCount= new HashMap<>() ;

		FileReader fr = new FileReader("SearchHistory.txt");
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null)
		{
			 if (! wordCount.containsKey(line.trim())) 
			 {

			    	wordCount.put(line.trim(), 1 ) ; 
		     }
	         else 
	         {
		            wordCount.put(line.trim(), wordCount.get(line.trim())+1) ;
		     }
		}
		br.close();
		
	}
	
	public String [] getTopThreeSearchQueries() throws IOException 
	{
		readSearchHistory();
		List<Entry<String, Integer>> list = new ArrayList<>(wordCount.entrySet());
		list.sort(Entry.comparingByValue());
		String [] result={list.get(list.size()-1).getKey(),list.get(list.size()-2).getKey(),list.get(list.size()-3).getKey()};
		return result;
	}

}
