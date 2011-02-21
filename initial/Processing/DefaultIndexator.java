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
//		System.err.println( page );
	}
	@Override
	public void indexLink(String page) {
		System.out.println( page );
		curPage = page;
	}
	@Override
	public void indexPage(ArrayList<String> aPage) {
		++numOfPage;
		outIndex.println( curPage );
		TreeMap<Integer, Integer> aIndexPage = new TreeMap<Integer, Integer>();
		for (String aWord : aPage) {
			if( !DefaultMorphologicalDictionary.isStopWord(aWord) ) {
				String aBaseWord = DefaultMorphologicalDictionary.getBase(aWord);
				int numOfWord = dictionary.add(aBaseWord);
				if( !aIndexPage.containsKey(numOfWord) ) {
					aIndexPage.put(numOfWord, 0 );
					if( idfInf.size() <= numOfWord ) {
						idfInf.add(1);
					} else {
						idfInf.set(numOfWord, idfInf.get(numOfWord) + 1);
					}
				}
				int countOfWord = aIndexPage.get(numOfWord);
				aIndexPage.put(numOfWord, countOfWord + 1);
			}
		}
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
			PrintStream ps = new PrintStream("idf");
			ps.println(numOfPage);
			ps.println( idfInf.size() );
			for (Integer aIntdex : idfInf) {
				ps.print( aIntdex + " " );
			}
			ps.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	private int numOfPage = 0;
	private String curPage;
	private TriplexTree dictionary = new TriplexTree();
	private PrintStream outIndex = null;
	private ArrayList<Integer> idfInf = new ArrayList<Integer>();
}
