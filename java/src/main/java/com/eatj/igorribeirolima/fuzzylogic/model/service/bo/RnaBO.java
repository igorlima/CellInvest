package com.eatj.igorribeirolima.fuzzylogic.model.service.bo;

import javax.inject.Named;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ObjectAndMessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.service.to.ArvoreArquivo;
import com.eatj.igorribeirolima.util.FileHelper;
import com.eatj.igorribeirolima.util.SCILAB;
import com.eatj.igorribeirolima.util.SCILAB_PROPERTIES;

@Named
public class RnaBO {
	
	public ReturnTO visualizarArquivo( String path ){
		return FileHelper.getContentFile(path);
	}
	
	public ReturnTO editarArquivo( String path, String content ){
		return FileHelper.writeFile(path, content);
	}
	
	public ReturnTO executarArquivo( String pathFile, String strCommandScilab ){
		return SCILAB.execute( pathFile, strCommandScilab );
	}
	
	public ReturnTO listarArvoreDeArquivos(){
		String root_rna = SCILAB_PROPERTIES.getString("scilab.root_rna");
		ArvoreArquivo arvoreArquivo = FileHelper.criar_arvore_de_diretorio_e_arquivo( root_rna );
		
		if( arvoreArquivo == null )
			return new MessageReturnTO( ReturnTO.Status.ERROR, "Não foi possível criar uma lista de diretórios" );
		else
			return new ObjectAndMessageReturnTO<ArvoreArquivo>( arvoreArquivo );
		
	}
	
}
