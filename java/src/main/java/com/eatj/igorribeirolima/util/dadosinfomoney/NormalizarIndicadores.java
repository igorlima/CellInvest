package com.eatj.igorribeirolima.util.dadosinfomoney;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eatj.igorribeirolima.util.regressao.Regressao;


public class NormalizarIndicadores {
  private final List<Double> lista_com_o_valor_zero = Arrays.asList( new Double[]{ 0.0 } );
  private final int N = 3;
  
  private Regressao regressao;
  
  private List<Double> valores_ifr;
  private List<Double> valores_estocastico;
  private List<Double> valores_linha_macd;
  private List<Double> valores_linha_sinal;
  private List<Double> valores_histograma;
  private List<Double> valores_obv;
  
  public NormalizarIndicadores( List<Double> ifr, List<Double> estocastico, List<Double> macdline, List<Double> macdsinal, List<Double> histograma, List<Double> obv ){
    this.regressao = new Regressao();
    
    this.valores_ifr = ifr;
    this.valores_estocastico = estocastico;
    this.valores_linha_macd = macdline;
    this.valores_linha_sinal = macdsinal;
    this.valores_histograma = histograma;
    this.valores_obv = obv;
    
  }
  
  public Double ifr(){
    return get_ultimo_valor( this.valores_ifr );
  }
  
  public Double ifr_normalizado(){
    return ifr()/100.0;
  }
  
  public Double estocastico(){
    return get_ultimo_valor( this.valores_estocastico );
  }
  
  public Double estocastico_normalizado(){
    return estocastico()/100.0;
  }
  
  @SuppressWarnings("unchecked")
  public Double distancia_do_valor_macd_ao_eixo_zero(){
    Double macd = get_ultimo_valor( this.valores_linha_macd );
    Double macd_normalizado = regressao.normalizar( macd, valores_linha_macd, valores_linha_sinal, lista_com_o_valor_zero );
    Double zero_normalizado = regressao.normalizar( 0.0, valores_linha_macd, valores_linha_sinal, lista_com_o_valor_zero );
    return macd_normalizado - zero_normalizado;
  }
  
  @SuppressWarnings("unchecked")
  public Double valor_linha_macd(){
    Double macd = get_ultimo_valor( this.valores_linha_macd );
    return regressao.normalizar( macd, valores_linha_macd, valores_linha_sinal );
  }
  
  @SuppressWarnings("unchecked")
  public Double distancia_do_valor_de_sinal_ao_eixo_zero(){
    Double sinal = get_ultimo_valor( this.valores_linha_sinal );
    Double sinal_normalizado = regressao.normalizar( sinal, valores_linha_macd, valores_linha_sinal, lista_com_o_valor_zero );
    Double zero_normalizado = regressao.normalizar( 0.0, valores_linha_macd, valores_linha_sinal, lista_com_o_valor_zero );
    return sinal_normalizado - zero_normalizado;
  }
  
  @SuppressWarnings("unchecked")
  public Double valor_linha_sinal(){
    Double sinal = get_ultimo_valor( this.valores_linha_sinal );
    return regressao.normalizar( sinal, valores_linha_macd, valores_linha_sinal );
  }
  
  public Double angulo_linha_macd(){
    return angulo( this.valores_linha_macd );
  }
  
  public Double angulo_linha_sinal(){
    return angulo( this.valores_linha_sinal );
  }
  
  /**
   * Retorna a distancia do histograma em relacao ao eixo 0, num intervalo de [0, 1]
   * @return
   */
  @SuppressWarnings("unchecked")
  public Double histograma(){
    Double ultimo = get_ultimo_valor( this.valores_histograma );
    Double ultimo_normalizado = regressao.normalizar( ultimo, valores_histograma, lista_com_o_valor_zero );
    Double zero_normalizado = regressao.normalizar( 0.0, valores_histograma, lista_com_o_valor_zero );
    return Math.abs( ultimo_normalizado - zero_normalizado );
  }
  
  public Double angulo_histograma(){
    return angulo( this.valores_histograma );
  }
  
  public Double angulo_obv(){
    return angulo( this.valores_obv );
  }
  
  /**
   * Retorna o ultimo valor da lista de Dado
   * @param dados
   * @return
   */
  private Double get_ultimo_valor( List<Double> dados ){
    int n = dados.size() -1;
    return dados.get(n);
  }
  
  /**
   * Retorna os 'n' últimos elementos da lista de double
   * @param dados
   * @param n
   * @return
   */
  private List<Double> get_ultimos_valores( List<Double> dados, int n ){
    int size = dados.size();
    Double[] ultimos = new Double[n];
    
    for( int i=1; i<=n; i++ )
      ultimos[n-i] = dados.get(size-i);
    
    return Arrays.asList( ultimos );
  }
  
  /**
   * Retorna 'n' valores para o eixo x
   * @param n
   * @return
   */
  private List<Double> get_eixo_x( int n ){
    List<Double> eixo_x = new ArrayList<Double>();
    for( int i=1; i<=n; i++ ) 
      eixo_x.add( new Double(i) );
    
    return eixo_x;
  }
  
  /**
   * Retorna o angulo (pi) dos N últimos numeros da lista de double
   * @param numeros
   * @return
   */
  private Double angulo( List<Double> numeros ){
    List<Double> valores_normalizados = regressao.normalizar( numeros );
    List<Double> ultimos_valores = get_ultimos_valores(valores_normalizados, N);
    return regressao.angulo_da_reta( get_eixo_x(N), ultimos_valores );
  }
}
