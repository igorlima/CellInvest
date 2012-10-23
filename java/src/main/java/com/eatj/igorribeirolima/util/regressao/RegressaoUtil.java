package com.eatj.igorribeirolima.util.regressao;

import java.util.ArrayList;
import java.util.List;

class RegressaoUtil {
  
  public List<Double> normalizar( List<Double> ... lista_de_numeros ){
    return normalizar( convert(lista_de_numeros) );
  }
  
  @SuppressWarnings("unchecked")
  public List<Double> normalizar( List<Double> dados ){
    Double maximo = maximo(dados);
    Double minimo = minimo(dados);
    
    List<Double> normalizacao = new ArrayList<Double>();
    for( Double x : dados )
      normalizacao.add( normalizar(x, maximo, minimo) );
    
    return normalizacao;
  }
  
  public Double normalizar( Double x, List<Double> ... lista_de_numeros ){
    Double maximo = maximo(lista_de_numeros);
    Double minimo = minimo(lista_de_numeros);
    return normalizar(x, maximo, minimo);
  }
  
  public Double normalizar( Double x, Double maximo, Double minimo ){
    return (x-minimo)/(maximo-minimo);
  }
  
  public Double maximo( List<Double> ... lista_de_numeros ){
    List<Double> dados = convert(lista_de_numeros);
    Double maximo = dados.get(0);
    for( Double dado : dados ) 
      maximo = Math.max( maximo, dado );
    
    return maximo;
  }
  
  public Double minimo( List<Double> ... lista_de_numeros ){
    List<Double> dados = convert(lista_de_numeros);
    Double minimo = dados.get(0);
    for( Double dado : dados ) 
      minimo = Math.min( minimo, dado );
    
    return minimo;
  }
  
  public Double media( List<Double> ... lista_de_numeros ){
    List<Double> dados = convert(lista_de_numeros);
    Double somario = 0.0;
    for( Double dado : dados ) somario += dado;
    
    return somario/dados.size();
  }
  
  public Double coef2pi( Double m ){
    return Math.atan(m);
  }
  
  private List<Double> convert( List<Double> ... lista_de_numeros ){
    List<Double> dados = new ArrayList<Double>();
    for( List<Double> numeros : lista_de_numeros ) dados.addAll(numeros);
    
    return dados;
  }
}
