import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Ignore;
import org.junit.Test;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.util.ConversaoDeDados;
import com.eatj.igorribeirolima.util.SCILAB;

public class ValidacaoRnaXlsTest {
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\ResultadosRNA.xls";
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
  
  private enum Saida{
    MuitoAlto(11),
    Alto(12),
    Neutro(13),
    Baixo(14),
    MuitoBaixo(15);
    
    private final int numero_da_coluna;
    private Saida( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    
    public int col(){
      return numero_da_coluna;
    }
    
  }
  
  @Test
  @Ignore
  public void classificar() throws IOException{
      
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
          input_scilab.add( "rna_indicador( " + get_dados(sheet, row) + ")" );
          rows.add(row);
        } catch (Exception e) {
          System.out.println( "<Error> Planilha: " + sheet.getSheetName() +". Linha: " + row );
        }
      }
      
      ReturnTO returnTO;
      returnTO = SCILAB.execute( path_scilab, input_scilab.toArray( new String[]{} ) );
      if( ((MessageReturnTO) returnTO).getMessage().indexOf( "ans" ) == -1 )
        returnTO = SCILAB.execute( path_scilab, input_scilab.toArray( new String[]{} ) );
      
      List<Map<String, Double>> camada_de_saida = get_camada_de_saida( returnTO );
      for( int i=0; i<rows.size(); i++ )
        alterar_valores(sheet, rows.get(i), camada_de_saida.get(i));
      
      System.out.println();
    }
        
    w.write( new FileOutputStream( path_arquivo ) );
  }
  
  private List<Map<String, Double>> get_camada_de_saida(ReturnTO returnTO){
    if( !returnTO.isSuccessful() ) return null;
    
    List<Map<String, Double>> lista_camadas = new ArrayList<Map<String,Double>>();
    String str = ((MessageReturnTO) returnTO).getMessage();
    str = str.substring( str.indexOf( "ans" )+3 );
    str = str.replace( 'D', 'E' );
    
    String[] saidas = str.split( "ans" );
    for( int i=0; i<saidas.length; i++ ){
      Map<String, Double> camada_de_saida = new HashMap<String, Double>();
      
      List<Double> result = ConversaoDeDados.string2listdouble(saidas[i]);
      camada_de_saida.put( "MuitoAlto", result.get(0) );
      camada_de_saida.put( "Alto", result.get(1) );
      camada_de_saida.put( "Neutro", result.get(2) );
      camada_de_saida.put( "Baixo", result.get(3) );
      camada_de_saida.put( "MuitoBaixo", result.get(4) );
      
      lista_camadas.add( camada_de_saida );
    }
    
    return lista_camadas;
  }

  private void alterar_valores( Sheet sheet, int row, Map<String, Double> camada_de_saida ){
    Cell cell;
    
    cell = sheet.getRow(row).createCell( Saida.MuitoAlto.col(), Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( camada_de_saida.get("MuitoAlto") );
    cell = sheet.getRow(row).createCell( Saida.Alto.col(), Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( camada_de_saida.get("Alto") );
    cell = sheet.getRow(row).createCell( Saida.Neutro.col(), Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( camada_de_saida.get("Neutro") );
    cell = sheet.getRow(row).createCell( Saida.Baixo.col(), Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( camada_de_saida.get("Baixo") );
    cell = sheet.getRow(row).createCell( Saida.MuitoBaixo.col(), Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( camada_de_saida.get("MuitoBaixo") );
    
  }
  
  private String get_dados( Sheet sheet, int row ){
    List<Double> dados = get_dados(sheet, row, Entrada.ifr, Entrada.est, Entrada.linhamacd, Entrada.difmacd, Entrada.alphamacd, Entrada.difalphamacd, Entrada.hist, Entrada.alphahist, Entrada.alphaobv ); 
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
