package Extract;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public interface Extractionable {
	ArrayList<String> getLinks();
	ArrayList<String> getWords();
	void setPage(URL aUrl) throws IOException;
}
