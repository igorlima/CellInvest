package com.eatj.igorribeirolima.util;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.util.exec.EXEC;


public class SCILAB {
	public static final String COMMAND_RNA;
	public static final String OPEN_SCILAB;
	public static final String PATH_OUTPUT_SCILAB;
	public static final String PATH_OUTPUT_RNA;
	public static final String PATH_INPUT_RNA;
	private static final String EXIT_SCILAB;
	
	static{
		COMMAND_RNA = SCILAB_PROPERTIES.getString("scilab.path")
				+ SCILAB_PROPERTIES.getString("scilab.command")
				+ "\""
				+ SCILAB_PROPERTIES.getString("scilab.path_rna")
				+ SCILAB_PROPERTIES.getString("scilab.file_sce")
				+ "\"";
		
		OPEN_SCILAB = SCILAB_PROPERTIES.getString("scilab.path") + SCILAB_PROPERTIES.getString("scilab.command");
		EXIT_SCILAB = "\nexit\n";
		PATH_OUTPUT_SCILAB = SCILAB_PROPERTIES.getString("scilab.path_rna") + SCILAB_PROPERTIES.getString("scilab.file_output_scilab");
		PATH_OUTPUT_RNA = SCILAB_PROPERTIES.getString("scilab.path_rna") + SCILAB_PROPERTIES.getString("scilab.file_output_rna");
		PATH_INPUT_RNA = SCILAB_PROPERTIES.getString("scilab.path_rna") + SCILAB_PROPERTIES.getString("scilab.file_input_rna");
	}
	
	public synchronized static ReturnTO execute( String strCommandScilab ){
		return execute( null, strCommandScilab );
	}
	
	public synchronized static ReturnTO execute( String pathFile, String ... commands ){
	  String strCommandScilab = "";
	  for( int i=0; i<commands.length; i++ )
	    strCommandScilab += commands[i] + "\n";
	  
		return EXEC.exec( OPEN_SCILAB + pathFile, strCommandScilab+EXIT_SCILAB );
	}
	
	public synchronized static boolean execute(){
		ReturnTO returnTO = EXEC.exec( COMMAND_RNA );
		
		if( !returnTO.isSuccessful() )
			return false;
		
		String message = ((MessageReturnTO) returnTO).getMessage();
		returnTO = FileHelper.writeFile( PATH_OUTPUT_SCILAB, message );
		
		
		if( returnTO.isSuccessful() ) return true;
		else return false;
		
	}
	
	public synchronized static String getOutputData(){
		System.out.println( "Getting output data..." );
		String strOutputData = FileHelper.readFileInComputer( PATH_OUTPUT_RNA );
		return strOutputData;
	}
	
	public synchronized static String getInputData(){
		System.out.println( "Getting input data..." );
		String strOutputData = FileHelper.readFileInComputer( PATH_INPUT_RNA );
		return strOutputData;
	}
}
