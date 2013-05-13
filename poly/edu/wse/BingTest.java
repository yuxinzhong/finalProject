package poly.edu.wse;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.*;

public class BingTest {
	public static Map<String,response> responses=new HashMap<String,response>();
	public static void main(String[] args) throws IOException {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ServerSocket serverSocket = new ServerSocket(9998);
					Pattern regex=Pattern.compile("[a-zA-Z0-9]+");
					while(true){
						Socket socket=serverSocket.accept();
						try {
							String word=(String) new ObjectInputStream(socket.getInputStream()).readObject();
							word=word.toLowerCase();
							System.out.println(word);
							Matcher regexMatcher=regex.matcher(word);
							List<String> keywords=new LinkedList<String>();
							while (regexMatcher.find()) {
								keywords.add(regexMatcher.group());
							}
							String uuid=UUID.randomUUID().toString();
							String string=SearchKeyWords(keywords,uuid).d.toString();
							new ObjectOutputStream(socket.getOutputStream()).writeObject(uuid+"~~~>"+string);
							//new ObjectOutputStream(socket.getOutputStream()).writeObject(doc.content);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							new ObjectOutputStream(socket.getOutputStream()).writeObject("sorry!no result for this query.");

						}

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ServerSocket serverSocket = new ServerSocket(9997);
					Pattern regex=Pattern.compile("[a-zA-Z0-9]+");
					while(true){
						Socket socket=serverSocket.accept();
						try {
							Relevant relevant=(Relevant) new ObjectInputStream(socket.getInputStream()).readObject();
							new ObjectOutputStream(socket.getOutputStream()).writeObject(getNextQuery(relevant));
							//new ObjectOutputStream(socket.getOutputStream()).writeObject(doc.content);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							new ObjectOutputStream(socket.getOutputStream()).writeObject("sorry!no result for this query.");

						}

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
		
		
		
//
//		System.out.println("please input keyword(s) for query:");
//		Scanner input=new Scanner(System.in);
//		String inputstring=input.nextLine();

//		System.out.println("please input precision@10 you want (between 0~1,e.g. 0.9) :");
//		float precise=input.nextFloat();
//		System.out.println("searching......");
//		boolean flag=true;
		
//		while(flag){
//			response response=SearchKeyWords(KeyWords);
//			System.out.println(response.d);
//			String tmp2="the keyword(s) in this search is(are): ";
//			Iterator<String> iterator2 = KeyWords.iterator();
		
//			while(iterator2.hasNext()){
//				String word=iterator2.next();
//				tmp2=tmp2+word+" ";
//			}
//			System.out.println(tmp2+"\n");
//			boolean[] relevant={false,false,false,false,false,false,false,false,false,false};

//			System.out.println("please mark the relevant result(from 1 to 10,like 1 2 4 9..) :");
//			input=new Scanner(System.in);
//			String newLine=input.nextLine();
//			input=new Scanner(newLine);
//			while(input.hasNextInt()){
//				int relevantresultnumber=input.nextInt()-1;
//				relevant[relevantresultnumber]=true;
//			}
//			
//			System.out.println("searching again......");
//			int trues=0;
//			for(int i=0;i<10;i++) {if (relevant[i]) trues++;}
//			if((float)trues/10>=precise) {flag=false;System.out.println("reach the precision@10!finished!");return;}
			//-------------
			
//			HashMap<String,Counter> map=new HashMap<String,Counter>();
//			Pattern regex = Pattern.compile("[a-zA-Z0-9]+");
	
//			try {
//				for(int i=0;i<10;i++){
//					Matcher regexMatcher = regex.matcher(response.d.results[i].Description+response.d.results[i].Title);
//					while (regexMatcher.find()) {
//						String word=regexMatcher.group();
//						word=word.toLowerCase();
//						if(!ignoredWordsDict.containsKey(word)){
//							if(map.containsKey(word)){
//								Counter counter=map.get(word);
//								counter.times[i]++;
//							}
//							else{
//								Counter counter=new Counter();
//								counter.times[i]++;
//								map.put(word, counter);
//							}
//						}
//					} 
//				}
//			} catch (PatternSyntaxException ex) {
//				// Syntax error in the regular expression
//			}
			
			
//			Iterator<String> iterator = map.keySet().iterator();
//			while(iterator.hasNext()){
//				String word=iterator.next();
//				getScoreToCounter(map.get(word),relevant);		
//				//System.out.println(word+":"+map.get(word).score);
//			}		
//			iterator = KeyWords.iterator();		
//			while(iterator.hasNext()){
//				String word=iterator.next();
//				map.get(word).score++;
//			}
			//++scoring finished above!++++++
//			KeyWords = new ArrayList<String>();
//			int needextra=1;
//			int needprevkeyword=keywordnumber;
//			System.out.println("there are new keyword(s) for query again:");
//			while(!(needextra==0&&needprevkeyword==0)){
//				String a=getMaxScoreWord(map);
//				if (KeyWordsMap.containsKey(a)){
//					needprevkeyword--;
//					KeyWords.add(a);
//					System.out.print(a+" ");
//				}
//				else{
//					if(needextra>0){
//						KeyWordsMap.put(a, null);
//						KeyWords.add(a);
//						needextra--;
//						System.out.print(a+" ");
//					}
//				}
//				map.remove(a);
//			}
//			System.out.println();
//			keywordnumber++;
//		}
	}

	/**
	 * @param uuid  
	* @Title: SearchKeyWords 
	* @Description: TODO
	* @param @param keyWords
	* @param @return
	* @param @throws IOException
	* @return response
	* @throws 
	*/
	private static response SearchKeyWords(List<String> keyWords, String uuid) throws IOException {
		String tmp="";
		Iterator<String> iterator = keyWords.iterator();
	
		while(iterator.hasNext()){
			String word=iterator.next();
			tmp=tmp+word+"+";
		}
		
		tmp=tmp.substring(0, tmp.length()-1);//
		// TODO Auto-generated method stub
		String bingUrl = "https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Web?Query=%27"+tmp+"%27&$top=10&$format=Json";
		System.out.println(bingUrl);
		//Provide your account key here. 
		String accountKey = "R5CIq9zmtnDwQ26dyeCOfF+2IwFvkFooTw16GiwBmS4=";
		
		byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);

		URL url = new URL(bingUrl);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
				
		InputStream inputStream = (InputStream) urlConnection.getContent();		
		byte[] contentRaw = new byte[urlConnection.getContentLength()];
		inputStream.read(contentRaw);
		String content = new String(contentRaw);
		
		Gson gson = new Gson(); 
		response response=gson.fromJson(content, response.class);
		BingTest.responses.put(uuid, response);
		return response;
	}

	/** 
	* @Title: getMaxScoreWord 
	* @Description: TODO
	* @param @param map
	* @param @return
	* @return String
	* @throws 
	*/
	private static String getMaxScoreWord(HashMap<String, Counter> map) {
		// TODO Auto-generated method stub
		Iterator<String> iterator = map.keySet().iterator();
		float maxscore=0;
		String maxscoreword="";
		while(iterator.hasNext()){
			String word=iterator.next();
			if(map.get(word).score>maxscore){
				maxscore=map.get(word).score;
				maxscoreword=word;
			}
		}
		return maxscoreword;
	}

	/** 
	* @Title: getScoreToCounter 
	* @Description: TODO
	* @param @param counter
	* @param @param relevant
	* @return void
	* @throws 
	*/
	private static void getScoreToCounter(Counter counter,
			boolean[] relevant) {
		// TODO Auto-generated method stub
		int Dr=0;
		int Dnr=0;
		float totalr=0;
		float totalnr=0;
		for(int i=0;i<10;i++){
			if(relevant[i]==true){
				Dr=Dr+1;
				totalr=totalr+counter.times[i];
			}
			else{
				Dnr=Dnr+1;
				totalnr=totalnr+counter.times[i];
			}
		}
		counter.score=(float) (0.75*totalr/Dr-0.15*totalnr/Dnr);
	}
	
	public static String getNextQuery(Relevant r){
		String[] ignoredWordsList={"he","she","is","am","are","was","were",
				   "and","or","this","that","the","in","at",
				   "a","an","to","of"};
		int howmany=18;//how many of ignored words above
		HashMap<String,Object> ignoredWordsDict=new HashMap<String,Object>();
		for(int i=0;i<howmany;i++) ignoredWordsDict.put(ignoredWordsList[i], null);
		ArrayList<String> KeyWords=new ArrayList<String>();
		HashMap<String,Object> KeyWordsMap=new HashMap<String,Object>();
		
		String keywords[]=r.query.split(" ");
		int keywordnumber=keywords.length;
		for(int i=0;i<keywords.length;i++){
			KeyWords.add(keywords[i]);
			KeyWordsMap.put(keywords[i], null);	
			//keywordnumber++;
		}
		boolean[] relevant=r.relevant;
		HashMap<String,Counter> map=new HashMap<String,Counter>();
		Pattern regex = Pattern.compile("[a-zA-Z0-9]+");
		
		try {
			for(int i=0;i<10;i++){
				response response=responses.get(r.responseUUID);
				//String plainText=getPlainText(response.d.results[i].Url);
				Matcher regexMatcher=null;
				//if (plainText!=null) 
				//	regexMatcher = regex.matcher(plainText);
				//else
				String plainTextString=response.d.results[i].Description+"|"+response.d.results[i].Title+"|"+response.d.results[i].DisplayUrl;
				regexMatcher = regex.matcher(plainTextString);
				List<String> wordsList=new LinkedList<String>();
				while (regexMatcher.find()) {
					String word=regexMatcher.group();
					word=word.toLowerCase();
					wordsList.add(word);
				} 
				
				for (int index=0;index<wordsList.size();index++) {
					String word=wordsList.get(index);
					if(!ignoredWordsDict.containsKey(word)){
						if(map.containsKey(word)){
							Counter counter=map.get(word);
							int lefttwo=index-2;
							int leftone=index-1;
							int rightone=index+1;
							int righttwo=index+2;
							int addScore=1;
							if(lefttwo>0&&KeyWords.contains(wordsList.get(lefttwo))){
								addScore=2;
							}
							if(leftone>0&&KeyWords.contains(wordsList.get(leftone))){
								addScore=2;
							}
							if(rightone<wordsList.size()&&KeyWords.contains(wordsList.get(rightone))){
								addScore=2;
							}
							if(righttwo<wordsList.size()&&KeyWords.contains(wordsList.get(righttwo))){
								addScore=2;
							}
							counter.times[i]=counter.times[i]+addScore;
						}
						else{
							Counter counter=new Counter();
							int lefttwo=index-2;
							int leftone=index-1;
							int rightone=index+1;
							int righttwo=index+2;
							int addScore=1;
							if(lefttwo>0&&KeyWords.contains(wordsList.get(lefttwo))){
								addScore++;
							}
							if(leftone>0&&KeyWords.contains(wordsList.get(leftone))){
								addScore++;
							}
							if(rightone<wordsList.size()&&KeyWords.contains(wordsList.get(rightone))){
								addScore++;
							}
							if(righttwo<wordsList.size()&&KeyWords.contains(wordsList.get(righttwo))){
								addScore++;
							}
							counter.times[i]=counter.times[i]+addScore;
							map.put(word, counter);
						}
					}
				}
			}
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String word=iterator.next();
			getScoreToCounter(map.get(word),relevant);		
		}		
		iterator = KeyWords.iterator();		
		while(iterator.hasNext()){
			String word=iterator.next();
			map.get(word).score=map.get(word).score+10;
		}
		KeyWords = new ArrayList<String>();

		String newQueryString="";
		for(int i=0;i<(keywordnumber+1);i++){
			String a=getMaxScoreWord(map);
			newQueryString+=(a+" ");
			map.remove(a);
		}
		return newQueryString;
	}

	private static String getPlainText(String url) {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		try {
			Document doc = Jsoup.connect(url).get();
	        StringBuilder builder = new StringBuilder();
	        try {
	        	/*String content=doc.toString();
	    		poly.edu.wse.Parser.parsePage(url,content, builder);
	    		Scanner scanner=new Scanner(builder.toString());
	    		while(scanner.hasNextLine()){
	    			sb.append(scanner.nextLine().split(" ")[0]).append("\n");
	    		}
	    		return sb.toString();	*/
	        	return (doc.title()+doc.body().text());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
}

