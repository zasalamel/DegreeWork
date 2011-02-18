package result;

import java.net.URL;
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
//		System.out.println(res);
		return res;
	}
	@Override
	public String getSubText(int index) {
		Hinter h = new Hinter(aAnswers[index - 1].getName(), aRequest);
		String res = h.getBetter();
		if( res.length() > 0 ) {
			return res;
		} else {
			return aAnswers[index - 1].getName();
		}
	}
	@Override
	public void processing() {
		System.out.println("processing request...");
		aAnswers = new RankedIndexPage[numOfMaxAnswers];
		Arrays.fill(ranks, 0.0);
		for ( AbstractIndexPage aPage  : IndexData.getAllPages() ) {
			RankedIndexPage page = (RankedIndexPage)(aPage);
			double rank = page.scaleProduct(aRankedIndexPage) / Math.sqrt( ( page.getLenght() * aRankedIndexPage.getLenght() ) );
			push(page, ranks, rank);
		}
	}
	@Override
	public void setRequest(String request) {
		System.out.println( "Request was sended..." );
		aRequest = request;
		aRankedIndexPage = RankedIndexPage.BuildPage(request);
		aRankedIndexPage.testPrint();
	}
	private void push( RankedIndexPage aPage, double[] ranks, double rank ) {
		if( alredyExist(rank) ) {
			return;
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
	private boolean alredyExist( double rank ) {
		for( int i = 0; i < numOfMaxAnswers; ++i ) {
			if( Math.abs( rank - ranks[i] ) <= 0.00001 ) {
				return true;
			}
		}
		return false;
	}
	private static final int numOfMaxAnswers = 20;
	private String aRequest = null;
	private RankedIndexPage aRankedIndexPage = null;
	private RankedIndexPage[] aAnswers = null;
	private double[] ranks = new double[numOfMaxAnswers];
	@Override
	public double getRank(int index) {
		// TODO Auto-generated method stub
		return ranks[index - 1];
	}
}
