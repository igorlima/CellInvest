package com.eatj.igorribeirolima.util.dadosinfomoney;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eatj.igorribeirolima.util.dadosinfomoney.dado.AnaliseTecnica;
import com.eatj.igorribeirolima.util.dadosinfomoney.dado.Dado;
import com.eatj.igorribeirolima.util.dadosinfomoney.dado.DadoAnaliseTecnica;
import com.eatj.igorribeirolima.util.dadosinfomoney.dado.DadoFactory;

public class DadosInfoMoney {
  
  private Map<AnaliseTecnica, List<Dado>> map = new HashMap<AnaliseTecnica, List<Dado>>();
  
  public DadosInfoMoney( String strAtivo ){
    map.put( AnaliseTecnica.PRICE, get(strAtivo, AnaliseTecnica.PRICE) );
    map.put( AnaliseTecnica.IFR, get(strAtivo, AnaliseTecnica.IFR) );
    map.put( AnaliseTecnica.MACD_SINAL, get(strAtivo, AnaliseTecnica.MACD_SINAL) );
    map.put( AnaliseTecnica.MACD_LINE, get(strAtivo, AnaliseTecnica.MACD_LINE) );
    map.put( AnaliseTecnica.HISTOGRAMA, get(strAtivo, AnaliseTecnica.HISTOGRAMA) );
    map.put( AnaliseTecnica.ESTOCASTICO, get(strAtivo, AnaliseTecnica.ESTOCASTICO) );
    map.put( AnaliseTecnica.OBV, get(strAtivo, AnaliseTecnica.OBV) );
  }
  
  public static List<Dado> get( String strAtivo, AnaliseTecnica analiseTecnica ){
    String db_csv = CSV.get(strAtivo, analiseTecnica);
    String[] data_csv = db_csv.split( "\n" );
    
    List<Dado> dados = new ArrayList<Dado>();
    for( String csv : data_csv )
      dados.add( DadoFactory.getIntance(analiseTecnica, csv) );
    
    Collections.sort(dados);
    return dados;
  }
  
  public List<Dado> get( AnaliseTecnica analiseTecnica ){
    return map.get( analiseTecnica );
  }
  
  public List<DadoAnaliseTecnica> convert(){
    List<DadoAnaliseTecnica> result = new ArrayList<DadoAnaliseTecnica>();
    Map<Date, DadoAnaliseTecnica> map = new HashMap<Date, DadoAnaliseTecnica>();
    
    for( AnaliseTecnica analiseTecnica : AnaliseTecnica.values() ){
      
      List<Dado> dados = get(analiseTecnica);
      for( Dado dado : dados ){
        DadoAnaliseTecnica dadoAnaliseTecnica = map.get(dado.getDate());
        if( dadoAnaliseTecnica == null ){
          dadoAnaliseTecnica = new DadoAnaliseTecnica();
          dadoAnaliseTecnica.setDate( dado.getDate() );
          map.put( dado.getDate(), dadoAnaliseTecnica );
        }
        
        dado.setDadoAnaliseTecnica(dadoAnaliseTecnica);
      }
      
    }
    
    result.addAll( map.values() );
    Collections.sort( result );
    return result;
  }
  
}
