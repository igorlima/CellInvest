package com.eatj.igorribeirolima.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversaoDeDados {
  public final static String notacaoCientifica = "(E\\-?[0-9]+)?"; //1.4210854715202004E-14
  public final static String NUMERO_REAL = "\\-?[0-9]+(\\.[0-9]+)?"+notacaoCientifica; //aceita somente numero Real
  
  public static List<Double> string2listdouble( String input ){
    
    List<Double> dadosDeEntrada = new ArrayList<Double>();
    
    Pattern pattern = Pattern.compile( NUMERO_REAL );
    Matcher matcher = pattern.matcher( input );
    
    while( matcher.find() )
      dadosDeEntrada.add( Double.parseDouble( matcher.group() ) );
    
    return dadosDeEntrada;
  }
  
}
