package result;
import java.io.OutputStream;
import java.util.Collection;

abstract public class AbstractIndexPage {
	abstract public  String getName();
	abstract public  int getCount( int numberPage );
	abstract public  Collection<Integer> getWords();
	public double scaleProduct( AbstractIndexPage aPage ) {
		double res = 0.0;
		for ( Integer numWord : aPage.getWords()) {
			res += getCount(numWord) * aPage.getCount(numWord);
		}
		return res;
	}
	public double getLenght() {
		double res = 0;
		for (Integer numWord : getWords()) {
			int countWord = getCount(numWord);
			res += countWord * countWord;
		}
		return res;
	}
}