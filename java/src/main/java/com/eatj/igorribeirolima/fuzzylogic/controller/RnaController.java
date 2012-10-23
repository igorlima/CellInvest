package com.eatj.igorribeirolima.fuzzylogic.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.RnaBO;

@Named
@RequestMapping( value = "/rna/**" )
public class RnaController {

	@Inject
	private RnaBO bo;

	@RequestMapping( value = "/visualizacao/arquivo", method = RequestMethod.POST )
	public ReturnTO visualizarArquivo( @RequestParam("path") String path ) {
		return bo.visualizarArquivo(path);
	}
	
	@RequestMapping( value = "/edicao/arquivo", method = RequestMethod.POST )
	public ReturnTO editarArquivo( @RequestParam("path") String path, @RequestParam("content") String content ) {
		return bo.editarArquivo(path, content);
	}
	
	@RequestMapping( value = "/execucao/arquivo", method = RequestMethod.POST )
	public ReturnTO executarArquivo( @RequestParam("pathFile") String pathFile, @RequestParam("strCommandScilab") String strCommandScilab ) {
		return bo.executarArquivo(pathFile, strCommandScilab);
	}
	
	@RequestMapping( value = "/listadediretorios", method = RequestMethod.GET )
	public ReturnTO listarArvoreDeArquivos() {
		return bo.listarArvoreDeArquivos();
	}
}
