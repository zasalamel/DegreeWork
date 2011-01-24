package Processing;

import java.util.ArrayList;

public class DefaultIndexator implements Indexable {
	@Override
	public void indexError(String page) {
		System.err.println( page );
	}
	@Override
	public void indexLink(String page) {
		System.out.println( page );
	}
	@Override
	public void indexPage(ArrayList<String> aPage) {
		
	}
}
