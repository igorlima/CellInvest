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

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.UnivConjFuzzyBO;

@Named
@RequestMapping( value = "/universoFuzzy/**" )
public class UnivFuzzyController {

	@Inject
	private UnivConjFuzzyBO bo;

	@RequestMapping( value = "/universoFuzzy/save", method = RequestMethod.POST )
	public ReturnTO saveOrUpdate( @RequestBody UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy ) {
		try{
			return bo.saveOrUpdate( universoDeDiscursoFuzzy );
		}catch( Exception exception ){
			String messageException = "\nException: " + exception.getMessage() + ". ";
			try{ messageException += "\nCause: " + exception.getCause().getMessage() + ". ";
			}catch( Exception e ){ }
			return new MessageReturnTO( Status.ERROR, "Não foi possível salvar o componente. " + messageException );
		}
	}

	@RequestMapping( value = "/universoFuzzy/{id}", method = RequestMethod.GET )
	public ReturnTO findById( @PathVariable Long id ) {
		return new ObjectAndMessageReturnTO<UniversoDeDiscursoFuzzy>( bo.retrieveById( id ) );
	}
	
	@RequestMapping( value = "/universoFuzzy/nome/{nome}", method = RequestMethod.GET )
	public ReturnTO find( @PathVariable String nome ) {
		return new ObjectAndMessageReturnTO<UniversoDeDiscursoFuzzy>( bo.retrieve( nome ) );
	}
	
	@RequestMapping( value = "/universoFuzzy/{nome}/dadosgrafico", method = RequestMethod.GET )
	public ReturnTO getConjuntoFuzzySaida( @PathVariable String nome ) throws Exception {
		Object dadosGrafico = bo.getDadosGrafico(nome);
		
		if( dadosGrafico == null ){
			return new MessageReturnTO( Status.ERROR, "O universo fuzzy '"+nome+"' não existe." );
		}else{
			return new ObjectAndMessageReturnTO<Object>( dadosGrafico );
		}
	}

	@RequestMapping( value = "/universoFuzzy/all", method = RequestMethod.GET )
	public ReturnTO find() {
		return new ObjectAndMessageReturnTO<Set<UniversoDeDiscursoFuzzy>>( bo.retrieve() );
	}
	
	@RequestMapping( value = "/universoFuzzy/{nome}/conjuntoFuzzySaida", method = RequestMethod.POST )
	public ReturnTO calcular( @PathVariable String nome, @RequestParam("input") Double dadoDeEntrada ) {
		return bo.calcular(nome, dadoDeEntrada);
	}
	
}
