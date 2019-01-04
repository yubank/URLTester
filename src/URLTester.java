import java.awt.PageAttributes.MediaType;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLTester {

	public static void main(String[] args) throws IOException {
		//String url="http://localhost:8080/MNB/Sample.do";
		//String url="http://localhost:8080/MNB/api/IFP_0001.do";
		String url="http://localhost:8080/api/sbs/IFP002";
		
		URL con = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)con.openConnection();
			
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setInstanceFollowRedirects(true);
		conn.setRequestMethod("POST");

		//String param="aaa1="+"12312"+"&"+"bbb2="+"한글";
		String param="{\"userId\":\"1231231\",\"password\":\"한글\"}";
		//String enParam=URLEncoder.encode(param, "UTF-8");
		//System.out.println("endParam:" + enParam);
		//conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		//conn.setRequestProperty("Content-Type", "application/text");
		conn.setRequestProperty("Content-Type", "application/json");
		//mimeType: 'application/json'
		//conn.setRequestProperty("Mime-Type", "application/json");
		//conn.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length", Integer.toString(param.length()));

		DataOutputStream os = new DataOutputStream(conn.getOutputStream());
		os.write(param.getBytes("UTF-8"));
		os.flush();
		os.close();

		System.out.println(String.format("HTTP return Code:%d", conn.getResponseCode()));
		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			String line = "";
			StringBuilder sb = new StringBuilder();
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8));

			do {
				line = br.readLine();
				System.out.println("line:" + line);
				if(line != null) {
					sb.append(line);
				}
			} while(line != null);
			
			System.out.println(sb.toString());
		}
	}
}
