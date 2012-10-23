package com.eatj.igorribeirolima.fuzzylogic.model.service.to;

import java.io.Serializable;
import java.util.List;

import com.eatj.igorribeirolima.util.dadosinfomoney.DadosDeEntradaModelagem;
import com.eatj.igorribeirolima.util.dadosinfomoney.dado.DadoAnaliseTecnica;

public class IndicadoresTecnicoTO implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private List<DadoAnaliseTecnica> indicadoresTecnicos;
  private DadosDeEntradaModelagemTO dadosDeEntradaModelagemTO;
  
  public IndicadoresTecnicoTO(){
    this( null, null );
  }
  
  public IndicadoresTecnicoTO( List<DadoAnaliseTecnica> indicadoresTecnicos, DadosDeEntradaModelagem dadosDeEntradaModelagem ){
    this.indicadoresTecnicos = indicadoresTecnicos;
    this.dadosDeEntradaModelagemTO = new DadosDeEntradaModelagemTO(dadosDeEntradaModelagem);
  }

  public List<DadoAnaliseTecnica> getIndicadoresTecnicos() {
    return indicadoresTecnicos;
  }

  public DadosDeEntradaModelagemTO getDadosDeEntradaModelagem() {
    return dadosDeEntradaModelagemTO;
  }

}
