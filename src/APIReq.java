import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.io.Reader;

import org.json.JSONException;
import org.json.JSONObject;




public class APIReq {
	static String id; 
	public static void main(String[] args) throws Exception{
		JSONObject json = readJson("http://api.goeuro.com/api/v2/position/suggest/en/dublin%20ireland");
		id = json.getString("_id");
		System.out.println(id);	 
	}

	public static String readAll(Reader r) throws IOException{
		StringBuilder builder = new StringBuilder();
		int c;
		while(( c = r.read()) != -1){
			builder.append((char) c);
		}
		String result =  builder.toString();
		result = result.replace("[", "").replace("]", "");
		return result;
		
	}

	public static JSONObject readJson(String url) throws IOException, JSONException {
		
		InputStream input = new URL(url).openStream();
		try{
				BufferedReader reader = new BufferedReader(new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8"))));
				String jsonText = readAll(reader);
				JSONObject json = new JSONObject(jsonText);
		return json; 
                } finally {
			input.close();
			}
	}
	
}
