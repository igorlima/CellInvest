package com.eatj.igorribeirolima.util.dadosinfomoney.dado;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

abstract class DadoAbstract implements Dado{
  private static final String notacaoCientifica = "(E\\-?[0-9]+)?"; //1.4210854715202004E-14
  private static final String NUMERO_REAL = "\\-?[0-9]+(\\.[0-9]+)?"+notacaoCientifica; //aceita somente numero Real
  private static final String STR_DATE = "[0-9][0-9][/][0-9][0-9][/][0-9][0-9]";
  private static final String CSV_VALIDO = "[0-9]+([;]"+NUMERO_REAL+")+[;]"+STR_DATE; 
  
  @DateTimeFormat( pattern="dd/MM/yy" )
  private Date date;
  protected Double valor;
  
  public DadoAbstract( String csv ) throws Exception{
    if( Pattern.matches( CSV_VALIDO, csv ) ){
      setDate( csv );
      setValor( csv );
    }else{
      throw new Exception( "CSV invalido" );
    }
  }
  
  private void setDate( String csv ) throws ParseException{
    Pattern pattern = Pattern.compile( STR_DATE );
    Matcher matcher = pattern.matcher( csv );
    
    String strDate = matcher.find() ? matcher.group() : null;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
    this.date = formatter.parse(strDate);
  }
  
  public Date getDate(){
    return this.date;
  }
  
  abstract void setValor( String csv );
  
  protected String getNumeroReal( String csv, int pos ){
    Pattern pattern = Pattern.compile( NUMERO_REAL );
    Matcher matcher = pattern.matcher( csv );
    
    for( int i=0; i<pos; i++ ) matcher.find();
    
    String strValor = matcher.group();
    return strValor;
  }
  
  public Double getValor(){
    return this.valor;
  }
  
  @Override
  public int compareTo(Dado dado) {
    try{
      return this.getDate().compareTo( dado.getDate() );
    }catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }
  
}
