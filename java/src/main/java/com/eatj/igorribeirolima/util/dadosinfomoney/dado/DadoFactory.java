package com.eatj.igorribeirolima.util.dadosinfomoney.dado;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class DadoFactory {
  
  @SuppressWarnings("rawtypes")
  private static Map<AnaliseTecnica, Class> type_dados = new HashMap<AnaliseTecnica, Class>();
  
  static{
    type_dados.put( AnaliseTecnica.PRICE, DadoPrice.class );
    type_dados.put( AnaliseTecnica.IFR, DadoIFR.class );
    type_dados.put( AnaliseTecnica.MACD_SINAL, DadoMacdSinal.class );
    type_dados.put( AnaliseTecnica.MACD_LINE, DadoMacdLine.class );
    type_dados.put( AnaliseTecnica.HISTOGRAMA, DadoHistograma.class );
    type_dados.put( AnaliseTecnica.ESTOCASTICO, DadoEstocastico.class );
    type_dados.put( AnaliseTecnica.OBV, DadoOBV.class );
  }
  
  @SuppressWarnings("unchecked")
  public static Dado getIntance( AnaliseTecnica analiseTecnica, String csv ){
    Constructor<Dado> constructor;
    try {
      constructor = type_dados.get(analiseTecnica).getConstructor( String.class );
      return constructor.newInstance(csv);
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }
}
