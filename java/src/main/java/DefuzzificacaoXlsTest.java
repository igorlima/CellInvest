import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Dominio;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.UnivConjFuzzyBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.UnivFuzzyMathmaticBO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/configJUnit/infrastructure-config.xml", "/configJUnit/mvc-config.xml", "/configJUnit/app-config.xml" })
public class DefuzzificacaoXlsTest {
  
  @Inject private UnivConjFuzzyBO bo;
  private UniversoDeDiscursoFuzzy universo;
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\Resultados.xls";
  private final int COL_DEFUZZIFICACAO = 28;
  
  private enum ConjuntoFuzzy{
    MuitoAlto(21),
    Alto(22),
    Neutro(23),
    Baixo(24),
    MuitoBaixo(25);
    
    private final int numero_da_coluna;
    private ConjuntoFuzzy( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    
    public int col(){
      return numero_da_coluna;
    }
  }
  
  @Test
  @Ignore
  public void calcular_defuzzificacao(){
    universo = bo.retrieve( "Indicador" );
    
    String planilha = "";
    int row = 0;;
    try {
      
      InputStream file_xls = new FileInputStream(path_arquivo);
      Workbook w = new HSSFWorkbook( file_xls );
      
      int quantidade_de_planilhas = w.getNumberOfSheets();
      for( int num_planilha = 0; num_planilha < quantidade_de_planilhas; num_planilha++ ){
        Sheet sheet = w.getSheetAt(num_planilha);
        planilha = sheet.getSheetName();
        System.out.println( planilha + "..." );
        
        for ( row = 1; row <= sheet.getLastRowNum(); row++) {
          
          Map<String, Map<String, Double>> conjFuzzySaida = get_conjunto_fuzzy_saida(sheet, row);
          Double defuzzificacao = defuzzyficar( conjFuzzySaida ).get( "Indicador" );
          sheet.getRow(row).getCell( COL_DEFUZZIFICACAO ).setCellValue( defuzzificacao ); 
          
        }
        
      }
      
      w.write( new FileOutputStream( path_arquivo ) );
    } catch (Exception e) {
      if( !planilha.isEmpty() && row>0 ) System.out.println( "<Error> Planilha: " + planilha + ". Linha: " + row );
      e.printStackTrace();
    } 
  }
  
  private Map<String, Map<String, Double>> get_conjunto_fuzzy_saida( Sheet sheet, int row ){
    Map<String, Map<String, Double>> conjFuzzySaida = new HashMap<String, Map<String,Double>>();
    
    Map<String, Double> conjFuzzySaidaIndicador = new HashMap<String, Double>();
    conjFuzzySaidaIndicador.put( "MuitoAlto", sheet.getRow(row).getCell( ConjuntoFuzzy.MuitoAlto.col() ).getNumericCellValue() );
    conjFuzzySaidaIndicador.put( "Alto", sheet.getRow(row).getCell( ConjuntoFuzzy.Alto.col() ).getNumericCellValue() );
    conjFuzzySaidaIndicador.put( "Neutro", sheet.getRow(row).getCell( ConjuntoFuzzy.Neutro.col() ).getNumericCellValue() );
    conjFuzzySaidaIndicador.put( "Baixo", sheet.getRow(row).getCell( ConjuntoFuzzy.Baixo.col() ).getNumericCellValue() );
    conjFuzzySaidaIndicador.put( "MuitoBaixo", sheet.getRow(row).getCell( ConjuntoFuzzy.MuitoBaixo.col() ).getNumericCellValue() );
    
    conjFuzzySaida.put( "Indicador", conjFuzzySaidaIndicador );
    return conjFuzzySaida;
  }
  
  public Map<String, Double> defuzzyficar( Map< String, Map<String, Double> > conjFuzzySaida ) throws Exception {
    double precisao = 1;
    
    Map<String, Double> retorno = new HashMap<String, Double>();
    for( String universoFuzzySaida : conjFuzzySaida.keySet() ){
      Dominio dominio = universo.getDominio();
      
      double i = dominio.isIncluiLimiteInferior() ? dominio.getLimiteInferior() : dominio.getLimiteInferior()+precisao;   
      
      double numerador = 0.0;
      double denominador = 0.0;
      while( dominio.isIncluiLimiteSuperior() ? i<=dominio.getLimiteSuperior() : i<dominio.getLimiteSuperior() ){
        
        double peso = maximo( minimo( i, universoFuzzySaida, conjFuzzySaida.get( universoFuzzySaida )) );
        numerador += i*peso;
        denominador += peso;
        
        i += precisao;
      }
      
      Double doubleDefuzzyficacao = numerador/denominador; 
      retorno.put( universoFuzzySaida, doubleDefuzzyficacao );
    }
    
    return retorno;
  }
  
  private Double maximo( Map<String, Double> conjFuzzy ){
    Double maximo = 0.0;
    
    Set<String> variaveisLinguisticas = conjFuzzy.keySet();
    for( String var : variaveisLinguisticas )
      maximo = Math.max( maximo, conjFuzzy.get(var) );
    
    return maximo;
  }
  
  private Map<String, Double> minimo( double i, String nmUniversoDiscurso, Map<String, Double> conjFuzzySaida ) throws Exception{
    Map<String, Double> fuzzyficacao = new UnivFuzzyMathmaticBO( universo ).fuzzyficar( i ).get( nmUniversoDiscurso );
    
    Set<String> variaveisLinguisticas = fuzzyficacao.keySet();
    for( String variavelLinguistica : variaveisLinguisticas )
      fuzzyficacao.put( variavelLinguistica, Math.min( fuzzyficacao.get(variavelLinguistica), conjFuzzySaida.get(variavelLinguistica) ) );
    
    return fuzzyficacao;
  }
}
