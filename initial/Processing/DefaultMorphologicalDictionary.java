package Processing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class DefaultMorphologicalDictionary {
	private static TreeMap<String, String> baseDictionary = new TreeMap<String, String>();
	private static TreeSet<String> badWord = new TreeSet<String>();
	static {
		try {
			System.out.println( "Begin loading morphological dictionary..." );
			Scanner in = new Scanner(new BufferedInputStream(new FileInputStream( new File( "morphological_dictionary.txt" ))));
			int i = 0;
			while( in.hasNext() ) {
				StringTokenizer worder = new StringTokenizer(in.nextLine().toLowerCase(), " \n\t-,");
				if( worder.countTokens() > 1 ) {
					String base = worder.nextToken();
					baseDictionary.put(base, base);
					while( worder.hasMoreTokens() ) {
						baseDictionary.put(worder.nextToken(), base);
					}
				} else {
					badWord.add(worder.nextToken());
				}
			}
			System.out.println( "End loading morphological dictionary..." );
		} catch (FileNotFoundException e) {
			System.err.println("Dictionary not found...");
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static String getBase( String word ) {
		if( baseDictionary.containsKey(word.toLowerCase()) ) {
			return baseDictionary.get(word.toLowerCase());
		}
		return word;
	}
	public static boolean isStopWord( String word ) {
		return badWord.contains(word);
	}
}