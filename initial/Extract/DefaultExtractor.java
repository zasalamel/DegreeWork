package Extract;


import java.io.IOException;
import org.mozilla.universalchardet.UniversalDetector;
//import org.mozilla.unisalchardet.

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader;

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
			e.printStackTrace();
			System.err.println( "Не правильный url..." );
		}
		InputStream in = null;
		try {
			in = aUrl.openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println( "Не возможно открыть страничку " + aUrl.toString() );
		}
		try {
			aPage = CharacterDetector.getPage(aUrl.toString());
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( "Че - то не так с документом...( возмоно он очень большой ) " + aUrl.toString() );
		} 
	}
	public String getPage() {
		return aPage;
	}
	@Override
	public ArrayList<String> getLinks() {
		ArrayList<String> res = new ArrayList<String>();
		for( int i = getNextEnteringAHREF( -1 ); i < aPage.length(); i = getNextEnteringAHREF( i ) ) {
			StringBuilder sb = new StringBuilder();
			for( int j = 1; aPage.charAt( i + j ) != '"' && aPage.charAt( i + j ) != '\'' ; ++j ) {
				sb.append(aPage.charAt(i + j) );
			}
			try {
				String tmpPage = baseUri.resolve(sb.toString() ).toString();
				res.add(tmpPage);
			} catch( Exception e ) {
//				System.err.println( "Ошибка парсинга " + sb.toString() + " " + baseUri.toString() );
				String str = myStupidAlgOfGetLink(baseUri.toString(), sb.toString());
				System.err.println( "Ошибка парсинга " + sb.toString() + " " + baseUri.toString() + " заменено " + str );
				res.add(str);
			}
		}
		return res;
	}
	@Override
	public ArrayList<String> getWords() {
		return getWords( aPage );
	}
	protected ArrayList<String> getWords( String aString ) {
		ArrayList<String> res = new ArrayList<String>();
		for( int i = 0; i < aString.length(); ) {
			StringBuilder aStringBuilder = new StringBuilder();
			for( ; i < aString.length() && isGoodChar( aString.charAt( i ) ) ; ++i ) {
				aStringBuilder.append( aString.charAt( i ) );
			}
			if( aStringBuilder.length() != 0 )  {			
				res.add( aStringBuilder.toString() );
			} else {
				++i;
			}
		}
		return res;
	}
	protected boolean isGoodChar( char ch ) {
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
    private static String myStupidAlgOfGetLink( String prefix, String inPostfix ) {
    	int shift = 0;
    	while( inPostfix.charAt(shift) == '.' ) {
    		++shift;
    	}
    	String postfix = inPostfix.substring(shift);
    	int resShift = 0;
    	int resCountEntry = getCountEntry(prefix, postfix, 0);
    	for( int i = 1; i < prefix.length(); ++i ) {
    		int p = getCountEntry(prefix, postfix, i);
    		if( p > resCountEntry ) {
    			resCountEntry = p;
    			resShift = i;
    		}
    	}
    	String res = prefix.substring(0, resShift);
    	return res.concat(postfix);
    }
    private static int getCountEntry( String prefix, String postfix, int shift ) {
    	int res = 0;
    	while( res < postfix.length() && res + shift < prefix.length() 
    			&& prefix.charAt(res + shift) == postfix.charAt(res) ) {
    		++res;
    	}
    	return res;
    }
	protected String aPage;
	private URI baseUri;
}

class CharacterDetector {
    private static CharacterDetector instance;
    private CharacterDetector() {}
    private UniversalDetector detector = new UniversalDetector(null);
    public static final CharacterDetector getInstance(){
        if ( instance == null ) instance = new CharacterDetector();
        return instance;
    }
    public String detect(InputStream stream) throws IOException {
        // Reset detector before using
        detector.reset();
        try {
    		byte[] buffer = new byte[200000];
    		int i = 0;
    		int len = 0;
    		while( ( len = stream.read( buffer, i, 1000 ) ) != -1 ) {
    			i += len;
    		}
    		detector.handleData(buffer, 0, i);
            detector.dataEnd();
            String aCharset = detector.getDetectedCharset();
            if( aCharset == null ) {
//            	System.out.println( new String( buffer, 0, i ) );
            	return new String( buffer, 0, i );            	
            } else {
            	return new String( new String( buffer, 0, i ).getBytes(), aCharset );
            }
        } finally {
            detector.reset();
        }
    }
    public static String getPage( String aPage ) throws IOException {
		CharacterDetector cd = CharacterDetector.getInstance();
		URL aUrl = new URL( aPage );
		return cd.detect( aUrl.openStream() ).toLowerCase();
    }
}
