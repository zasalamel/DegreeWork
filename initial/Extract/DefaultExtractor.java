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
	public void setPage( URL aUrl ) {
		aPage = "";
		try {
			baseUri = aUrl.toURI();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.err.println( "Не правильный url..." );
		}
		Scanner aScanner = null;
		try {
			aScanner = new Scanner( aUrl.openStream() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println( "Не возможно открыть страничку " + aUrl.toString() );
		}
		StringBuilder aStringBuilder = new StringBuilder("");
		try {
			while( aScanner != null && aScanner.hasNextLine() ) {
				String tmpStr = aScanner.nextLine();
				if( aStringBuilder.length() + tmpStr.length() > 200000 ) {
					throw new Exception();
				}
				aStringBuilder.append(tmpStr);
//				System.out.print(aStringBuilder.toString());
				aStringBuilder.append("\n");
			}
		} catch( Exception e ) {
			System.err.println( "Че - то не так с документом...( возмоно он очень большой ) " + aUrl.toString() );
		}
		aPage = aStringBuilder.toString().toLowerCase();
	}
	public String getPage() {
		return aPage;
	}
	@Override
	public ArrayList<String> getLinks() {
		ArrayList<String> res = new ArrayList<String>();
		for( int i = getNextEnteringAHREF( -1 ); i < aPage.length();
			i = getNextEnteringAHREF( i ) ) {
			StringBuilder sb = new StringBuilder();
			for( int j = 1; aPage.charAt( i + j ) != '"' && aPage.charAt( i + j ) != '\'' ; ++j ) {
				sb.append(aPage.charAt(i + j) );
			}
			try {
				String tmpPage = baseUri.resolve(sb.toString() ).toString();
				res.add(tmpPage);
			} catch( Exception e ) {
				System.err.println( "Ошибка парсинга..." + sb.toString() + " " + baseUri.toString() );
			}
		}
		return res;
	}
	@Override
	public ArrayList<String> getWords() {
		ArrayList<String> res = new ArrayList<String>();
		for( int i = 0; i < aPage.length(); ) {
			StringBuilder aStringBuilder = new StringBuilder();
			for( ;isGoodChar( aPage.charAt( i ) ) && i < aPage.length(); ++i ) {
				aStringBuilder.append( aPage.charAt( i ) );
			}
			if( aStringBuilder.length() != 0 )  {			
				res.add( aStringBuilder.toString() );
			} else {
				++i;
			}
		}
		return res;
	}
	private static boolean isGoodChar( char ch ) {
		return ( ch >= 'а' && ch <= 'я' );
	}
	private int getNextEnteringAHREF( int from ) {
		int res = -1;
		String ahref = "a href";
		for( int i = from + 1; i < aPage.length() - ahref.length(); ++i ) {
			int shift = 0;
			for( ;shift < ahref.length(); ++shift ) { 
				if( ahref.charAt(shift) != aPage.charAt(shift + i) ) {  
					break;
				}
			}
			if( shift == ahref.length() ) {
				res = i + ahref.length();
				for( ;res < aPage.length() && aPage.charAt(res) != '"' && aPage.charAt(res) != '\''; ++res );
				return res;
			}
		}
		return aPage.length();
	}
	private String aPage;
	private URI baseUri;
}
