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

			
			// Get values for GIT user name and repository
			String user;
            String repos;
            Scanner ins = new Scanner(System.in);
		    		    
            System.out.println("Enter user: ");
		    user = ins.nextLine();
		    System.out.println("You entered user: " +user);
		    
		    System.out.println("Enter user repository name: ");
		    repos = ins.nextLine();
		    System.out.println("You entered repository name: " +repos);
		    
		    
		    
		    // Create Git url based on input provided - Test with user: sonalibais and repo: Projects
		    String gitString = "";
		    gitString = gitString.concat("https://api.github.com/repos/").concat(user).concat("/").concat(repos).concat("/events");
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
					//System.out.println("\n GIt API Response: "); 
					//System.out.println(inline);
					
					//Close the stream when reading the data has been finished
					scan.close();
				}
				
				//JSONParser reads the data from string object and break each data into key value pairs
				JSONParser parse = new JSONParser();
				//Parse the string in to JSONArray object
				JSONArray arr = (JSONArray)parse.parse(inline);
	
                //Display JSON array    
		            for(int i=0;i<arr.size();i++){
		                System.out.println("array is " + arr.get(i));

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