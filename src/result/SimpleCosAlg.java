package result;

import java.util.Arrays;

public class SimpleCosAlg implements Resultable {
	@Override
	public String getAnswer(int index) {
		return aAnswers[index - 1].getName();
	}
	@Override
	public String getName(int index) {
		return aAnswers[index - 1].getName();
	}
	@Override
	public int getNumOfAnswers() {
		int res = 0;
		while( res < numOfMaxAnswers && aAnswers[res] != null ) {
			++res;
		}
		System.out.println(res);
		return res;
	}
	@Override
	public String getSubText(int index) {
		return aAnswers[index - 1].getName();
	}
	@Override
	public void processing() {
		System.out.println("processing request...");
		aAnswers = new RankedIndexPage[numOfMaxAnswers];
		double[] ranks = new double[numOfMaxAnswers];
		Arrays.fill(ranks, 0.0);
		for ( AbstractIndexPage aPage  : IndexData.getAllPages() ) {
			RankedIndexPage page = (RankedIndexPage)(aPage);
			double rank = page.scaleProduct(aRankedIndexPage) / ( page.getLenght() * aRankedIndexPage.getLenght() );
			push(page, ranks, rank);
		}
	}
	@Override
	public void setRequest(String request) {
		System.out.println( "Request was sended..." );
		aRankedIndexPage = RankedIndexPage.BuildPage(request);
	}
	private void push( RankedIndexPage aPage, double[] ranks, double rank ) {
		if( rank > 0 ) {
//			System.out.println( aPage.getName()  );
//			System.out.println( rank  );
		}
		int pos = numOfMaxAnswers - 1;
		while( pos >= 0 && ranks[pos] < rank  ) {
			if( pos + 1 != numOfMaxAnswers ) {
				ranks[pos + 1] = ranks[pos];
				aAnswers[pos + 1] = aAnswers[pos];
			}
			--pos;
		}
		++pos;
		if( pos < numOfMaxAnswers ) {
			ranks[pos] = rank;
			aAnswers[pos] = aPage;
		}
	}
	private static final int numOfMaxAnswers = 20;
	private RankedIndexPage aRankedIndexPage = null;
	private RankedIndexPage[] aAnswers = null;
}
