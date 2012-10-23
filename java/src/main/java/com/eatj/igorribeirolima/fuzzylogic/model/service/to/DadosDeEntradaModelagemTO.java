package com.eatj.igorribeirolima.fuzzylogic.model.service.to;

import java.io.Serializable;

import com.eatj.igorribeirolima.util.dadosinfomoney.DadosDeEntradaModelagem;

public class DadosDeEntradaModelagemTO implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Double ifr;
  private Double ifrNormalizado;
  private Double estocastico;
  private Double estocasticoNormalizado;
  private Double distanciaMacdAoEixoZero;
  private Double distanciaSinalAoEixoZero;
  private Double linhaMacd;
  private Double linhaSinal;
  private Double anguloMacd;
  private Double anguloSinal;
  private Double histograma;
  private Double anguloHistograma;
  private Double anguloObv;

  public DadosDeEntradaModelagemTO( DadosDeEntradaModelagem dados ){
    if( dados == null ) return;
    
    this.ifr = dados.ifr();
    this.ifrNormalizado = dados.ifr_normalizado();
    this.estocastico = dados.estocastico();
    this.estocasticoNormalizado = dados.estocastico_normalizado();
    this.distanciaMacdAoEixoZero = dados.distancia_do_valor_macd_ao_eixo_zero();
    this.distanciaSinalAoEixoZero = dados.distancia_do_valor_de_sinal_ao_eixo_zero();
    this.linhaMacd = dados.valor_linha_macd();
    this.linhaSinal = dados.valor_linha_sinal();
    this.anguloMacd = dados.angulo_linha_macd();
    this.anguloSinal = dados.angulo_linha_sinal();
    this.histograma = dados.histograma();
    this.anguloHistograma = dados.angulo_histograma();
    this.anguloObv = dados.angulo_obv();
  }

  public Double getIfr() {
    return ifr;
  }
  
  public Double getIfrNormalizado() {
    return ifrNormalizado;
  }

  public Double getEstocastico() {
    return estocastico;
  }
  
  public Double getEstocasticoNormalizado() {
    return estocasticoNormalizado;
  }
  
  public Double getDistanciaMacdAoEixoZero() {
    return distanciaMacdAoEixoZero;
  }

  public Double getDistanciaSinalAoEixoZero() {
    return distanciaSinalAoEixoZero;
  }

  public Double getLinhaMacd() {
    return linhaMacd;
  }

  public Double getLinhaSinal() {
    return linhaSinal;
  }

  public Double getAnguloMacd() {
    return anguloMacd;
  }

  public Double getAnguloSinal() {
    return anguloSinal;
  }

  public Double getHistograma() {
    return histograma;
  }

  public Double getAnguloHistograma() {
    return anguloHistograma;
  }

  public Double getAnguloObv() {
    return anguloObv;
  }
  
}
