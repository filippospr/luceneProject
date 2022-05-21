package model;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.LevenshteinDistance;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class QuerySpellChecker 
{
	public QuerySpellChecker() throws IOException 
	{
		createSpellIndex();
	}
	
	private  void createSpellIndex() throws IOException 
	{
		 String spellCheckDir = "SpellingIndex";
		 String indexDir = "Index";
		 String indexField = "title";
		 
		 Directory dir = FSDirectory.open( Paths.get(spellCheckDir));
		 SpellChecker spell = new SpellChecker(dir);
		 Directory dir2 = FSDirectory.open(Paths.get(indexDir));
		 IndexReader r = DirectoryReader.open(dir2);
		 try 
		 {
			 IndexWriterConfig config = new IndexWriterConfig(new KeywordAnalyzer());
			 spell.indexDictionary(
					 new LuceneDictionary(r, indexField),config, false);
		 } finally {
			 r.close();
		 }
		 dir.close();
		 dir2.close();
	}
	
	public String[] suggestSearchQuery(String query) throws IOException 
	{
		 String spellCheckDir = "SpellingIndex";
		 String wordToRespell = query;
		 Directory dir = FSDirectory.open(Paths.get(spellCheckDir));
		 
		 SpellChecker spell = new SpellChecker(dir);
		 spell.setStringDistance(new LevenshteinDistance());
		 
		 String[] suggestions = spell.suggestSimilar(wordToRespell, 5);
		 
		 return suggestions;
	}
}
