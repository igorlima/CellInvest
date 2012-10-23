package com.eatj.igorribeirolima.util.dadosinfomoney.dado;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DadoAnaliseTecnica implements Serializable, Comparable<DadoAnaliseTecnica> {
  private static final long serialVersionUID = 1L;
  
  private Date date;
  private Double price;
  private Double ifr;
  private Double estocastico;
  private Double macdline;
  private Double macdsinal;
  private Double histograma;
  private Double obv;
  
  public String getStrDate() {
    return new SimpleDateFormat("dd/MM/yy").format( getDate() );
  }
  
  public Date getDate() {
    return date;
  }
  
  public void setDate(Date date) {
    this.date = date;
  }
  
  public Double getPrice() {
    return price;
  }
  
  public void setPrice(Double price) {
    this.price = price;
  }
  
  public Double getIfr() {
    return ifr;
  }
  
  public void setIfr(Double ifr) {
    this.ifr = ifr;
  }
  
  public Double getEstocastico() {
    return estocastico;
  }
  
  public void setEstocastico(Double estocastico) {
    this.estocastico = estocastico;
  }
  
  public Double getMacdline() {
    return macdline;
  }
  
  public void setMacdline(Double macdline) {
    this.macdline = macdline;
  }
  
  public Double getMacdsinal() {
    return macdsinal;
  }
  
  public void setMacdsinal(Double macdsinal) {
    this.macdsinal = macdsinal;
  }
  
  public Double getHistograma() {
    return histograma;
  }
  
  public void setHistograma(Double histograma) {
    this.histograma = histograma;
  }
  
  public Double getObv() {
    return obv;
  }
  
  public void setObv(Double obv) {
    this.obv = obv;
  }
  
  @Override
  public int compareTo(DadoAnaliseTecnica dado) {
    try{
      return this.getDate().compareTo( dado.getDate() );
    }catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }
}
