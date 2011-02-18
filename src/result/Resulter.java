package result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Resulter {
	public Resulter( String request ) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Scanner s = new Scanner(new File( "config.txt" ));
		String aClassName = s.nextLine();
		s.close();
		aResulter = (Resultable)(Class.forName(aClassName).newInstance());
		aResulter.setRequest(request);
		aResulter.processing();
	}
	public int getNumOfAnswers() {
		return aResulter.getNumOfAnswers();
	}
	public String getAnswer( int index ) {
		return aResulter.getAnswer(index);
	}
	public String getSubText( int index ) {
		return aResulter.getSubText(index);
	}
	public String getName( int index ) {
		return aResulter.getName(index);
	}
	public double getRank( int index  ) {
		return aResulter.getRank(index);
	}
	private result.Resultable aResulter = null;
}
