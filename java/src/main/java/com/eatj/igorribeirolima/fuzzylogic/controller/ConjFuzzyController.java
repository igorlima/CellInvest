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

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.ConjFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.ConjFuzzyBO;

@Named
@RequestMapping( value = "/conjuntoFuzzy/**" )
public class ConjFuzzyController {

	@Inject
	private ConjFuzzyBO bo;

	@RequestMapping( value = "/conjuntoFuzzy/save", method = RequestMethod.POST )
	public ReturnTO saveOrUpdate( @RequestBody ConjFuzzy conjFuzzy ) {
		return bo.saveOrUpdate( conjFuzzy );
	}

	@RequestMapping( value = "/conjuntoFuzzy/{id}", method = RequestMethod.GET )
	public ReturnTO findById( @PathVariable Long id ) {
		return new ObjectAndMessageReturnTO<ConjFuzzy>( bo.retrieveById( id ) );
	}
	
	@RequestMapping( value = "/conjuntoFuzzy/nome/{nome}", method = RequestMethod.GET )
  public ReturnTO findByName( @PathVariable String nome ) {
    return new ObjectAndMessageReturnTO<ConjFuzzy>( bo.retrieve( nome ) );
  }
	
	@RequestMapping( value = "/conjuntoFuzzy/all", method = RequestMethod.GET )
	public ReturnTO find() {
		return new ObjectAndMessageReturnTO<Set<ConjFuzzy>>( bo.retrieve() );
	}

}
