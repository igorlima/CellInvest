package com.eatj.igorribeirolima.util.dadosinfomoney.dado;

import java.util.Date;


public interface Dado extends Comparable<Dado> {
  
  Date getDate();
  Double getValor();
  void setDadoAnaliseTecnica( DadoAnaliseTecnica dadoAnaliseTecnica );
  
}
