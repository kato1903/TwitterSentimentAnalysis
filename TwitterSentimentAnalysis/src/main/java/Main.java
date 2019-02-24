//    Copyright 2013 Petter T�rnberg
//
//    This demo code has been kindly provided by Petter T�rnberg <pettert@chalmers.se>
//    for the SentiWordNet website.
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import twitter4j.*;

public class Main {
	
	public static String[] NEG = {"not",
			"dont", "cant", "shouldnt", "couldnt", "havent", "hasnt", "hadnt", "wont",
			"doesnt", "didnt", "isn", "arent", "aint", "never", "no", "noone", "none", "nowhere",
			"nothing", "neither", "nor", "no sense of", "lack", "lacks", "lacked", "lacking",
			"far from", "away from", "have yet to", "has yet to", "had yet to"};
	
	private Map<String, Double> dictionary;

	public Main(String pathToSWN) throws IOException {
		// This is our main dictionary representation
		dictionary = new HashMap<String, Double>();
		
		// From String to list of doubles.
		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

		BufferedReader csv = null;
		try {
			csv = new BufferedReader(new FileReader(pathToSWN));
			int lineNumber = 0;

			String line;
			while ((line = csv.readLine()) != null) {
				lineNumber++;

				// If it's a comment, skip this line.
				if (!line.trim().startsWith("#")) {
					// We use tab separation
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];

					// Example line:
					// POS ID PosS NegS SynsetTerm#sensenumber Desc
					// a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
					// ascetic#2 practicing great self-denial;...etc

					// Is it a valid line? Otherwise, through exception.
					if (data.length != 6) {
						throw new IllegalArgumentException(
								"Incorrect tabulation format in file, line: "
										+ lineNumber);
					}

					// Calculate synset score as score = PosS - NegS
					Double synsetScore = Double.parseDouble(data[2])
							- Double.parseDouble(data[3]);

					// Get all Synset terms
					String[] synTermsSplit = data[4].split(" ");

					// Go through all terms of current synset.
					for (String synTermSplit : synTermsSplit) {
						// Get synterm and synterm rank
						String[] synTermAndRank = synTermSplit.split("#");
						String synTerm = synTermAndRank[0] + "#"
								+ wordTypeMarker;

						int synTermRank = Integer.parseInt(synTermAndRank[1]);
						// What we get here is a map of the type:
						// term -> {score of synset#1, score of synset#2...}

						// Add map to term if it doesn't have one
						if (!tempDictionary.containsKey(synTerm)) {
							tempDictionary.put(synTerm,
									new HashMap<Integer, Double>());
						}

						// Add synset link to synterm
						tempDictionary.get(synTerm).put(synTermRank,
								synsetScore);
					}
				}
			}

			// Go through all the terms.
			for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary
					.entrySet()) {
				String word = entry.getKey();
				Map<Integer, Double> synSetScoreMap = entry.getValue();

				// Calculate weighted average. Weigh the synsets according to
				// their rank.
				// Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
				// Sum = 1/1 + 1/2 + 1/3 ...
				double score = 0.0;
				double sum = 0.0;
				for (Map.Entry<Integer, Double> setScore : synSetScoreMap
						.entrySet()) {
					score += setScore.getValue() / (double) setScore.getKey();
					sum += 1.0 / (double) setScore.getKey();
				}
				score /= sum;

				dictionary.put(word, score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (csv != null) {
				csv.close();
			}
		}
	}

	public double extract(String word, String pos) {
		if(dictionary.get(word + "#" + pos)!=null) return dictionary.get(word + "#" + pos);
		else return 0.0;
	}
	
	public double extract(String token) {
		if(dictionary.get(token)!=null) return dictionary.get(token);
		else return 0.0;
	}
	
	public double total(String cumle)
	{	
		
		if(cumle.length() < 2)
			return 0.0;
		
//		System.out.println(". Cumlesi icin girdi "+(cumle));
		
		int i;
		double total = 0.0;
		String[] kelime = new String[50];
		cumle.replaceAll("[^\\x20-\\x7e]", "");
		kelime = cumle.split(" ");
		
		if (kelime == null)
			return total;
		
		total += extract(kelime[0],kelimeSec(kelime[0]));
		for(i = 1 ; i < kelime.length; i++){
//			System.out.println(kelime[i]+" "+extract(kelime[i],"a"));
					total += isNegatif(kelime[i-1])*extract(kelime[i],kelimeSec(kelime[i]));			
		}
//		total /= kelime.length;
//		System.out.println("Sentiment totali = "+total);
		return total;		
	}
	
	public String kelimeSec(String a)
	{	
		String b = "a";
		if(extract(a,"a")!=0)
		{
			b = "a";
			return b;
		}
		if(extract(a,"n")!=0)
		{
			b = "n";
			return b;
		}		
		if(extract(a,"f")!=0)
		{
			b = "n";
			return b;
		}
		return a;
		
		
	}
	
	public int isNegatif(String kelime)
	{
		int i = 0;
		for(i = 0;i < NEG.length; i++)
			if(kelime.equals(NEG[i]))
				return -1;
		
		
		return 1;
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
		
//		for(i=0;i<kelime.length;i++)
//			System.out.println(kelime[i]);
		
		if(extract(kelime[0],kelimeSec(kelime[0])) > 0)
			total +=1;
		if (extract(kelime[0],kelimeSec(kelime[0])) < 0)
			total +=-1;

//		System.out.println("Sentiment Totali baslangic  = "+total);
//		System.out.println("İlk kelimeden sonraki total = "+total);
		for(i = 1 ; i < kelime.length; i++){
//			System.out.println(kelime[i]+" için girdi."+"extract ı = "+extract(kelime[i],kelimeSec(kelime[i])));
			if(extract(kelime[i],kelimeSec(kelime[i])) > 0)
				total += isNegatif(kelime[i-1]);
		    if (extract(kelime[i],kelimeSec(kelime[i])) < 0)
				total += -1*isNegatif(kelime[i-1]);
			
			
//			System.out.println("Sentiment Totali 2  = "+total);
		}
		
//		System.out.println("Sentiment Totali 2 = "+total / kelime.length);
		return total / kelime.length;
	}
	
	
	public static void main(String [] args) throws IOException {
		
		
		
		
		
		String pathToSWN = "C:/Users/Toprak/Desktop/SentiWordNet_3.0.0_20130122.txt";
		Main sentiwordnet = new Main(pathToSWN);
/*		String a = "The venom in this man is hard to believe";
		double b =sentiwordnet.total(a);
		double c =sentiwordnet.total2(a);
		System.out.println(b+" "+c);
		System.out.println(sentiwordnet.extract("The",sentiwordnet.kelimeSec("The")));
		System.out.println("Venom puanı"+sentiwordnet.extract("venom",sentiwordnet.kelimeSec("venom")));
		System.out.println(sentiwordnet.extract("in",sentiwordnet.kelimeSec("in")));
		System.out.println(sentiwordnet.extract("this",sentiwordnet.kelimeSec("this")));
		System.out.println(sentiwordnet.extract("man",sentiwordnet.kelimeSec("man")));
		System.out.println(sentiwordnet.extract("is",sentiwordnet.kelimeSec("is")));
		System.out.println(sentiwordnet.extract("hard",sentiwordnet.kelimeSec("hard")));
		System.out.println(sentiwordnet.extract("to",sentiwordnet.kelimeSec("to")));
		System.out.println(sentiwordnet.extract("believe",sentiwordnet.kelimeSec("believe"))); */
//		System.out.println("good#a "+sentiwordnet.extract("good",sentiwordnet.kelimeSec("good")));
//		System.out.println("bad#a "+sentiwordnet.extract("good", "n"));
//		System.out.println("blue#a "+sentiwordnet.extract("blue", "a"));
//		System.out.println("blue#n "+sentiwordnet.extract("blue", "n"));
		
/*		String cumle = "The story is not that complicated .\r\n" + 
				"\r\n" + 
				"But the execution is very good .";
			String[] kelime = null;
			kelime = cumle.split(" ");
			int i;
			double total = 0.0;
			for(i = 0 ; i < kelime.length; i++){
				System.out.println(kelime[i]+" "+sentiwordnet.extract(kelime[i],"a"));
				total += sentiwordnet.extract(kelime[i],"a");
			}
			System.out.println("Total = "+sentiwordnet.isNegatif("dont")); */
			
			
	}
}