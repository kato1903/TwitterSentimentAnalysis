import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.backingdata.twitter.crawler.Twitter4jExampleREST;

import Bayes2.MyFilteredClassifier;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class Test7 {
	
	private static Logger logger = Logger.getLogger(Twitter4jExampleREST.class.getName());
	
	public static void main(String [] args) throws IOException {
//				String a = "어이구... (어쩐지 네 행동이 얄밉게 느껴져 눈을 흘기다 갑작스런 입맞춤에 당황한 표정으로 눈을 느리게 깜빡였다. 들큰한 초콜릿의 맛에 순순히 받아 삼키며 혀끝을 가볍게 얽어 문지르듯 핥아올렸다.)";
//				System.out.println(a);
				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true).setJSONStoreEnabled(true);
				TwitterFactory tf = new TwitterFactory(cb.build());
				Twitter twitter = tf.getInstance();
				AccessToken accessToken = new AccessToken("1058760116323958786-ctap8Dcfg2SaWHzdqnp1dabfEon4CB", "F2lPi0zE9VYLQHkzQTM73xITOMLvstO49A6ljVCObF7yf");
				twitter.setOAuthConsumer("auySgA9r3fAheI5g2f0BXL6ig", "irA4nN7JlWHYCpucFLD3D4PGKPjYfYROWliNrC61lThz43igg7");
				twitter.setOAuthAccessToken(accessToken);
								
				LinkedList<String> tweetler = new LinkedList<String>();
				String queryString = "the big bang theory";
				Query query = new Query(queryString);
				query.count(100);
			    QueryResult result = null;
			    int countTw = 1;
			    int MaxTweet = 10000;			  
				try {
					result = twitter.search(query);					
				} catch (TwitterException e) {
					logger.info("Exception while searching for tweets by a query string: " + e.getMessage());
					e.printStackTrace();
				} 
				System.out.println("Query result for " + queryString + ":");
				do {		
				
				for (Status status : result.getTweets()) {
//			        System.out.println(countTw++ + " > @" + status.getUser().getScreenName() + " (" + status.getCreatedAt().toString() + ") : " + status.getText() + "\n");
//			        System.out.println(countTw++ +" "+ status.getText() + "\n ");
			         
//			        if(status.isRetweet() == false)
//			        {	
						
			        	 tweetler.add(status.getText());
//			        	 System.out.println(tweetler.get(countTw-1));
//			        	 System.out.println(countTw++ +" "+ status.getText() + "\n ");
//			        	countTw++;
//			        }	
			    }
					query=result.nextQuery();
					if(query!=null)
					{
						try {
							result=twitter.search(query);
						} catch (TwitterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} while(countTw < MaxTweet && query!=null );
		
//		String a = "うける～ｗｗ　自分の足元みてみなさいねｗｗ　[踏んでる]のよｗｗ";
//		tweetler.add(a);
		String path0 = "C:/Users/Toprak/Documents/SentiWordNet_3.0.0_20130122.txt";
		String path1 = "C:/Users/Toprak/Documents/positive.txt";
		String path2 = "C:/Users/Toprak/Documents/negative.txt";
		Main sentiwordnet = new Main(path0);
		Liu  liu = new Liu(path1,path2);
		Preprocess preprocess = new Preprocess();			
		Tweet tweet = new Tweet(queryString,tweetler,preprocess,liu,sentiwordnet);
//		tweet.yazdirPreTweet();
		tweet.yuzdeHesapla();
	    System.out.println(tweet);
	    System.out.println("\n");
//	    tweet.positifyazdir();
//		System.out.println(tweet.negatifYuzde());
//		System.out.println(tweet.positifYuzde());	    
	    
	    LinkedList<String> prossedtweetler = new LinkedList<String>();
	    
	    prossedtweetler = tweet.getPreTweetler();
	    
	    MyFilteredClassifier classifier;
//		String a = "C:/Users/Toprak/eclipse-workspace/weka-example-master/data/smstest.txt";
		String b = "C:/Users/Toprak/eclipse-workspace/twitter-crawler/data/model3";
		
//			System.out.println("Usage: java MyClassifier <fileData> <fileModel>");
			
			classifier = new MyFilteredClassifier();
//			classifier.load(a);
			classifier.loadModel(b);
			
			for(String str: prossedtweetler)
		      {
				classifier.ayir(str);			
		      }
			
			classifier.bilgi();
	    
	    
			
	}
	
	
	
	
}


