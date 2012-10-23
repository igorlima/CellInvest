import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.util.FileHelper;
import com.eatj.igorribeirolima.util.SCILAB;
import com.eatj.igorribeirolima.util.SCILAB_PROPERTIES;
import com.eatj.igorribeirolima.util.dadosinfomoney.DadosInfoMoney;
import com.eatj.igorribeirolima.util.dadosinfomoney.DadosDeEntradaModelagem;
import com.eatj.igorribeirolima.util.dadosinfomoney.dado.AnaliseTecnica;
import com.eatj.igorribeirolima.util.dadosinfomoney.dado.DadoFactory;
import com.eatj.igorribeirolima.util.regressao.Regressao;


public class Teste {
	
	@Test
	@Ignore
	public void teste1(){
		FileHelper.criar_arvore_de_diretorio_e_arquivo( SCILAB_PROPERTIES.getString( "scilab.root_rna" ) );
	}
	
	@Test
	@Ignore
	public void teste2(){
//		int exitVal=0;
//		Runtime rt = Runtime.getRuntime();
//		Process proc;
//		try {
//			proc = rt.exec( "ping localhost" );
//			
//			BufferedWriter writer = new BufferedWriter(  
//                    new OutputStreamWriter(proc.getOutputStream()) );  
//
//			String message = "exit";
//			writer.write(message, 0, 1);
//			System.out.println("\n\twaiting..."); 
//			
//			exitVal = proc.waitFor();
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		ReturnTO returnTO = SCILAB.execute( "\"C:\\Users\\igorlima\\Desktop\\dados para treinamento RNA\\RNA_ANALISE_TECNICA\\RNA_ANALISE_TECNICA.sce\"", "rna_indicador( 0.4, 0.6, 0.3, 0.35, 0.35, 0.10, 0.81, 0.90 )" );
//		ReturnTO returnTO = EXEC.exec( SCILAB.COMMAND_RNA );
		System.out.println( returnTO );
		FileHelper.getContentFile( "C:\\Users\\igorlima\\Desktop\\dados para treinamento RNA\\RNA_ANALISE_TECNICA\\RNA_ANALISE_TECNICA.sce" );
	}
	
	@Test
	@Ignore
  public void teste3() throws Exception{
	  DadosInfoMoney getDados = new DadosInfoMoney("SANB4");
	  getDados.convert();
	  DadosInfoMoney.get( "BBAS3", AnaliseTecnica.PRICE );
	  System.out.println( DadoFactory.getIntance(AnaliseTecnica.IFR, "990;23.8100;78.2000;-26.5477;05/01/12" ) );
    //System.out.println( CSV.get( "BBAS3", AnaliseTecnica.MACD_LINE ) );
  }
	
	@Test
	@Ignore
	public void teste_regressao(){
	  Regressao regressao = new Regressao();
	  Double[] x = new Double[]{ 1.00, 2.00, 3.00};
	  Double[] y = new Double[]{ 0.32, 0.41, 0.43};
	  
	  Double b = regressao.coeficiente_angular( Arrays.asList(x), Arrays.asList(y) );
	  System.out.println( b );
	  
	  b = regressao.angulo_da_reta( Arrays.asList(x), Arrays.asList(y) );
	  System.out.println( b );
	}
	
	@Test
	@Ignore
	public void teste_dados_de_entrada_das_modelagens(){
	  DadosInfoMoney dadosInfoMoney = new DadosInfoMoney( "bicb4" );
	  DadosDeEntradaModelagem dadosModelagem = new DadosDeEntradaModelagem(dadosInfoMoney);
	  
	  System.out.println( dadosModelagem.ifr() );
	  System.out.println( dadosModelagem.estocastico() );
	  System.out.println( dadosModelagem.valor_linha_macd() );
	  System.out.println( dadosModelagem.valor_linha_sinal() );
	  System.out.println( dadosModelagem.angulo_linha_macd() );
	  System.out.println( dadosModelagem.angulo_linha_sinal() );
	  System.out.println( dadosModelagem.histograma() );
	  System.out.println( dadosModelagem.angulo_histograma() );
	  System.out.println( dadosModelagem.angulo_obv() );
	}
}
