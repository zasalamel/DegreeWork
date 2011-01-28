package result;

public class testClass implements Resultable {
	public testClass() {}
	public void setRequest( String request ) {	
	}
	public void processing() {
	}
	public int getNumOfAnswers() {
		return data.length;
	}
	public String getAnswer( int index ) {
		return data[index - 1];
	}
	public String getSubText( int index ) {
		return new String( "Это подсказ ка для " + Integer.toString(index) + " th ссылки в запросе)))... надобно сделать её подлинее и <b>пожирнее</b>..." );
	}
	public String getName( int index ) {
		String res = new String( "Это " + Integer.toString(index) + " th ссылка в запросе)))" );
		return res;
	}
	private String data[] = new String[] {
			"http://university.tversu.ru/",
			"http://university.tversu.ru/news/index1020.html",
			"http://university.tversu.ru/awards/",
			"http://university.tversu.ru/news/index1029.html",
			"http://university.tversu.ru/news/index1032.html",
			"http://university.tversu.ru/news/index1038.html",
			"http://university.tversu.ru/news/index980.html",
			"http://university.tversu.ru/news/index1019.html",
			"http://university.tversu.ru/news/index827.html",
			"http://university.tversu.ru/news/index1031.html",
			"http://university.tversu.ru/news/index1021.html",
			"http://university.tversu.ru/140let/index.html",
			"http://university.tversu.ru/news/index1018.html",
			"http://university.tversu.ru/news/index945.html",
			"http://university.tversu.ru/news/index1036.html",
			"http://university.tversu.ru/news/index1027.html",
			"http://university.tversu.ru/news/index1030.html",
			"http://university.tversu.ru/news/index1024.html",
			"http://university.tversu.ru/news/index1022.html",
			"http://university.tversu.ru/news/index1033.html",
			"http://university.tversu.ru/news/index1034.html",
			"http://university.tversu.ru/news/index1011.html",
			"http://university.tversu.ru/news/index1041.html",
			"http://university.tversu.ru/news/index976.html",
			"http://university.tversu.ru/news/index1042.html"
		};
}
