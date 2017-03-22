package com.agave.robot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUnit {

	public String doGet(String uriAPI, HashMap<String, String> head) {
		// String uriAPI = "http://XXXXX?str=I+am+get+String";
		// String uriAPI="http://www.baidu.com";
		String result = "";
		// HttpGet httpRequst = new HttpGet(URI uri);
		// HttpGet httpRequst = new HttpGet(String uri);
		// 创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象。
		HttpGet httpRequst = new HttpGet(uriAPI);

		if (null != head) {
			for (Iterator<String> it = head.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				httpRequst.addHeader(key, head.get(key));
			}
		}
		// new DefaultHttpClient().execute(HttpUriRequst requst);
		try {
			// 使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);// 其中HttpGet是HttpUriRequst的子类
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
				// 一般来说都要删除多余的字符
				result.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
			} else
				httpRequst.abort();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		return result;
	}

	public String doPost(String uriAPI, List<NameValuePair> params, HashMap<String, String> head) {
		// String uriAPI = "http://localhost:9090/createItem";//Post方式没有参数在这里
		String result = "";
		HttpPost httpRequst = new HttpPost(uriAPI);// 创建HttpPost对象
		if (null != head) {
			for (Iterator<String> it = head.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				httpRequst.addHeader(key, head.get(key));
			}
		}
		// List <NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("name", "Httpclient test post"));
		// params.add(new BasicNameValuePair("mode", "copy"));
		// params.add(new BasicNameValuePair("from", "testng001"));
		try {
			httpRequst.setEntity((HttpEntity) new UrlEncodedFormEntity(params, HTTP.UTF_8));
			DefaultHttpClient httpclient = new DefaultHttpClient();
//			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
			HttpResponse httpResponse = httpclient.execute(httpRequst);
			int responsecode=httpResponse.getStatusLine().getStatusCode();
			String remessage=httpResponse.getStatusLine().getReasonPhrase();
			List<Cookie> cookies = httpclient.getCookieStore().getCookies();//get cookies
			result="{httpResponse.StatusCode:"+responsecode+",httpResponse.reasonphrase:"+remessage+"}" 
					+"@@" + cookies.toString();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity) +"@@" + cookies.toString();// 取出应答字符串
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		return result;
	}
	public String doJSONPost(String uriAPI, String params, HashMap<String, String> head) {
		// String uriAPI = "http://localhost:9090/createItem";//Post方式没有参数在这里
		String result = "";
		HttpPost httpRequst = new HttpPost(uriAPI);// 创建HttpPost对象
		if (null != head) {
			for (Iterator<String> it = head.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				httpRequst.addHeader(key, head.get(key));
			}
		}
		// List <NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("name", "Httpclient test post"));
		// params.add(new BasicNameValuePair("mode", "copy"));
		// params.add(new BasicNameValuePair("from", "testng001"));
		try {
//			httpRequst.setEntity((HttpEntity) new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpRequst.setEntity(new StringEntity(params, Charset.forName("UTF-8")));
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
//			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
			HttpResponse httpResponse = httpclient.execute(httpRequst);
			int responsecode=httpResponse.getStatusLine().getStatusCode();
			String remessage=httpResponse.getStatusLine().getReasonPhrase();
//			List<Cookie> cookies = httpclient.getCookieStore().getCookies();//get cookies
//			result="{httpResponse.StatusCode:"+responsecode+",httpResponse.reasonphrase:"+remessage+"}" 
//					+"@@" + cookies.toString();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
//				result = EntityUtils.toString(httpEntity) +"@@" + cookies.toString();// 取出应答字符串
				result = EntityUtils.toString(httpEntity);
				System.out.println(result);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		return result;
	}
}
