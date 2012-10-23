package com.eatj.igorribeirolima.util.dadosinfomoney.dado;


class DadoPrice extends DadoAbstract{

  public DadoPrice(String csv) throws Exception {
    super(csv);
  }

  public void setValor( String csv ) {
    this.valor = Double.parseDouble( getNumeroReal(csv,2) );
  }
  
  public void setDadoAnaliseTecnica( DadoAnaliseTecnica dadoAnaliseTecnica ){
    dadoAnaliseTecnica.setPrice( getValor() );
  }
  
}
