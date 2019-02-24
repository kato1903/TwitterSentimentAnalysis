import java.text.Normalizer;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;


public class Preprocess {
	
	ArrayList<String> tweets = new ArrayList<>();
	public static String[] stopwords = {"i","is"};
	public static void main(String[] args) throws IOException {
		
		Preprocess preprocess = new Preprocess();
		LinkedList<String> tweetler = new LinkedList<String>();
		BufferedReader br = new BufferedReader(new FileReader("file.txt"));
		try {
			
		    String line = br.readLine();
		    tweetler.add(line);
		    while (line != null) {		    		
		        line = br.readLine();
		        tweetler.add(line);
		    }
		} finally {
		    br.close();
		} 
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		for(String str: tweetler)
		{
			str = preprocess.Preprocesss(str);
			writer.println(str);
		}
		
		
//		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
//		writer.println("The first line");
//		writer.println("The second line");
		writer.close();
    }
	
	public String asci(String a)
	{		
		return a.replaceAll("[^\\x20-\\x7e]", "");		
	}
	
	public  String Noktalama(String s) {
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
	
	public  String Rakamlar(String s)
	{
		s = s.replaceAll("[0-9]", " ");
		return s;
	}
	
	public  String Bosluk(String s) {
		s = s.replaceAll("\\s{2,}", " ");
		return s;
	}
	
	public  String TekrarlananHarf(String s) {
        s = s.replaceAll ("(\\w)\\1{2,}", "$1"); 
         s = s.replaceAll ("(([A-Za-z])(\\2{2})+)", "$2"); 

        return s;
    }
	
	public  String Preprocesss(String s)
	{	
		s = asci(s);
		s = etsil(s);
		s = s.toLowerCase();
		s = linkSil2(s);
//		s = linksil2(s);
		s = Rakamlar(s);
		s = Noktalama(s);
		s = TekrarlananHarf(s);
		s = stopWordSil(s);
		s = Bosluk(s);
		return s;
	}
	
	public  String linkSil2(String s){
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
	
	
	public  String stopWordSil(String s){
        
		for (String key : stopwords) {
            if (s.matches(".*\\b"+key+"\\b.*")) {
                s = s.replaceAll("\\b"+key+"\\b", " ");
            }            
        }

	    return s.replaceAll("\\b\\w{1,2}\\b","");
		
    }
	
	private  String linkSil(String commentstr)
	{
	    // rid of ? and & in urls since replaceAll can't deal with them
	    String commentstr1 = commentstr.replaceAll("\\?", "").replaceAll("\\&", "");

	    String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	    Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
	    Matcher m = p.matcher(commentstr);
	    int i = 0;
	    while (m.find()) {
	        commentstr = commentstr1.replaceAll(m.group(i).replaceAll("\\?", "").replaceAll("\\&", ""),"").trim();
	        i++;
	    }
	    return commentstr;
	}   
	
	private String linksil2(String commentstr)
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
	
	private String etsil(String a)
    {	
		a = a.trim().replaceAll(" +", " ");
		a = a.replaceAll("@\\p{L}+", "");	
        return a;
    }
	
}
