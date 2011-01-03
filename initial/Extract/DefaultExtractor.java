package Extract;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DefaultExtractor implements Extractionable {
	public DefaultExtractor() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setPage( URL aUrl ) throws IOException {
		try {
			baseUri = aUrl.toURI();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner aScanner = new Scanner( aUrl.openStream() );
		StringBuilder aStringBuilder = new StringBuilder("");
		while( aScanner.hasNextLine() ) { 
			aStringBuilder.append( aScanner.nextLine() );
			aStringBuilder.append("\n");
		}
		aPage = aStringBuilder.toString();
	}
	public String getPage() {
		return aPage;
	}
	@Override
	public ArrayList<String> getLinks() {
		ArrayList<String> res = new ArrayList<String>();
		for( int i = SupportClassForDefaultExatractor.getNextEnteringAHREF(aPage, -1); i < aPage.length();
			i = SupportClassForDefaultExatractor.getNextEnteringAHREF(aPage, i) ) {
			StringBuilder sb = new StringBuilder();
			for( int j = 1; aPage.charAt( i + j ) != '"'; ++j ) {
				sb.append(aPage.charAt(i + j) );
			}
			res.add( baseUri.resolve(sb.toString() ).toString()  );
		}
		return res;
	}
	@Override
	public ArrayList<String> getWords() {
		// TODO Auto-generated method stub
		return null;
	}
	private String aPage;
	private URI baseUri;
}


class SupportClassForDefaultExatractor {
	static int getNextEnteringAHREF( String text, int from ) {
		int res = -1;
		String ahref = "a href";
		for( int i = from + 1; i < text.length() - ahref.length(); ++i ) {
			int shift = 0;
			for( ;shift < ahref.length(); ++shift ) { 
				if( ahref.charAt(shift) != text.charAt(shift + i) ) {  
					break;
				}
			}
			if( shift == ahref.length() ) {
				res = i + ahref.length();
				for( ;res < text.length() && text.charAt(res) != '"'; ++res );
				return res;
			}
		}
		return text.length();
	}
}