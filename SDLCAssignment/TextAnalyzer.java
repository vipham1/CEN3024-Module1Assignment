package SDLCAssignment;

import java.net.*;
import java.util.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TextAnalyzer {
	public static void main(String[] args) throws IOException {
		// Open and scan url then jsoup and scan through actual poem
        URL url = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
        Scanner urlScan = new Scanner(url.openStream());
        String html = urlScan.useDelimiter("\\A").next();
        Document doc = Jsoup.parse(html);
        String poem = doc.select("h1,p").text().replaceAll("[^a-zA-Z '-]", "").toLowerCase();
		Scanner poemScan = new Scanner(poem);
        
		// Create Hashmap to store words and instance count
        Map<String,Integer> poemMap = new HashMap<String, Integer>(); 
        while (poemScan.hasNext()) {   
            String word = poemScan.next();
            // If word is not read in poem yet add a value of 1 (this makes sures there are no 0's)
            if(poemMap.containsKey(word) == false) {
            	poemMap.put(word,1);
            }
            // else if word is present remove the previous key and replace with new key, then increase the count
            else {
                int count = (int)(poemMap.get(word));
                poemMap.remove(word);  
                poemMap.put(word,count+1); 
            }
        } 
        poemScan.close();
        
        // Puts poemMap in a set
        Set<Map.Entry<String, Integer>> poemSet = poemMap.entrySet(); 

        // Create a wordList from the poemSet to compare
        List<Map.Entry<String, Integer>> wordList = new ArrayList<Map.Entry<String, Integer>>(poemSet);
        Collections.sort( wordList, new Comparator<Map.Entry<String, Integer>>() {
        	// Sort by descending count values
            public int compare( Map.Entry<String, Integer> a, Map.Entry<String, Integer> b ) {
                return (b.getValue()).compareTo( a.getValue() ); 
            }
        });
        
        // Output Results
        int numCount = 1;
        //loop to go through eat key(word) and value(count) in wordList
        for(Map.Entry<String, Integer> i:wordList) {
            if(numCount <= 20) {
                System.out.println(i.getKey() + ": " + i.getValue());
                numCount++;
            }
        }
	}
}