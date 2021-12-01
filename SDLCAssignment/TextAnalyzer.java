package SDLCAssignment;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/**
 * TextAnalyzer is the class to generate the top 20 words from a poem
 * @author VivianPham
 * @version 1.0
 */

public class TextAnalyzer {
	
    /** 
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
		/** Open and scan url then jsoup and scan through actual poem*/
        URL url = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
        Scanner urlScan = new Scanner(url.openStream());
        String html = urlScan.useDelimiter("\\A").next();
        Document doc = Jsoup.parse(html);
        String poem = doc.select("h1,p").text().replaceAll("[^a-zA-Z '-]", "").toLowerCase();
		Scanner poemScan = new Scanner(poem);
        
		/** Create Hashmap to store words and instance count*/
        HashMap<String,Integer> poemMap = new HashMap<String, Integer>(); 
        while (poemScan.hasNext()) {   
            String word = poemScan.next();
            /**If word is not read in poem yet add a value of 1 (this makes sures there are no 0's) */ 
            if(poemMap.containsKey(word) == false) {
            	poemMap.put(word,1);
            }
            /**else if word is present remove the previous key and replace with new key, then increase the count */ 
            else {
                int count = (int)(poemMap.get(word));
                poemMap.remove(word);  
                poemMap.put(word,count+1); 
            }
        } 
        poemScan.close();
        
        /**Puts poemMap in a set */
        Set<Map.Entry<String, Integer>> poemSet = poemMap.entrySet(); 

        /**Create a wordList from the poemSet to compare */
        List<Map.Entry<String, Integer>> wordList = new ArrayList<Map.Entry<String, Integer>>(poemSet);
        Collections.sort( wordList, new Comparator<Map.Entry<String, Integer>>() {
        	/**Sort by descending count values */
            public int compare( Map.Entry<String, Integer> a, Map.Entry<String, Integer> b ) {
                return (b.getValue()).compareTo( a.getValue() ); 
            }
        });
        
        int numCount = 1;
        /**
         * loop to go through eat key(word) and value(count) in wordList
         * @return the results 
        */
        for(Map.Entry<String, Integer> i:wordList) {
            if(numCount <= 20) {
                Connection conn = getConnection();
                PreparedStatement insertIn = conn.prepareStatement("INSERT INTO wordfreq(word, frequency) VALUES ('"+i.getKey()+"', '"+i.getValue()+"')");
                insertIn.executeUpdate();
                numCount++;
            }
        }

        getResult();
	}

    public static ResultSet getResult() throws Exception {
        Connection conn = getConnection();
        PreparedStatement data = conn.prepareStatement("SELECT word, frequency FROM wordFreq");

        ResultSet result = data.executeQuery();

        while(result.next()){
            System.out.println(result.getString("word")+":"+result.getString("frequency"));
        }
        return result;
    }

    public static void createTable() throws Exception{
        Connection conn = getConnection();
        PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS wordFreq(word varchar(255), frequency int)");
        create.executeUpdate();
    }

    public static Connection getConnection() throws Exception{
        try{
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/word_occurences";
        String username = "root";
        String password = "dn35wxzvb";
        Class.forName(driver);
        
        Connection conn = DriverManager.getConnection(url,username,password);
        System.out.println("Connected");
        return conn;}
        catch(Exception e){System.out.println(e);}
        return null;
    }
}