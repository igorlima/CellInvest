package com.eatj.igorribeirolima.fuzzylogic.model.domain.enumerator;

public enum Fuzzy {
  MuitoAlto,
  Alto,
  Neutro,
  Baixo,
  MuitoBaixo;
  
  /**
   * Método que converte o enum em uma string
   * @return
   */
  public String nm(){
    return this.toString();
  }

}


