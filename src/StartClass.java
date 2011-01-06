import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.DefaultDesktopManager;

import Extract.DefaultExtractor;
import Processing.DefaultHashMapGrapg;
import Processing.Graph;


public class StartClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Graph g = new DefaultHashMapGrapg( "university.tversu", new URL("http://university.tversu.ru/"), new DefaultExtractor());
			System.out.println( ((DefaultHashMapGrapg)(g)).getNumOfVertex() );
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		DefaultExtractor aDefaultExtractor = new DefaultExtractor();
		try {
			aDefaultExtractor.setPage(new URL("http://university.tversu.ru/informatisation/"));
			System.out.println( aDefaultExtractor.getPage() );
			ArrayList<String> s = aDefaultExtractor.getLinks();
			for (String string : s) {
				System.out.println( string );
			}
			s = aDefaultExtractor.getWords();
			for( String string: s ) {
				System.out.println( string );
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		System.out.println("qwe");
		
	}

}
