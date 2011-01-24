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
	public DefaultHashMapGraph( String aName, URL aUrl, Extractionable aExtractionable, Indexable aIndexator ) {
		indexator = aIndexator;
		LinkedList<String> aQueue = new LinkedList<String>();
		aQueue.push( aUrl.toString());
		while( !aQueue.isEmpty() ) {
			String curPage = aQueue.pop();
			if( edges.get( curPage ) == null && curPage.contains(aName) ) {
				if( indexator != null ) {
					indexator.indexLink( curPage );
				}
				if( isFile( curPage ) ) {
					edges.put(curPage, new HashSet<String>());
					continue;
				} 
				try {
					aExtractionable.setPage(new URL(curPage));
					edges.put( curPage , new HashSet<String>( aExtractionable.getLinks() ) );
					if( indexator != null ) {
						indexator.indexPage( aExtractionable.getWords() );
					}
					aQueue.addAll( edges.get(curPage) );
				} catch ( Exception e ) {
					if( indexator != null ) {
						indexator.indexError( "чё - то не то..." + curPage );
					}
				}
			}
		}
	}
//	public DefaultHashMapGraph( String aName, URL aUrl, Extractionable aExtractionable, Indexable aIndexator ) {
//		this( aName, aUrl, aExtractionable );
//		indexator = aIndexator;
//		this( aName, aUrl, aExtractionable );
//	}
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
	private String[] Extensions = new String[] { ".pdf", ".doc", ".avi", ".jpg", ".ppt", ".zip", ".rar", ".xls", ".gif", ".bmp" };
	private HashMap<String, HashSet<String> > edges = new HashMap<String, HashSet<String>>();
//	private HashMap<String, ArrayList<String> > values = new HashMap<String, ArrayList<String>>();
}
