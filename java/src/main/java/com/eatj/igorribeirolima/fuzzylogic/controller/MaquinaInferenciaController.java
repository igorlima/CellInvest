package com.eatj.igorribeirolima.fuzzylogic.controller;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ObjectAndMessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO.Status;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.MaquinaInferencia;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.MaquinaInferenciaBO;

@Named
@RequestMapping( value = "/maquinaInferencia/**" )
public class MaquinaInferenciaController {

	@Inject
	private MaquinaInferenciaBO bo;

	@RequestMapping( value = "/maquinaInferencia/save", method = RequestMethod.POST )
	public ReturnTO saveOrUpdate( @RequestBody MaquinaInferencia maquinaInferencia ) {
		try{
			return bo.saveOrUpdate( maquinaInferencia );
		}catch( Exception exception ){
			String messageException = "\nException: " + exception.getMessage() + ". ";
			try{ messageException += "\nCause: " + exception.getCause().getMessage() + ". ";
			}catch( Exception e ){ }
			return new MessageReturnTO( Status.ERROR, "Não foi possível salvar o componente. " + messageException );
		}
	}

	@RequestMapping( value = "/maquinaInferencia/{id}", method = RequestMethod.GET )
	public ReturnTO findById( @PathVariable Long id ) {
		return new ObjectAndMessageReturnTO<MaquinaInferencia>( bo.retrieveById( id ) );
	}
	
	@RequestMapping( value = "/maquinaInferencia/nome/{nome}", method = RequestMethod.GET )
  public ReturnTO findByName( @PathVariable String nome ) {
    return new ObjectAndMessageReturnTO<MaquinaInferencia>( bo.retrieve( nome ) );
  }
	
	@RequestMapping( value = "/maquinaInferencia/all", method = RequestMethod.GET )
	public ReturnTO find() {
		return new ObjectAndMessageReturnTO<Set<MaquinaInferencia>>( bo.retrieve() );
	}

}
