import java.io.*;
import java.util.*;

public class homework3 
{
	public static void main(String[] args) throws Exception
	{
	
		int numberOfFiles = 0;
		int totalDocumentLength = 0;
		float averageDocumentLength = 0;
		String text = "";
		String cranfieldDir = args[0];
		String queriesFilePath = args[1];
		File f1 = new File(cranfieldDir);
	
		HashMap<String, IndexStorage> indexStorage = new HashMap<String, IndexStorage>(); //df, tf and list of documents containing the term
		HashMap<String, String> headlineHashMap = new HashMap<String, String>();

		HashMap<String, FrequencyOfMostFrequenctStemAndTotalWordOccurrences> forFrequencyOfMostFrequentStemAndTotalWordOccurrences = new HashMap<String, FrequencyOfMostFrequenctStemAndTotalWordOccurrences>();
		Map<String,Integer> tfInDocument = new HashMap<String,Integer>();
		long startTime = System.currentTimeMillis();
		if(f1.isDirectory()) 
		{
			String[] files = f1.list();
			numberOfFiles = files.length;
		
			for(int j = 0; j<files.length;j++)
			{
				File f = new File(cranfieldDir + "/" + files[j]);
				FileReader fr = new FileReader(f);
				int documentIDInt = j+1;
				String documentID = Integer.toString(documentIDInt);
				BufferedReader br = new BufferedReader(fr);
				
				String s;
				while((s = br.readLine()) !=null) 
				{
					text += "\n" + s;
				}
				fr.close();
				
				String documentTextString = " ";
				tokenInformation ti = new tokenInformation();
				
				headlineHashMap.put(documentID,homework3.getHeadline(text));
				
				String trimmed = text.trim();
				String lowerCaseString = trimmed.toLowerCase(); //Case folding
				String a = lowerCaseString.replaceAll("\\<.*?>",""); //SGML tags removed and words with dashes are considered as two different different words
				String b = a.replaceAll("\n"," ").trim();
				String output = b.replaceAll("\\s+"," "); 
				String[] forRemovingPunctuation = output.split(" ");
				
				int TotalWordOccurrences = forRemovingPunctuation.length;

				stopWordsClass stopWordsObject = new stopWordsClass();
				Stemmer stemmer = new Stemmer();
				ArrayList<String> stopWords = stopWordsObject.stopWordsListMethod();
				StringBuilder wordsAfterRemovingPunctuationAndStopWords = new StringBuilder();
				StringBuilder wordsAfterStemming = new StringBuilder();
				String wordsAfterRemovingPunctuation;
				for(int i = 0; i<forRemovingPunctuation.length;i++) 
				{
					wordsAfterRemovingPunctuation =  ti.removePunctuation(forRemovingPunctuation[i]);
					if(!stopWords.contains(wordsAfterRemovingPunctuation))
					wordsAfterRemovingPunctuationAndStopWords.append(wordsAfterRemovingPunctuation + " ");
				}
		
		
				StringTokenizer forStemming = new StringTokenizer(wordsAfterRemovingPunctuationAndStopWords.toString());
				while(forStemming.hasMoreTokens())
				{
					String token = forStemming.nextToken();
					for(int i=0; i <token.length(); i++)
					{	
						stemmer.add(token.charAt(i));
					}						
					stemmer.stem();
					token = stemmer.toString();
					wordsAfterStemming.append(token + " ");
				}
				documentTextString = wordsAfterStemming.toString();
				StringTokenizer documentTextStringTokenizer = new StringTokenizer(documentTextString.trim());
				while(documentTextStringTokenizer.hasMoreTokens())
				{
					String token = documentTextStringTokenizer.nextToken().trim();

					if(tfInDocument.containsKey(token))
					{
						int frequency = tfInDocument.get(token);
						frequency++;
						tfInDocument.remove(token);
						tfInDocument.put(token,frequency);
					} else 
					{
						tfInDocument.put(token,1);
					}
					
					if(indexStorage.containsKey(token))
					{
						IndexStorage is = indexStorage.get(token);
						if(is.documentIDs.contains(documentID))
						{
							int index =0;
							for(int k = 0;k<is.documentIDs.size();k++) 
							{
								TermFrequency tf = is.termFrequency.get(k);
								if(tf.getDocumentID().equals(documentID))
								{
									index = k;
								}
							}
						
							TermFrequency termFrequency = is.termFrequency.get(index);
							int count = termFrequency.getNoOfTimesTermOccursInDocument();
							
							count++;
							TermFrequency newTF = new TermFrequency(count,documentID);
							is.termFrequency.remove(index);
							is.termFrequency.add(newTF);
						} else 
						{			
							TermFrequency tf = new TermFrequency(1, documentID);
							is.termFrequency.add(tf);
							is.documentIDs.add(documentID);
							is.documentFrequency++;
						}
					} else
					{
						ArrayList<TermFrequency> termFrequencies = new ArrayList<TermFrequency>();
						TermFrequency termFrequency = new TermFrequency(1, documentID);
						termFrequencies.add(termFrequency);
						ArrayList<String> documentIDs = new ArrayList<String>();
						documentIDs.add(documentID);
						
						int documentFrequency = 1;
						indexStorage.put(token, new IndexStorage(termFrequencies,documentFrequency,documentIDs));
					}
				}
		
				ArrayList<Integer> frequenciesInDocument = new ArrayList<Integer>(tfInDocument.values());
				int maxFrequency = Collections.max(frequenciesInDocument);
				FrequencyOfMostFrequenctStemAndTotalWordOccurrences ft = new FrequencyOfMostFrequenctStemAndTotalWordOccurrences(documentID, maxFrequency, TotalWordOccurrences);
				totalDocumentLength = totalDocumentLength + TotalWordOccurrences;
				ft.setDocumentID(documentID);
				forFrequencyOfMostFrequentStemAndTotalWordOccurrences.put(documentID,ft);
				tfInDocument.clear();
				text= "";
			}
		}
		
		
		ArrayList<String> queryList = new ArrayList<String>();
		File queryFile = new File(queriesFilePath);
		FileReader queryFileReader = new FileReader(queryFile);
		BufferedReader queryBufferedReader = new BufferedReader(queryFileReader);
		String string;
		String query = "";
	
		while((string = queryBufferedReader.readLine()) !=null) 
		{
			if(string.length() != 0) 
				{
			if(string.startsWith("Q") && string.endsWith(":"))
			{
				
					if(query.length()>0)
					{
						queryList.add(query);
						query = "" ;
					}
				
			} else
			{
				query = query  + " " + string ;
			}
			}
		}
		if(query.length()>0)
		{
			queryList.add(query);
			query = "";
		}
		queryFileReader.close();
		ArrayList<String> indexedQueryList = new ArrayList<String>();
		
		for(int i = 0; i<queryList.size();i++)
		{
			indexedQueryList.add(homework3.getFinalProcessedString(queryList.get(i)));
		}
		averageDocumentLength = totalDocumentLength/numberOfFiles;
		for(int i=0;i<indexedQueryList.size();i++)
		{
			for (String documentID: forFrequencyOfMostFrequentStemAndTotalWordOccurrences.keySet())
				{
					forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).setW1Score(0.0);
					forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).setW2Score(0.0);
				
				}
			int tf=0;
			int maxtf=0;
			int df=0;
			int doclen=0;
			ArrayList<String> indexedQueryWords = new ArrayList<String>();
			String[] tokens = indexedQueryList.get(i).split(" ");
			for(int j = 0; j<tokens.length;j++) 
			{
				indexedQueryWords.add(tokens[j]);
			}
			
			for(int k = 0; k <indexedQueryWords.size();k++)
			{
				
				String searchToken = indexedQueryWords.get(k).trim();
				IndexStorage isTerm = indexStorage.get(searchToken.trim());
				ArrayList<TermFrequency> termFrequency = isTerm.termFrequency;
				
				for (String documentID: forFrequencyOfMostFrequentStemAndTotalWordOccurrences.keySet())
				{
				
					FrequencyOfMostFrequenctStemAndTotalWordOccurrences ft = forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID);
					maxtf = ft.getFrequencyOfMostFrequentStem();
					doclen = ft.getTotalWordOccurrences();
					if(termFrequency != null)
					{
						df = isTerm.getDocumentFrequency();
						
						for(int m=0;m<termFrequency.size();m++)
						{
							if(termFrequency.get(m).getDocumentID().equals(documentID))
							{
								
								tf = termFrequency.get(m).getNoOfTimesTermOccursInDocument();
							}
							
						}
					}
					
					double W1Score = getW1Score(tf,maxtf,df,numberOfFiles);
					double W2Score = getW2Score(tf,df,doclen,numberOfFiles,averageDocumentLength);
					forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).setW1Score(forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW1Score() +W1Score);
					forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).setDocumentID(documentID);
					forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).setW2Score(forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW2Score() + W2Score);
				} 
			
			}
			
			FrequencyOfMostFrequenctStemAndTotalWordOccurrences [] w1Top10Documents = homework3.getTop10DocumentsUnderW1(forFrequencyOfMostFrequentStemAndTotalWordOccurrences);
			FrequencyOfMostFrequenctStemAndTotalWordOccurrences [] w2Top10Documents = homework3.getTop10DocumentsUnderW2(forFrequencyOfMostFrequentStemAndTotalWordOccurrences);
			System.out.println("Query: " + queryList.get(i));
			System.out.println("Indexed Query: " + indexedQueryList.get(i));
			for(int l = 0; l<10; l++ ) 
			{

				System.out.println("Rank: " + (l+1) + " W1 Score: " + w1Top10Documents[l].getW1Score() + " "+ "Document Name: " + homework3.getCranfieldName(Integer.parseInt(w1Top10Documents[l].getDocumentID())) );
			System.out.println("Rank: " + (l+1) + " W2 Score: " + w2Top10Documents[l].getW2Score() + " "+ "Document Name: " + homework3.getCranfieldName(Integer.parseInt(w2Top10Documents[l].getDocumentID()))  );
			}
			System.out.println(" ");
			w1Top10Documents = null;
			w2Top10Documents = null;

			
		}
		long endTime = System.currentTimeMillis();
		System.out.println("time taken to run the program in ms : " +(endTime - startTime) );

	}
	
	 public static String getCranfieldName(int documentID)
	 {
		 String documentName = "" + documentID;
		 switch(4-documentName.length()){
		 case 0 : documentName = "cranfield"+documentID; 
		 break;
		 case 1 : documentName = "cranfield"+"0"+documentID; 
		 break;
		 case 2 : documentName = "cranfield"+"00"+documentID; 
		 break;
		 case 3 : documentName = "cranfield"+"000"+documentID; 
		 break;
		 default: documentName = "cranfield"+documentID; 
		 break;
		 }
		 return documentName;
	 }
	
	public static String getHeadline(String text) 
	{
		StringTokenizer st = new StringTokenizer(text);
		String headline = "";
		boolean flag = false;
		while(st.hasMoreTokens())
		{
			String token = st.nextToken();
			if(token.equalsIgnoreCase("<TITLE>"))
			{
				flag = true;
			}
			if(flag)
			{
				headline = headline +" " +  token;
			}
			if(token.equalsIgnoreCase("</TITLE>"))
			{
				flag = false;
			}
		
		}
		StringTokenizer hd = new StringTokenizer(headline);
		ArrayList<String> headlineArrayList = new ArrayList<String>();
		while(hd.hasMoreTokens())
		{
			headlineArrayList.add(hd.nextToken());
		}
		headlineArrayList.remove("<TITLE>");
		headlineArrayList.remove("</TITLE>");
		String headlineString = "";
		for(int i = 0;i<headlineArrayList.size();i++)
		{
			headlineString = headlineString +" " + headlineArrayList.get(i);
		}
		return headlineString;
	}
	
	
	
	public static FrequencyOfMostFrequenctStemAndTotalWordOccurrences[] getTop10DocumentsUnderW1(HashMap<String, FrequencyOfMostFrequenctStemAndTotalWordOccurrences> forFrequencyOfMostFrequentStemAndTotalWordOccurrences ){
		FrequencyOfMostFrequenctStemAndTotalWordOccurrences[] t = new FrequencyOfMostFrequenctStemAndTotalWordOccurrences[10];
		int filled=0;
		for(String documentID: forFrequencyOfMostFrequentStemAndTotalWordOccurrences.keySet()){
		
				if(filled>0){
					int index = checkW1Score(0,filled-1,forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW1Score(),t);
					
					for(int i = filled-1;i	> index;i-- ){
						t[i] = t[i-1];
						
					}
					
					t[index] = new FrequencyOfMostFrequenctStemAndTotalWordOccurrences(documentID, forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW1Score(),forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW2Score());
					
					
				}else{
					
					t[0] = new FrequencyOfMostFrequenctStemAndTotalWordOccurrences(documentID, forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW1Score(),forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW2Score());
				}		
			if(filled<10)
				filled++;
		}
		for(int i =0;i<t.length;i++)
		{
			//System.out.println(t[i].getDocumentID() +" " + t[i].getW1Score());
		}
		return t;
	}
	
		public static FrequencyOfMostFrequenctStemAndTotalWordOccurrences[] getTop10DocumentsUnderW2(HashMap<String, FrequencyOfMostFrequenctStemAndTotalWordOccurrences> forFrequencyOfMostFrequentStemAndTotalWordOccurrences ){
		FrequencyOfMostFrequenctStemAndTotalWordOccurrences[] t = new FrequencyOfMostFrequenctStemAndTotalWordOccurrences[10];
		int filled=0;
		for(String documentID: forFrequencyOfMostFrequentStemAndTotalWordOccurrences.keySet()){
				if(filled>0){
					int index = checkW2Score(0,filled-1,forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW2Score(),t);
					
					for(int i = filled-1;i	> index;i-- ){
						t[i] = t[i-1];
					}
					t[index] = new FrequencyOfMostFrequenctStemAndTotalWordOccurrences(documentID, forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW1Score(),forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW2Score());
				}else{
					t[0] = new FrequencyOfMostFrequenctStemAndTotalWordOccurrences(documentID, forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW1Score(),forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID).getW2Score());
				}		
			if(filled<10)
				filled++;
		}
		return t;
	}
	
	public static int checkW1Score(int first, int last,double w1Score,FrequencyOfMostFrequenctStemAndTotalWordOccurrences[] top)
	{	
		int returnValue=-1;
		if(last==0){
			return 0;
		}
		int middle = first + (last-first)/2;
	    FrequencyOfMostFrequenctStemAndTotalWordOccurrences f =  top[middle];
		if (last==first ){
			return middle;
		}else if(f.getW1Score() == w1Score){
		    return middle;
		}else if(f.getW1Score() < w1Score){
			return checkW1Score(first,middle,w1Score,top);
		}else if(f.getW1Score() > w1Score){
			return checkW1Score(middle+1,last,w1Score,top);
		}
		return returnValue;
	}
	
	public static int checkW2Score(int first, int last,double w2Score,FrequencyOfMostFrequenctStemAndTotalWordOccurrences[] top)
	{	
		int returnValue=-1;
		if(last==0){
			return 0;
		}
		int middle = first + (last-first)/2;
	    FrequencyOfMostFrequenctStemAndTotalWordOccurrences f =  top[middle];
		if (last==first ){
			return middle;
		}else if(f.getW2Score() == w2Score){
		    return middle;
		}else if(f.getW2Score() < w2Score){
			return checkW2Score(first,middle,w2Score,top);
		}else if(f.getW2Score() > w2Score){
			return checkW2Score(middle+1,last,w2Score,top);
		}
		return returnValue;
	}
	
	public static double getW1Score(int  tf,int maxtf,int df, int collectionSize)
	{ 
		if(df==0) 
		{
			 return 0;
		} else 
		{
			double w1Score=  ( 0.4 + 0.6*(Math.log(tf+0.5)/Math.log(maxtf+1)) ) * ( Math.log(collectionSize/df) / Math.log(collectionSize));
			return w1Score;
		}
	}
	
	public static double getW2Score(int tf, int df,int docLen, int collectionSize, float averageDocumentLength)
	{	 
		if(df==0) 
		{
			return 0;
		} else 
		{
			double w2Score=  ( 0.4 + 0.6*(tf/(tf+0.5+1.5*(docLen/averageDocumentLength))) ) * ( Math.log(collectionSize/df) / Math.log(collectionSize));
			return w2Score;
		}
	}
	

	
	public static String getFinalProcessedString(String text) 
	{
		tokenInformation ti = new tokenInformation();
		String trimmed = text.trim();
		String lowerCaseString = trimmed.toLowerCase(); //Case folding
		String a = lowerCaseString.replaceAll("\\<.*?>"," "); //SGML tags removed and words with dashes are considered as two different different words
		String c = a.replaceAll("-"," ");
		String b = c.replaceAll("\n"," ");
		String[] forRemovingPunctuation = b.split(" ");
		stopWordsClass stopWordsObject = new stopWordsClass();
		Stemmer stemmer = new Stemmer();
		ArrayList<String> stopWords = stopWordsObject.stopWordsListMethod();
		StringBuilder wordsAfterRemovingPunctuationAndStopWords = new StringBuilder();
		StringBuilder wordsAfterStemming = new StringBuilder();
		for(int i = 0; i<forRemovingPunctuation.length;i++) 
		{
			String wordAfterRemovingPunctuation =  ti.removePunctuation(forRemovingPunctuation[i]);
			if(!stopWords.contains(wordAfterRemovingPunctuation))
			wordsAfterRemovingPunctuationAndStopWords.append(wordAfterRemovingPunctuation + " ");
		}
		
		StringTokenizer forStemming = new StringTokenizer(wordsAfterRemovingPunctuationAndStopWords.toString());
		while(forStemming.hasMoreTokens())
		{
			String token = forStemming.nextToken();
			for(int i=0; i <token.length(); i++)
			{	
				stemmer.add(token.charAt(i));
			}						
			stemmer.stem();
			token = stemmer.toString();
			wordsAfterStemming.append(token + " ");
		}
		String documentTextString = wordsAfterStemming.toString();
		return documentTextString;

	}
	
}

class tokenInformation {

public int numberOfFiles = 0;

	public String readFilesToString(String cranfieldDir) throws Exception 
	{	
		String text = "";
		File f1 = new File(cranfieldDir);
		if(f1.isDirectory()) 
		{
			String[] files = f1.list();
			numberOfFiles = files.length;
			for(int i = 0; i<files.length;i++)
			{
				File f = new File(cranfieldDir + "/" + files[i]);
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String s;
				while((s = br.readLine()) !=null) 
				{
					text += "\n" + s;
				}
				fr.close();
			}
		}
		return text.substring(1);
	}
	
	
	public String removePunctuation(String word)
	{
		if(word.contains("\'"))
		{
			int aposIndex = word.indexOf('\'');
			String afterAposWord = word.substring(aposIndex+1,word.length());
			
			if(afterAposWord.length() == 1)
			{
				if(afterAposWord.equals("d") || afterAposWord.equals("s") || afterAposWord.equals("m")) // handling we'd, I'm, sheriff's
				{
					word = word.substring(0,aposIndex-1);
				} 
				else if (afterAposWord.equals("t"))
				{
					if(word.charAt(aposIndex-1) == 'n') 
					{
					 word = word.substring(0,aposIndex-1)+"t";
					}
				}
			} 
			else if(afterAposWord.length() == 2) 
			{
				if(afterAposWord.equals("re") || afterAposWord.equals("ve") || afterAposWord.equals("ll")) 
				{
					word = word.substring(0,aposIndex-1);
				}
			}
		}
		
		int lastCharIndex = word.length();
		String withoutPunctuation = "";
		for(int i = 0;i<lastCharIndex;i++)
		{
			
			if(Character.isLetterOrDigit(word.charAt(i)))
			{
				withoutPunctuation += word.charAt(i);
			}
		}
		return withoutPunctuation;
	}
	
}

class FrequencyOfMostFrequenctStemAndTotalWordOccurrences 
{
	String documentID;
	int frequencyOfMostFrequentStem;
	int totalWordOccurrences;
	double W1Score;
	double W2Score;
	
	FrequencyOfMostFrequenctStemAndTotalWordOccurrences(String documentID, double W1Score,double W2Score)
	{	
		this.documentID = documentID;
		this.W1Score=W1Score;
		this.W2Score = W2Score;
	}
	
	FrequencyOfMostFrequenctStemAndTotalWordOccurrences(String documentID,int frequencyOfMostFrequentStem,int totalWordOccurrences)
	{	
		this.documentID = documentID;
		this.frequencyOfMostFrequentStem = frequencyOfMostFrequentStem;
		this.totalWordOccurrences=totalWordOccurrences;
	}


	
	public String getDocumentID() 
	{
		return documentID;
	
	}

	public int getTotalWordOccurrences()
	{
		return totalWordOccurrences;
	}
	
	public int getFrequencyOfMostFrequentStem()
	{
		return frequencyOfMostFrequentStem;
	}
	public double getW1Score()
	{
		return W1Score;
	}

	public void setW1Score(double w1Score)
	{
		W1Score = w1Score;
	}
	public void setDocumentID(String DocumentID)
	{
		documentID = DocumentID;
	}
	
	public double getW2Score() 
	{
		return W2Score;
	}

	public void setW2Score(double w2Score)
	{
		W2Score = w2Score;
	}
}

class TermFrequency 
{
	int noOfTimesTermOccursInDocument;
	String documentID;
	
	TermFrequency(int noOfTimesTermOccursInDocument, String documentID)
	{
		this.noOfTimesTermOccursInDocument = noOfTimesTermOccursInDocument;
		this.documentID = documentID;
	}	
	
	public String getDocumentID() 
	{
		return documentID;
	}
	
	public int getNoOfTimesTermOccursInDocument()
	{
		return noOfTimesTermOccursInDocument;
	}

}

class IndexStorage 
{
	ArrayList<TermFrequency> termFrequency;
	int documentFrequency;
	ArrayList<String> documentIDs;
	
	IndexStorage(ArrayList<TermFrequency> termFrequency,int documentFrequency, ArrayList<String> documentIDs)
	{
		this.termFrequency = termFrequency;
		this.documentFrequency = documentFrequency;
		this.documentIDs = documentIDs;
		
	}
	
	public ArrayList<String> getDocumentIDs() 
	{
		return documentIDs;
	}
	
	public int getDocumentFrequency() 
	{
		return documentFrequency;
	}
}

