package com.hl.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.hl.commons.GlobalVariate;
import com.hl.ui.HomePageActivity;
import com.hl.ui.LoginActivity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author Ku_wan
 *
 */
public class HttpDownload {
	
	private static HttpClient httpClient =null;
	
	public static HttpClient getHttpClient(){
		if(httpClient == null){
			httpClient = new DefaultHttpClient();
		}
		return httpClient;
	}
	
	public static String getJSONData(String url)
			throws ClientProtocolException, IOException {
		String result = "";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = getHttpClient();
		HttpResponse httpResponse = null;

		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				InputStream inputStream = httpEntity.getContent();
				result = convertStreamToString(inputStream);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
			httpResponse = null;
		}
		return result;

	}
	
	public static String sendPostHttpRequest(String url,List<NameValuePair> params){
		//����HttpPost���Ӷ���(HttpPost ��Ϊ HttpRequest)
		HttpPost httpPost = new HttpPost(GlobalVariate.SERVERADDRESS+url);
//		params.add(new BasicNameValuePair("userId", Storage.getString(context, "userId")));
		try {
			//�����ַ���
			HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
			//
			httpPost.setEntity(httpEntity);
			//ʹ��DefaultHttpClient Ϊ HttpClient
			HttpClient httpClient = getHttpClient();
			//ȡ��HttpResponse ����
			HttpResponse httpResponse = httpClient.execute(httpPost);
			//HttpStatus.SC_OK ��ʾ���ӳɹ�
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				//ȡ�÷��ص��ַ���
				String result ="";
				if(httpResponse.getEntity() != null){
					InputStream is = httpResponse.getEntity().getContent();
					result = convertStreamToString(is);
//					result = result.replaceAll("\r", "");
					return result.trim();
				}
			}else {
				System.out.println("fail the request");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"),// ��ֹģ�����ϵ�����
					512 * 1024);
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			Log.e("DataProvier convertStreamToString", e.getLocalizedMessage(),e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}

