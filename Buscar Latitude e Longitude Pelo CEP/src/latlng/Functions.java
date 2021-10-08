package latlng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Scanner;
import org.json.*;

public class Functions {

	private String key;
	private URL url;
	
	public Functions(String key, URL url) {
		this.key = key;
		this.url = url;
	}

	// IMPRESSÃO RESULTADO FINAL;
		public static double[] printLatLng(JSONObject jSON, URL url) throws IOException {
			double array[] = {0, 0};
			
			HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			if (jSON.isEmpty()) {
				System.out
						.println("Houve um Erro(" + connect.getResponseCode() + " - " + connect.getResponseMessage() + ")");
				return array;
			} else {
				double lng = jSON.getDouble("lng");
				double lat = jSON.getDouble("lat");
				array[0]=lat;
				array[1]=lng;
				System.out.println("Latitude : " + lat + "\nLongitude: " + lng);
				return array;
			}

		}

		// INTERAÇÃO COM O USUARIO;
		public static String perguntarCEP() {
			Scanner input = new Scanner(System.in);
			String cep = " ";
			System.out.println("Digite seu CEP");
			cep = input.nextLine();
			input.close();
			return cep;
		}

		// VALIDAÇÃO;
		public static JSONObject isValid(URL url) throws JSONException, IOException {
			String jsonStr = "{}";
			JSONObject jsonERRO = new JSONObject(jsonStr);
			try {
				JSONObject locationJSON = readJSON(url);
				return locationJSON;
			} catch (Exception e) {
				return jsonERRO;
			}
		}

		// LEITURA E FORMATAÇÃO DO JSON;
		public static JSONObject readJSON(URL url) throws JSONException, IOException {

			HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			String jsonStr = "{}";
			JSONObject jsonERRO = new JSONObject(jsonStr);

			if (connect.getResponseCode() == 200) {
				JSONObject json = readJsonFromUrl(url.toString());
				JSONArray resultsJSONArray = json.getJSONArray("results");
				JSONObject resultsJSON = resultsJSONArray.getJSONObject(0);
				JSONObject geometryJSON = resultsJSON.getJSONObject("geometry");
				JSONObject locationJSON = geometryJSON.getJSONObject("location");

				return locationJSON;
			} else {
				return jsonERRO;
			}
		}

		private static String readAll(Reader rd) throws IOException {
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			return sb.toString();
		}

		public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
			InputStream is = new URL(url).openStream();
			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				JSONObject json = new JSONObject(jsonText);
				return json;
			} finally {
				is.close();
			}
		}
	
}
