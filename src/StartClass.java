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
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, BadLocationException {
		ArrayList<String> al = new ArrayList<String>();
//		al.add("http://university.tversu.ru");
		al.add("http://university.tversu.ru/");
//		al.add("http://pmkinfo.tversu.ru");
		new DefaultHashMapGraph(al, new AdvansedExtractor(), new DefaultIndexator());

	}
}
/*        HTMLEditorKit kit = new HTMLEditorKit();
        HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
        doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
        kit.read(new InputStreamReader(new FileInputStream("C:\\Users\\SeeD\\Desktop\\Тверской государственный университет.mht"), "windows-1251"), doc, 0);
        ElementIterator it = new ElementIterator(doc);
        Element elem;
        while ((elem = it.next()) != null) {
        	System.out.println( elem.getName() );
            if (elem.getName().equals("link")) {*/
//            	((HTMLDocument.BlockElement) elem).
 //           	System.out.println( elem.getAttributes().toString() );
//            	AttributeSet as = elem.getAttributes();
 //           	Set<String> qwe = (Set<String>)(as.getAttributeNames() );
//            	for ( String string :  ) {
 //           		System.out.println( string );	
//				}
            	
//            	System.out.println( as.toString() );
            	
       //     	for ( Object string : as.getAttributeNames()) {
					
	//			}
//                String s = (String) elem.getAttributes().getAttribute("tppabs");
/*                if (s != null) {
                    if (elem instanceof HTMLDocument.BlockElement) {
 //                       ((HTMLDocument.BlockElement) elem).removeAttribute("tppabs");
 //                       ((HTMLDocument.BlockElement) elem).removeAttribute(HTML.Attribute.SRC);
 //                       ((HTMLDocument.BlockElement) elem).addAttribute(HTML.Attribute.SRC, s);
                    } else {
                        if (elem instanceof HTMLDocument.RunElement) {
//                            ((HTMLDocument.RunElement) elem).removeAttribute("tppabs");
 //                           ((HTMLDocument.RunElement) elem).removeAttribute(HTML.Attribute.SRC);
 //                           ((HTMLDocument.RunElement) elem).addAttribute(HTML.Attribute.SRC, s);
                        }
                    }
 //                   jTextArea1.append(s);
                }*/
//            }
 //       }
//	URL aUrl = new URL("http://university.tversu.ru/docs/1479-%D0%BE.doc");
//	System.out.println( aUrl. );
//	File f = new File( "C:\\Users\\SeeD\\Desktop\\Лекции по аналитической геометрии МГУ.djvu" );
//	File f = new File( aUrl.toString() );
//	System.out.println( f.isHidden() );
//		Resulter r = new Resulter("qwe");
		
//		System.out.println("qwe\nqwe");
/*		Resulter r = new Resulter(request)
		for( int i = 1; i <= r.getNumOfAnswers(); ++i ) {
			System.out.println( r.getAnswer(i) );
			System.out.println( r.getName(i) );
			System.out.println( r.getSubText(i) );
		}*/
/*		String qwe = "qwe-qwe/qw.qweqwe.qwedsd...s";
		StringTokenizer st = new StringTokenizer(qwe, "/-.");
		while( st.hasMoreTokens() ) {
			System.out.println( st.nextToken() );
		}*/
//		System.out.println("1");
//		Hinter h = new Hinter("file://localhost/C:/Users/SeeD/Desktop/Тверской%20государственный%20университет.mht", "декан технологии");
//		System.out.println( h.getBetter());
/*		AdvansedExtractor ae = new AdvansedExtractor();
		ae.setPage(new URL("file://localhost/C:/Users/SeeD/Desktop/Тверской%20государственный%20университет.mht"));
		for (String string : ae.getParagraphs()  ) {
			System.out.println(string);
		}*/
//		Hinter h = new Hinter("http://university.tversu.ru/", "");
//		new DefaultHashMapGraph("university.tversu", new URL( "http://university.tversu.ru" ), new AdvansedExtractor(), new DefaultIndexator());
/*		Scanner in = new Scanner( System.in );
		for( String str = in.next(); !str.equals("!"); str = in.next() ) {
			System.out.println( DefaultMorphologicalDictionary.getBase(str) );
		}*/
//	}
//}
