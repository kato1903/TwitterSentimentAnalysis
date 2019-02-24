import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Liu {
	
	public static String[] NEG = {"not",
			"dont", "cant", "shouldnt", "couldnt", "havent", "hasnt", "hadnt", "wont",
			"doesnt", "didnt", "isn", "arent", "aint", "never", "no", "noone", "none", "nowhere",
			"nothing", "neither", "nor", "no sense of", "lack", "lacks", "lacked", "lacking",
			"far from", "away from", "have yet to", "has yet to", "had yet to"};
	
	private Map<String, Integer> dictionary;
	
	public Liu(String positive,String negative) throws IOException {
		
		dictionary = new HashMap<String, Integer>();
		BufferedReader csv = null;
		try {
			csv = new BufferedReader(new FileReader(positive));

			String line;
			while ((line = csv.readLine()) != null) {
				dictionary.put(line,1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (csv != null) {
				csv.close();
			}
		}
		try {
			csv = new BufferedReader(new FileReader(negative));

			String line;
			while ((line = csv.readLine()) != null) {
				dictionary.put(line,-1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (csv != null) {
				csv.close();
			}
		}
		
	}
						
	 public int  deger(String kelime)
	 {
		 if(dictionary.get(kelime)!=null) 
			 return dictionary.get(kelime);		 
		 else 
			 return 0; 
	 }
	
	 public double total2(String cumle)
		{
		 if(cumle.length() < 2)
				return 0.0;
		 
			int i;
			double total = 0.0;
			String[] kelime = null;
			kelime = cumle.split(" ");
		if(kelime == null)	
			return 0.0;
			
			if(deger(kelime[0]) > 0)
				total +=1;
		    if (deger(kelime[0]) < 0)
				total +=-1;
			for(i = 1 ; i < kelime.length; i++){
				
				if(deger(kelime[i]) > 0)
					total += isNegatif(kelime[i-1]);
				else if (deger(kelime[i]) < 0)
					total += -1*isNegatif(kelime[i-1]);
				
//				System.out.println("Total ="+total);
			}
			
			
			
//			System.out.println("Liu nun  totali = "+total / kelime.length);
			return total / kelime.length;
		}
	
	 public int isNegatif(String kelime)
		{
			int i = 0;
			for(i = 0;i < NEG.length; i++)
				if(kelime.equals(NEG[i]))
					return -1;
			
			
			return 1;
		}
	
	
	
	public static void main(String[] args) throws IOException {
		
		String path1 = "C:/Users/Toprak/Documents/positive.txt";
		String path2 = "C:/Users/Toprak/Documents/negative.txt";
		
		Liu liu = new Liu(path1,path2);
		String cumle = "bad is a movie good therefore!!!";
//		System.out.println(cumle+" = "+liu.total2(cumle));
		String kelime[] = null;
		kelime = cumle.split(" ");
		double total = 0.0;
//		for (int i=0;i<kelime.length;i++)
//		{
//			System.out.println(kelime[i]+" Puanı = "+liu.deger(kelime[i]));
//		}
		
		
		
	}

}
