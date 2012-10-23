import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Ignore;
import org.junit.Test;


public class NormalizacaoRnaXlsTest {
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\ResultadosRNA.xls";
  
  private enum TipoNormalizacao{
    IFR(1),
    ESTOCASTICO(2),
    DISTANCIA_MACD_AO_EIXO_ZERO(3),
    DIFERENCA_LINHA_MACD_E_SINAL(4),
    ALPHA_MACD(5),
    DIFERENCA_ALPHA_MACD_E_ALPHA_SINAL(6),
    DISTANCIA_HISTOGRAMA_AO_EIXO(7),
    ALPHA_HISTOGRAMA(8),
    ALPHA_OBV(9);
    
    private final int numero_da_coluna;
    private TipoNormalizacao( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    
    public int col(){
      return numero_da_coluna;
    }
    
    public int n(){
      return numero_da_coluna+10;
    }
  }
  
  @Test
  @Ignore
  public void normalizar_entrada_da_modelagem_rna(){
    
    String planilha = "";
    int row = 0;
    try {
      
      InputStream file_xls = new FileInputStream(path_arquivo);
      Workbook w = new HSSFWorkbook( file_xls );
      
      int quantidade_de_planilhas = w.getNumberOfSheets();
      for( int num_planilha = 0; num_planilha < quantidade_de_planilhas; num_planilha++ ){
        Sheet sheet = w.getSheetAt(num_planilha);
        planilha = sheet.getSheetName();
        System.out.println( planilha + "..." );
        
        for ( row = 2; row <= sheet.getLastRowNum(); row++)
          normalizar_valores(sheet, row);
        
      }
      
      
      w.write( new FileOutputStream( path_arquivo ) );
    } catch (Exception e) {
      if( !planilha.isEmpty() && row>0 ) System.out.println( "<Error> Planilha: " + planilha + ". Linha: " + row );
      e.printStackTrace();
    } 
  }
  
  private void normalizar_valores( Sheet sheet, int row ){
    
    normalizar_ifr(sheet, row);
    normalizar_est(sheet, row);
    normalizar_linhamacd(sheet, row);
    normalizar_difmacd(sheet, row);
    normalizar_alphamacd(sheet, row);
    normalizar_difalphamacd(sheet, row);
    normalizar_hist(sheet, row);
    normalizar_alphahist(sheet, row);
    normalizar_alphaobv(sheet, row);
    
  }
  
  private void normalizar_ifr( Sheet sheet, int row ){
    try{
      double value = sheet.getRow(row).getCell( TipoNormalizacao.IFR.col() ).getNumericCellValue();
      value = value/100.0;
      Cell cell = sheet.getRow(row).createCell( TipoNormalizacao.IFR.n(), Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue( value );
    }catch( Exception exception ){
      System.out.println( "<Error> Planilha: " + sheet.getSheetName() + ". Linha: " + row + ". Exception: " + exception.toString() );
    }
  }
  
  private void normalizar_est( Sheet sheet, int row ){
    try{
      double value = sheet.getRow(row).getCell( TipoNormalizacao.ESTOCASTICO.col() ).getNumericCellValue();
      value = value/100.0;
      Cell cell = sheet.getRow(row).createCell( TipoNormalizacao.ESTOCASTICO.n(), Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue( value );
    }catch( Exception exception ){
      System.out.println( "<Error> Planilha: " + sheet.getSheetName() + ". Linha: " + row + ". Exception: " + exception.toString() );
    }
  }
  
  private void normalizar_linhamacd( Sheet sheet, int row ){
    try{
      double value = sheet.getRow(row).getCell( TipoNormalizacao.DISTANCIA_MACD_AO_EIXO_ZERO.col() ).getNumericCellValue();
      value = (value+1.0)/2.0;
      Cell cell = sheet.getRow(row).createCell( TipoNormalizacao.DISTANCIA_MACD_AO_EIXO_ZERO.n(), Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue( value );
    }catch( Exception exception ){
      System.out.println( "<Error> Planilha: " + sheet.getSheetName() + ". Linha: " + row + ". Exception: " + exception.toString() );
    }
  }
  
  private void normalizar_difmacd( Sheet sheet, int row ){
    try{
      double value = sheet.getRow(row).getCell( TipoNormalizacao.DIFERENCA_LINHA_MACD_E_SINAL.col() ).getNumericCellValue();
      value = (value+2.0)/4.0;
      Cell cell = sheet.getRow(row).createCell( TipoNormalizacao.DIFERENCA_LINHA_MACD_E_SINAL.n(), Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue( value );
    }catch( Exception exception ){
      System.out.println( "<Error> Planilha: " + sheet.getSheetName() + ". Linha: " + row + ". Exception: " + exception.toString() );
    }
  }
  
  private void normalizar_alphamacd( Sheet sheet, int row ){
    try{
      double value = sheet.getRow(row).getCell( TipoNormalizacao.ALPHA_MACD.col() ).getNumericCellValue();
      value = (value+1.57)/3.14;
      Cell cell = sheet.getRow(row).createCell( TipoNormalizacao.ALPHA_MACD.n(), Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue( value );
    }catch( Exception exception ){
      System.out.println( "<Error> Planilha: " + sheet.getSheetName() + ". Linha: " + row + ". Exception: " + exception.toString() );
    }
  }
  
  private void normalizar_difalphamacd( Sheet sheet, int row ){
    try{
      double value = sheet.getRow(row).getCell( TipoNormalizacao.DIFERENCA_ALPHA_MACD_E_ALPHA_SINAL.col() ).getNumericCellValue();
      value = (value+3.14)/6.28;
      Cell cell = sheet.getRow(row).createCell( TipoNormalizacao.DIFERENCA_ALPHA_MACD_E_ALPHA_SINAL.n(), Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue( value );
    }catch( Exception exception ){
      System.out.println( "<Error> Planilha: " + sheet.getSheetName() + ". Linha: " + row + ". Exception: " + exception.toString() );
    }
  }
  
  private void normalizar_hist( Sheet sheet, int row ){
    try{
      double value = sheet.getRow(row).getCell( TipoNormalizacao.DISTANCIA_HISTOGRAMA_AO_EIXO.col() ).getNumericCellValue();
      Cell cell = sheet.getRow(row).createCell( TipoNormalizacao.DISTANCIA_HISTOGRAMA_AO_EIXO.n(), Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue( value );
    }catch( Exception exception ){
      System.out.println( "<Error> Planilha: " + sheet.getSheetName() + ". Linha: " + row + ". Exception: " + exception.toString() );
    }
  }
  
  private void normalizar_alphahist( Sheet sheet, int row ){
    try{
      double value = sheet.getRow(row).getCell( TipoNormalizacao.ALPHA_HISTOGRAMA.col() ).getNumericCellValue();
      value = (value+1.57)/3.14;
      Cell cell = sheet.getRow(row).createCell( TipoNormalizacao.ALPHA_HISTOGRAMA.n(), Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue( value );
    }catch( Exception exception ){
      System.out.println( "<Error> Planilha: " + sheet.getSheetName() + ". Linha: " + row + ". Exception: " + exception.toString() );
    }
  }
  
  private void normalizar_alphaobv( Sheet sheet, int row ){
    try{
      double value = sheet.getRow(row).getCell( TipoNormalizacao.ALPHA_OBV.col() ).getNumericCellValue();
      value = (value+1.57)/3.14;
      Cell cell = sheet.getRow(row).createCell( TipoNormalizacao.ALPHA_OBV.n(), Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue( value );
    }catch( Exception exception ){
      System.out.println( "<Error> Planilha: " + sheet.getSheetName() + ". Linha: " + row + ". Exception: " + exception.toString() );
    }
  }
}
