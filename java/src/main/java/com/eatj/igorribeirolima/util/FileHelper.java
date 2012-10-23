package com.eatj.igorribeirolima.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ObjectAndMessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.service.to.ArvoreArquivo;

public abstract class FileHelper {

	public static String readFile( Resource resource ) throws FileNotFoundException, IOException{
		if ( !resource.exists() ) throw new FileNotFoundException();
		System.out.println( "Lendo arquivo da aplicação. Path: " + resource.getFile().getCanonicalPath() );
		
		BufferedReader reader = null;
		String strContentFile = "";
		try{ 
			InputStream in = resource.getInputStream();
			reader = new BufferedReader( new InputStreamReader( in ) );
			while ( reader.ready() ){
				strContentFile += reader.readLine() + "\n";
			}
		}finally{
			reader.close();
		}
		
		return strContentFile;
	}
	
	public static String readFileInThisApplication( String pathFile ) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext();
		Resource resource = appContext.getResource( "classpath:" + pathFile );
		try {
			return readFile(resource);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println();
			System.out.println( ">>> Arquivo '" + resource.getFilename() + "' não encontrado. <<<" );
			return null;
		}
	}

	public static String readFileInComputer( String pathFile ) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext();
		Resource resource = appContext.getResource( "file:" + pathFile );
		try {
			return readFile(resource);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println();
			System.out.println( ">>> Arquivo '" + resource.getFilename() + "' não encontrado. <<<" );
			return null;
		}
	}
	
	private static File getFile( String pathFile ){
		ApplicationContext appContext = new ClassPathXmlApplicationContext();
		Resource resource = appContext.getResource("file:" + pathFile);
		File file;
		try {
			file = resource.getFile();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ReturnTO getContentFile( String pathFile ) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext();
		Resource resource = appContext.getResource( "file:" + pathFile );
		try {
			return new ObjectAndMessageReturnTO<String>( readFile(resource) );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new MessageReturnTO( ReturnTO.Status.ERROR, "Problema ao ler o arquivo." );
	}

	public static ReturnTO writeFile( String pathFile, String strContentFile ) {

		try {
			File file = getFile(pathFile);
			if ( !file.exists() )
					file.createNewFile();

			FileWriter fileWriter = new FileWriter( file );
			fileWriter.write( strContentFile );
			fileWriter.close();
			return new MessageReturnTO();
		} catch ( IOException e ) {
			System.err.println( "Path: " + pathFile );
			e.printStackTrace();
			return new MessageReturnTO( ReturnTO.Status.ERROR, e.getMessage() );
		} catch ( Exception e ) {
			System.err.println( "Path: " + pathFile );
			e.printStackTrace();
			return new MessageReturnTO( ReturnTO.Status.ERROR, e.getMessage() );
		}
		
	}
	
	public static ArvoreArquivo criar_arvore_de_diretorio_e_arquivo(String path) {
		try {
			File file = getFile(path);
			ArvoreArquivo arvoreArquivo = new ArvoreArquivo();
			arvoreArquivo.setName( file.getName() );
			arvoreArquivo.setPath( file.getPath() );
			criar_arvore_de_diretorio_e_arquivo( file, arvoreArquivo);
			
			return arvoreArquivo;
		} catch (Exception e) {
		  e.printStackTrace();
			return null;
		}

	}
	
	private static void criar_arvore_de_diretorio_e_arquivo( File file, ArvoreArquivo arvoreArquivo ) {
		if ( file.isDirectory()) {
			for( File f : file.listFiles() ){
				ArvoreArquivo newChild = new ArvoreArquivo();
				newChild.setName( f.getName() );
				newChild.setPath( f.getPath() );
				criar_arvore_de_diretorio_e_arquivo(f, newChild);
				arvoreArquivo.addChildren(newChild);
			}
		} else {
			arvoreArquivo.setName( file.getName() );
			arvoreArquivo.setPath( file.getPath() );
		}
		
		if( !CollectionUtils.isEmpty(arvoreArquivo.getChildren()) )
		  Collections.sort( arvoreArquivo.getChildren() );
		
	}
	
}
