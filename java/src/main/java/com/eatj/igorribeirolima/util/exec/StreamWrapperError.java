package com.eatj.igorribeirolima.util.exec;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.eatj.igorribeirolima.util.exec.RuntimeExec.Type;

class StreamWrapperError extends StreamWrapper {

	StreamWrapperError( Closeable closeable, Type type) {
		super(closeable,type);
	}
	
	@Override
	public void run() {
		try {
			InputStream is = (InputStream) closeable;
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line);// .append("\n");
				buffer.append('\n');
			}
			message = buffer.toString();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
}
