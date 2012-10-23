package com.eatj.igorribeirolima.fuzzylogic.model.service.bo;

import com.eatj.igorribeirolima.util.FileHelper;
import com.google.gson.Gson;

public class MockBO {
	private static String rootPathExample = "\\examples\\";
	private static String rootPathMock = "\\mock\\";

	public static Object getMockTest( String type, String strExample ) {
		return getMock( rootPathMock, type, strExample );
	}
	
	public static Object getExample( String type, String strExample ) {
		return getMock( rootPathExample, type, strExample );
	}
	
	private static Object getMock( String rootPath, String type, String strExample ) {
		Gson gson = new Gson();
		try {
			Object obj = null;
			
			try{
				obj = gson.fromJson( FileHelper.readFileInThisApplication( rootPath + strExample ), Class.forName( type ) );
			}catch (ClassNotFoundException classNotFoundException) {
				System.err.println( "Path: " + rootPath + strExample );
				classNotFoundException.printStackTrace();
				return null;
			}
			
			return obj;

		} catch ( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}
	
}
