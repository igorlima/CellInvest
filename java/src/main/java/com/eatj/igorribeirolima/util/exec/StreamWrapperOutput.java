package com.eatj.igorribeirolima.util.exec;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

import com.eatj.igorribeirolima.util.exec.RuntimeExec.Type;

class StreamWrapperOutput extends StreamWrapper {

	private String strCommand;
	
	StreamWrapperOutput( Closeable closeable, Type type) {
		super(closeable,type);
	}
	
	StreamWrapperOutput( Closeable closeable, Type type, String strCommand) {
		super(closeable,type);
		this.strCommand = strCommand;
	}
	
	@Override
	public void run() {
		try {
			if( this.strCommand == null ) return;
			
			OutputStream os = (OutputStream) closeable;
			os.write( this.strCommand.getBytes() );
			os.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
}
