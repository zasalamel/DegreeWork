package Extract;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DefaultExtractor implements Extractionable {
	public DefaultExtractor() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setPage( URL aUrl ) throws IOException {
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<String> getWords() {
		// TODO Auto-generated method stub
		return null;
	}
	private String aPage;
}
