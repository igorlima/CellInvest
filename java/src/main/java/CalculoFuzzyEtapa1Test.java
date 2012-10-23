import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.SistemaFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.enumerator.Fuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.enumerator.VariavelLinguistca;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.SistemaFuzzyBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.UnivConjFuzzyBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.SistemaFuzzyMathmaticBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.UnivFuzzyMathmaticBO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/configJUnit/infrastructure-config.xml", "/configJUnit/mvc-config.xml", "/configJUnit/app-config.xml" })
public class CalculoFuzzyEtapa1Test {
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\ResultadoFuzzyFuzzyRna.xls";
  private UniversoDeDiscursoFuzzy universoIfr;
  private UniversoDeDiscursoFuzzy universoEstocastico;
  private UniversoDeDiscursoFuzzy universoAlphaObv;
  private SistemaFuzzy sistemaMacd;
  private SistemaFuzzy sistemaHistograma;
  
  @Inject private SistemaFuzzyBO sistemaFuzzyBO;
  @Inject private UnivConjFuzzyBO univConjFuzzyBO;
  
  @Before
  public void init(){
    universoIfr = univConjFuzzyBO.retrieve( VariavelLinguistca.IFR.nm() );
    universoEstocastico = univConjFuzzyBO.retrieve( VariavelLinguistca.Estocastico.nm() );
    universoAlphaObv = univConjFuzzyBO.retrieve( VariavelLinguistca.AlphaOBV.nm() );
    sistemaMacd = sistemaFuzzyBO.retrieve( VariavelLinguistca.MACD.nm() );
    sistemaHistograma = sistemaFuzzyBO.retrieve( VariavelLinguistca.Histograma.nm() );
  }
  
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
    MuitoAlto(11, Fuzzy.MuitoAlto), 
    Alto(12, Fuzzy.Alto), 
    Neutro(13, Fuzzy.Neutro), 
    Baixo(14, Fuzzy.Baixo), 
    MuitoBaixo(15, Fuzzy.MuitoBaixo);
    private final int numero_da_coluna;
    private final Fuzzy saida_fuzzy;
    private IfrSaida( int numero_da_coluna, Fuzzy saida_fuzzy ){
      this.numero_da_coluna = numero_da_coluna;
      this.saida_fuzzy = saida_fuzzy;
    }
    public void set( Sheet sheet, int row, Map<String, Double> conjunto_fuzzy_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( conjunto_fuzzy_de_saida.get( saida_fuzzy.nm() ) );
    }
  }
  
  private enum EstocasticoSaida{
    MuitoAlto(16, Fuzzy.MuitoAlto), 
    Alto(17, Fuzzy.Alto), 
    Neutro(18, Fuzzy.Neutro), 
    Baixo(19, Fuzzy.Baixo), 
    MuitoBaixo(20, Fuzzy.MuitoBaixo);
    private final int numero_da_coluna;
    private final Fuzzy saida_fuzzy;
    private EstocasticoSaida( int numero_da_coluna, Fuzzy saida_fuzzy ){
      this.numero_da_coluna = numero_da_coluna;
      this.saida_fuzzy = saida_fuzzy;
    }
    public void set( Sheet sheet, int row, Map<String, Double> conjunto_fuzzy_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( conjunto_fuzzy_de_saida.get( saida_fuzzy.nm() ) );
    }
  }
  
  private enum MacdSaida{
    MuitoAlto(21, Fuzzy.MuitoAlto), 
    Alto(22, Fuzzy.Alto), 
    Neutro(23, Fuzzy.Neutro), 
    Baixo(24, Fuzzy.Baixo), 
    MuitoBaixo(25, Fuzzy.MuitoBaixo);
    private final int numero_da_coluna;
    private final Fuzzy saida_fuzzy;
    private MacdSaida( int numero_da_coluna, Fuzzy saida_fuzzy ){
      this.numero_da_coluna = numero_da_coluna;
      this.saida_fuzzy = saida_fuzzy;
    }
    public void set( Sheet sheet, int row, Map<String, Double> conjunto_fuzzy_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( conjunto_fuzzy_de_saida.get( saida_fuzzy.nm() ) );
    }
  }
  
  private enum HistogramaSaida{
    MuitoAlto(26, Fuzzy.MuitoAlto), 
    Alto(27, Fuzzy.Alto), 
    Neutro(28, Fuzzy.Neutro), 
    Baixo(29, Fuzzy.Baixo), 
    MuitoBaixo(30, Fuzzy.MuitoBaixo);
    private final int numero_da_coluna;
    private final Fuzzy saida_fuzzy;
    private HistogramaSaida( int numero_da_coluna, Fuzzy saida_fuzzy ){
      this.numero_da_coluna = numero_da_coluna;
      this.saida_fuzzy = saida_fuzzy;
    }
    public void set( Sheet sheet, int row, Map<String, Double> conjunto_fuzzy_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( conjunto_fuzzy_de_saida.get( saida_fuzzy.nm() ) );
    }
  }
  
  private enum ObvSaida {
    MuitoAlto(31, Fuzzy.MuitoAlto), 
    Alto(32, Fuzzy.Alto), 
    Neutro(33, Fuzzy.Neutro), 
    Baixo(34, Fuzzy.Baixo), 
    MuitoBaixo(35, Fuzzy.MuitoBaixo);
    private final int numero_da_coluna;
    private final Fuzzy saida_fuzzy;
    private ObvSaida( int numero_da_coluna, Fuzzy saida_fuzzy ){
      this.numero_da_coluna = numero_da_coluna;
      this.saida_fuzzy = saida_fuzzy;
    }
    public void set( Sheet sheet, int row, Map<String, Double> conjunto_fuzzy_de_saida ){
      Cell cell = sheet.getRow(row).createCell( numero_da_coluna, Cell.CELL_TYPE_NUMERIC );
      cell.setCellValue( conjunto_fuzzy_de_saida.get( saida_fuzzy.nm() ) );
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
      
      for ( int row = 2; row <= sheet.getLastRowNum(); row++){
        try{
          alterar_os_valores_do_conjunto_fuzzy_ifr(sheet, row, dado_entrada_ifr(sheet, row));
          alterar_os_valores_do_conjunto_fuzzy_estocastico(sheet, row, dado_entrada_estocastico(sheet, row));
          alterar_os_valores_do_conjunto_fuzzy_macd(sheet, row, dados_entrada_macd(sheet, row));
          alterar_os_valores_do_conjunto_fuzzy_histograma(sheet, row, dados_entrada_histograma(sheet, row));
          alterar_os_valores_do_conjunto_fuzzy_obv(sheet, row, dado_entrada_obv(sheet, row));
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
  
  private void alterar_os_valores_do_conjunto_fuzzy_ifr( Sheet sheet, int row, Double dado ) throws Exception{
    Map<String, Double> conjunto_fuzzy_de_saida = new UnivFuzzyMathmaticBO(universoIfr).fuzzyficar(dado).get( VariavelLinguistca.IFR.nm() );
    for( IfrSaida saida : IfrSaida.values() ) 
      saida.set(sheet, row, conjunto_fuzzy_de_saida);
  }
  
  private void alterar_os_valores_do_conjunto_fuzzy_estocastico( Sheet sheet, int row, Double dado ) throws Exception{
    Map<String, Double> conjunto_fuzzy_de_saida = new UnivFuzzyMathmaticBO(universoEstocastico).fuzzyficar(dado).get( VariavelLinguistca.Estocastico.nm() );
    for( EstocasticoSaida saida : EstocasticoSaida.values() )
      saida.set(sheet, row, conjunto_fuzzy_de_saida);
  }
  
  private void alterar_os_valores_do_conjunto_fuzzy_macd( Sheet sheet, int row, List<Double> dados ) throws Exception{
    Map<String, Double> conjunto_fuzzy_de_saida = new SistemaFuzzyMathmaticBO(sistemaMacd).conjuntoFuzzySaida(dados).get( VariavelLinguistca.MACD.nm() );
    for( MacdSaida saida : MacdSaida.values() )
      saida.set(sheet, row, conjunto_fuzzy_de_saida);
  }
  
  private void alterar_os_valores_do_conjunto_fuzzy_histograma( Sheet sheet, int row, List<Double> dados ) throws Exception{
    Map<String, Double> conjunto_fuzzy_de_saida = new SistemaFuzzyMathmaticBO(sistemaHistograma).conjuntoFuzzySaida(dados).get( VariavelLinguistca.Histograma.nm() );
    for( HistogramaSaida saida : HistogramaSaida.values() )
      saida.set(sheet, row, conjunto_fuzzy_de_saida);
  }
  
  private void alterar_os_valores_do_conjunto_fuzzy_obv( Sheet sheet, int row, Double dado ) throws Exception{
    Map<String, Double> conjunto_fuzzy_de_saida = new UnivFuzzyMathmaticBO(universoAlphaObv).fuzzyficar(dado).get( VariavelLinguistca.AlphaOBV.nm() );
    for( ObvSaida saida : ObvSaida.values() )
      saida.set(sheet, row, conjunto_fuzzy_de_saida);
  }
  
  private Double dado_entrada_ifr( Sheet sheet, int row ){
    return sheet.getRow(row).getCell( Entrada.ifr.col() ).getNumericCellValue();
  }
  
  private Double dado_entrada_estocastico( Sheet sheet, int row ){
    return sheet.getRow(row).getCell( Entrada.est.col() ).getNumericCellValue();
  }
  
  private List<Double> dados_entrada_macd( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( Entrada.linhamacd.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( Entrada.difmacd.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( Entrada.alphamacd.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( Entrada.difalphamacd.col() ).getNumericCellValue() );
    return dados;
  }
  
  private List<Double> dados_entrada_histograma( Sheet sheet, int row ){
    List<Double> dados = new ArrayList<Double>();
    dados.add( sheet.getRow(row).getCell( Entrada.hist.col() ).getNumericCellValue() );
    dados.add( sheet.getRow(row).getCell( Entrada.alphahist.col() ).getNumericCellValue() );
    return dados;
  }
  
  private Double dado_entrada_obv( Sheet sheet, int row ){
    return sheet.getRow(row).getCell( Entrada.alphaobv.col() ).getNumericCellValue();
  }
  
}
