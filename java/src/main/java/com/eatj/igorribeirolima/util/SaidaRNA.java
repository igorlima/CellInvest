package com.eatj.igorribeirolima.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.enumerator.Neuronio;

public class SaidaRNA {
  public final static String REGEX_SAIDA_RNA = "(\\s+" + ConversaoDeDados.NUMERO_REAL + "){5}";
  
  /**
   * Método que, dada uma string, extrai todas as camadas de saída de uma rede neural.
   * Caso a string de entrada tenha 'n' camdas de saídas, o método extrai as 'n' camada de saídas,
   * numa lista de string. Sendo que cada string contida na lista, é uma camada de saída. 
   * 
   * @param input
   * @return
   */
  private static List<String> extrair_as_saidas_rnas( String input ){
    List<String> saidasRNAs = new ArrayList<String>();
    
    Pattern pattern = Pattern.compile( REGEX_SAIDA_RNA );
    Matcher matcher = pattern.matcher( input );
    
    while(matcher.find()) saidasRNAs.add( matcher.group() );
    
    return saidasRNAs;
  }
  
  /**
   * Método que extrai somente os valores da camada de saída de uma rede neural do scilab.
   * Eliminando todos os outros caracteres que não seguem o padrão da camada de saída.
   * 
   * @param saida_do_scilab
   * @return
   */
  public static String extrair_saida_rna( String saida_do_scilab ){
    Pattern pattern = Pattern.compile( REGEX_SAIDA_RNA );
    Matcher matcher = pattern.matcher( saida_do_scilab );
    
    if( matcher.find() )
      return matcher.group();
    else
      return null;
    
  }
  
  /**
   * Método que, dada uma camada de saída numa string, converte esta string
   * num Map, o qual contém os valores em double de cada neuronios da camada de saída.
   * Cada neurônio irá representar uma intensidade de MuitoAlto, Alto, Neuto, Baixo, MuitoBaixo 
   * 
   * @param camada_de_saida
   * @return
   */
  private static Map<Neuronio, Double> extrair_camada_de_saida( String camada_de_saida ){
    Map<Neuronio, Double> map = new HashMap<Neuronio, Double>();
    
    List<Double> result = ConversaoDeDados.string2listdouble(camada_de_saida);
    map.put( Neuronio.MuitoAlto, result.get(0) );
    map.put( Neuronio.Alto, result.get(1) );
    map.put( Neuronio.Neutro, result.get(2) );
    map.put( Neuronio.Baixo, result.get(3) );
    map.put( Neuronio.MuitoBaixo, result.get(4) );
    
    return map;
  }
  
  private static String alterar_caractere_da_notacao_cientifica( String str ){
    return str.replace( 'D', 'E' );
  }
  
  /**
   * Método que, dado um returnoTO com sucesso e que tenha uma string válida,
   * extrai todas as camadas de saídas da rede neural e retorna numa lista.
   * 
   * @param returnTO
   * @return
   */
  public static List<Map<Neuronio, Double>> extrair_as_camadas_de_saida(ReturnTO returnTO){
    if( !returnTO.isSuccessful() ) throw new RuntimeException( "O objeto returnTO não é um retorno de sucesso!" );
    
    String str = ((MessageReturnTO) returnTO).getMessage();
    List<Map<Neuronio, Double>> lista_camadas = new ArrayList<Map<Neuronio,Double>>();
    
    str = alterar_caractere_da_notacao_cientifica(str);
    List<String> camadas_de_saida = extrair_as_saidas_rnas( str );
    for( String saida : camadas_de_saida ){
      Map<Neuronio, Double> camada_de_saida = extrair_camada_de_saida(saida);
      lista_camadas.add( camada_de_saida );
    }
    
    return lista_camadas;
  }
  
}
