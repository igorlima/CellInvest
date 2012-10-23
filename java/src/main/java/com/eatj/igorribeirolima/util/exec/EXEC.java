package com.eatj.igorribeirolima.util.exec;

import java.io.IOException;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.util.SCILAB_PROPERTIES;
import com.eatj.igorribeirolima.util.exec.RuntimeExec.Type;


public class EXEC{
	private static final int EXIT_WITH_SUCCESS = 0;
	private static EXEC _exec = new EXEC();
	
	public static synchronized ReturnTO exec( String[] strCommand, String strInputCommand ){
		return _exec.execute(strCommand, strInputCommand);
	}
	
	public static synchronized ReturnTO exec( String[] strCommand ){
		return _exec.execute(strCommand, null);
	}
	
	public static synchronized ReturnTO exec( String strCommand, String strInputCommand ){
		return _exec.execute(strCommand, strInputCommand);
	}
	
	public static synchronized ReturnTO exec( String strCommand ){
		return _exec.execute(strCommand, null);
	}
	
	private ReturnTO execute( String strCommand, String strInputCommand ){
	  
		String[] cmdarray = { SCILAB_PROPERTIES.getString("operationsystem.shellscript"), SCILAB_PROPERTIES.getString("operationsystem.args_shellscript"), strCommand };
		return execute(cmdarray, strInputCommand);
	}
	
	private ReturnTO execute( String[] cmdarray, String strInputCommand ){
		ReturnTO returnTO;
		
		Runtime rt = Runtime.getRuntime();
		RuntimeExec rte = new RuntimeExec();
		StreamWrapper error, output, input;
		
		try {
			Process proc = rt.exec( cmdarray );
			
			error = rte.getStreamWrapper(proc.getErrorStream(), Type.ERROR);
			output = rte.getStreamWrapper(proc.getInputStream(), Type.OUTPUT);
			input = rte.getStreamWrapper(proc.getOutputStream(), Type.INPUT, strInputCommand );
			
			output.start();
			input.start();
			error.start();
			
			int exitVal = proc.waitFor();
			proc.destroy();
			
			returnTO = exitVal == EXIT_WITH_SUCCESS ? new MessageReturnTO( ReturnTO.Status.SUCCESS, output.message + "\n" + error.message ) : new MessageReturnTO( ReturnTO.Status.ERROR, error.message ); 
		} catch (IOException e) {
			e.printStackTrace();
			returnTO = new MessageReturnTO( ReturnTO.Status.ERROR, e.getMessage() );
		} catch (InterruptedException e) {
			e.printStackTrace();
			returnTO = new MessageReturnTO( ReturnTO.Status.ERROR, e.getMessage() );
		} catch (NullPointerException e) {
			e.printStackTrace();
			returnTO = new MessageReturnTO( ReturnTO.Status.ERROR, e.getMessage() );
		}
		
		return returnTO;
	}
		
}
