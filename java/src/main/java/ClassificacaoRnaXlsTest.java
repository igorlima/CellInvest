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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.SistemaFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.SistemaFuzzyBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.UnivConjFuzzyBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.SistemaFuzzyMathmaticBO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/configJUnit/infrastructure-config.xml", "/configJUnit/mvc-config.xml", "/configJUnit/app-config.xml" })
public class ClassificacaoRnaXlsTest {
  
  @Inject private UnivConjFuzzyBO universoBO;
  @Inject private SistemaFuzzyBO sistemaFuzzyBO;
  
  private final String path_arquivo = "C:\\Users\\igorlima\\Desktop\\TreinamentoRNA.xls";
  private final int NUM_PLANILHA = 8;
  
  private enum TipoClassificacao{
    ifr(10, "IFR", new int[]{ 6, 7, 8, 9, 10} ),
    est(11, "Estocastico", new int[]{ 11, 12, 13, 14, 15} ),
    linhamacd(12, "DistanciaDoMacdAoEixoZero", new int[]{ 11, 12, 13, 14, 15} ),
    difmacd(13, "DiferencaMacdComSinal", new int[]{ 11, 12, 13, 14, 15} ),
    alphamacd(14, "AlphaMacd", new int[]{ 11, 12, 13, 14, 15} ),
    difalphamacd(15, "DiferencaDoAlphaMacd", new int[]{ 11, 12, 13, 14, 15} ),
    hist(16, "DistanciaDoHistogramaAoEixoZero", new int[]{ 11, 12, 13, 14, 15} ),
    alphahist(17, "AlphaHistograma", new int[]{ 11, 12, 13, 14, 15} ),
    alphaobv(18, "AlphaOBV", new int[]{ 4, 5, 6, 7, 8} ),
    Momento(-1, "Momento", new int[]{19,20,21,22,23} ),
    MACD(-1, "MACD", new int[]{13,14,15,16,17} ),
    Histograma(-1, "Histograma", new int[]{18,19,20,21,22} ),
    Sinal(-1, "Sinal", new int[]{24,25,26,27,28} ),
    Volume(-1, "Volume", new int[]{29,30,31,32,33} ),
    Indicador(-1, "Indicador", new int[]{34,35,36,37,38} );
    
    private final int numero_da_coluna;
    private final String nome;
    private final int[] entrada;
    private TipoClassificacao( int numero_da_coluna, String nome, int[] entrada ){
      this.numero_da_coluna = numero_da_coluna;
      this.nome = nome;
      this.entrada = entrada;
    }
    
    public int col(){
      return numero_da_coluna;
    }
    
    public String nm(){
      return nome;
    }
    
    public int[] e(){
      return entrada;
    }
  }
  
  @Test
  @Ignore
  public void classificar() throws IOException{
    //UniversoDeDiscursoFuzzy ifr = universoBO.retrieve( TipoClassificacao.ifr.nm() );
    //UniversoDeDiscursoFuzzy estocastico = universoBO.retrieve( TipoClassificacao.est.nm() );
    //UniversoDeDiscursoFuzzy alphaobv = universoBO.retrieve( TipoClassificacao.alphaobv.nm() );
    SistemaFuzzy momento = sistemaFuzzyBO.retrieve( TipoClassificacao.Momento.nm() );
    //SistemaFuzzy macd = sistemaFuzzyBO.retrieve( TipoClassificacao.MACD.nm() );
    //SistemaFuzzy histograma = sistemaFuzzyBO.retrieve( TipoClassificacao.Histograma.nm() );
    SistemaFuzzy sinal = sistemaFuzzyBO.retrieve( TipoClassificacao.Sinal.nm() );
    SistemaFuzzy volume = sistemaFuzzyBO.retrieve( TipoClassificacao.Volume.nm() );
    SistemaFuzzy indicador = sistemaFuzzyBO.retrieve( TipoClassificacao.Indicador.nm() );
    
    //UnivFuzzyMathmaticBO univFuzzyMathmaticBO;
    SistemaFuzzyMathmaticBO sistemaFuzzyMathmaticBO;
    Map<String, Map<String, Double>> conjFuzzySaida;
    
    int row = 0;;
      
      InputStream file_xls = new FileInputStream(path_arquivo);
      Workbook w = new HSSFWorkbook( file_xls );
      
      Sheet sheet = w.getSheetAt(NUM_PLANILHA);
      
      for ( row = 3; row <= sheet.getLastRowNum(); row++) {
        System.out.print( " " + row );
        
        try {
          //univFuzzyMathmaticBO = new UnivFuzzyMathmaticBO(ifr);
          //conjFuzzySaida = univFuzzyMathmaticBO.fuzzyficar(get_dado(sheet, row, TipoClassificacao.ifr));
          //alterar_valores(sheet, row, conjFuzzySaida, TipoClassificacao.ifr );
          
          //univFuzzyMathmaticBO = new UnivFuzzyMathmaticBO(estocastico);
          //conjFuzzySaida = univFuzzyMathmaticBO.fuzzyficar(get_dado(sheet, row, TipoClassificacao.est));
          //alterar_valores(sheet, row, conjFuzzySaida, TipoClassificacao.est );
          
          //univFuzzyMathmaticBO = new UnivFuzzyMathmaticBO(alphaobv);
          //conjFuzzySaida = univFuzzyMathmaticBO.fuzzyficar(get_dado(sheet, row, TipoClassificacao.alphaobv));
          //alterar_valores(sheet, row, conjFuzzySaida, TipoClassificacao.alphaobv );
          
          /*sistemaFuzzyMathmaticBO = new SistemaFuzzyMathmaticBO(macd);
          conjFuzzySaida = sistemaFuzzyMathmaticBO.conjuntoFuzzySaida( get_dados(sheet, row, TipoClassificacao.linhamacd, TipoClassificacao.difmacd, TipoClassificacao.alphamacd, TipoClassificacao.difalphamacd ));
          alterar_valores(sheet, row, conjFuzzySaida, TipoClassificacao.MACD );
          
          sistemaFuzzyMathmaticBO = new SistemaFuzzyMathmaticBO(histograma);
          conjFuzzySaida = sistemaFuzzyMathmaticBO.conjuntoFuzzySaida( get_dados(sheet, row, TipoClassificacao.hist, TipoClassificacao.alphahist ));
          alterar_valores(sheet, row, conjFuzzySaida, TipoClassificacao.Histograma );*/
          
          sistemaFuzzyMathmaticBO = new SistemaFuzzyMathmaticBO(momento);
          conjFuzzySaida = sistemaFuzzyMathmaticBO.conjuntoFuzzySaida( get_dados(sheet, row, TipoClassificacao.ifr, TipoClassificacao.est ));
          alterar_valores(sheet, row, conjFuzzySaida, TipoClassificacao.Momento );
          
          sistemaFuzzyMathmaticBO = new SistemaFuzzyMathmaticBO(sinal);
          conjFuzzySaida = sistemaFuzzyMathmaticBO.conjuntoFuzzySaida( get_dados(sheet, row, TipoClassificacao.linhamacd, TipoClassificacao.difmacd, TipoClassificacao.alphamacd, TipoClassificacao.difalphamacd, TipoClassificacao.hist, TipoClassificacao.alphahist ));
          alterar_valores(sheet, row, conjFuzzySaida, TipoClassificacao.Sinal );
          
          sistemaFuzzyMathmaticBO = new SistemaFuzzyMathmaticBO(volume);
          conjFuzzySaida = sistemaFuzzyMathmaticBO.conjuntoFuzzySaida( get_dados(sheet, row, TipoClassificacao.alphaobv ));
          alterar_valores(sheet, row, conjFuzzySaida, TipoClassificacao.Volume );
          
          sistemaFuzzyMathmaticBO = new SistemaFuzzyMathmaticBO(indicador);
          conjFuzzySaida = sistemaFuzzyMathmaticBO.conjuntoFuzzySaida( get_dados(sheet, row, TipoClassificacao.ifr, TipoClassificacao.est, TipoClassificacao.linhamacd, TipoClassificacao.difmacd, TipoClassificacao.alphamacd, TipoClassificacao.difalphamacd, TipoClassificacao.hist, TipoClassificacao.alphahist, TipoClassificacao.alphaobv ));
          alterar_valores(sheet, row, conjFuzzySaida, TipoClassificacao.Indicador );
        } catch (Exception e) {
          System.out.println( "<Error> Linha: " + row );
          continue;
        } 
        
      }
        
      w.write( new FileOutputStream( path_arquivo ) );
  }
  
  private void alterar_valores( Sheet sheet, int row, Map<String, Map<String, Double>> conjFuzzySaida, TipoClassificacao tipo ){
    Cell cell;
    
    cell = sheet.getRow(row).createCell( tipo.e()[0], Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( conjFuzzySaida.get(tipo.nm()).get("MuitoAlto") );
    cell = sheet.getRow(row).createCell( tipo.e()[1], Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( conjFuzzySaida.get(tipo.nm()).get("Alto") );
    cell = sheet.getRow(row).createCell( tipo.e()[2], Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( conjFuzzySaida.get(tipo.nm()).get("Neutro") );
    cell = sheet.getRow(row).createCell( tipo.e()[3], Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( conjFuzzySaida.get(tipo.nm()).get("Baixo") );
    cell = sheet.getRow(row).createCell( tipo.e()[4], Cell.CELL_TYPE_NUMERIC );
    cell.setCellValue( conjFuzzySaida.get(tipo.nm()).get("MuitoBaixo") );
    
  }
  
  private Double get_dado( Sheet sheet, int row, TipoClassificacao tipo ){
    return sheet.getRow(row).getCell( tipo.col() ).getNumericCellValue();
  }
  
  private List<Double> get_dados( Sheet sheet, int row, TipoClassificacao ... tipos  ){
    List<Double> valores = new ArrayList<Double>();
    
    for( int i=0; i<tipos.length; i++ )
      valores.add( sheet.getRow(row).getCell( tipos[i].col() ).getNumericCellValue() );
    
    return valores;
  }
  
}
