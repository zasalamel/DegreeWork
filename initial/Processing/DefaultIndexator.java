package Processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.TreeMap;

public class DefaultIndexator implements Indexable {
	public DefaultIndexator() {}
	@Override
	public void indexError(String page) {
		System.err.println( page );
	}
	@Override
	public void indexLink(String page) {
		System.out.println( page );
		curPage = page;
	}
	@Override
	public void indexPage(ArrayList<String> aPage) {
		TreeMap<Integer, Integer> aIndexPage = new TreeMap<Integer, Integer>();
		for (String aWord : aPage) {
			if( !isBadWord(aWord) ) {
				String aBaseWord = DefaultMorphologicalDictionary.getBase(aWord);
				int numOfWord = dictionary.add(aBaseWord);
				if( !aIndexPage.containsKey(numOfWord) ) {
					aIndexPage.put(numOfWord, 0 );
				}
				int countOfWord = aIndexPage.get(numOfWord);
				aIndexPage.put(numOfWord, countOfWord + 1);
			}
		}
		outIndex.println( curPage );
		for ( Integer numOfWord: aIndexPage.keySet() ) {
			int count = aIndexPage.get(numOfWord);
			outIndex.print( numOfWord.toString() + " " + new Integer(count).toString() + " " );
		}
		outIndex.println();	
		outIndex.flush();
	}
	static private boolean isBadWord( String word ) {
		return !DefaultMorphologicalDictionary.isStopWord(word);
	}
	@Override
	public void beginIndexable() {
		try {
			outIndex = new PrintStream(new File( "indexData" ));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	@Override
	public void endIndexable()  {
		// TODO Auto-generated method stub
		try {
			dictionary.save("dictionary");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	private String curPage;
	private TriplexTree dictionary = new TriplexTree();
	private PrintStream outIndex = null;
}
