import java.io.BufferedWriter;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;

import result.IndexData;
import result.Resulter;

import Extract.DefaultExtractor;
import Processing.DefaultHashMapGraph;
import Processing.DefaultIndexator;
import Processing.DefaultMorphologicalDictionary;
import Processing.Graph;
import Processing.TriplexTree;


public class StartClass {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Resulter r = new Resulter("qwe");
		
		System.out.println("qwe\nqwe");
/*		Resulter r = new Resulter(request)
		for( int i = 1; i <= r.getNumOfAnswers(); ++i ) {
			System.out.println( r.getAnswer(i) );
			System.out.println( r.getName(i) );
			System.out.println( r.getSubText(i) );
		}*/
//		new DefaultHashMapGraph("university.tversu", new URL( "http://university.tversu.ru" ), new DefaultExtractor(), new DefaultIndexator());
/*		Scanner in = new Scanner( System.in );
		for( String str = in.next(); !str.equals("!"); str = in.next() ) {
			System.out.println( DefaultMorphologicalDictionary.getBase(str) );
		}*/
	}
}
