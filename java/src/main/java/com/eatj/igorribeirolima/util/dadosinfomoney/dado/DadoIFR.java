package com.eatj.igorribeirolima.util.dadosinfomoney.dado;


class DadoIFR extends DadoAbstract{

  public DadoIFR(String csv) throws Exception {
    super(csv);
  }

  public void setValor( String csv ) {
    this.valor = Double.parseDouble( getNumeroReal(csv,3) );
  }
  
  public void setDadoAnaliseTecnica( DadoAnaliseTecnica dadoAnaliseTecnica ){
    dadoAnaliseTecnica.setIfr( getValor() );
  }
  
}
