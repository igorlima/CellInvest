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
public class CalculoFuzzyEtapa2Test {
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\ResultadoFuzzyFuzzyRna.xls";
  private MaquinaInferencia maquinaMomento;
  private MaquinaInferencia maquinaSinal;
  private MaquinaInferencia maquinaVolume;
  
  @Inject private MaquinaInferenciaBO maquinaInferenciaBO;
  
  @Before
  public void init(){
    maquinaMomento = maquinaInferenciaBO.retrieve( VariavelLinguistca.Momento.toString() );
    maquinaSinal = maquinaInferenciaBO.retrieve( VariavelLinguistca.Sinal.toString() );
    maquinaVolume = maquinaInferenciaBO.retrieve( VariavelLinguistca.Volume.toString() );
  }
  
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
    MuitoAlto(36, Fuzzy.MuitoAlto), 
    Alto(37, Fuzzy.Alto), 
    Neutro(38, Fuzzy.Neutro), 
    Baixo(39, Fuzzy.Baixo), 
    MuitoBaixo(40, Fuzzy.MuitoBaixo);
    private final int numero_da_coluna;
    private final Fuzzy saida_fuzzy;
    private MomentoSaida( int numero_da_coluna, Fuzzy saida_fuzzy ){
      this.numero_da_coluna = numero_da_coluna;
      this.saida_fuzzy = saida_fuzzy;
    }
    public int set( Sheet sheet, int row, Map<String, Double> conjunto_fuzzy_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( conjunto_fuzzy_de_saida.get( saida_fuzzy.nm() ) );
      return numero_da_coluna; 
    }
  }
  
  private enum SinalSaida {
    MuitoAlto(41, Fuzzy.MuitoAlto), 
    Alto(42, Fuzzy.Alto), 
    Neutro(43, Fuzzy.Neutro), 
    Baixo(44, Fuzzy.Baixo), 
    MuitoBaixo(45, Fuzzy.MuitoBaixo);
    private final int numero_da_coluna;
    private final Fuzzy saida_fuzzy;
    private SinalSaida( int numero_da_coluna, Fuzzy saida_fuzzy ){
      this.numero_da_coluna = numero_da_coluna;
      this.saida_fuzzy = saida_fuzzy;
    }
    public int set( Sheet sheet, int row, Map<String, Double> conjunto_fuzzy_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( conjunto_fuzzy_de_saida.get( saida_fuzzy.nm() ) );
      return numero_da_coluna; 
    }
  }
  
  private enum VolumeSaida {
    MuitoAlto(46, Fuzzy.MuitoAlto), 
    Alto(47, Fuzzy.Alto), 
    Neutro(48, Fuzzy.Neutro), 
    Baixo(49, Fuzzy.Baixo), 
    MuitoBaixo(50, Fuzzy.MuitoBaixo);
    private final int numero_da_coluna;
    private final Fuzzy saida_fuzzy;
    private VolumeSaida( int numero_da_coluna, Fuzzy saida_fuzzy ){
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
          alterar_os_valores_do_conjunto_fuzzy_momento(sheet, row, dados_de_entrada_da_maquina_de_inferencia_Momento(sheet, row));
          alterar_os_valores_do_conjunto_fuzzy_sinal(sheet, row, dados_de_entrada_da_maquina_de_inferencia_Sinal(sheet, row));
          alterar_os_valores_do_conjunto_fuzzy_volume(sheet, row, dados_de_entrada_da_maquina_de_inferencia_Volume(sheet, row));
        } catch (Exception e) {
          System.out.println( "<Error> Planilha: " + sheet.getSheetName() +". Linha: " + row );
        }
      }
      
      System.out.println();
    }
        
    System.out.println( "salvando arquivo..." );
    w.write( new FileOutputStream( path_arquivo ) );
    System.out.println( "FIM!!!" );
  }
  
  private void alterar_os_valores_do_conjunto_fuzzy_momento( Sheet sheet, int row, Map<String, Map<String,Double>> dados ) throws Exception{
    Map<String, Double> conjunto_fuzzy_de_saida = new MaqInferenciaMathmaticBO(maquinaMomento).conjuntoFuzzySaida( dados ).get( VariavelLinguistca.Momento.nm() );
    for( MomentoSaida saida : MomentoSaida.values() ) 
      saida.set(sheet, row, conjunto_fuzzy_de_saida);
  }
  
  private void alterar_os_valores_do_conjunto_fuzzy_sinal( Sheet sheet, int row, Map<String, Map<String,Double>> dados ) throws Exception{
    Map<String, Double> conjunto_fuzzy_de_saida = new MaqInferenciaMathmaticBO(maquinaSinal).conjuntoFuzzySaida( dados ).get( VariavelLinguistca.Sinal.nm() );
    for( SinalSaida saida : SinalSaida.values() )
      saida.set(sheet, row, conjunto_fuzzy_de_saida);
  }
  
  private void alterar_os_valores_do_conjunto_fuzzy_volume( Sheet sheet, int row, Map<String, Map<String,Double>> dados ) throws Exception{
    Map<String, Double> conjunto_fuzzy_de_saida = new MaqInferenciaMathmaticBO(maquinaVolume).conjuntoFuzzySaida( dados ).get( VariavelLinguistca.Volume.nm() );
    for( VolumeSaida saida : VolumeSaida.values() )
      saida.set(sheet, row, conjunto_fuzzy_de_saida);
  }
  
  private Map<String, Map<String,Double>> dados_de_entrada_da_maquina_de_inferencia_Momento( Sheet sheet, int row ){
    Map<String, Map<String,Double>> conjFuzzyEntrada = new HashMap<String, Map<String,Double>>();
    conjFuzzyEntrada.put( VariavelLinguistca.IFR.toString(), dados_de_entrada_ifr(sheet, row) );
    conjFuzzyEntrada.put( VariavelLinguistca.Estocastico.toString(), dados_de_entrada_estocastico(sheet, row) );
    return conjFuzzyEntrada;
  }
  
  private Map<String, Map<String,Double>> dados_de_entrada_da_maquina_de_inferencia_Sinal( Sheet sheet, int row ){
    Map<String, Map<String,Double>> conjFuzzyEntrada = new HashMap<String, Map<String,Double>>();
    conjFuzzyEntrada.put( VariavelLinguistca.MACD.toString(), dados_de_entrada_macd(sheet, row) );
    conjFuzzyEntrada.put( VariavelLinguistca.Histograma.toString(), dados_de_entrada_histograma(sheet, row) );
    return conjFuzzyEntrada;
  }
  
  private Map<String, Map<String,Double>> dados_de_entrada_da_maquina_de_inferencia_Volume( Sheet sheet, int row ){
    Map<String, Map<String,Double>> conjFuzzyEntrada = new HashMap<String, Map<String,Double>>();
    conjFuzzyEntrada.put( VariavelLinguistca.AlphaOBV.toString(), dados_de_entrada_alpha_bv(sheet, row) );
    return conjFuzzyEntrada;
  }
  
  private Map<String,Double> dados_de_entrada_ifr( Sheet sheet, int row ){
    Map<String,Double> conjFuzzyEntrada = new HashMap<String, Double>();
    conjFuzzyEntrada.put( IfrSaida.MuitoAlto.toString(), sheet.getRow(row).getCell( IfrSaida.MuitoAlto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( IfrSaida.Alto.toString(), sheet.getRow(row).getCell( IfrSaida.Alto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( IfrSaida.Neutro.toString(), sheet.getRow(row).getCell( IfrSaida.Neutro.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( IfrSaida.Baixo.toString(), sheet.getRow(row).getCell( IfrSaida.Baixo.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( IfrSaida.MuitoBaixo.toString(), sheet.getRow(row).getCell( IfrSaida.MuitoBaixo.col() ).getNumericCellValue() );
    return conjFuzzyEntrada;
  }
  
  private Map<String,Double> dados_de_entrada_estocastico( Sheet sheet, int row ){
    Map<String,Double> conjFuzzyEntrada = new HashMap<String, Double>();
    conjFuzzyEntrada.put( EstocasticoSaida.MuitoAlto.toString(), sheet.getRow(row).getCell( EstocasticoSaida.MuitoAlto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( EstocasticoSaida.Alto.toString(), sheet.getRow(row).getCell( EstocasticoSaida.Alto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( EstocasticoSaida.Neutro.toString(), sheet.getRow(row).getCell( EstocasticoSaida.Neutro.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( EstocasticoSaida.Baixo.toString(), sheet.getRow(row).getCell( EstocasticoSaida.Baixo.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( EstocasticoSaida.MuitoBaixo.toString(), sheet.getRow(row).getCell( EstocasticoSaida.MuitoBaixo.col() ).getNumericCellValue() );
    return conjFuzzyEntrada;
  }
  
  private Map<String,Double> dados_de_entrada_macd( Sheet sheet, int row ){
    Map<String,Double> conjFuzzyEntrada = new HashMap<String, Double>();
    conjFuzzyEntrada.put( MacdSaida.MuitoAlto.toString(), sheet.getRow(row).getCell( MacdSaida.MuitoAlto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( MacdSaida.Alto.toString(), sheet.getRow(row).getCell( MacdSaida.Alto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( MacdSaida.Neutro.toString(), sheet.getRow(row).getCell( MacdSaida.Neutro.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( MacdSaida.Baixo.toString(), sheet.getRow(row).getCell( MacdSaida.Baixo.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( MacdSaida.MuitoBaixo.toString(), sheet.getRow(row).getCell( MacdSaida.MuitoBaixo.col() ).getNumericCellValue() );
    return conjFuzzyEntrada;
  }
  
  private Map<String,Double> dados_de_entrada_histograma( Sheet sheet, int row ){
    Map<String,Double> conjFuzzyEntrada = new HashMap<String, Double>();
    conjFuzzyEntrada.put( HistogramaSaida.MuitoAlto.toString(), sheet.getRow(row).getCell( HistogramaSaida.MuitoAlto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( HistogramaSaida.Alto.toString(), sheet.getRow(row).getCell( HistogramaSaida.Alto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( HistogramaSaida.Neutro.toString(), sheet.getRow(row).getCell( HistogramaSaida.Neutro.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( HistogramaSaida.Baixo.toString(), sheet.getRow(row).getCell( HistogramaSaida.Baixo.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( HistogramaSaida.MuitoBaixo.toString(), sheet.getRow(row).getCell( HistogramaSaida.MuitoBaixo.col() ).getNumericCellValue() );
    return conjFuzzyEntrada;
  }
  
  private Map<String,Double> dados_de_entrada_alpha_bv( Sheet sheet, int row ){
    Map<String,Double> conjFuzzyEntrada = new HashMap<String, Double>();
    conjFuzzyEntrada.put( ObvSaida.MuitoAlto.toString(), sheet.getRow(row).getCell( ObvSaida.MuitoAlto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( ObvSaida.Alto.toString(), sheet.getRow(row).getCell( ObvSaida.Alto.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( ObvSaida.Neutro.toString(), sheet.getRow(row).getCell( ObvSaida.Neutro.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( ObvSaida.Baixo.toString(), sheet.getRow(row).getCell( ObvSaida.Baixo.col() ).getNumericCellValue() );
    conjFuzzyEntrada.put( ObvSaida.MuitoBaixo.toString(), sheet.getRow(row).getCell( ObvSaida.MuitoBaixo.col() ).getNumericCellValue() );
    return conjFuzzyEntrada;
  }
  
  
}
