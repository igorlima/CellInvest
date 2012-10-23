import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.enumerator.Neuronio;
import com.eatj.igorribeirolima.util.SCILAB;
import com.eatj.igorribeirolima.util.SaidaRNA;

public class CalculoRnaEtapa3Test {
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\ResultadoFuzzyFuzzyRna.xls";
  private final String path_scilab = "\"C:\\Users\\igorlima\\Desktop\\dados para treinamento RNA\\RNA_ANALISE_TECNICA\\RNA_ANALISE_TECNICA.sce\"";
  
  private enum MomentoSaida {
    MuitoAlto(36), Alto(37), Neutro(38), Baixo(39), MuitoBaixo(40);
    private final int numero_da_coluna;
    private MomentoSaida( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    public int col(){
      return numero_da_coluna; 
    }
  }
  
  private enum SinalSaida {
    MuitoAlto(41), Alto(42), Neutro(43), Baixo(44), MuitoBaixo(45);
    private final int numero_da_coluna;
    private SinalSaida( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    public int col(){
      return numero_da_coluna; 
    }
  }
  
  private enum VolumeSaida {
    MuitoAlto(46), Alto(47), Neutro(48), Baixo(49), MuitoBaixo(50);
    private final int numero_da_coluna;
    private VolumeSaida( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    public int col(){
      return numero_da_coluna; 
    }
  }
  
  private enum IndicadorSaida {
    MuitoAlto(51, Neuronio.MuitoAlto), 
    Alto(52, Neuronio.Alto), 
    Neutro(53, Neuronio.Neutro), 
    Baixo(54, Neuronio.Baixo), 
    MuitoBaixo(55, Neuronio.MuitoBaixo);
    private final int numero_da_coluna;
    private final Neuronio neuronio;
    private IndicadorSaida( int numero_da_coluna, Neuronio neuronio ){
      this.numero_da_coluna = numero_da_coluna;
      this.neuronio = neuronio;
    }
    public int set( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( camada_de_saida.get( neuronio ) );
      return numero_da_coluna; 
    }
  }
  
  @Test
  public void calcular_saida_da_primeira_etapa() throws IOException{
      
    InputStream file_xls = new FileInputStream(path_arquivo);
    Workbook w = new HSSFWorkbook( file_xls );
    
    int quantidade_de_planilhas = w.getNumberOfSheets();
    for( int num_planilha = 0; num_planilha < quantidade_de_planilhas; num_planilha++ ){
      Sheet sheet = w.getSheetAt(num_planilha);
      System.out.println( sheet.getSheetName() + "..." );
      
      List<String> input_scilab = new ArrayList<String>();
      List<Integer> rows = new ArrayList<Integer>();
      for ( int row = 2; row <= sheet.getLastRowNum(); row++){
        try{
          String command_scilab = "";
          command_scilab += "ann_FF_run( [ " + dados_de_entrada_da_rna_Indicador(sheet,row).replace(',',';') + " ], N_INDICADOR, W_INDICADOR )\n";
          input_scilab.add( command_scilab );
          rows.add(row);
        } catch (Exception e) {
          System.out.println( "<Error> Planilha: " + sheet.getSheetName() +". Linha: " + row );
        }
      }
      
      ReturnTO returnTO;
      System.out.println( "Realizando cálculos no Scilab..." );
      returnTO = SCILAB.execute( path_scilab, input_scilab.toArray( new String[]{} ) );
      if( ((MessageReturnTO) returnTO).getMessage().indexOf( "ans" ) == -1 )
        returnTO = SCILAB.execute( path_scilab, input_scilab.toArray( new String[]{} ) );
      
      System.out.println( "Extraindo as camadas de saídas..." );
      List<Map<Neuronio, Double>> camada_de_saida = SaidaRNA.extrair_as_camadas_de_saida(returnTO);
      if( camada_de_saida.size() > 0 ){
        for( int i=0; i<rows.size(); i++ ){
          alterar_os_valores_da_camada_de_saida_da_rna_Indicador( sheet, rows.get(i), camada_de_saida.get(i) );
        }
      }else{
        System.out.println( "Error na camada de saída da planilha " + sheet.getSheetName() + ". Index da planilha igual a " + num_planilha + "." );
      }
      
      System.out.println();
    }
        
    System.out.println( "salvando arquivo..." );
    w.write( new FileOutputStream( path_arquivo ) );
    System.out.println( "FIM!!!" );
  }
  
  private void alterar_os_valores_da_camada_de_saida_da_rna_Indicador( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
    for( IndicadorSaida saida : IndicadorSaida.values() ) 
      saida.set(sheet, row, camada_de_saida);
  }
  
  private String dados_de_entrada_da_rna_Indicador( Sheet sheet, int row ){
    return dados_entrada_momento(sheet, row) + ',' + dados_entrada_sinal(sheet, row) + ',' + dados_entrada_volume(sheet, row);
  }
  
  private String dados_entrada_momento( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( MomentoSaida.MuitoAlto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( MomentoSaida.Alto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( MomentoSaida.Neutro.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( MomentoSaida.Baixo.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( MomentoSaida.MuitoBaixo.col() ).getNumericCellValue() );
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String dados_entrada_sinal( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( SinalSaida.MuitoAlto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( SinalSaida.Alto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( SinalSaida.Neutro.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( SinalSaida.Baixo.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( SinalSaida.MuitoBaixo.col() ).getNumericCellValue() );
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String dados_entrada_volume( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( VolumeSaida.MuitoAlto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( VolumeSaida.Alto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( VolumeSaida.Neutro.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( VolumeSaida.Baixo.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( VolumeSaida.MuitoBaixo.col() ).getNumericCellValue() );
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
}
