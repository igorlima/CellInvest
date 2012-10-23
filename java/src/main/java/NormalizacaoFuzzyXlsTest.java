import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Ignore;
import org.junit.Test;

import com.eatj.igorribeirolima.util.dadosinfomoney.NormalizarIndicadores;


public class NormalizacaoFuzzyXlsTest {
  
  private final String path_arquivo_leitura_xls = "C:\\Users\\igorlima\\Desktop\\Resultados.xls";
  private final String path_arquivo_escrita_xls = "C:\\Users\\igorlima\\Desktop\\Resultados.xls";
  private final int K = 7;
  
  private enum TipoIndicador{
    IFR(2),
    ESTOCASTICO(3),
    MACD_LINE(4),
    MACD_SINAL(5),
    HISTOGRAMA(6),
    OBV(7);
    
    private final int numero_da_coluna;
    private TipoIndicador( int numero_da_coluna ){
      this.numero_da_coluna = numero_da_coluna;
    }
    
    public int col(){
      return numero_da_coluna;
    }
  }
  
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
  
  @Test
  @Ignore
  public void calcular_valores_de_entrada_da_modelagem_fuzzy(){
    
    String planilha = "";
    int row = 0;;
    try {
      
      InputStream file_xls = new FileInputStream(path_arquivo_leitura_xls);
      Workbook w = new HSSFWorkbook( file_xls );
      
      int quantidade_de_planilhas = w.getNumberOfSheets();
      for( int num_planilha = 0; num_planilha < quantidade_de_planilhas; num_planilha++ ){
        Sheet sheet = w.getSheetAt(num_planilha);
        planilha = sheet.getSheetName();
        System.out.println( planilha + "..." );
        
        for ( row = 1; row <= sheet.getLastRowNum()-K+1; row++) {
          NormalizarIndicadores normalizacao = get_valores_normalizados(sheet, row);
          alterar_valores_de_normalizacao_da_planilha(sheet, row, normalizacao);
        }
        
      }
      
      
      w.write( new FileOutputStream( path_arquivo_escrita_xls ) );
    } catch (Exception e) {
      if( !planilha.isEmpty() && row>0 ) System.out.println( "<Error> Planilha: " + planilha + ". Linha: " + row );
      e.printStackTrace();
    } 
  }
  
  private void alterar_valores_de_normalizacao_da_planilha( Sheet sheet, int row, NormalizarIndicadores normalizacao ){
    
    sheet.getRow(row).getCell( TipoNormalizacao.IFR.col() ).setCellValue( normalizacao.ifr() );
    sheet.getRow(row).getCell( TipoNormalizacao.ESTOCASTICO.col() ).setCellValue( normalizacao.estocastico() );
    sheet.getRow(row).getCell( TipoNormalizacao.DISTANCIA_MACD_AO_EIXO_ZERO.col() ).setCellValue( normalizacao.distancia_do_valor_macd_ao_eixo_zero() );
    sheet.getRow(row).getCell( TipoNormalizacao.DIFERENCA_LINHA_MACD_E_SINAL.col() ).setCellValue( normalizacao.valor_linha_macd() - normalizacao.valor_linha_sinal() );
    sheet.getRow(row).getCell( TipoNormalizacao.ALPHA_MACD.col() ).setCellValue( normalizacao.angulo_linha_macd() );
    sheet.getRow(row).getCell( TipoNormalizacao.DIFERENCA_ALPHA_MACD_E_ALPHA_SINAL.col() ).setCellValue( normalizacao.angulo_linha_macd() - normalizacao.angulo_linha_sinal() );
    sheet.getRow(row).getCell( TipoNormalizacao.DISTANCIA_HISTOGRAMA_AO_EIXO.col() ).setCellValue( normalizacao.histograma() );
    sheet.getRow(row).getCell( TipoNormalizacao.ALPHA_HISTOGRAMA.col() ).setCellValue( normalizacao.angulo_histograma() );
    sheet.getRow(row).getCell( TipoNormalizacao.ALPHA_OBV.col() ).setCellValue( normalizacao.angulo_obv() );
    
  }
  
  private NormalizarIndicadores get_valores_normalizados( Sheet sheet, int row ){
    List<Double> ifr = criar_lista_de_indicadores( sheet, row, TipoIndicador.IFR ); 
    List<Double> estocastico = criar_lista_de_indicadores( sheet, row, TipoIndicador.ESTOCASTICO ); 
    List<Double> macdline = criar_lista_de_indicadores( sheet, row, TipoIndicador.MACD_LINE ); 
    List<Double> macdsinal = criar_lista_de_indicadores( sheet, row, TipoIndicador.MACD_SINAL ); 
    List<Double> histograma = criar_lista_de_indicadores( sheet, row, TipoIndicador.HISTOGRAMA ); 
    List<Double> obv = criar_lista_de_indicadores( sheet, row, TipoIndicador.OBV ); 
    
    NormalizarIndicadores normalizacao = new NormalizarIndicadores( ifr, estocastico, macdline, macdsinal, histograma, obv );
    return normalizacao;
  }
  
  private List<Double> criar_lista_de_indicadores( Sheet sheet, int row, TipoIndicador tipo ){
    List<Double> dados = new ArrayList<Double>();
    for( int i=row; i< row+K; i++ ){
      dados.add( sheet.getRow(i).getCell(tipo.col()).getNumericCellValue() );
    }
    return dados;
  }
}
