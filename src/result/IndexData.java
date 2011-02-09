package result;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Processing.TriplexTree;

public class IndexData {
	private static final int maxLinks = 20000;
	private static TriplexTree dictionary = null;
	private static ArrayList<AbstractIndexPage> pages = new ArrayList<AbstractIndexPage>();
	static {
		Scanner in = null;
		try {
			dictionary = new TriplexTree("dictionary");
			System.out.println( "Dictionary was loaded..." );
			in = new Scanner(new File("indexData"));
			while( in.hasNext() ) {
				pages.add(new RankedIndexPage(in));
			}
			System.out.println( "Index Page was loaded..." );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static int getNumberOfWord( String word ) {
		return dictionary.getNumberOfWord(word);
	}
	public static ArrayList<AbstractIndexPage> getAllPages() {
		return pages;
	}
}
