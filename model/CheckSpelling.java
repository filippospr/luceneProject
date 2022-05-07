package model;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.LevenshteinDistance; //more algorithms
import org.apache.lucene.search.spell.LuceneLevenshteinDistance;
import org.apache.lucene.search.spell.NGramDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class CheckSpelling
{
    public void checkdictionary(String query) throws IOException
    {
        Directory directory = FSDirectory.open(Paths.get("Index"));
        PlainTextDictionary txt_dictionary = new PlainTextDictionary(Paths.get("eng_dictionary.txt"));
        try (SpellChecker mySpellingChecker = new SpellChecker(directory)) {
			mySpellingChecker.indexDictionary(txt_dictionary, new IndexWriterConfig(new KeywordAnalyzer()), false);
			directory.close();        
			
			// Searching and presenting the suggested words by selecting a string distance
			mySpellingChecker.setStringDistance(new JaroWinklerDistance()); //choose algorithm
			//checker.setStringDistance(new LevenshteinDistance());
			//checker.setStringDistance(new LuceneLevenshteinDistance());
			//checker.setStringDistance(new NGramDistance()); 
			
			String[] suggestions = mySpellingChecker.suggestSimilar(query, 10); //how many suggestions
			System.out.println("By '" + query + "' did you mean:");
			for(String suggestion : suggestions)
			    System.out.println("\t" + suggestion);
		}
    }
}
