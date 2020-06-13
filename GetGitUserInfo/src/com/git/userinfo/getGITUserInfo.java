/**
Project Details - Call git API and get public user, repository and event info based on user input
 * 
 */
package com.git.userinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.*;

/**
 * @author Sonali Bais
 *
 */
public class getGITUserInfo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		String gitString = "";
	    gitString = "https://api.github.com/repos/sonalibais/Projects/events";
	    System.out.println("URL " +gitString);
		
	    String inline = "";
			
			try
			{
				URL gitURL = new URL(gitString);
				
				//Parse URL into HttpURLConnection to open connection and get JSON Data
			    HttpURLConnection conn = (HttpURLConnection)gitURL.openConnection();
			    
				//Set the request to GET
				conn.setRequestMethod("GET");
				
				//Use the connect method to create the connection bridge
				conn.connect();
				
				//Get the status code returned by Git API
				int code = conn.getResponseCode();
				System.out.println("Git API Response code: " +code);
				System.out.println("***********************************************************************************************************************");
				//System.out.println("-----------------------------------------------------------------------------------------------------------------------");
				//Checking response code from Git API and if it is not 200(OK Status) then throw exception
				if(code != 200)
					throw new RuntimeException("HttpResponseCode: " +code);
				else
				{
					//Read JSON data returned by Git API
					Scanner scan = new Scanner(gitURL.openStream());
					while(scan.hasNext())
					{
						inline+=scan.nextLine();
					}

					
					//Close the stream when reading the data has been finished
					scan.close();
				}
				
				//JSONParser reads the data from string object and break each data into key value pairs
				JSONParser parse = new JSONParser();
				//Parse the string in to JSONArray object
				JSONArray arr = (JSONArray)parse.parse(inline);

				
				
                //Display JSON array    
		            for(int i=0;i<arr.size();i++){
			         			    	 
				        //Create JSON object to store individual entry of JSONArray
		            	JSONObject rootObj = (JSONObject) arr.get(i);
		                
		            	//Extract first level JSON key values for event type and create time.
		                String event=(String) rootObj.get("type");
		                String createTime =(String) rootObj.get("created_at").toString();
		           
		                System.out.println ("Event Type: "+event+"            Create Time: "+createTime);
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		    			 }
				
	            // Closing the connection
				conn.disconnect();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}