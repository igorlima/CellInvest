package com.eatj.igorribeirolima.fuzzylogic.controller;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufla.lemaf.commons.model.service.to.ObjectAndMessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Funcao;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.FuncaoBO;

@Named
@RequestMapping( value = "/funcao/**" )
public class FuncaoController {

	@Inject
	private FuncaoBO bo;

	@RequestMapping( value = "/funcao/save", method = RequestMethod.POST )
	public ReturnTO saveOrUpdate( @RequestBody Funcao funcao ) {
		return bo.saveOrUpdate( funcao );
	}

	@RequestMapping( value = "/funcao/{id}", method = RequestMethod.GET )
	public ReturnTO findById( @PathVariable Long id ) {
		return new ObjectAndMessageReturnTO<Funcao>( bo.retrieveById( id ) );
	}
	
	@RequestMapping( value = "/funcao/all", method = RequestMethod.GET )
	public ReturnTO find() {
		return new ObjectAndMessageReturnTO<Set<Funcao>>( bo.retrieve() );
	}

}
