package Processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class SynonimDictionatry {
	private static TreeMap<String, ArrayList<String> > dis = new TreeMap<String, ArrayList<String>>();
	static  {
		Scanner in = null;
		try {
			in = new Scanner( new File( "synonim_dis.txt" ) );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		while( in.hasNext() ) {
			StringTokenizer st = new StringTokenizer(in.nextLine().trim(), ",");
			String aString = st.nextToken();
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(aString);
			while( st.hasMoreTokens() ) {
				arrayList.add(st.nextToken().trim());
			}
			dis.put(aString, arrayList);
		}
		System.out.println("Dictionatry was loaded...");
	}
	public static ArrayList<String> getSynonim( String word ) {
		ArrayList<String> res = dis.get(word);
		if( res == null ) {
			res = new ArrayList<String>();
			res.add(word);
		}
		return res;
	}
}
