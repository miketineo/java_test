package com.miketineo.java_test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.io.Reader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;



public class JavaTest {
	static Integer j_id; 
	static String j_name, j_type;
	static FileWriter writer;
	 
   
	public static void main(String[] args) throws Exception{
		if (args.length != 1){
			throw new Error("You Should provide 1 parameter");
		} 
		
		String query = args[0];
		JSONArray json = readJson("http://api.goeuro.com/api/v2/position/suggest/en/"+query);
		
			
		try{
		writer = new FileWriter("results.csv");
		
		for (int i= 0; i < json.length(); i++){
			JSONObject jo = json.getJSONObject(i);
			j_id = jo.getInt("_id");
			j_name = jo.getString("name");
			j_type = jo.getString("type");
			Double j_lat = jo.getJSONObject("geo_position").getDouble("latitude");
			Double j_lon = jo.getJSONObject("geo_position").getDouble("longitude");
			
			
			writer.append(j_id.toString()+", "+j_name+", "+j_type+", "+j_lat.toString()+" , "+j_lon.toString()+"\n");
		}
		writer.flush();
		writer.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public static String readAll(Reader r) throws IOException{
		StringBuilder builder = new StringBuilder();
		int c;
		while(( c = r.read()) != -1){
			builder.append((char) c);
		}
		String result =  builder.toString();
		return result;
		
	}

	public static JSONArray readJson(String url) throws IOException, JSONException {
		
		InputStream input = new URL(url).openStream();
		try{
				BufferedReader reader = new BufferedReader(new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8"))));
				String jsonText = readAll(reader);
				JSONArray json = new JSONArray(jsonText);
		return json; 
                } finally {
			input.close();
			}
	}
	
}
