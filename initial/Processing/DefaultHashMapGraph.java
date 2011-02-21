package Processing;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;

import Extract.Extractionable;

public class DefaultHashMapGraph implements Graph {
	public DefaultHashMapGraph( ArrayList<String> aStartPages, Extractionable aExtractionable, Indexable aIndexator ) {
		indexator = aIndexator;
		extractor = aExtractionable;
		if( indexator != null ) {
			indexator.beginIndexable();
		}
		for (String aPage : aStartPages) {
			System.out.println(aPage);
			try {
				processing(aPage);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		if( indexator != null ) {
			indexator.endIndexable();
		}
	}
	private void processing( String aStartPage ) throws MalformedURLException {
		URL aUrl = new URL(aStartPage);
		LinkedList<String> aQueue = new LinkedList<String>();
		aQueue.push( aStartPage );
		while( !aQueue.isEmpty() ) {
			String curPage = aQueue.pop();
			if( edges.get( curPage ) == null && curPage.contains(aUrl.getHost()) && !isFile(curPage) ) {
				if( indexator != null ) {
					indexator.indexLink( curPage );
				}
				if( isFile( curPage ) ) {
					edges.put(curPage, new HashSet<String>());
					continue;
				} 
				try {
					extractor.setPage(new URL(curPage));
					edges.put( curPage , new HashSet<String>( extractor.getLinks() ) );
					if( indexator != null ) {
						indexator.indexPage( extractor.getWords() );
					}
					aQueue.addAll( edges.get(curPage) );
				} catch ( Exception e ) {
					e.printStackTrace();
					if( indexator != null ) {
						indexator.indexError( "чё - то не то..." + curPage );
					}
				}
			}
		}
	}
	@Override
	public Collection<String> getLinks(String aPage) {
		return edges.get(aPage);
	}
	@Override
	public ArrayList<String> getValue(String aPage) {
		return null;
	}
	public int getNumOfVertex() {
		return edges.size();
	}
	public void setIndexator( Indexable newIndexator ) {
		indexator = newIndexator;
	}
	private boolean isFile( String aName ) {
		for (String aExt : Extensions) {
			if( aName.contains( aExt ) ) {
				return true;
			}
		}
		return false;
	}
	private Indexable indexator = null;
	private Extractionable extractor = null;
	private String[] Extensions = new String[] { ".pdf", ".doc", ".avi", ".jpg", ".ppt", ".zip", ".rar", ".xls", ".gif", ".bmp" , ".rtf"};
	private HashMap<String, HashSet<String> > edges = new HashMap<String, HashSet<String>>();
//	private HashMap<String, ArrayList<String> > values = new HashMap<String, ArrayList<String>>();
}
