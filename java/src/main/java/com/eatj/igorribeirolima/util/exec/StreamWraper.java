package com.eatj.igorribeirolima.util.exec;

import java.io.Closeable;

import com.eatj.igorribeirolima.util.exec.RuntimeExec.Type;

abstract class StreamWrapper extends Thread {

	Type type = null;
	String message = null;
	Closeable closeable = null;

	public String getMessage() {
		return message;
	}

	StreamWrapper( Closeable closeable, Type type) {
		this.closeable = closeable;
		this.type = type;
	}

}
