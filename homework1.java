import java.io.*;
import java.util.*;

public class homework1 {

	public static void main(String[] args) throws Exception 
	{
		tokenInformation ti = new tokenInformation();
		String s = ti.readFilesToString("C:\\Users\\Rajesh\\Desktop\\Sem 3\\Information Retrieval\\homework1\\rxs125730\\Cranfield");
		String lowerCaseString = s.toLowerCase(); //Case folding
		String a = lowerCaseString.replaceAll("\\<.*?>"," "); //words with dashes are considered as two different different words
		String b = a.replaceAll("\n"," ");
		StringTokenizer st = new StringTokenizer(b);
		String tokens = "";
		HashMap<String,Integer> tokenMap = new HashMap<String,Integer>();
		int count = 0;

		String afterStemming = "";
		
		StringTokenizer wordsTokenizer = new StringTokenizer(b);
		HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
		
		while(wordsTokenizer.hasMoreTokens()) 
		{
			String word = wordsTokenizer.nextToken();
			if(wordsMap.containsKey(word)) 
			{
				wordsMap.put(word, wordsMap.get(word)+1);
			} else {
				wordsMap.put(word,1);
			}
		}
		
		
		ArrayList<Integer> wordFrequency = new ArrayList<Integer>(wordsMap.values());
		
		int countOfOnlyOnceWords = 0;
		for(int i=0;i<wordFrequency.size();i++) {
		 if(wordFrequency.get(i) == 1) {
		 countOfOnlyOnceWords++;
		 }
		}
		Collections.sort(wordFrequency);
		Collections.reverse(wordFrequency);
		Map<String, Integer> sortedMap = new HashMap<String, Integer>();
		sortedMap = MapUtils.sortByValue(wordsMap);
		
		LinkedHashMap<String, Integer> linkedWords = new LinkedHashMap<String,Integer>(sortedMap);
		ArrayList<Integer> values = new ArrayList<Integer>(linkedWords.values());
		int size = values.size();
		
		 Iterator i = linkedWords.entrySet().iterator();
		 for(int u = 0; u<size-30;u++){
		i.next();
		}
		while(i.hasNext()){
        System.out.println(i.next());
		}

		while(st.hasMoreTokens())
		{
			String tokenAfterPunctuationRemoval = ti.removePunctuation(st.nextToken());
			
			if(tokenMap.containsKey(tokenAfterPunctuationRemoval)) {
			tokenMap.put(tokenAfterPunctuationRemoval,tokenMap.get(tokenAfterPunctuationRemoval)+1);
			
			} else {
			tokenMap.put(tokenAfterPunctuationRemoval,1);
			
			}
			
				tokens += " " + tokenAfterPunctuationRemoval;
		}
		
		
		StringTokenizer t = new StringTokenizer(tokens);
		
		System.out.println("Total tokens: " + t.countTokens());
		System.out.println("Unique tokens: " + tokenMap.size());
		System.out.println("Unique words: " + wordsMap.size());
		System.out.println("Count of words that occurred only once: " + countOfOnlyOnceWords);
		System.out.println("Average number of word tokens: " + t.countTokens()/ti.numberOfFiles);
		
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
 
class MapUtils // http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
{
    public static <K, V extends Comparable<? super V>> Map<K, V> 
        sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
