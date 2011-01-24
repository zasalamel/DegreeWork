package Processing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;


public class TriplexTree {
	public TriplexTree() {}
	public TriplexTree( Reader in ) throws IOException {
		read(aRood, in);
	}
	public TriplexTree( String filename ) throws IOException {
		Reader in = new BufferedReader( new FileReader( filename ) );
		read(aRood, in);
		in.close();
	}
	public int add( String aWord ) {
		setWord( aRood, aWord, 0 );
		return numberOFCurWord - 1;
	}
	public int getNumberOfWord( String aWord ) {
		return getNumberOfWord(aRood, aWord, 0);
	}
	public Set<String> getAllWord() {
		Set<String> res = new TreeSet<String>();
		getAllWord(aRood, new StringBuilder(), res);
		return res;
	}
	public void save( Writer out ) throws IOException {
//		out.write(openBracket);
		save(aRood, out);
		out.write(closeBracket);
	}
	public void save( String filename ) throws IOException {
		Writer out = new BufferedWriter(new FileWriter( filename ));
		save( out );
		out.flush();
		out.close();
	}
	private int setWord( TriplexTreeNode aTriplexTreeNode, String aWord, int pos ) {
		if( aTriplexTreeNode == null ) {
			aTriplexTreeNode = new TriplexTreeNode(); 
		}
		if( pos == aWord.length() ) {
			int res =  numberOFCurWord;
			if( aTriplexTreeNode.getNumberOfWord() >= 0 ) {
				res = aTriplexTreeNode.getNumberOfWord();
			} else {
				res = numberOFCurWord++;
			}
			aTriplexTreeNode.setNumberOfWord( res );
			return res;
		} else {
			return setWord( aTriplexTreeNode.getNode(aWord.charAt(pos)), aWord, pos + 1 );
		}
	}
	private int getNumberOfWord( TriplexTreeNode aTriplexTreeNode, String aWord, int pos ) {
		if( aTriplexTreeNode == null ) {
			return -1;
		}
		if( pos == aWord.length() ) {
			return aTriplexTreeNode.getNumberOfWord();
		}
		return getNumberOfWord(aTriplexTreeNode.getNode(aWord.charAt(pos)), aWord, pos + 1);
	}
	private void getAllWord( TriplexTreeNode aTriplexTreeNode, StringBuilder aCurWord, Set<String> res ) {
		if( aTriplexTreeNode == null ) { 
			return;
		}
		if( aTriplexTreeNode.getNumberOfWord() >= 0 ) {
			res.add(aCurWord.toString());
		}
		for (Character aCharacter : aTriplexTreeNode.getSymvolls()) {
			aCurWord.append(aCharacter);
			getAllWord( aTriplexTreeNode.getNode(aCharacter), aCurWord, res );
			aCurWord.deleteCharAt(aCurWord.length() - 1);
		}
	}
	private void read( TriplexTreeNode aTriplexTreeNode, Reader in ) throws IOException {
		char ch = (char) in.read();
		if( ch == delimer ) {
			aTriplexTreeNode.setNumberOfWord( getInt( in ) );
			numberOFCurWord = Math.max(numberOFCurWord, aTriplexTreeNode.getNumberOfWord() + 1);
			ch = (char)in.read();
		}
		while( ch != closeBracket ) {
			read(aTriplexTreeNode.getNode(ch), in);
			ch = (char)in.read();
		}
	}
	private static int getInt( Reader in ) throws IOException {
		int res = 0;
		for( char ch = (char)in.read(); ch != delimer; ch = (char) in.read() ) {
			res = res * 10 + ( ch - '0' );
		}
		return res;
	}
	private void save( TriplexTreeNode aTriplexTreeNode, Writer out ) throws IOException {
		if( aTriplexTreeNode == null ) {
			return;
		}
		if( aTriplexTreeNode.getNumberOfWord() >= 0 ) {
			out.write( delimer );
			out.write( aTriplexTreeNode.getNumberOfWord().toString() );
			out.write( delimer );
		}
		for (Character aSymvol : aTriplexTreeNode.getSymvolls()) {
			out.write(aSymvol);
//			out.write(openBracket);
			save( aTriplexTreeNode.getNode(aSymvol), out );
			out.write(closeBracket);
		}
	}
	protected static final Character delimer = '$';
	protected static final Character openBracket = '(';
	protected static final Character closeBracket = ')';
	protected int numberOFCurWord = 0;
	protected TriplexTreeNode aRood = new TriplexTreeNode();
	protected static class TriplexTreeNode {
		public TriplexTreeNode() {
			
		}
		private int numberOfWord = -1;
		private Map<Character, TriplexTreeNode> aMap = new TreeMap<Character, TriplexTreeNode>();
		TriplexTreeNode getNode( Character symvol ) {
			TriplexTreeNode res = aMap.get(symvol);
			if( res == null ) {
				res = new TriplexTreeNode();
			}
			aMap.put(symvol, res);
			return res;
		}
		public Integer getNumberOfWord() {
			return numberOfWord;
		}
		public void setNumberOfWord( int newNumberOfWord ) {
			numberOfWord = newNumberOfWord;
		}
		public Set<Character> getSymvolls() {
			return aMap.keySet();
		}
	}
}

