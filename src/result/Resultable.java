package result;

public interface Resultable {
	public void setRequest( String request );
	public void processing();
	public int getNumOfAnswers();
	public String getAnswer( int index );
	public String getSubText( int index );
	public String getName( int index );
	public double getRank( int index );
}
