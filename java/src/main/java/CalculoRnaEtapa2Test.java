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

public class CalculoRnaEtapa2Test {
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\ResultadoRnaRnaFuzzy.xls";
  private final String path_scilab = "\"C:\\Users\\igorlima\\Desktop\\dados para treinamento RNA\\RNA_ANALISE_TECNICA\\RNA_ANALISE_TECNICA.sce\"";
  
  private enum IfrSaida {
    MuitoAlto(11), Alto(12), Neutro(13), Baixo(14), MuitoBaixo(15);
    private final int numero_da_coluna;
    private IfrSaida( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    public int col(){
      return numero_da_coluna; 
    }
  }
  
  private enum EstocasticoSaida{
    MuitoAlto(16), Alto(17), Neutro(18), Baixo(19), MuitoBaixo(20);
    private final int numero_da_coluna;
    private EstocasticoSaida( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    public int col(){
      return numero_da_coluna; 
    }
  }
  
  private enum MacdSaida{
    MuitoAlto(21), Alto(22), Neutro(23), Baixo(24), MuitoBaixo(25);
    private final int numero_da_coluna;
    private MacdSaida( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    public int col(){
      return numero_da_coluna; 
    }
  }
  
  private enum HistogramaSaida{
    MuitoAlto(26), Alto(27), Neutro(28), Baixo(29), MuitoBaixo(30);
    private final int numero_da_coluna;
    private HistogramaSaida( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    public int col(){
      return numero_da_coluna; 
    }
  }
  
  private enum ObvSaida {
    MuitoAlto(31), Alto(32), Neutro(33), Baixo(34), MuitoBaixo(35);
    private final int numero_da_coluna;
    private ObvSaida( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    public int col(){
      return numero_da_coluna; 
    }
  }
  
  private enum MomentoSaida {
    MuitoAlto(36, Neuronio.MuitoAlto), 
    Alto(37, Neuronio.Alto), 
    Neutro(38, Neuronio.Neutro), 
    Baixo(39, Neuronio.Baixo), 
    MuitoBaixo(40, Neuronio.MuitoBaixo);
    private final int numero_da_coluna;
    private final Neuronio neuronio;
    private MomentoSaida( int numero_da_coluna, Neuronio neuronio ){
      this.numero_da_coluna = numero_da_coluna;
      this.neuronio = neuronio;
    }
    public int set( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( camada_de_saida.get( neuronio ) );
      return numero_da_coluna; 
    }
  }
  
  private enum SinalSaida {
    MuitoAlto(41, Neuronio.MuitoAlto), 
    Alto(42, Neuronio.Alto), 
    Neutro(43, Neuronio.Neutro), 
    Baixo(44, Neuronio.Baixo), 
    MuitoBaixo(45, Neuronio.MuitoBaixo);
    private final int numero_da_coluna;
    private final Neuronio neuronio;
    private SinalSaida( int numero_da_coluna, Neuronio neuronio ){
      this.numero_da_coluna = numero_da_coluna;
      this.neuronio = neuronio;
    }
    public int set( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( camada_de_saida.get( neuronio ) );
      return numero_da_coluna; 
    }
  }
  
  private enum VolumeSaida {
    MuitoAlto(46, Neuronio.MuitoAlto), 
    Alto(47, Neuronio.Alto), 
    Neutro(48, Neuronio.Neutro), 
    Baixo(49, Neuronio.Baixo), 
    MuitoBaixo(50, Neuronio.MuitoBaixo);
    private final int numero_da_coluna;
    private final Neuronio neuronio;
    private VolumeSaida( int numero_da_coluna, Neuronio neuronio ){
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
          command_scilab += "ann_FF_run( [ " + dados_de_entrada_da_rna_Momento(sheet,row).replace(',',';') + " ], N_MOMENTO, W_MOMENTO )\n";
          command_scilab += "ann_FF_run( [ " + dados_de_entrada_da_rna_Sinal(sheet,row).replace(',',';')   + " ], N_SINAL,   W_SINAL   )\n";
          command_scilab += "ann_FF_run( [ " + dados_de_entrada_da_rna_Volume(sheet, row).replace(',',';') + " ], N_VOLUME,  W_VOLUME  )\n";
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
          alterar_os_valores_da_camada_de_saida_da_rna_Momento( sheet, rows.get(i), camada_de_saida.get(i*3+0));
          alterar_os_valores_da_camada_de_saida_da_rna_Sinal(   sheet, rows.get(i), camada_de_saida.get(i*3+1));
          alterar_os_valores_da_camada_de_saida_da_rna_Volume(  sheet, rows.get(i), camada_de_saida.get(i*3+2));
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
  
  private void alterar_os_valores_da_camada_de_saida_da_rna_Momento( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
    for( MomentoSaida saida : MomentoSaida.values() ) 
      saida.set(sheet, row, camada_de_saida);
  }
  
  private void alterar_os_valores_da_camada_de_saida_da_rna_Sinal( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
    for( SinalSaida saida : SinalSaida.values() )
      saida.set(sheet, row, camada_de_saida);
  }
  
  private void alterar_os_valores_da_camada_de_saida_da_rna_Volume( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
    for( VolumeSaida saida : VolumeSaida.values() )
      saida.set(sheet, row, camada_de_saida);
  }
  
  private String dados_de_entrada_da_rna_Momento( Sheet sheet, int row ){
    return dados_entrada_ifr(sheet, row) + ',' + dados_entrada_estocastico(sheet, row);
  }
  
  private String dados_de_entrada_da_rna_Sinal( Sheet sheet, int row ){
    return dados_entrada_macd(sheet, row) + ',' + dados_entrada_histograma(sheet, row);
  }
  
  private String dados_de_entrada_da_rna_Volume( Sheet sheet, int row ){
    return dados_entrada_obv(sheet, row);
  }
  
  
  private String dados_entrada_ifr( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( IfrSaida.MuitoAlto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( IfrSaida.Alto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( IfrSaida.Neutro.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( IfrSaida.Baixo.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( IfrSaida.MuitoBaixo.col() ).getNumericCellValue() );
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String dados_entrada_estocastico( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( EstocasticoSaida.MuitoAlto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( EstocasticoSaida.Alto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( EstocasticoSaida.Neutro.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( EstocasticoSaida.Baixo.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( EstocasticoSaida.MuitoBaixo.col() ).getNumericCellValue() );
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String dados_entrada_macd( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( MacdSaida.MuitoAlto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( MacdSaida.Alto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( MacdSaida.Neutro.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( MacdSaida.Baixo.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( MacdSaida.MuitoBaixo.col() ).getNumericCellValue() );
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String dados_entrada_histograma( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( HistogramaSaida.MuitoAlto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( HistogramaSaida.Alto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( HistogramaSaida.Neutro.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( HistogramaSaida.Baixo.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( HistogramaSaida.MuitoBaixo.col() ).getNumericCellValue() );
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String dados_entrada_obv( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( ObvSaida.MuitoAlto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( ObvSaida.Alto.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( ObvSaida.Neutro.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( ObvSaida.Baixo.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( ObvSaida.MuitoBaixo.col() ).getNumericCellValue() );
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
}
