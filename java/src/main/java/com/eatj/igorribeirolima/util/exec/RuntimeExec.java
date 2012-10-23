package com.eatj.igorribeirolima.util.exec;

import java.io.Closeable;

class RuntimeExec {
	
	public static enum Type{
		ERROR,
		OUTPUT,
		INPUT
	}
	
	public StreamWrapper getStreamWrapper(Closeable closeable, Type type) {
		return getStreamWrapper(closeable, type, null);
	}
	
	public StreamWrapper getStreamWrapper(Closeable closeable, Type type, String strCommand ) {
		
		if( Type.ERROR.equals( type ) )
			return new StreamWrapperError(closeable, type);
		else if( Type.OUTPUT.equals( type ) )
			return new StreamWrapperInput(closeable, type);
		else if( Type.INPUT.equals( type ) )
			return new StreamWrapperOutput(closeable, type, strCommand );
			
		return null;
	}

}
