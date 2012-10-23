package test.com.eatj.igorribeirolima.util;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.eatj.igorribeirolima.util.FileHelper;
import com.eatj.igorribeirolima.util.SCILAB;
import com.eatj.igorribeirolima.util.SCILAB_PROPERTIES;

public class ScilabTest {
	private static String path_output_file;
	
	@BeforeClass
	public static void initPathOutputFile(){
		path_output_file = SCILAB_PROPERTIES.getString("scilab.path_rna");
		path_output_file += SCILAB_PROPERTIES.getString("scilab.file_output_scilab");
	}
	
	@Test
	public void executingScilab(){
		SCILAB.execute();
		String output_scilab = FileHelper.readFileInComputer( path_output_file );
		assertTrue( output_scilab.contains( "Rna executada com sucesso" ) );
	}
	
}
