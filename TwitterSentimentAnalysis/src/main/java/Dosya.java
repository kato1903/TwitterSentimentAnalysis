import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dosya {

	public static void main(String[] args) throws IOException {
		
		Scanner in = new Scanner(new FileReader("file.txt"));
		LinkedList<String> tweetler = new LinkedList<String>();
		LinkedList<String> tweetler2 = new LinkedList<String>();
		while(in.hasNextLine()) {
		    tweetler.add(in.nextLine());
		}
		in.close();
//		String b = "negative,'";
		String c = "'";
		for(String str: tweetler)
		{	
			String b = "positive,'";
			str = etsil(str);
			str = str.toLowerCase();
			str = asci(str);
			str = linkSil2(str);
			str = Noktalama(str);
			str = Rakamlar(str);
			b += str;
			b += c;
			tweetler2.add(b);
		}
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		
		for(String str: tweetler2)
		{
			System.out.println(str);
			writer.println(str);
		}
		writer.close(); 
		
		
		
	}

	public static String asci(String a)
	{		
		return a.replaceAll("[^\\x20-\\x7e]", "");		
	}
	
	private static String linksil2(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            i++;
        }
        return commentstr;
    }
	
	public static String linkSil2(String s){
        String aux ="";
        if (s.contains("http")) {
            if (s.contains("…")) {
                Integer http = s.indexOf("http");
                if (s.indexOf("…", http) != -1) {
                    aux = s.substring(s.indexOf("http"), s.indexOf("…", http)+1);
                    s = s.replace(aux, " ");
                }
            }
        }
        s = s.replaceAll("@\\s*(\\w+)", " ");
        return s;
    }
	
	private static String etsil(String a)
    {	
		a = a.trim().replaceAll(" +", " ");
		a = a.replaceAll("@\\p{L}+", "");	
        return a;
    }
	
	public static String Bosluk(String s) {
		s = s.replaceAll("\\s{2,}", " ");
		return s;
	}
	
	public static String Noktalama(String s) {
        s = s.replaceAll("[^\\p{ASCII}]", "");
        s = s.replace( "." , " ");
        s = s.replace( "/" , " ");
        s = s.replace( "-" , " ");
        s = s.replace( ";" , " ");
        s = s.replace( "!" , " ");
        s = s.replace( ":" , " ");
        s = s.replace( "?" , " ");
        s = s.replace( "," , " ");
        s = s.replace( "(" , " ");
        s = s.replace( ")" , " ");
        s = s.replace( "[" , " ");
        s = s.replace( "]" , " ");
        s = s.replace( "\"" , " ");
        s = s.replace( "'" , "");
        s = s.replace( "#" , " ");
        s = s.replace( "&", " ");
        s = s.replace( "=", " ");
        s = s.replace( "|", " ");
        s = s.replace( ">", " ");
        s = s.replace( "<", " ");
        s = s.replace( "*", " ");
        s = s.replace( "_", " ");
        s = s.replace( "%", " ");
        s = s.replace( "@", " ");
        s = s.replace( "\\", " ");
        s = s.replace( "{", " ");
        s = s.replace( "}", " ");
        s = s.replace( "^", " ");
        s = s.replace( "$", " ");
        s = s.replace( "+", " ");
        s = s.replace( "*", " ");
        s = s.replace( "~", " ");
        s = s.replace( "`", " "); 
        return s;
    }
	
	public static String Rakamlar(String s)
	{
		s = s.replaceAll("[0-9]", " ");
		s = s.trim().replaceAll(" +", " ");
		return s;
	}
	
}	
