package Processing;

import java.util.ArrayList;

public interface Indexable {
	void beginIndexable();
	void endIndexable();
	void indexLink( String page );
	void indexPage( ArrayList<String> aPage );
	void indexError( String page );
}
