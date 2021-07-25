package utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilities {
	protected static final Logger LOGGER = LoggerFactory.getLogger(Utilities.class);
	
	public static final String APP_URL = System.getProperty("com.lockton.stoploss.workitemhandlers.app-url", "https://dev-lockton-cms.seventablets.com");
	
//	2018-03-26T00:00:00.000Z
	public static final DateTimeFormatter DATE_FROM_CRUD = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
	public static final DateTimeFormatter DATE_OUT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	private Utilities() {}
	
	public static final LocalDate getLocalDate(Object obj) throws DateTimeParseException {
		LocalDate date = null;
		if (obj != null) {
			if (obj instanceof LocalDate) {
				date = (LocalDate)obj;
			} else {
				try {
					date = LocalDate.parse(obj.toString());
				} catch (DateTimeParseException e) {
					try {
						date = LocalDate.parse(obj.toString(), Utilities.DATE_FROM_CRUD);
					} catch (DateTimeParseException ee) {
						date = LocalDate.parse(obj.toString(), Utilities.DATE_OUT);
					}
				}
			}
		}
		return date;
	}
	
	public static final List<Map<String,String>> getJson(final String url) throws ClientProtocolException, IOException {
		return getJson(url, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T getJson(final String url, final String username, final String password, TypeReference<T> typeRef) throws ClientProtocolException, IOException {
		T results = null;
		JSONObject jsonObject;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		if (username != null && password != null) {
			final String auth = username + ":" + password;
			final byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("ISO-8859-1")));
			final String authHeader = "Basic " + new String(encodedAuth);
			httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		}
		HttpResponse response = httpClient.execute(httpGet);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode < 200 || statusCode >= 300) {
			throw new IOException("Server returned " + statusCode + " when trying to download " + url);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Object obj = new Object();
		try {
			JSONParser jsonParser = new JSONParser();
			try {
				jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				obj = jsonObject;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(obj instanceof JSONArray) {
				results = objectMapper.readValue(response.getEntity().getContent(), typeRef);
			}else if(obj instanceof JSONArray) {
				List<Map<String,String>> resultMapList = new ArrayList<Map<String,String>>();
				Map<String,String> resultMap = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<Map<String,String>>(){});
				resultMapList.add(resultMap);
				results = ( T ) resultMapList;
			}
		} catch (JsonMappingException e) {
		} 
		httpClient.close();
		return results;
	}
	
	public static final List<Map<String,String>> getJson(final String url, final String username, final String password) throws ClientProtocolException, IOException {
		List<Map<String,String>> resultsList = null;
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		HttpGet httpGet = new HttpGet(url);
//		if (username != null && password != null) {
//			final String auth = username + ":" + password;
//			final byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("ISO-8859-1")));
//			final String authHeader = "Basic " + new String(encodedAuth);
//			httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
//		}
//		HttpResponse response = httpClient.execute(httpGet);
//		int statusCode = response.getStatusLine().getStatusCode();
//		if (statusCode < 200 || statusCode >= 300) {
//			throw new IOException("Server returned " + statusCode + " when trying to download " + url);
//		}
//		ObjectMapper objectMapper = new ObjectMapper();
//		resultsList = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<Map<String,String>>>(){});
//		httpClient.close();
		resultsList = getJson(url, username, password, new TypeReference<List<Map<String,String>>>(){});
		return resultsList;
	}
	
	public static final Map<String,String> getJsonObject(final String url, final String username, final String password) throws ClientProtocolException, IOException {
		Map<String,String> results = null;
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		HttpGet httpGet = new HttpGet(url);
//		if (username != null && password != null) {
//			final String auth = username + ":" + password;
//			final byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("ISO-8859-1")));
//			final String authHeader = "Basic " + new String(encodedAuth);
//			httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
//		}
//		HttpResponse response = httpClient.execute(httpGet);
//		int statusCode = response.getStatusLine().getStatusCode();
//		if (statusCode < 200 || statusCode >= 300) {
//			throw new IOException("Server returned " + statusCode + " when trying to download " + url);
//		}
//		ObjectMapper objectMapper = new ObjectMapper();
//		results = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<Map<String,String>>(){});
//		httpClient.close();
		results = getJson(url, username, password, new TypeReference<Map<String,String>>(){});
		return results;
	}

	public static final String postJsonString(final String url, final String jsonString) throws Exception {
		return postJsonString(url, jsonString, null, null);
	}

	public static final String postJsonString(final String url, final String jsonString, final String username, final String password) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		if (username != null && password != null) {
			final String auth = username + ":" + password;
			final byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("ISO-8859-1")));
			final String authHeader = "Basic " + new String(encodedAuth);
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		}
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setEntity(new StringEntity(jsonString));
		HttpResponse response = httpClient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		String results = EntityUtils.toString(response.getEntity());
		if (statusCode < 200 || statusCode >= 300) {
			LOGGER.error("POST " + url + " returned body: " + results);
			// throw new IOException("Server returned " + statusCode + " when trying to post to " + url);
		}
		httpClient.close();
		return results;
	}

	public static final String postJsonStringWithBearer(final String url, final String jsonString, final String bearerToken) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		if (bearerToken != null) {
			final String authHeader = "Bearer " + bearerToken;
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
			httpPost.setHeader("kctoken", bearerToken);
		}
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setEntity(new StringEntity(jsonString));
		HttpResponse response = httpClient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		String results = EntityUtils.toString(response.getEntity());
		if (statusCode < 200 || statusCode >= 300) {
			LOGGER.error("POST " + url + " returned body: " + results);
			throw new IOException("Server returned " + statusCode + " when trying to post to " + url + "   " + results);
		}
		httpClient.close();
		return results;
	}

	public static final String putJsonString(final String url, final String jsonString) throws Exception {
		return putJsonString(url, jsonString, null, null);
	}

	public static final String putJsonString(final String url, final String jsonString, final String username, final String password) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		if (username != null && password != null) {
			final String auth = username + ":" + password;
			final byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("ISO-8859-1")));
			final String authHeader = "Basic " + new String(encodedAuth);
			httpPut.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		}
		httpPut.setHeader("Accept", "application/json");
		httpPut.setHeader("Content-type", "application/json");
		httpPut.setEntity(new StringEntity(jsonString));
		HttpResponse response = httpClient.execute(httpPut);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode < 200 || statusCode >= 300) {
			throw new IOException("Server returned " + statusCode + " when trying to post to " + url);
		}
		String results = EntityUtils.toString(response.getEntity());
		httpClient.close();
		return results;
	}
	
	
	public static final ByteArrayOutputStream downloadURL(final String url) throws ClientProtocolException, IOException {
		LOGGER.info("downloadURL: " + url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode < 200 || statusCode >= 300) {
			throw new IOException("Server returned " + statusCode + " when trying to download " + url);
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtils.copy(response.getEntity().getContent(), out);
		return out;
	}
	
	
	public static String urlEncode(String input) {
		String output = input;
		try {
			output = URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.warn("Take shelter! UTF-8 No Longer Exists");
		}
		// Outlook and Nodejs don't seem to like spaces represented as '+', instead encode them as "%20"
		output = output.replace("+", "%20");
		return output;
	}
	
	public static void main(String args[]) {
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		try {
			json = (JSONObject) parser.parse("{\n        \"id\": 783,\n        \"field_name\": \"bundled_with_aso\",\n        \"field_value\": \"Third Party Carrier\",\n        \"field_description\": \"Third Party Carrier\",\n        \"display_order\": 0,\n        \"log_id\": null,\n        \"layout_id\": null,\n        \"staging_record_id\": null,\n        \"error_message\": null,\n        \"target_repo_record_id\": null,\n        \"processing_status\": null,\n        \"target_repo_id\": null\n    }");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String,String> results = objectMapper.readValue(json.toJSONString(), new TypeReference<Map<String,String>>(){});
			System.out.println("id=> "+results.get("id"));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
