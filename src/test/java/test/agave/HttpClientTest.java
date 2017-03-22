package test.agave;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
public class HttpClientTest {
//	设置链接参数
//	HttpParams httpParameters = new BasicHttpParams();  
//	HttpConnectionParams.setConnectionTimeout(httpParameters, 10*1000);//设置请求超时10秒  
//	HttpConnectionParams.setSoTimeout(httpParameters, 10*1000); //设置等待数据超时10秒  
//	HttpConnectionParams.setSocketBufferSize(params, 8192);  
//	HttpClient httpclient = new DefaultHttpClient(httpParameters); //此时构造DefaultHttpClient时将参数传入  

	@Test
	public void doGet()  
    {  
//        String uriAPI = "http://XXXXX?str=I+am+get+String";  
		String uriAPI="http://192.168.10.232:8080/job/KC_CI_LightKey_Business_v1_install/56/api/json?pretty=true";
        String result= "";  
//      HttpGet httpRequst = new HttpGet(URI uri);  
//      HttpGet httpRequst = new HttpGet(String uri);  
//      创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象。  
        HttpGet httpRequst = new HttpGet(uriAPI);  
  
//      new DefaultHttpClient().execute(HttpUriRequst requst);  
        try {  
   //使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。  
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);//其中HttpGet是HttpUriRequst的子类  
            if(httpResponse.getStatusLine().getStatusCode() == 200)  
            {  
                HttpEntity httpEntity = httpResponse.getEntity();  
                result = EntityUtils.toString(httpEntity);//取出应答字符串  
            // 一般来说都要删除多余的字符   
                result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格    
            }  
                   else   
                        httpRequst.abort();  
           } catch (ClientProtocolException e) {  
            e.printStackTrace();  
            result = e.getMessage().toString();  
        } catch (IOException e) {  
            e.printStackTrace();  
            result = e.getMessage().toString();  
        } 
        System.out.println(result);
    }  
	
	@Test
	public void doPost()  
    {  
        String uriAPI = "http://192.168.210.232:8080/createItem";//Post方式没有参数在这里  
        String result = "";  
        HttpPost httpRequst = new HttpPost(uriAPI);//创建HttpPost对象  
          
        List <NameValuePair> params = new ArrayList<NameValuePair>();  
        params.add(new BasicNameValuePair("name", "Httpclient test post")); 
        params.add(new BasicNameValuePair("mode", "copy")); 
        params.add(new BasicNameValuePair("from", "testng001")); 
        
          
        try {  
            httpRequst.setEntity((HttpEntity) new UrlEncodedFormEntity(params,HTTP.UTF_8));  
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);  
            if(httpResponse.getStatusLine().getStatusCode() == 200)  
            {  
                HttpEntity httpEntity = httpResponse.getEntity();  
                result = EntityUtils.toString(httpEntity);//取出应答字符串  
            }  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            result = e.getMessage().toString();  
        }  
        catch (ClientProtocolException e) {  
            e.printStackTrace();  
            result = e.getMessage().toString();  
        }  
        catch (IOException e) {  
            e.printStackTrace();  
            result = e.getMessage().toString();  
        }  
    }  
	
}
