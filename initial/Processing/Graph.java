package Processing;

import java.util.ArrayList;
import java.util.Collection;

public interface Graph {
	Collection<String> getLinks( String aPage );
	ArrayList<String> getValue( String aPage );
}