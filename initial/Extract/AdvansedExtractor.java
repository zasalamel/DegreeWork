package Extract;

import java.net.URL;
import java.util.ArrayList;

public class AdvansedExtractor extends DefaultExtractor {
	public ArrayList<String> getParagraphs() {
		return aParagraphs;
	}
	@Override
	public void setPage(URL aUrl) {
		super.setPage(aUrl);
//		System.out.println( aPage );
		aParagraphs = new ArrayList<String>();
		if( aPage.equals("") ) {
			return;
		}
		int i = 0;
		while( i < aPage.length() && !isNext(i, "<html") ) {
			++i;
		}
		i += 6;
		while( i < aPage.length() && aPage.charAt(i) != '>' ) {
			++i;
		}
		++i;
		while( i < aPage.length() ) {
			if( aPage.charAt(i) == '<' ) {
				if( isNext(i, endTeg) ) {
					break;
				} else if( isNext(i, "<script") ) {
					while( !isNext(i, "</script>") ) {
						++i;
					}
					i += 9;
				} else if( isNext(i, "<style") ) {
					while( !isNext(i, "</style>") ) {
						++i;
					}
					i += 8;					
				}
				while( i < aPage.length() && aPage.charAt(i) != '>' ) {
					++i;
				}
				++i;
			} else {
				StringBuilder sb = new StringBuilder();
				while( i < aPage.length() && aPage.charAt(i) != '<' ) {
					if( aPage.charAt(i) == '\n' ) {
						sb.append( " " );
					} else {
						sb.append( aPage.charAt(i) );
					}
					++i;
				}
				if( isGood( sb.toString() ) ) {
					aParagraphs.add(sb.toString());
					System.out.println(sb.toString());
				}
//				System.out.println(sb.toString());
			}
		}
		
	}
	@Override
	public ArrayList<String> getWords() {
		ArrayList<String> res = new ArrayList<String>();
		for (String string : aParagraphs) {
			res.addAll(getWords( string ));
		}
		return res;
	}
	@Override
	protected boolean isGoodChar(char ch) {
		// TODO Auto-generated method stub
		return ( 'à' <= ch && ch <= 'ÿ' ) || (  'a' <= ch && ch <= 'z' );
	}
	private boolean isGood( String aPar ) {
		for( int i = 0; i < aPar.length(); ++i ) {
			if( isGoodChar( aPar.charAt(i) ) ) {
				return true;
			}
		}
		return false;
	}
	private boolean isNext( int from, String ahref ) {
		int pos = 0;
		while( from < aPage.length() && pos < ahref.length() ) {
			if( aPage.charAt(from) != ahref.charAt(pos) ) {
				return false;
			}
			++pos;
			++from;
		}
//		System.out.println( ahref.length() );
		return pos == ahref.length();
	}
	private static final String endTeg = "</html>"; 
	private ArrayList<String> aParagraphs = null;
}
