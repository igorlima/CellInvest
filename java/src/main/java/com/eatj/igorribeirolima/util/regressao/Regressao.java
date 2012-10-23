package com.eatj.igorribeirolima.util.regressao;

import java.util.List;

public class Regressao {
  
  private RegressaoUtil util = new RegressaoUtil();
  
  public Double angulo_da_reta( List<Double> x, List<Double> y ){
    return util.coef2pi( coeficiente_angular(x, y) );
  }
  
  @SuppressWarnings("unchecked")
  public Double coeficiente_angular( List<Double> x, List<Double> y ){
    if( x.size() != y.size() ) throw new RuntimeException( "A quantidade de valores de x DEVEM ser iguais a quantidade de valores de y" );
    
    Double x_media = util.media(x);
    Double y_media = util.media(y);
    
    Double numerador = 0.0;
    Double denominador = 0.0;
    for( int i=0; i<x.size(); i++ ){
      numerador += ( x.get(i)-x_media )*( y.get(i)-y_media );
      denominador += Math.pow( x.get(i)-x_media, 2 );
    }
    
    return numerador/denominador;
  }
  
  @SuppressWarnings("unchecked")
  public Double coeficiente_linear( List<Double> x, List<Double> y ){
    Double x_media = util.media(x);
    Double y_media = util.media(y);
    Double coeficiente_angular = coeficiente_angular(x, y);
    
    return y_media - coeficiente_angular*x_media; 
  }
  
  public Double normalizar( Double x, List<Double> ... lista_de_numeros ){
    return util.normalizar(x, lista_de_numeros);
  }
  
  public List<Double> normalizar( List<Double> dados ){
    return util.normalizar(dados);
  }
  
  public List<Double> normalizar( List<Double> ... dados ){
    return util.normalizar(dados);
  }
  
}
