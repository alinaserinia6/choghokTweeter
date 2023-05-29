package com.example.exm;

public class Request {
	String method;
	Object p, q, r;

	public Request(String method, Object p) {
		this.method = method;
		this.p = p;
	}

	public Request(String method, Object p, Object q) {
		this.method = method;
		this.p = p;
		this.q = q;
	}

	public Request(String method, Object p, Object q, Object r) {
		this.method = method;
		this.p = p;
		this.q = q;
		this.r = r;
	}
}
