import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.MaquinaInferencia;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.enumerator.Fuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.enumerator.VariavelLinguistca;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.MaquinaInferenciaBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.MaqInferenciaMathmaticBO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/configJUnit/infrastructure-config.xml", "/configJUnit/mvc-config.xml", "/configJUnit/app-config.xml" })
public class CalculoFuzzyEtapa3Test {
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\ResultadoRnaRnaFuzzy.xls";
  MaquinaInferencia maquinaIndicador;
  
  @Inject private MaquinaInferenciaBO maquinaInferenciaBO;
  
  @Before
  public void init(){
    maquinaIndicador = maquinaInferenciaBO.retrieve( VariavelLinguistca.Indicador.toString() );
  }
  
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
    private SinalSaida( int numero_da_coluna){
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
    MuitoAlto(51, Fuzzy.MuitoAlto), 
    Alto(52, Fuzzy.Alto), 
    Neutro(53, Fuzzy.Neutro), 
    Baixo(54, Fuzzy.Baixo), 
    MuitoBaixo(55, Fuzzy.MuitoBaixo);
    private final int numero_da_coluna;
    private final Fuzzy saida_fuzzy;
    private IndicadorSaida( int numero_da_coluna, Fuzzy saida_fuzzy ){
      this.numero_da_coluna = numero_da_coluna;
      this.saida_fuzzy = saida_fuzzy;
    }
    public int set( Sheet sheet, int row, Map<String, Double> conjunto_fuzzy_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( conjunto_fuzzy_de_saida.get( saida_fuzzy.nm() ) );
      return numero_da_coluna; 
    }
  }
  
  @Test
  public void calcular_saida_da_segunda_etapa() throws IOException{
    
    InputStream file_xls = new FileInputStream(path_arquivo);
    Workbook w = new HSSFWorkbook( file_xls );
    
    int quantidade_de_planilhas = w.getNumberOfSheets();
    for( int num_planilha = 0; num_planilha < quantidade_de_planilhas; num_planilha++ ){
      
      Sheet sheet = w.getSheetAt(num_planilha);
      System.out.println( sheet.getSheetName() + "..." );
      
      for ( int row = 2; row <= sheet.getLastRowNum(); row++){
        try{
          alterar_os_valores_do_conjunto_fuzzy_indicador( sheet, row, dados_de_entrada_da_maquina_de_inferencia_Indicador(sheet, row) );
        } catch (Exception e) {
          System.out.println( "<Error> Planilha: " + sheet.getSheetName() +". Linha: " + row );
        }
      }
      
      System.out.println();
    }
    
    w.write( new FileOutputStream( path_arquivo ) );
    System.out.println( "salvando arquivo..." );
    System.out.println( "FIM!!!" );
  }
  
  private void alterar_os_valores_do_conjunto_fuzzy_indicador( Sheet sheet, int row, Map<String, Map<String,Double>> dados ) throws Exception{
    Map<String, Double> conjunto_fuzzy_de_saida = new MaqInferenciaMathmaticBO(maquinaIndicador).conjuntoFuzzySaida( dados ).get( VariavelLinguistca.Indicador.nm() );
    for( IndicadorSaida saida : IndicadorSaida.values() ) 
      saida.set(sheet, row, conjunto_fuzzy_de_saida);
  }
  
  private Map<String, Map<String,Double>> dados_de_entrada_da_maquina_de_inferencia_Indicador( Sheet sheet, int row ){
    Map<String, Map<String,Double>> conjFuzzyEntrada = new HashMap<String, Map<String,Double>>();
    conjFuzzyEntrada.put( VariavelLinguistca.Momento.toString(), dados_de_entrada_momento(sheet, row) );
    conjFuzzyEntrada.put( VariavelLinguistca.Sinal.toString(), dados_de_entrada_sinal(sheet, row) );
    conjFuzzyEntrada.put( VariavelLinguistca.Volume.toString(), dados_de_entrada_volume(sheet, row) );
    return conjFuzzyEntrada;
  }
  
  private Map<String,Double> dados_de_entrada_momento( Sheet sheet, int row ){
    Map<String,Double> conjFuzzyEntrada = new HashMap<String, Double>();
    conjFuzzyEntrada.put( MomentoSaida.MuitoAlto.toString(), sheet.getRow(row).getCell( MomentoSaida.MuitoAlto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( MomentoSaida.Alto.toString(), sheet.getRow(row).getCell( MomentoSaida.Alto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( MomentoSaida.Neutro.toString(), sheet.getRow(row).getCell( MomentoSaida.Neutro.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( MomentoSaida.Baixo.toString(), sheet.getRow(row).getCell( MomentoSaida.Baixo.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( MomentoSaida.MuitoBaixo.toString(), sheet.getRow(row).getCell( MomentoSaida.MuitoBaixo.col() ).getNumericCellValue() );
    return conjFuzzyEntrada;
  }
  
  private Map<String,Double> dados_de_entrada_sinal( Sheet sheet, int row ){
    Map<String,Double> conjFuzzyEntrada = new HashMap<String, Double>();
    conjFuzzyEntrada.put( SinalSaida.MuitoAlto.toString(), sheet.getRow(row).getCell( SinalSaida.MuitoAlto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( SinalSaida.Alto.toString(), sheet.getRow(row).getCell( SinalSaida.Alto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( SinalSaida.Neutro.toString(), sheet.getRow(row).getCell( SinalSaida.Neutro.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( SinalSaida.Baixo.toString(), sheet.getRow(row).getCell( SinalSaida.Baixo.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( SinalSaida.MuitoBaixo.toString(), sheet.getRow(row).getCell( SinalSaida.MuitoBaixo.col() ).getNumericCellValue() );
    return conjFuzzyEntrada;
  }
  
  private Map<String,Double> dados_de_entrada_volume( Sheet sheet, int row ){
    Map<String,Double> conjFuzzyEntrada = new HashMap<String, Double>();
    conjFuzzyEntrada.put( VolumeSaida.MuitoAlto.toString(), sheet.getRow(row).getCell( VolumeSaida.MuitoAlto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( VolumeSaida.Alto.toString(), sheet.getRow(row).getCell( VolumeSaida.Alto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( VolumeSaida.Neutro.toString(), sheet.getRow(row).getCell( VolumeSaida.Neutro.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( VolumeSaida.Baixo.toString(), sheet.getRow(row).getCell( VolumeSaida.Baixo.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( VolumeSaida.MuitoBaixo.toString(), sheet.getRow(row).getCell( VolumeSaida.MuitoBaixo.col() ).getNumericCellValue() );
    return conjFuzzyEntrada;
  }
  
}
