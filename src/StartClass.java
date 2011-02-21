import java.io.BufferedWriter;

import javax.swing.text.AttributeSet;
import java.io.Console;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import javax.smartcardio.ATR;
import javax.swing.text.BadLocationException;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.Element;

import org.omg.PortableServer.AdapterActivator;

import result.Hinter;
import result.IndexData;
import result.Resulter;

import Extract.AdvansedExtractor;
import Extract.DefaultExtractor;
import Processing.DefaultHashMapGraph;
import Processing.DefaultIndexator;
import Processing.DefaultMorphologicalDictionary;
import Processing.Graph;
import Processing.SynonimDictionatry;
import Processing.TriplexTree;
//import javax.




public class StartClass {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws BadLocationException 
	 */
	private static boolean isNext( String word, String iner, int shift ) {
		int res = 0;
		for( ; res < iner.length() && res + shift < word.length() 
			&& iner.charAt(res) == word.charAt(res + shift) ; ++res ) { }
		return res == iner.length();
	}
	private static int nextInner( String word, char ch, int shift ) {
		int res = shift;
		while( res < word.length() && word.charAt(res) != ch ) {
			++res;
		}
		return res;
	}
	private static String processing( String str ) {
		StringBuilder res = new StringBuilder();
		for( int i = 0; i < str.length(); ++i ) {
			char ch = str.charAt(i);
			if( ch == '[' ) { i = nextInner(str, ']', i); } else 
			if( ch == '(' ) { i = nextInner(str, ')', i); } else
			if( ch == '<' ) { i = nextInner(str, '>', i); } else
			if( ch == '.' ) { break; } else
			{ 
				if( isNext(str, ";", i) ) {
					res.append(',');
				} else if( isNext(str, "см.", i) ) {
					res.append(',');
					i += 2;
				} else if( isNext(str, "||", i) ) {
					res.append(',');
					++i;
				} else {
					res.append(str.charAt(i));
				}
			}
		}
		return res.toString();
	}
	private static boolean isgoodWord( String str ) {
		for( int i = 0; i < str.length(); ++i ) {
			char ch = str.charAt(i);
			if( !( 'а' <= ch && ch <= 'я' ) ) {
				return false;
			}
		}
		return true;
	}
	private static String tokenerString( String str ) {
		int count = 0;
		StringBuilder res = new StringBuilder();
		StringTokenizer st = new StringTokenizer(str, ",");
		String fitstTocken = st.nextToken();
		if( !isgoodWord( fitstTocken.trim() ) ) {
			return "";
		}
		res.append(fitstTocken.trim());
		while( st.hasMoreTokens() ) {
			String aTocken = st.nextToken();
			if( isgoodWord(aTocken.trim()) ) {
				++count;
				res.append(",");
				res.append(aTocken.trim());
			}
		}
		if( count == 0 )  {
			return "";
		} else {
		   return res.toString();
		}
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, BadLocationException {
		for (String string : SynonimDictionatry.getSynonim("телефон")) {
			System.out.println( string );
		} 
//		generate();
//		ArrayList<String> al = new ArrayList<String>();
//		al.add( "http://university.tversu.ru/" );
//		al.add("http://pmkinfo.tversu.ru/pmk/index.php");
//		new DefaultHashMapGraph(al, new AdvansedExtractor(), new DefaultIndexator());

	}
	/**
	 * @throws FileNotFoundException
	 */
	private static void generate() throws FileNotFoundException {
		Scanner in = new Scanner(new File( "1.txt" ) );
		String prev = in.nextLine().toLowerCase();
		PrintWriter out = new PrintWriter("synonim_dis.txt");
		while( in.hasNext() ) {
			StringBuilder sb = new StringBuilder(prev);
			while( in.hasNext() ) {
				prev = in.nextLine().toLowerCase();
				if( prev.length() > 0 && prev.charAt(0) == ' ' ) {
					sb.append(prev.trim());
				} else {
					break;
				}
			}
			String str = tokenerString(processing( sb.toString()));
			if( str.length() > 0 ) {
				out.println( str );
			}
		}
		out.flush();
	}
}
