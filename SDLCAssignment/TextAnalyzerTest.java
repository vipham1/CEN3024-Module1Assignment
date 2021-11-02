package SDLCAssignment;

import java.net.*;
import java.util.*;

import javax.swing.text.html.HTMLDocument.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class TextAnalyzerTest {
    public static ArrayList<String> results = new ArrayList<String>();
    public static String resultsToString = new String();
    public static ArrayList<String> TextAnalyzerResults() throws IOException{
        URL url = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
        Scanner urlScan = new Scanner(url.openStream());
        String html = urlScan.useDelimiter("\\A").next();
        Document doc = Jsoup.parse(html);
        String poem = doc.select("h1,p").text().replaceAll("[^a-zA-Z '-]", "").toLowerCase();
		Scanner poemScan = new Scanner(poem);
        
		// Create Hashmap to store words and instance count
        HashMap<String,Integer> poemMap = new HashMap<String, Integer>(); 
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
                results.add(i.getKey() + ": " + i.getValue());
                numCount++;
            }
        }

        
        //return the results
        return results;
    }

    public static String TextAnalyzerStringResults(){
        resultsToString = String.join(", ", results);
        return resultsToString;
    }

    @Test
    public void testTextAnalyzerResults() throws IOException { 
        String expectedResults = "the: 57, and: 38, i: 32, my: 24, of: 22, that: 18, this: 16, a: 15, door: 14, raven: 11, chamber: 11, bird: 10, is: 10, on: 10, nevermore: 9, at: 8, with: 8, or: 8, then: 8, lenore: 8";
        assertEquals(expectedResults, TextAnalyzerStringResults());
    }

    @Test
    public void testTextAnalyzerResultsLength() throws IOException{
        TextAnalyzerResults();
        assertEquals(20, results.size());
    }
}
