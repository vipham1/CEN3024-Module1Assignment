/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textanalyzer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.net.*;
import java.util.*;
import java.io.*;
import javafx.geometry.Pos;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author vitu1
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
    
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
        
        Button btn = new Button();
        Label lbl = new Label();
        Text results = new Text();
        results.setWrappingWidth(200);
        lbl.setText("Let's get the top 20 words!");
        btn.setText("Analyze");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                // Output Results
                int numCount = 1;
                StringBuilder sb = new StringBuilder();
                //loop to go through eat key(word) and value(count) in wordList
                for(Map.Entry<String, Integer> i:wordList) {
                    if(numCount <= 20) {
                        sb.append(i.getKey() + ": " + i.getValue() + ", ");
                        numCount++;
                    }
                } results.setText(sb.toString());
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        root.getChildren().add(lbl);
        root.getChildren().add(results);
        StackPane.setAlignment(btn, Pos.CENTER);
        StackPane.setAlignment(lbl, Pos.TOP_CENTER);
        StackPane.setAlignment(results, Pos.BOTTOM_CENTER);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Text Analyzer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
