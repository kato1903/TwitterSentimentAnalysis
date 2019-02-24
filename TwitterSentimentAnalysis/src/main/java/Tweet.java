import java.util.LinkedList;
import java.util.List;

public class Tweet {
	
	String konu;
	LinkedList<String> tweetler;
	LinkedList<String> preTweetler;
	LinkedList<String> Positivetweetler;
	Preprocess preprocess;
	Liu liu;
	Main main;
	int durum[];
	
	public String toString()
	{	
		
		System.out.println("Lexicon based Yontemi Sonuclari");
		int toplam = durum[0] + durum[1];
		double oran = (double) durum[0] / (durum[0] + durum[1]) ;
		oran = oran*100;
		
		
		return konu + " Konusunda " + getTweetSayisi() + " adet tweet arasindan "+durum[0]+ " adet positif "+durum[1]+ " adet negatif Tweet "+"Positiflik orani = "+oran;
	}
	
	public double negatifYuzde()
	{
		return 100 * (double) durum[1] / (durum[0] + durum[1] + durum[2]);
	}
	
	public double positifYuzde()
	{
		return 100 * (double) durum[0] / (durum[0] + durum[1] + durum[2]);
	}
	
	public void positifyazdir()
	{
		for(String str: Positivetweetler)
			System.out.println(str);
	}
	
	public double yuzdeHesapla()
	{
		double total = 0.0;
		process();
		int i = 1;
		for(String str: preTweetler)
	      {
		total += main.total(str) + main.total2(str) + liu.total2(str);
  	  	total /= 3;
  	  	
  	  	if(total > 0)
  	  	{
  	  		durum[0]++;  // System.out.println(i+". Tweet Positif");
  	  		Positivetweetler.add(str);
  	  	}
  	  	else if (total < 0)
  	  	{
  	  		durum[1]++;  // System.out.println(i+". Tweet Negatif");
  	  	}
  	  	else
  	  	{
  	  		durum[2]++; // System.out.println(i+". Tweet Notr");
  	  	}
  	  	total = 0;
 	  	i++;
	      }
		double toplamPuan = (double) durum[0] / (durum[0] + durum[1] + durum[2]);
		return 100*toplamPuan;
	}
	
	
	
	public void process()
	{
		for(String str: tweetler)
	      {
//			System.out.print("Girdi ki yazıyo "+str + "\n");
			preTweetler.add(preprocess.Preprocesss(str));
			
	      }
	}
	
	public void yazdirPreTweet ()
	{
		
		for(String str: tweetler)
	      {
			System.out.print(str + "\n");			
	      }
	
	}
	
	public int getTweetSayisi()
	{	
//		System.out.println("Tweetler size = "+tweetler.size());
		return tweetler.size();
	}
	
	public Tweet(String konu, LinkedList<String> tweetler, Preprocess preprocess, Liu liu, Main main) {
		super();
		this.konu = konu;
		this.tweetler = tweetler;
		this.preprocess = preprocess;
		this.liu = liu;
		this.main = main;
		preTweetler = new LinkedList<String>();
		Positivetweetler = new LinkedList<String>();
		durum = new int[3];
		durum[0] = 0;
		durum[1] = 0;
		durum[2] = 0;
	}
	public Preprocess getPreprocess() {
		return preprocess;
	}
	public void setPreprocess(Preprocess preprocess) {
		this.preprocess = preprocess;
	}
	public Liu getLiu() {
		return liu;
	}
	public void setLiu(Liu liu) {
		this.liu = liu;
	}
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	public Tweet(String konu, LinkedList<String> tweetler) {
		super();
		this.konu = konu;
		this.tweetler = tweetler;
	}
	public String getKonu() {
		return konu;
	}
	public void setKonu(String konu) {
		this.konu = konu;
	}
	public LinkedList<String> getTweetler() {
		return tweetler;
	}
	public void setTweetler(LinkedList<String> tweetler) {
		this.tweetler = tweetler;
	}
	public LinkedList<String> getPreTweetler() {
		return preTweetler;
	}
	public void setPreTweetler(LinkedList<String> preTweetler) {
		this.preTweetler = preTweetler;
	}
	

	
}
