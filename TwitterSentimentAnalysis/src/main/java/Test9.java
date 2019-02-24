
public class Test9 {

	public static void main(String[] args) {
		String a = "うける～ｗｗ　自分の足元みてみなさいねｗｗ　[踏んでる]のよｗｗ";
		total(a);
	}
	
	public static double total(String cumle)
	{	
		if(cumle==null)
			return 0.0;
		int i;
		double total = 0.0;
		String[] kelime = new String[50];
		kelime = cumle.split(" ");
		if (kelime[0] == null)
			return total;
		total += extract(kelime[0],"a");
		for(i = 1 ; i < kelime.length; i++){
//			System.out.println(kelime[i]+" "+extract(kelime[i],"a"));
//					total += isNegatif(kelime[i-1])*extract(kelime[i],"a");			
		}
		total /= kelime.length;
//		System.out.println("Sentiment totali = "+total);
		return total;		
	}
	
	public static double extract(String word, String pos) {
		return 0.0;
	}
	
}
