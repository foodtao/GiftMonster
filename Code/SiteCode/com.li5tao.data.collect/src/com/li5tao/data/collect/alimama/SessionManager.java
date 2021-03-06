package com.li5tao.data.collect.alimama;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/*
 * 登录操作
 */
public class SessionManager {
	
	private static String _username = "food0517@163.com"; //用户名
	private static String _password = "tao3230517"; //密码
	private static String _passwordEncrypt = "6c346857fa701323c6055b2d0787b3f6"; //加密后的密码
	private static final String url_Index = "http://www.alimama.com/index.htm";  //阿里妈妈首页
	private static final String url_loginaction = "http://www.alimama.com/member/minilogin_act.htm"; //阿里妈妈登录页面
	private static final String url_proxy = "http%3A%2F%2Fwww.alimama.com%2Fproxy.htm"; //登录跳转proxy页面
	private static final String url_pub = "http://pub.alimama.com/index.htm";  //登录成功后系统首页
	
	/*
	 * 代理信息
	 */
	private static String proxyAddress = "";
	private static int proxyPort = 0;
	private static String proxyUserName = "";
	private static String proxyPassword = "";
	
	private static SessionInfo _sessionInfo = null;
	
	/*
	 * 获取登录抓取数据所需的相关会话信息。
	 */
	public static SessionInfo getSessionInfo() throws Exception{
		Date now = new Date();
		if(_sessionInfo == null || (now.getTime() - _sessionInfo.get_lastModifyDate().getTime()) > 1000 * 60 * 10){
			_sessionInfo = new SessionInfo();
			Cookie[] cookies = getPubIndexCookie();
			String token = getCookieValue(cookies,"_tb_token_");
			String t = getCookieValue(cookies,"t");
			_sessionInfo.set_cookies(cookies);
			_sessionInfo.set_t(t);
			_sessionInfo.set_token(token);
			_sessionInfo.set_lastModifyDate(new Date());
			
		}
		
		return _sessionInfo;
	}
	
	/*
	 * 登录成功后访问系统首页取出最新的cookie
	 * return：返回cookie。
	 */
	private static Cookie[] getPubIndexCookie() throws Exception{
		Cookie[] cookieRet = login();
		if(cookieRet.length > 0){
			BasicCookieStore cookieStore = new BasicCookieStore();
			cookieStore.addCookies(cookieRet);
			CloseableHttpClient httpclient = HttpClients.custom()
					.setDefaultCookieStore(cookieStore)
					.build();
			try{
				HttpGet httpGet = new HttpGet(url_pub);
				CloseableHttpResponse response = httpclient.execute(httpGet);
				try{
					List<Cookie> cookies = cookieStore.getCookies();
					if(!cookies.isEmpty()){
						cookieRet = new Cookie[cookies.size()];
						int index = 0;
						for(Cookie cookie : cookies){
							cookieRet[index] = cookie;
							index++;
						}
					}
				}finally{
					response.close();
				}
			}finally{
				httpclient.close();
			}
		}
		
		return cookieRet;
	}
	
	/*
	 * 登录阿里妈妈；
	 * return：返回登录成功后的cookies
	 */
	private static Cookie[] login() throws Exception{
		Cookie[] cookieRet = getLoginPerare();
		if(cookieRet.length > 0){
			String token = getCookieValue(cookieRet,"_tb_token_");
			
			BasicCookieStore cookieStore = new BasicCookieStore();
			cookieStore.addCookies(cookieRet);
			CloseableHttpClient httpclient = HttpClients.custom()
					.setDefaultCookieStore(cookieStore)
					.build();
			try{
				HttpUriRequest login = RequestBuilder.post()
						.setUri(new URI(url_loginaction))
						.addParameter("_tb_token_",token)
						.addParameter("style", "")
						.addParameter("redirect", "")
						.addParameter("proxy", url_proxy)
						.addParameter("logname", _username)
						.addParameter("originalLogpasswd", _password)
						.addParameter("logpasswd", _passwordEncrypt)
						.build();
				CloseableHttpResponse response = httpclient.execute(login);
				try{
					List<Cookie> cookies = cookieStore.getCookies();
					cookieRet = new Cookie[cookies.size()];
					int index = 0;
					if(!cookies.isEmpty()){
						for(Cookie cookie : cookies){
							cookieRet[index] = cookie;
							index++;
							if(cookie.getName().equals("alimamapw")){
								token = cookie.getValue();
								System.out.println(token);
							}
						}
					}
					
				}finally{
					response.close();
				}
			}finally{
				httpclient.close();
			}
		}
		return cookieRet;
	}
	
	/*
	 * 登录阿里妈妈前准备信息获取
	 * return：返回登录所需cookies
	 */
	private static Cookie[] getLoginPerare() throws Exception{
		Cookie[] cookiesRet = null;
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore)
				.build();
		
		try{
			HttpGet httpGet = new HttpGet(url_Index);
			CloseableHttpResponse response = httpClient.execute(httpGet); 
			try{
				List<Cookie> cookies = cookieStore.getCookies();
				if(!cookies.isEmpty()){
					cookiesRet = new Cookie[cookies.size()];
					int index = 0;
					for(Cookie cookie : cookies){
						cookiesRet[index] = cookie;
						index++;
						if(cookie.getName().equals("_tb_token_")){
							System.out.println(cookie.getValue());
						}
					}
				}
			}finally{
				response.close();
			}
			
		}finally{
			httpClient.close();
		}
		
		return cookiesRet;
	}
	
	/*
	 * 从cookie中提取出指定cookie名称的值
	 * return：返回相应的值
	 */
	private static String getCookieValue(Cookie[] cookies,String name){
		String value = "";
		if(cookies.length > 0){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals(name)){
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}
	
	/*
	 * 登录方法
	 * return:返回登录成功后的Cookies
	 */
	public void LoginIn() throws Exception{
		Cookie[] cookiesret;
		String token = "";
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore)
				.build();
		try{
			HttpGet httpget = new HttpGet(url_Index);
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			try{
				List<Cookie> cookies = cookieStore.getCookies();
				if(!cookies.isEmpty()){
					for(Cookie cookie : cookies){
						if(cookie.getName().equals("_tb_token_")){
							token = cookie.getValue();
							System.out.println(token);
						}
					}
				}
				
			}finally{
				response.close();
			}
			
			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI(url_loginaction))
					.addParameter("_tb_token_",token)
					.addParameter("style", "")
					.addParameter("redirect", "")
					.addParameter("proxy", url_proxy)
					.addParameter("logname", _username)
					.addParameter("originalLogpasswd", _password)
					.addParameter("logpasswd", _passwordEncrypt)
					.build();
			
			response = httpclient.execute(login);
			try{
				List<Cookie> cookies = cookieStore.getCookies();
				cookiesret = new Cookie[cookies.size()];
				int index = 0;
				boolean isSucceed = false;
				if(!cookies.isEmpty()){
					for(Cookie cookie : cookies){
						cookiesret[index] = cookie;
						if(cookie.getName().equals("alimamapw")){
							token = cookie.getValue();
							isSucceed = true;
							System.out.println(token);
						}
						index++;
					}
				}
				
			}finally{
				response.close();
			}
			
		}finally{
			httpclient.close();
		}
	}
	
	/*
	 * main method
	 */
	public static void main(String[] args){
		try{

		}catch(Exception e){
			
		}
	}
}
