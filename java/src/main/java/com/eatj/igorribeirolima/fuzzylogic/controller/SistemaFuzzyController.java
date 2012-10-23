package com.eatj.igorribeirolima.fuzzylogic.controller;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ObjectAndMessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO.Status;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.SistemaFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.SistemaFuzzyBO;

@Named
@RequestMapping( value = "/sistemaFuzzy/**" )
public class SistemaFuzzyController {

	@Inject
	private SistemaFuzzyBO bo;

	@RequestMapping( value = "/sistemaFuzzy/save", method = RequestMethod.POST )
	public ReturnTO saveOrUpdate( @RequestBody SistemaFuzzy sistemaFuzzy ) {
		try{
			return bo.saveOrUpdate( sistemaFuzzy );
		}catch( Exception exception ){
			String messageException = "\nException: " + exception.getMessage() + ". ";
			try{ messageException += "\nCause: " + exception.getCause().getMessage() + ". ";
			}catch( Exception e ){ }
			return new MessageReturnTO( Status.ERROR, "Não foi possível salvar o componente. " + messageException );
		}
	}

	@RequestMapping( value = "/sistemaFuzzy/{id}", method = RequestMethod.GET )
	public ReturnTO findById( @PathVariable Long id ) {
		return new ObjectAndMessageReturnTO<SistemaFuzzy>( bo.retrieveById( id ) );
	}
	
	@RequestMapping( value = "/sistemaFuzzy/nome/{nome}", method = RequestMethod.GET )
  public ReturnTO findByName( @PathVariable String nome ) {
    return new ObjectAndMessageReturnTO<SistemaFuzzy>( bo.retrieve( nome ) );
  }
	
	@RequestMapping( value = "/sistemaFuzzy/all", method = RequestMethod.GET )
	public ReturnTO find() {
		return new ObjectAndMessageReturnTO<Set<SistemaFuzzy>>( bo.retrieve() );
	}
	
	@RequestMapping( value = "/sistemaFuzzy/{nome}/conjuntoFuzzySaida", method = RequestMethod.POST )
	public ReturnTO calcular( @PathVariable String nome, @RequestParam("input") String input ) {
		return bo.calcular(nome, input);
	}

}
