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

public class CalculoRnaEtapa1Test {
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\ResultadoRnaRnaFuzzy.xls";
  private final String path_scilab = "\"C:\\Users\\igorlima\\Desktop\\dados para treinamento RNA\\RNA_ANALISE_TECNICA\\RNA_ANALISE_TECNICA.sce\"";
  
  private enum Entrada{
    ifr(1),
    est(2),
    linhamacd(3),
    difmacd(4),
    alphamacd(5),
    difalphamacd(6),
    hist(7),
    alphahist(8),
    alphaobv(9);
    
    private final int numero_da_coluna;
    private Entrada( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    
    public int col(){
      return numero_da_coluna;
    }
    
  }
  
  private enum IfrSaida {
    MuitoAlto(11, Neuronio.MuitoAlto), 
    Alto(12, Neuronio.Alto), 
    Neutro(13, Neuronio.Neutro), 
    Baixo(14, Neuronio.Baixo), 
    MuitoBaixo(15, Neuronio.MuitoBaixo);
    private final int numero_da_coluna;
    private final Neuronio neuronio_de_saida;
    private IfrSaida( int numero_da_coluna, Neuronio neuronio ){
      this.numero_da_coluna = numero_da_coluna;
      this.neuronio_de_saida = neuronio;
    }
    public void set( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( camada_de_saida.get( neuronio_de_saida ) );
    }
  }
  
  private enum EstocasticoSaida{
    MuitoAlto(16, Neuronio.MuitoAlto), 
    Alto(17, Neuronio.Alto), 
    Neutro(18, Neuronio.Neutro), 
    Baixo(19, Neuronio.Baixo), 
    MuitoBaixo(20, Neuronio.MuitoBaixo);
    private final int numero_da_coluna;
    private final Neuronio neuronio_de_saida;
    private EstocasticoSaida( int numero_da_coluna, Neuronio neuronio ){
      this.numero_da_coluna = numero_da_coluna;
      this.neuronio_de_saida = neuronio;
    }
    public void set( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( camada_de_saida.get( neuronio_de_saida ) );
    }
  }
  
  private enum MacdSaida{
    MuitoAlto(21, Neuronio.MuitoAlto), 
    Alto(22, Neuronio.Alto), 
    Neutro(23, Neuronio.Neutro), 
    Baixo(24, Neuronio.Baixo), 
    MuitoBaixo(25, Neuronio.MuitoBaixo);
    private final int numero_da_coluna;
    private final Neuronio neuronio_de_saida;
    private MacdSaida( int numero_da_coluna, Neuronio neuronio ){
      this.numero_da_coluna = numero_da_coluna;
      this.neuronio_de_saida = neuronio;
    }
    public void set( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( camada_de_saida.get( neuronio_de_saida ) );
    }
  }
  
  private enum HistogramaSaida{
    MuitoAlto(26, Neuronio.MuitoAlto), 
    Alto(27, Neuronio.Alto), 
    Neutro(28, Neuronio.Neutro), 
    Baixo(29, Neuronio.Baixo), 
    MuitoBaixo(30, Neuronio.MuitoBaixo);
    private final int numero_da_coluna;
    private final Neuronio neuronio_de_saida;
    private HistogramaSaida( int numero_da_coluna, Neuronio neuronio ){
      this.numero_da_coluna = numero_da_coluna;
      this.neuronio_de_saida = neuronio;
    }
    public void set( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( camada_de_saida.get( neuronio_de_saida ) );
    }
  }
  
  private enum ObvSaida {
    MuitoAlto(31, Neuronio.MuitoAlto), 
    Alto(32, Neuronio.Alto), 
    Neutro(33, Neuronio.Neutro), 
    Baixo(34, Neuronio.Baixo), 
    MuitoBaixo(35, Neuronio.MuitoBaixo);
    private final int numero_da_coluna;
    private final Neuronio neuronio_de_saida;
    private ObvSaida( int numero_da_coluna, Neuronio neuronio ){
      this.numero_da_coluna = numero_da_coluna;
      this.neuronio_de_saida = neuronio;
    }
    public void set( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( camada_de_saida.get( neuronio_de_saida ) );
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
          command_scilab += "rna_ifr( " + get_dados_ifr(sheet, row) + ")\n";
          command_scilab += "rna_estocastico( " + get_dados_estocastico(sheet, row) + ")\n";
          command_scilab += "rna_macd( " + get_dados_macd(sheet, row) + ")\n";
          command_scilab += "rna_histograma( " + get_dados_histograma(sheet, row) + ")\n";
          command_scilab += "rna_obv( " + get_dados_obv(sheet, row) + ")\n";
          input_scilab.add( command_scilab );
          rows.add(row);
        } catch (Exception e) {
          System.out.println( "<Error> Planilha: " + sheet.getSheetName() +". Linha: " + row );
        }
      }
      
      ReturnTO returnTO;
      returnTO = SCILAB.execute( path_scilab, input_scilab.toArray( new String[]{} ) );
      if( ((MessageReturnTO) returnTO).getMessage().indexOf( "ans" ) == -1 )
        returnTO = SCILAB.execute( path_scilab, input_scilab.toArray( new String[]{} ) );
      
      List<Map<Neuronio, Double>> camada_de_saida = SaidaRNA.extrair_as_camadas_de_saida(returnTO);
      if( camada_de_saida.size() > 0 ){
        for( int i=0; i<rows.size(); i++ ){
          alterar_os_valores_da_camada_de_saida_da_rede_ifr(         sheet, rows.get(i), camada_de_saida.get(i*5+0));
          alterar_os_valores_da_camada_de_saida_da_rede_estocastico( sheet, rows.get(i), camada_de_saida.get(i*5+1));
          alterar_os_valores_da_camada_de_saida_da_rede_macd(        sheet, rows.get(i), camada_de_saida.get(i*5+2));
          alterar_os_valores_da_camada_de_saida_da_rede_histograma(  sheet, rows.get(i), camada_de_saida.get(i*5+3)); 
          alterar_os_valores_da_camada_de_saida_da_rede_obv(         sheet, rows.get(i), camada_de_saida.get(i*5+4));   
        }
      }else{
        System.out.println( "Error na camada de saída da planilha " + sheet.getSheetName() + ". Index da planilha igual a " + num_planilha + "." );
      }
      
      System.out.println();
    }
        
    w.write( new FileOutputStream( path_arquivo ) );
  }
  
  private void alterar_os_valores_da_camada_de_saida_da_rede_ifr( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
    for( IfrSaida saida : IfrSaida.values() ) 
      saida.set(sheet, row, camada_de_saida);
  }
  
  private void alterar_os_valores_da_camada_de_saida_da_rede_estocastico( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
    for( EstocasticoSaida saida : EstocasticoSaida.values() )
      saida.set(sheet, row, camada_de_saida);
  }
  
  private void alterar_os_valores_da_camada_de_saida_da_rede_macd( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
    for( MacdSaida saida : MacdSaida.values() )
      saida.set(sheet, row, camada_de_saida);
  }
  
  private void alterar_os_valores_da_camada_de_saida_da_rede_histograma( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
    for( HistogramaSaida saida : HistogramaSaida.values() )
      saida.set(sheet, row, camada_de_saida);
  }
  
  private void alterar_os_valores_da_camada_de_saida_da_rede_obv( Sheet sheet, int row, Map<Neuronio, Double> camada_de_saida ){
    for( ObvSaida saida : ObvSaida.values() )
      saida.set(sheet, row, camada_de_saida);
  }
  
  private String get_dados_ifr( Sheet sheet, int row ){
    List<Double> dados = get_dados(sheet, row, Entrada.ifr ); 
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String get_dados_estocastico( Sheet sheet, int row ){
    List<Double> dados = get_dados(sheet, row, Entrada.est ); 
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String get_dados_macd( Sheet sheet, int row ){
    List<Double> dados = get_dados(sheet, row, Entrada.linhamacd, Entrada.difmacd, Entrada.alphamacd, Entrada.difalphamacd ); 
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String get_dados_histograma( Sheet sheet, int row ){
    List<Double> dados = get_dados(sheet, row, Entrada.hist, Entrada.alphahist ); 
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private String get_dados_obv( Sheet sheet, int row ){
    List<Double> dados = get_dados(sheet, row, Entrada.alphaobv ); 
    String parametros = dados.toString();
    return parametros.substring( 1, parametros.length()-1 );
  }
  
  private List<Double> get_dados( Sheet sheet, int row, Entrada ... tipos  ){
    List<Double> valores = new ArrayList<Double>();
    
    for( int i=0; i<tipos.length; i++ )
      valores.add( sheet.getRow(row).getCell( tipos[i].col() ).getNumericCellValue() );
    
    return valores;
  }
  
}
