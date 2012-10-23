package com.eatj.igorribeirolima.util.dadosinfomoney.dado;


class DadoEstocastico extends DadoAbstract{

  public DadoEstocastico(String csv) throws Exception {
    super(csv);
  }

  public void setValor( String csv ) {
    this.valor = Double.parseDouble( getNumeroReal(csv,3) );
  }
  
  public void setDadoAnaliseTecnica( DadoAnaliseTecnica dadoAnaliseTecnica ){
    dadoAnaliseTecnica.setEstocastico( getValor() );
  }
  
}
