package result;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import Processing.DefaultMorphologicalDictionary;

abstract public class AbstractIndexPage {
	abstract public  String getName();
	abstract public  int getCount( int numberPage );
	abstract public  Collection<Integer> getWords();
	public double scaleProduct( AbstractIndexPage aPage ) {
		double res = 0.0;
		for ( Integer numWord : aPage.getWords()) {
			res += getCount(numWord) * aPage.getCount(numWord);
		}
		return res;
	}
	public double getLenght() {
		double res = 0;
		for (Integer numWord : getWords()) {
			int countWord = getCount(numWord);
			res += countWord * countWord;
		}
		return res;
	}
}

class RankedIndexPage extends AbstractIndexPage {
	public RankedIndexPage() {}
	public static RankedIndexPage BuildPage( String aQuest ) {
		PrintStream ps = null;
		try {
			ps = new PrintStream(new File( "out.txt" ));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringTokenizer st = new StringTokenizer(aQuest.toLowerCase(), " .,/!?\n\t");
		TreeMap<Integer, Integer> tmpPage = new TreeMap<Integer, Integer>();
		while( st.hasMoreTokens() ) {
			String word = st.nextToken().toLowerCase();
			ps.println(word);
			if( !DefaultMorphologicalDictionary.isStopWord(word) ) {
				int indexWord = IndexData.getNumberOfWord(DefaultMorphologicalDictionary.getBase(word));
				ps.println( DefaultMorphologicalDictionary.getBase(word) );
				ps.println(IndexData.getNumberOfWord(DefaultMorphologicalDictionary.getBase(word) ) );
				if( indexWord > 0 ) {
					if( !tmpPage.containsKey( indexWord ) ) {
						tmpPage.put(indexWord, 0);
					}
					int p = tmpPage.get(indexWord);
					tmpPage.put(indexWord, p + 1);
				}
			}
		}
		ps.flush();
		int[] res = new int[tmpPage.size() * 2];
		int l = 0;
		for (int i : tmpPage.keySet()) {
			res[l++] = i;
			res[l++] = tmpPage.get(i);
		}
		return new RankedIndexPage( aQuest, res, true );
	}
	public RankedIndexPage( String aName, int[] aPage, boolean isSorted ) {
		page = aPage.clone();
		name = aName;
		if( !isSorted ) {
			sort();
		}
	}
	public RankedIndexPage( Scanner in ) {
		name = in.nextLine().trim();
		String[] indexes =  in.nextLine().trim().split(" ");
		page = new int[indexes.length];
		if( name != null && !name.equals("") && indexes != null && indexes[0] != null && indexes[0] != "" && indexes.length > 1 ) {
			for( int i = 0; i < indexes.length; ++i ) {
				page[i] = Integer.parseInt(indexes[i]);
			}
		}
	}
	@Override
	public int getCount(int numberPage) {
//		System.out.println(name);
//		System.out.println( numberPage );
		if( page == null || page.length < 2 ) {
			return 0;
		}
//		System.out.println("&");
		int minRes = 0;
		int maxRes = page.length / 2;
		while( minRes != maxRes ) {
//			System.out.println( "x" + minRes );
//			System.out.println( "y" + maxRes );
			int p = ( minRes + maxRes ) / 2;
			if( p == minRes ) {
				break;
			}
//			System.out.println( page[p * 2] );
			if( page[p * 2] < numberPage ) {
				minRes = p + 1;
			} else if( page[p * 2] > numberPage ) {
				maxRes = p - 1;
			} else {
				minRes = maxRes = p;
			}		
		}
		if( page[minRes * 2] == numberPage ) {
			return page[2 * minRes + 1];
		}
//		System.out.println("&");
		return 0;
	}
	@Override
	public String getName() {
		return name;
	}
	public void testPrint() {
		System.out.println( name );
		for ( int val : page) {
//			System.out.print( val + " " );
		}
		System.out.println();
	}
	private void sort() {
		for( int i = 0; i < page.length; i += 2 ) {
			for( int j = i; j > 0 && page[i] > page[i - 1]; j -= 2 ) {
				int tmp = page[i];
				page[i] = page[i - 2];
				page[i - 2] = tmp;
				tmp = page[i + 1];
				page[i + 1] = page[i - 2 + 1];
				page[i - 2 + 1] = tmp;
			}
		}
	}
	private String name = null;
	private int[] page = null;
	@Override
	public Collection<Integer> getWords() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for( int i = 0; i < page.length; i += 2 ) {
			res.add(page[i]);
		}
		return res;
	}
}