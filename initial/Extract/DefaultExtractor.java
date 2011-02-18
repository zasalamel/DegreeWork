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
			System.err.println( "�� ���������� url..." );
		}
		InputStream in = null;
		try {
			in = aUrl.openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println( "�� �������� ������� ��������� " + aUrl.toString() );
		}
		try {
			aPage = CharacterDetector.getPage(aUrl.toString());
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( "�� - �� �� ��� � ����������...( ������� �� ����� ������� ) " + aUrl.toString() );
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
				System.err.println( "������ ��������..." + sb.toString() + " " + baseUri.toString() );
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
		return ( ch >= '�' && ch <= '�' );
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
            return new String( new String( buffer, 0, i ).getBytes(), aCharset );
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
