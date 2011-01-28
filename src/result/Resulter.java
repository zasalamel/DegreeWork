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
		System.out.println( aClassName );
		aResulter = (Resultable)(Class.forName("result.testClass").newInstance());
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
	private result.Resultable aResulter = null;
}
