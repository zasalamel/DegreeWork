package result;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.omg.CORBA.Request;

import Extract.AdvansedExtractor;
import Extract.DefaultExtractor;
import Processing.DefaultMorphologicalDictionary;

public class Hinter {
	public Hinter( String url, String reques ) {
		name = url;
		aReques = reques;
		try {
			AdvansedExtractor aExtractor = new AdvansedExtractor();
			aExtractor.setPage(new URL(url));
			aParagraphs = aExtractor.getParagraphs();
		} catch ( Exception e) {
			e.printStackTrace();
			System.out.println( "can not open" + url );
		}
	}
	public String getHint() {
		return "";	
	}
	public String getName() {
		return name;
	}
	public String getBetter() {
		double res = 0;
		String resWord = "";
		for (String  aPar : aParagraphs) {
			double denuminator = numOfWord(aPar);
			StringTokenizer st = new StringTokenizer(RankedIndexPage.getNormalRequest(aReques), " " );
			String tmp = aPar;
			while( st.hasMoreTokens() ) {
				 tmp = addPrevAndPos(tmp, DefaultMorphologicalDictionary.getBase(st.nextToken()), "<b>", "</b>");
			}
			double numinator = numOfIn(tmp, "<b>");
			if( numinator / denuminator > res ) {
				res = numinator / denuminator;
				resWord = tmp;
			}
		}
		return resWord;
	}
	
	private static String addPrevAndPos( String str, String word, String prev, String post ) {
		StringBuilder newPar = new StringBuilder();
		for( int i = 0; i < str.length(); ) {
			if( isGoodSymvod(str.charAt(i)) ) {
				StringBuilder curWord = new StringBuilder();
				while( i < str.length() && isGoodSymvod( str.charAt(i) ) ) {
					curWord.append(str.charAt(i));
					++i;
				}
				if( word.equals( DefaultMorphologicalDictionary.getBase(( curWord.toString() ) ) ) ) {	
					newPar.append(prev);
					newPar.append(curWord);
					newPar.append(post);
				} else {
					newPar.append(curWord);
				}
			} else {
				newPar.append(str.charAt(i));
				++i;
			}
		}
		return newPar.toString();
	}
	private static int numOfIn( String s1, String s2 ) {
		int res = 0;
		for( int i = 0; i < s1.length(); ++i ) {
			int len = 0;
			while( i + len < s1.length() && len < s2.length() && s1.charAt(i + len) == s2.charAt(len) ) {
				++len;
			}
			if( len == s2.length() ) {
				++res;
			}
		}
		return res;
	}
	private static int numOfWord( String aPar ) {
		int res = 0;
		for( int i = 0; i < aPar.length(); ) {
			if( isGoodSymvod( aPar.charAt(i) ) ) {
				++res;
				while( i < aPar.length() && isGoodSymvod(aPar.charAt(i)) ) {
					++i;
				}
			} else {
				++i;
			}
		}
		return res;
	}
	private static boolean isGoodSymvod( char ch ) {
		return (  'à' <= ch && ch <= 'ÿ' ) || ( 'a' <= ch && ch <= 'z' );
	}
	private ArrayList<String> aParagraphs = new ArrayList<String>();
	private String name = null;
	private String aReques = null;
	private String[] aPage = null;
}
