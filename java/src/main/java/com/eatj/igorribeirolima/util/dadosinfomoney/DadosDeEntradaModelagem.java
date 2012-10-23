package com.eatj.igorribeirolima.util.dadosinfomoney;

import java.util.ArrayList;
import java.util.List;

import com.eatj.igorribeirolima.util.dadosinfomoney.dado.AnaliseTecnica;
import com.eatj.igorribeirolima.util.dadosinfomoney.dado.Dado;


public class DadosDeEntradaModelagem {
  
  private NormalizarIndicadores normalizacao;
  
  public DadosDeEntradaModelagem( DadosInfoMoney dados ){
    
    List<Double> ifr = dados2double( dados.get(AnaliseTecnica.IFR));
    List<Double> estocastico = dados2double( dados.get(AnaliseTecnica.ESTOCASTICO));
    List<Double> macdline = dados2double( dados.get(AnaliseTecnica.MACD_LINE));
    List<Double> macdsinal = dados2double( dados.get(AnaliseTecnica.MACD_SINAL));
    List<Double> histograma = dados2double( dados.get(AnaliseTecnica.HISTOGRAMA));
    List<Double> obv = dados2double( dados.get(AnaliseTecnica.OBV));
    normalizacao = new NormalizarIndicadores( ifr, estocastico, macdline, macdsinal, histograma, obv);
    
  }
  
  public Double ifr(){
    return normalizacao.ifr();
  }
  
  public Double ifr_normalizado(){
    return normalizacao.ifr_normalizado();
  }
  
  public Double estocastico(){
    return normalizacao.estocastico();
  }
  
  public Double estocastico_normalizado(){
    return normalizacao.estocastico_normalizado();
  }
  
  public Double distancia_do_valor_macd_ao_eixo_zero(){
    return normalizacao.distancia_do_valor_macd_ao_eixo_zero();
  }
  
  public Double valor_linha_macd(){
    return normalizacao.valor_linha_macd();
  }
  
  public Double distancia_do_valor_de_sinal_ao_eixo_zero(){
    return normalizacao.distancia_do_valor_de_sinal_ao_eixo_zero();
  }
  
  public Double valor_linha_sinal(){
    return normalizacao.valor_linha_sinal();
  }
  
  public Double angulo_linha_macd(){
    return normalizacao.angulo_linha_macd();
  }
  
  public Double angulo_linha_sinal(){
    return normalizacao.angulo_linha_sinal();
  }
  
  /**
   * Retorna a distancia do histograma em relacao ao eixo 0, num intervalo de [0, 1]
   * @return
   */
  public Double histograma(){
    return normalizacao.histograma();
  }
  
  public Double angulo_histograma(){
    return normalizacao.angulo_histograma();
  }
  
  public Double angulo_obv(){
    return normalizacao.angulo_obv();
  }
  
  /**
   * Converte um lista do tipo 'Dado' para um lista de double
   * @param dados
   * @return
   */
  private List<Double> dados2double( List<Dado> dados ){
    List<Double> numeros = new ArrayList<Double>();
    for( Dado dado : dados ) numeros.add(dado.getValor());
    
    return numeros;
  }
  
}
