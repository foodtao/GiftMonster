package com.li5tao.data.collect.alimama;

import org.apache.http.cookie.Cookie;

/*
 * ץȡ����ʱ�Ự��Ϣ����ʵ��
 */
public class SessionInfo {
	/*
	 * ��¼�ɹ���õ���cookie��
	 */
	private Cookie[] _cookies;
	
	/*
	 * ץȡ��Ϣʱ���õ�token
	 */
	private String _token = "";
	
	/*
	 * ץȡ��Ϣʱ���õ�t����
	 */
	private String _t = "";
	
	public Cookie[] get_cookies() {
		return _cookies;
	}

	public void set_cookies(Cookie[] _cookies) {
		this._cookies = _cookies;
	}

	public String get_token() {
		return _token;
	}

	public void set_token(String _token) {
		this._token = _token;
	}
	
	public String get_t() {
		return _t;
	}

	public void set_t(String _t) {
		this._t = _t;
	}

	
}