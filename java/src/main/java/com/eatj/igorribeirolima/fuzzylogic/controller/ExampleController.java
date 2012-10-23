package com.eatj.igorribeirolima.fuzzylogic.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.ExampleBO;

@Named
@RequestMapping( value = "/example/**" )
public class ExampleController {

	@Inject
	private ExampleBO bo;

	@RequestMapping( value = "/example/all", method = RequestMethod.GET )
	public ReturnTO findById() {
		return bo.listAll();
	}

	@RequestMapping( value = "/example/type/{type}/name/{name}", method = RequestMethod.GET )
	public ReturnTO getAnyJsonExample( @PathVariable String type, @PathVariable String name ) {
		
		return bo.getJsonExample( type, name + ".json" );
		
	}
}
