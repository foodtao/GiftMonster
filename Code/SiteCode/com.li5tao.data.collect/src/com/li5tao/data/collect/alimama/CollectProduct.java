package com.li5tao.data.collect.alimama;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/*
 * ץȡ��Ʒ����
 */
public class CollectProduct {
	
	public static void getProduct() {
		try{
			String token="";
			String t = "";
			Login login = new Login("food0517@163.com","tao3230517","6c346857fa701323c6055b2d0787b3f6");
			Cookie[] cookies = login.LoginIn();
			BasicCookieStore cookieStore = new BasicCookieStore();
			cookieStore.addCookies(cookies);
			CloseableHttpClient httpclient = HttpClients.custom()
					.setDefaultCookieStore(cookieStore)
					.build();
			HttpGet httpget = new HttpGet("http://pub.alimama.com/index.htm");
			CloseableHttpResponse response = httpclient.execute(httpget);
			try{
				HttpEntity entity = response.getEntity();
				List<Cookie> cookies1 = cookieStore.getCookies();
				if(!cookies1.isEmpty()){
					for(Cookie cookie : cookies1){
						if(cookie.getName().equals("_tb_token_")){
							token = cookie.getValue();
							System.out.println(token);
						}
						else if(cookie.getName().equals("t")){
							t = cookie.getValue();
						}
					}
				}
				
				BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
				   StringBuffer buffer = new StringBuffer();
				   String line = "";
				   while ((line = in.readLine()) != null){
				     buffer.append(line);
				   }
				   System.out.println(buffer.toString());
				
			}finally{
				response.close();
			}
			
			httpget =  new HttpGet("http://pub.alimama.com/pubauc/searchAuctionList.json?spm=0.0.0.0.nBjKHW&q=%E6%89%8B%E6%9C%BA&t="+t+"&_tb_token_="+token+"&_input_charset=utf-8");
			response = httpclient.execute(httpget);
			try{
				HttpEntity entity = response.getEntity();
				BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
				   StringBuffer buffer = new StringBuffer();
				   String line = "";
				   while ((line = in.readLine()) != null){
				     buffer.append(line);
				   }
				   System.out.println(buffer.toString());
				
			}finally{
				response.close();
			}
		}catch(Exception e){
			
		}
	}
	
	public static void main(String[] args){
		getProduct();
	}
}