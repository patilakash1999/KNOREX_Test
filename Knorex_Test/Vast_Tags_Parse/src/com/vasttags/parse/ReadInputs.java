package com.vasttags.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadInputs {
	
	    private String readXmlFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
   
        
    private String readXmlFromUrl(String url) {
        StringBuilder result = new StringBuilder();
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

	public static void main(String[] args) {

		ReadInputs readInputs = new ReadInputs();
	        
	        String filePath = "C:/Users/Akash Patil/Desktop/Knorex_Test/Vast_Tags_Parse/src/com/vasttags/parse/VastTags.xml";
	        String url = "https://raw.githubusercontent.com/InteractiveAdvertisingBureau/VAST_Samples/master/VAST%203.0%20Samples/Inline_Companion_Tag-test.xml";
	        
	        // Read from file
	        String xmlFromFile = readInputs.readXmlFromFile(filePath);
	        System.out.println("XML from file:\n" + xmlFromFile);
	        
	        // Read from URL
	        String xmlFromUrl = readInputs.readXmlFromUrl(url);
	        System.out.println("XML from URL:\n" + xmlFromUrl);
		
		
	}

}
