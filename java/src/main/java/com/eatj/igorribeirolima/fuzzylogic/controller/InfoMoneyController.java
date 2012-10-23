package com.eatj.igorribeirolima.fuzzylogic.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.InfoMoneyBO;

@Named
@RequestMapping( value = "/analisetecnica/**" )
public class InfoMoneyController {
  
  @Inject
  private InfoMoneyBO bo;
  
	@RequestMapping( value = "/dados/{strAtivo}", method = RequestMethod.GET )
	public ReturnTO getDadosInfoMoney( @PathVariable String strAtivo ) {
	  try{
	    return bo.getDadosInfoMoney(strAtivo);
	  }catch (Exception e) {
      return new MessageReturnTO( ReturnTO.Status.ERROR, e.getMessage() );
    }
	}
	
}
