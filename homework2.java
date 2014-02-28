import java.io.*;
import java.util.*;

public class homework2 
{
	public static void main(String[] args) throws Exception
	{
	int numberOfFiles = 0;
	String text = "";
	public static String dir = args[0];
	File f1 = new File(dir);
	
	HashMap<String, IndexStorage> indexStorage = new HashMap<String, IndexStorage>(); //df, tf and list of documents containing the term

	HashMap<String, FrequencyOfMostFrequenctStemAndTotalWordOccurrences> forFrequencyOfMostFrequentStemAndTotalWordOccurrences = new HashMap<String, FrequencyOfMostFrequenctStemAndTotalWordOccurrences>();
	Map<String,Integer> tfInDocument = new HashMap<String,Integer>();
	long startTime = System.currentTimeMillis();
	if(f1.isDirectory()) 
	{
		String[] files = f1.list();
		numberOfFiles = files.length;
		
		for(int j = 0; j<files.length;j++)
		{
			File f = new File(dir + "/" + files[j]);
			FileReader fr = new FileReader(f);
			int documentIDInt = j+1;
			forCompressedIndex fci = new forCompressedIndex();
			String documentIDdelta = fci.getDeltaCode(documentIDInt);
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
		
			String trimmed = text.trim();
			String lowerCaseString = trimmed.toLowerCase(); //Case folding
			String a = lowerCaseString.replaceAll("\\<.*?>"," "); //SGML tags removed and words with dashes are considered as two different different words
			String b = a.replaceAll("\n"," ");
			String[] forRemovingPunctuation = b.split(" ");
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
						String gammaCode = fci.getGammaCode(count);
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
			FrequencyOfMostFrequenctStemAndTotalWordOccurrences ft = new FrequencyOfMostFrequenctStemAndTotalWordOccurrences();
			ft.frequencyOfMostFrequentStem = maxFrequency;
			ft.totalWordOccurrences = TotalWordOccurrences;
			forFrequencyOfMostFrequentStemAndTotalWordOccurrences.put(documentID,ft);
			tfInDocument.clear();
		
		
			text= "";
		}
	}
	long endTime = System.currentTimeMillis();
	
	System.out.println("Time taken to build the index is " + (endTime - startTime) + "ms");
	System.out.println("The number of inverted lists in the index: " + indexStorage.size());
	
	HashMap<String, CompressedIndexStorage> compressedIndexStorageMap = new HashMap<String,CompressedIndexStorage>();
	
	for (String key: indexStorage.keySet()) 
	{
		IndexStorage is = indexStorage.get(key);
		ArrayList<CompressedTermFrequency> compressedTermFrequencyList = new ArrayList<CompressedTermFrequency>();
		
		for(int i = 0;i<is.termFrequency.size();i++)
		{
			CompressedTermFrequency ctf = homework2.toCompressTermFrequency(is.termFrequency.get(i));
			compressedTermFrequencyList.add(ctf);
		}
		CompressedIndexStorage compressedIndexStorage = new CompressedIndexStorage( compressedTermFrequencyList,is.getDocumentFrequency(),is.getDocumentIDs());
		compressedIndexStorageMap.put(key,compressedIndexStorage);
	}	
	String[] terms = {"shock"};
	
	storeIndex(indexStorage,forFrequencyOfMostFrequentStemAndTotalWordOccurrences);
	storeCompressedIndex(compressedIndexStorageMap);
	
	for (String documentID: forFrequencyOfMostFrequentStemAndTotalWordOccurrences.keySet())
	{

            String key =documentID.toString();
            FrequencyOfMostFrequenctStemAndTotalWordOccurrences ft = forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID);  
            //System.out.println(key + " " + ft.frequencyOfMostFrequentStem + " " + ft.totalWordOccurrences);  
	} 
	
	for(int p=0;p<terms.length;p++)
	{
	 	String processedString = homework2.getFinalProcessedWord(terms[p].trim());
		IndexStorage isTerm = indexStorage.get(processedString.trim());
		System.out.println("term: " + terms[p]);
		System.out.println("Document Frequency: " + isTerm.documentFrequency);
		
		for(int t=0;t<isTerm.termFrequency.size();t++)
		{
			TermFrequency tf = isTerm.termFrequency.get(t);
			System.out.print(tf.getDocumentID() + " -->" + tf.getNoOfTimesTermOccursInDocument() + "  ");
		}
	}
	 
}
	
	public static String getFinalProcessedWord(String text) 
	{
		tokenInformation ti = new tokenInformation();
		String trimmed = text.trim();
		String lowerCaseString = trimmed.toLowerCase(); //Case folding
		String a = lowerCaseString.replaceAll("\\<.*?>"," "); //SGML tags removed and words with dashes are considered as two different different words
		String b = a.replaceAll("\n"," ");
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
	
	public static void storeIndex(HashMap<String, IndexStorage> indexMap, HashMap<String, FrequencyOfMostFrequenctStemAndTotalWordOccurrences> forFrequencyOfMostFrequentStemAndTotalWordOccurrences )
	{
		String dirPathToStoreIndex = dir;
		String filePathToStoreIndex = dirPathToStoreIndex + "/Index";
		File index = new File(filePathToStoreIndex);
		
			try 
			{
			boolean fileCreated = index.createNewFile();
			if(fileCreated)
			{
				FileOutputStream out = new FileOutputStream(filePathToStoreIndex);
				String fileData = "";
				Set<String> tokens = indexMap.keySet();
				Iterator<String> itr = tokens.iterator();
				String token = "";
				while(itr.hasNext()) 
				{
					token = itr.next();
					IndexStorage is = indexMap.get(token);
					fileData = "token: " + token + " docfreq: " + is.getDocumentFrequency();
					out.write(fileData.getBytes());
					for(int i = 0; i < is.termFrequency.size(); i++) 
					{
						TermFrequency tf = is.termFrequency.get(i);
						fileData = tf.getDocumentID() + " --->" + tf.getNoOfTimesTermOccursInDocument() + " ";
						out.write(fileData.getBytes());
					}
					out.write(fileData.getBytes());

				}

			for (String documentID: forFrequencyOfMostFrequentStemAndTotalWordOccurrences.keySet())
				{

            String key =documentID.toString();
            FrequencyOfMostFrequenctStemAndTotalWordOccurrences ft = forFrequencyOfMostFrequentStemAndTotalWordOccurrences.get(documentID);
			fileData = "Frequences of most frequent stems and Total Word Occcurences";
			fileData = key + " -->" + ft.frequencyOfMostFrequentStem + " : " + ft.totalWordOccurrences;
			out.write(fileData.getBytes()); 			
            
			} 
					out.close();
			}
			} catch (Exception e)
			{
			  System.out.println("Exception occurred while creating the index file");
			}

	}
	
	public static void storeCompressedIndex(HashMap<String, CompressedIndexStorage> compressedIndexMap )
	{
		String dirPathToStoreIndex = dir;
		String filePathToStoreIndex = dirPathToStoreIndex + "/CompressedIndex";
		File index = new File(filePathToStoreIndex);
		
			try 
			{
			boolean fileCreated = index.createNewFile();
			if(fileCreated)
			{
				FileOutputStream out = new FileOutputStream(filePathToStoreIndex);
				String fileData = "";
				Set<String> tokens = compressedIndexMap.keySet();
				Iterator<String> itr = tokens.iterator();
				String token = "";
				while(itr.hasNext()) 
				{
					token = itr.next();
					CompressedIndexStorage cis = compressedIndexMap.get(token);
					fileData = "token: " + token + " docfreq: " + cis.getDocumentFrequency();
					out.write(fileData.getBytes());
					for(int i = 0; i < cis.compressedTermFrequency.size(); i++) 
					{
						CompressedTermFrequency ctf = cis.compressedTermFrequency.get(i);
						fileData = ctf.getDocumentIDdelta() + " --->" + ctf.getNoOfTimesTermOccursInDocument() + " ";
						out.write(fileData.getBytes());
					}
					out.write(fileData.getBytes());

				}

					out.close();
			}
			} catch (Exception e)
			{
			  System.out.println("Exception occurred while creating the index file");
			}

	}
	
	public static CompressedTermFrequency toCompressTermFrequency(TermFrequency termFrequency) 
	{
		int noOfTimesTermOccursInDocument = termFrequency.noOfTimesTermOccursInDocument;
		String documentID = termFrequency.documentID;
		int documentIDint = Integer.parseInt(documentID);
		forCompressedIndex fci = new forCompressedIndex();
		
		String noOfTimesTermOccursInDocumentGammaCompressed = fci.getGammaCode(noOfTimesTermOccursInDocument);
		String documentIDdelta = fci.getDeltaCode(documentIDint);
		CompressedTermFrequency compressedTermFrequency = new CompressedTermFrequency(noOfTimesTermOccursInDocumentGammaCompressed,documentIDdelta);
		return compressedTermFrequency;
	}
}

class tokenInformation {

public int numberOfFiles = 0;

	public String readFilesToString(String dir) throws Exception 
	{	
		String text = "";
		File f1 = new File(dir);
		if(f1.isDirectory()) 
		{
			String[] files = f1.list();
			numberOfFiles = files.length;
			for(int i = 0; i<files.length;i++)
			{
				File f = new File(dir + "/" + files[i]);
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
	int frequencyOfMostFrequentStem;
	int totalWordOccurrences;
	
	public int getTotalWordOccurrences()
	{
		return totalWordOccurrences;
	}
	
	public int getFrequencyOfMostFrequentStem()
	{
		return frequencyOfMostFrequentStem;
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

class CompressedTermFrequency 
{
	String noOfTimesTermOccursInDocumentGammaCompressed;
	String documentIDdelta;
	
	CompressedTermFrequency(String noOfTimesTermOccursInDocumentGammaCompressed, String documentIDdelta)
	{
		this.noOfTimesTermOccursInDocumentGammaCompressed = noOfTimesTermOccursInDocumentGammaCompressed;
		this.documentIDdelta = documentIDdelta;
	}	
	
	public String getDocumentIDdelta() 
	{
		return documentIDdelta;
	}
	
	public String getNoOfTimesTermOccursInDocument()
	{
		return noOfTimesTermOccursInDocumentGammaCompressed;
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

class CompressedIndexStorage 
{
	ArrayList<CompressedTermFrequency> compressedTermFrequency;
	int documentFrequency;
	ArrayList<String> documentIDs;
	
	CompressedIndexStorage(ArrayList<CompressedTermFrequency> compressedTermFrequency,int documentFrequency, ArrayList<String> documentIDs)
	{
		this.compressedTermFrequency = compressedTermFrequency;
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


class forCompressedIndex 
{
	public String getGammaCode(int frequency)
	{
		String binary = Integer.toBinaryString(frequency);
		String gammaCode = "";
		binary=binary.substring(1);
		for(int i=0;i<binary.length();i++){
			gammaCode = gammaCode + "1";
		}
		gammaCode = gammaCode + "0";
		gammaCode = gammaCode + binary;
		return gammaCode;		
	}
	
	public String getDeltaCode(int documentID)
	{
		String deltaCode = "";
		String binary = Integer.toBinaryString(documentID);
		String gammaCode = getGammaCode(binary.length());
		binary=binary.substring(1);
		deltaCode = gammaCode + binary;
		return deltaCode;		
	}
}
