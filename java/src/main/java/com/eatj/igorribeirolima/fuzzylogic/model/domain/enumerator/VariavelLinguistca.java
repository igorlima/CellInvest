package com.eatj.igorribeirolima.fuzzylogic.model.domain.enumerator;

public enum VariavelLinguistca {
  IFR,
  Estocastico,
  MACD,
  Histograma,
  AlphaOBV,
  Momento,
  Sinal,
  Volume,
  Indicador;
  
  /**
   * Método que converte o enum em uma string
   * @return
   */
  public String nm(){
    return this.toString();
  }
}
