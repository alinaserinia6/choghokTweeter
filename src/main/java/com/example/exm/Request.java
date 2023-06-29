package com.example.exm;

import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {
	RM method;
	Object p, q, r;

	public Request(RM method) {
		this.method = method;
	}

	public Request(RM method, Object p) {
		this.method = method;
		this.p = p;
	}

	public Request(RM method, Object p, Object q) {
		this.method = method;
		this.p = p;
		this.q = q;
	}

	public Request(RM method, Object p, Object q, Object r) {
		this.method = method;
		this.p = p;
		this.q = q;
		this.r = r;
	}

	public RM getMethod() {
		return method;
	}

	public Object get1() {
		return p;
	}

	public Object get2() {
		return q;
	}

	public Object get3() {
		return r;
	}

}
