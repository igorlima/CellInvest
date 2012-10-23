package com.eatj.igorribeirolima.util.dadosinfomoney.dado;


class DadoMacdLine extends DadoAbstract{

  public DadoMacdLine(String csv) throws Exception {
    super(csv);
  }

  public void setValor( String csv ) {
    this.valor = Double.parseDouble( getNumeroReal(csv,3) );
  }
  
  public void setDadoAnaliseTecnica( DadoAnaliseTecnica dadoAnaliseTecnica ){
    dadoAnaliseTecnica.setMacdline( getValor() );
  }
  
}
