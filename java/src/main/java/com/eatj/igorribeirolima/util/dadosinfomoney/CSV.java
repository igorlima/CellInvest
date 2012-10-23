package com.eatj.igorribeirolima.util.dadosinfomoney;

import java.util.HashMap;
import java.util.Map;

import com.eatj.igorribeirolima.util.dadosinfomoney.dado.AnaliseTecnica;

class CSV {
  private static Map<AnaliseTecnica, String> map = new HashMap<AnaliseTecnica, String>();
  private static String QTE_LINHAS = "&linhas=7";
  static{
    map.put(AnaliseTecnica.PRICE, "http://custom.infomoney.com.br/rotinas/GeraDadosGrafico.asp?ativo=strAtivo&tipo_cotacao=2&atv_dado10=28" + QTE_LINHAS );
    map.put(AnaliseTecnica.IFR, "http://custom.infomoney.com.br/rotinas/GeraDadosGrafico.asp?ativo=strAtivo&tipo_cotacao=2&atv_dado10=28" + QTE_LINHAS );
    map.put(AnaliseTecnica.MACD_SINAL, "http://custom.infomoney.com.br/rotinas/GeraDadosGrafico.asp?ativo=strAtivo&tipo_cotacao=2&atv_dado11=82&atv_dado12=83" + QTE_LINHAS );
    map.put(AnaliseTecnica.MACD_LINE, "http://custom.infomoney.com.br/rotinas/GeraDadosGrafico.asp?ativo=strAtivo&tipo_cotacao=2&atv_dado11=82&atv_dado12=83" + QTE_LINHAS );
    map.put(AnaliseTecnica.HISTOGRAMA, "http://custom.infomoney.com.br/rotinas/GeraDadosGrafico.asp?ativo=strAtivo&tipo_cotacao=2&atv_dado11=397" + QTE_LINHAS );
    map.put(AnaliseTecnica.ESTOCASTICO, "http://custom.infomoney.com.br/rotinas/GeraDadosGrafico.asp?ativo=strAtivo&tipo_cotacao=2&atv_dado11=85" + QTE_LINHAS );
    map.put(AnaliseTecnica.OBV, "http://custom.infomoney.com.br/rotinas/GeraDadosGrafico.asp?ativo=strAtivo&tipo_cotacao=2&atv_dado11=84" + QTE_LINHAS );
  }
  
  public static String get( String strAtivo, AnaliseTecnica analiseTecnica ){
    String url = map.get(analiseTecnica);
    url = url.replace( "strAtivo", strAtivo );
    return HTTPRequest.get(url);
  }
  
}
