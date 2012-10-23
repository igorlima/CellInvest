package com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import java.util.regex.Pattern;

public class OpFuzzyMathmaticBO {
	
	public static final String letras = "([a-z]|[A-Z]|[_])+"; //verifica palavras somente com letras, com pelo menos um caracter, e sem espacos
	public static final String elementoRegraFuzzy = letras+"[=][=]"+letras; // <IF> tendencia==muitaAlta <THEN> indicador==alto;
	public static final String operadores = "\\&\\&|\\|\\|"; //operadores &&, ||
	
	public static final String operacaoSimples = "("+Fx.numeroReal+ ")(("+ operadores +")("+Fx.numeroReal+"))?"; //suporta apenas a operacao entre dois numeros
	public static final String operacaoSimplesAnd = "("+Fx.numeroReal+ ")((\\&\\&)("+Fx.numeroReal+"))"; // 0.3 && 0.5 , 0.1 && 0.25
	public static final String operacaoSimplesOr = "("+Fx.numeroReal+ ")((\\|\\|)("+Fx.numeroReal+"))"; // 0.3 && 0.5 , 0.1 && 0.25
	
	public static final String operacaoRegraSimples = "("+elementoRegraFuzzy+ ")(("+ operadores +")("+elementoRegraFuzzy+"))?"; //suporta apenas a operacao entre dois numeros
	public static final String primeiraValidacaoDaRegra = "[<][I][F][>]"+"([=()&|]|"+letras+")+"+"[<][T][H][E][N][>]"+"([=()&|]|"+letras+")+";
	
	
	private static Double calculaExpressaoSimples( String strOperacaoSimples ) throws Exception{
		
		if( Pattern.matches( operacaoSimples, strOperacaoSimples) ){
			//Caso seja um numero real
			if( Pattern.matches( Fx.numeroReal, strOperacaoSimples) ){
				return Double.parseDouble( strOperacaoSimples );
			//Caso seja uma operacao And
			}else if( Pattern.matches( operacaoSimplesAnd, strOperacaoSimples) ){
				String valores[] = strOperacaoSimples.split("\\&\\&");
				return Math.min( Double.parseDouble(valores[0]), Double.parseDouble(valores[1]) );
			//Caso seja uma operacao Or
			}else if( Pattern.matches( operacaoSimplesOr, strOperacaoSimples) ){
				String valores[] = strOperacaoSimples.split("\\|\\|");
				return Math.max( Double.parseDouble(valores[0]), Double.parseDouble(valores[1]) );
			//Caso contrario
			}else{
				throw new Exception( "Operacao nao encontrada. Ou operacao nao implementada." );
			}
		}else{
			throw new Exception( "Expressao com dois operando invalida" );
		}
		
	}
	
	/**
	 * 
	 * @param strBaseDeRegra
	 * @return
	 * 
	 * Exemplo:
	 *      "<IF> (OBV==MA && OBV==MA) || OBV==MA <THEN> FC==MA;"+
			"<IF> (OBV==A  && OBV==A ) || OBV==A  <THEN> FC==A;"+
			"<IF> (OBV==N  && OBV==N ) || OBV==N  <THEN> FC==N;"+
			"<IF> (OBV==B  && OBV==B ) || OBV==B  <THEN> FC==B;"+
			"<IF> (OBV==MB && OBV==MB) || OBV==MB <THEN> FC==MB;";
	 */
	public static boolean isBaseDeRegraValida( String strBaseDeRegra ){
		// Remover todos os espacos em branco
		strBaseDeRegra = strBaseDeRegra.replaceAll( "\\s", "" );
		
		String regras[] = strBaseDeRegra.split( ";" );
		for( String regra : regras)
			if( !isRegraValida(regra) )
				return false;
		
		return true;
	}
	
	/**
	 * 
	 * @param strRegra
	 * @return
	 * 
	 * Exemplo: "<IF> OBV==MA <THEN> FC==MA"
	 */
	private static boolean isRegraValida( String strRegra ){
		//Remover todos os espacos em branco
		strRegra = strRegra.replaceAll( "\\s", "" );
		
		if( !Pattern.matches( primeiraValidacaoDaRegra, strRegra) )
			return false;
		
		String antecedenteRegra = strRegra.split( "[<][T][H][E][N][>]" )[0].split( "[<][I][F][>]" )[1];
		String consequenteRegra = strRegra.split( "[<][T][H][E][N][>]" )[1] ;
		if( !isOperacaoDeRegraValida(antecedenteRegra) ) return false;
		if( !Pattern.matches( elementoRegraFuzzy, consequenteRegra) ) return false;
		
		return true;
	}
	
	private static boolean isOperacaoDeRegraValida( String strOperacaoRegra ){
		
		//verificar se os parenteses da expressao estao corretos
		if( !Fx.hasParentesesOK(strOperacaoRegra, '(', ')' ) )
			return false;
		
		//Remover todos os espacos em branco
		strOperacaoRegra = strOperacaoRegra.replaceAll( "\\s", "" );
		
		//'Calcular' todas as expressoes dentro dos parenteses
		while( strOperacaoRegra.contains( "(" ) ){
			try{
				int indexAbreParentese = strOperacaoRegra.lastIndexOf( '(' )+1;
				int indexFechaParentese = strOperacaoRegra.indexOf( ')', strOperacaoRegra.lastIndexOf( '(' ) );
				
				if (!Pattern.matches(operacaoRegraSimples, strOperacaoRegra.substring(
						indexAbreParentese, indexFechaParentese)))
					return false;
				
				strOperacaoRegra = strOperacaoRegra.substring(0, indexAbreParentese-1)
						+ "univFuzzy==conjFuzzy" //Para fins de verificacao, o resultado de todas as expressoes dentro dos parenteses serah considerada como univFuzzy==conjFuzzy 
						+ strOperacaoRegra.substring(indexFechaParentese+1);
			}catch( IndexOutOfBoundsException indexOutOfBoundsException ){
				return false;
			}
		}
		
		if( Pattern.matches(operacaoRegraSimples, strOperacaoRegra) )
			return true;
		else
			return false;
		
	}
	
	
	public static boolean isOperacaoValida( String strOperacao ){
		
		//verificar se os parenteses da expressao estao corretos
		if( !Fx.hasParentesesOK(strOperacao, '(', ')' ) )
			return false;
		
		//Remover todos os espacos em branco
		strOperacao = strOperacao.replaceAll( "\\s", "" );
		
		//'Calcular' todas as expressoes dentro dos parenteses
		while( strOperacao.contains( "(" ) ){
			try{
				int indexAbreParentese = strOperacao.lastIndexOf( '(' )+1;
				int indexFechaParentese = strOperacao.indexOf( ')', strOperacao.lastIndexOf( '(' ) );
				
				if (!Pattern.matches(operacaoSimples, strOperacao.substring(
						indexAbreParentese, indexFechaParentese)))
					return false;
				
				strOperacao = strOperacao.substring(0, indexAbreParentese-1)
						+ 0.0 //Para fins de verificacao, o resultado de todas as expressoes dentro dos parenteses serah considerada como 0.0 
						+ strOperacao.substring(indexFechaParentese+1);
			}catch( IndexOutOfBoundsException indexOutOfBoundsException ){
				return false;
			}
		}
		
		if( Pattern.matches(operacaoSimples, strOperacao) )
			return true;
		else
			return false;
		
	}
	
	public static Double calcular( String strOperacao ) throws Exception {
		if( !isOperacaoValida( strOperacao ) )
			throw new Exception( "Operacao Invalida." );
		
		strOperacao = strOperacao.replaceAll( "\\s", "" );
		
		while( strOperacao.contains( "(" ) ){
			int indexAbreParentese = strOperacao.lastIndexOf( '(' )+1;
			int indexFechaParentese = strOperacao.indexOf( ')', strOperacao.lastIndexOf( '(' ) );
			
			strOperacao = strOperacao.substring(0, indexAbreParentese-1)
					+ calculaExpressaoSimples(strOperacao.substring(
							indexAbreParentese, indexFechaParentese))
					+ strOperacao.substring(indexFechaParentese+1);
		}
		
		return calculaExpressaoSimples(strOperacao);
		
	}
}
