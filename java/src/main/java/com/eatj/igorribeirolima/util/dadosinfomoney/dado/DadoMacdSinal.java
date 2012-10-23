package com.eatj.igorribeirolima.util.dadosinfomoney.dado;


class DadoMacdSinal extends DadoAbstract{

  public DadoMacdSinal(String csv) throws Exception {
    super(csv);
  }

  public void setValor( String csv ) {
    this.valor = Double.parseDouble( getNumeroReal(csv,4) );
  }
  
  public void setDadoAnaliseTecnica( DadoAnaliseTecnica dadoAnaliseTecnica ){
    dadoAnaliseTecnica.setMacdsinal( getValor() );
  }
  
}
