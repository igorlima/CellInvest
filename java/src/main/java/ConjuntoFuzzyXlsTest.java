import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.SistemaFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.SistemaFuzzyBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.SistemaFuzzyMathmaticBO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/configJUnit/infrastructure-config.xml", "/configJUnit/mvc-config.xml", "/configJUnit/app-config.xml" })
public class ConjuntoFuzzyXlsTest {
  
  @Inject private SistemaFuzzyBO bo;
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\ResultadosFuzzy.xls";
  
  private enum TipoNormalizacao{
    IFR(10),
    ESTOCASTICO(11),
    DISTANCIA_MACD_AO_EIXO_ZERO(12),
    DIFERENCA_LINHA_MACD_E_SINAL(13),
    ALPHA_MACD(14),
    DIFERENCA_ALPHA_MACD_E_ALPHA_SINAL(15),
    DISTANCIA_HISTOGRAMA_AO_EIXO(16),
    ALPHA_HISTOGRAMA(17),
    ALPHA_OBV(18);
    
    private final int numero_da_coluna;
    private TipoNormalizacao( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    
    public int col(){
      return numero_da_coluna;
    }
  }
  
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
  public void calcular_conjunto_fuzzy(){
    SistemaFuzzy sistemaFuzzy = bo.retrieve("Indicador");
    
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
          
          List<Double> dadosDeEntrada = get_valores_normalizados(sheet, row);
          if( dadosDeEntrada == null ) continue;
          SistemaFuzzyMathmaticBO sistemaFuzzyMathmaticBO = new SistemaFuzzyMathmaticBO(sistemaFuzzy);
          Map<String, Map<String, Double>> conjFuzzySaida = sistemaFuzzyMathmaticBO.conjuntoFuzzySaida(dadosDeEntrada);
          alterar_valores_de_normalizacao_da_planilha(sheet, row, conjFuzzySaida );
          
        }
        
      }
      
      w.write( new FileOutputStream( path_arquivo ) );
    } catch (Exception e) {
      if( !planilha.isEmpty() && row>0 ) System.out.println( "<Error> Planilha: " + planilha + ". Linha: " + row );
      e.printStackTrace();
    } 
  }
  
  private void alterar_valores_de_normalizacao_da_planilha( Sheet sheet, int row, Map<String, Map<String, Double>> conjFuzzySaida ){
    
    sheet.getRow(row).getCell( ConjuntoFuzzy.MuitoAlto.col() ).setCellValue( conjFuzzySaida.get("Indicador").get("MuitoAlto") );
    sheet.getRow(row).getCell( ConjuntoFuzzy.Alto.col() ).setCellValue( conjFuzzySaida.get("Indicador").get("Alto") );
    sheet.getRow(row).getCell( ConjuntoFuzzy.Neutro.col() ).setCellValue( conjFuzzySaida.get("Indicador").get("Neutro") );
    sheet.getRow(row).getCell( ConjuntoFuzzy.Baixo.col() ).setCellValue( conjFuzzySaida.get("Indicador").get("Baixo") );
    sheet.getRow(row).getCell( ConjuntoFuzzy.MuitoBaixo.col() ).setCellValue( conjFuzzySaida.get("Indicador").get("MuitoBaixo") );
    
  }
  
  private List<Double> get_valores_normalizados( Sheet sheet, int row ){
    List<Double> valores = new ArrayList<Double>();
    
    try{
      valores.add( sheet.getRow(row).getCell( TipoNormalizacao.IFR.col() ).getNumericCellValue() );
      valores.add( sheet.getRow(row).getCell( TipoNormalizacao.ESTOCASTICO.col() ).getNumericCellValue() );
      valores.add( sheet.getRow(row).getCell( TipoNormalizacao.DISTANCIA_MACD_AO_EIXO_ZERO.col() ).getNumericCellValue() );
      valores.add( sheet.getRow(row).getCell( TipoNormalizacao.DIFERENCA_LINHA_MACD_E_SINAL.col() ).getNumericCellValue() );
      valores.add( sheet.getRow(row).getCell( TipoNormalizacao.ALPHA_MACD.col() ).getNumericCellValue() );
      valores.add( sheet.getRow(row).getCell( TipoNormalizacao.DIFERENCA_ALPHA_MACD_E_ALPHA_SINAL.col() ).getNumericCellValue() );
      valores.add( sheet.getRow(row).getCell( TipoNormalizacao.DISTANCIA_HISTOGRAMA_AO_EIXO.col() ).getNumericCellValue() );
      valores.add( sheet.getRow(row).getCell( TipoNormalizacao.ALPHA_HISTOGRAMA.col() ).getNumericCellValue() );
      valores.add( sheet.getRow(row).getCell( TipoNormalizacao.ALPHA_OBV.col() ).getNumericCellValue() );
    }catch( NullPointerException exception ){
      System.out.println( "<Error: NullPointerException> Planilha: " + sheet.getSheetName() + ". Linha: " + row );
      return null;
    }
    
    return valores;
  }
  
}
