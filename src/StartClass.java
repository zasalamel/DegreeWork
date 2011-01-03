import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import Extract.DefaultExtractor;


public class StartClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DefaultExtractor aDefaultExtractor = new DefaultExtractor();
		try {
			aDefaultExtractor.setPage(new URL("http://pmkinfo.tversu.ru/spec/pln.php?s=it&d=cs"));
			System.out.println( aDefaultExtractor.getPage() );
			ArrayList<String> s = aDefaultExtractor.getLinks();
			for (String string : s) {
				System.out.println( string );
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
