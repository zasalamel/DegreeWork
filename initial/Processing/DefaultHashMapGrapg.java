package Processing;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;

import Extract.Extractionable;

public class DefaultHashMapGrapg implements Graph {
	public DefaultHashMapGrapg( URL aUrl, Extractionable aExtractionable ) {
		LinkedList<String> aQueue = new LinkedList<String>();
		aQueue.push( aUrl.toString());
		while( !aQueue.isEmpty() ) {
			String curPage = aQueue.pop();
			System.out.println( curPage );
			if( values.get( curPage ) == null ) {
				try {
					aExtractionable.setPage(new URL(curPage));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				edges.put( curPage , new HashSet<String>( aExtractionable.getLinks() ) );
				values.put(curPage, aExtractionable.getWords() );
				aQueue.addAll( edges.get(curPage) );
			}
		}
	}
	@Override
	public Collection<String> getLinks(String aPage) {
		return edges.get(aPage);
	}
	@Override
	public ArrayList<String> getValue(String aPage) {
		return values.get(aPage);
	}
	public int getNumOfVertex() {
		return values.size();
	}
	private HashMap<String, HashSet<String> > edges = new HashMap<String, HashSet<String>>();
	private HashMap<String, ArrayList<String> > values = new HashMap<String, ArrayList<String>>();
}
