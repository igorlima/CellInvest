package com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fx {
	//equacao da resta > y=y0+(x-x0)/(m)
	
	public static final String operadores = "\\+|\\-|\\/|\\*|\\^"; //operadores +, -, \, *, ^
	public static final String notacaoCientifica = "(E\\-?[0-9]+)?"; //1.4210854715202004E-14
	public static final String numeroReal = "\\-?[0-9]+(\\.[0-9]+)?"+notacaoCientifica; //aceita somente numero Real
	public static final String digito = numeroReal+"|x"; //numero reais e variavel x
	
	public static final String exprSimplesExp = "("+digito+ ")((\\^)("+digito+"))"; // x^x , 9.9^x
	public static final String exprSimplesMult = "("+digito+ ")((\\*)("+digito+"))"; // x*x , 9.9 * x
	public static final String exprSimplesDiv = "("+digito+ ")((\\/)("+digito+"))"; // x/x , 9.9 / x
	public static final String exprSimplesSub = "("+digito+ ")((\\-)("+digito+"))"; // x-x , 9.9 - x
	public static final String exprSimplesSoma = "("+digito+ ")((\\+)("+digito+"))"; // x+x , 9.9 + x
	
	public static final String expressaoSimples = "("+digito+ ")(("+ operadores +")("+digito+"))"; //suporta apenas a operacao entre dois numeros
	public static final String expressao = "("+digito+ ")(("+ operadores +")("+digito+"))*";
	
	private static Double calculaExpressaoSimples( String strExpressaoSimples ) throws Exception{
		
		if( Pattern.matches( expressaoSimples, strExpressaoSimples) ){
			String valores[] = splitExpressaoSimples( strExpressaoSimples );
			
			//Caso seja uma soma
			if( Pattern.matches( exprSimplesSoma, strExpressaoSimples) ){
				return Double.parseDouble(valores[0])+Double.parseDouble(valores[1]);
			//Caso seja uma subtracao
			}else if( Pattern.matches( exprSimplesSub, strExpressaoSimples) ){
				return Double.parseDouble(valores[0])+Double.parseDouble(valores[1]);
			//Caso seja uma multiplicacao
			}else if( Pattern.matches( exprSimplesMult, strExpressaoSimples) ){
				return Double.parseDouble(valores[0])*Double.parseDouble(valores[1]);
			//Caso seja uma divisao
			}else if( Pattern.matches( exprSimplesDiv, strExpressaoSimples) ){
				return Double.parseDouble(valores[0])/Double.parseDouble(valores[1]);
			//Caso seja uma operacao exponencial
			}else if( Pattern.matches( exprSimplesExp, strExpressaoSimples) ){
				return Math.pow( Double.parseDouble(valores[0]), Double.parseDouble(valores[1]));
			//Caso nao encontre a operacao desejada
			}else{
				throw new Exception( "Operacao nao encontrada. Ou operacao nao implementada." );
			}
		}else{
			throw new Exception( "Expressao com dois operando invalida" );
		}
		
	}
	
	private static String[] splitExpressaoSimples( String strExpressaoSimples ) throws Exception{
		Pattern pattern = Pattern.compile( Fx.numeroReal );
		Matcher matcher = pattern.matcher( strExpressaoSimples );

		String valores[] = new String[2];
		int i = 0;
		while( matcher.find() ){
			if( i > 1 ) 
				throw new Exception( "Expressao simples com mais de dois operando." );
			
			valores[i] = matcher.group();
			i++;
		}
		
		return valores;
	}
	
	public static Double fx( String strExpressao, Double x ) throws Exception {
		if( x != null )
			strExpressao = strExpressao.replaceAll( "x", ""+x );
		
		strExpressao = strExpressao.replaceAll( "\\s", "" );
		
		while( strExpressao.contains( "(" ) ){
			try{
				int indexAbreParentese = strExpressao.lastIndexOf( '(' )+1;
				int indexFechaParentese = strExpressao.indexOf( ')', strExpressao.lastIndexOf( '(' ) );
				
				strExpressao = strExpressao.substring(0, indexAbreParentese-1)
						+ calcularExpressao(strExpressao.substring(
								indexAbreParentese, indexFechaParentese))
						+ strExpressao.substring(indexFechaParentese+1);
			}catch( IndexOutOfBoundsException indexOutOfBoundsException ){
				throw new Exception( "Expressao com parenteses incorretos" );
			}
		}
		return calcularExpressao(strExpressao);
		
	}
	
	public static Double fx( String strExpressao, Integer x ) throws Exception {
		return fx( strExpressao, x.doubleValue() );
	}
	
	public static Double fx( String strExpressao, Long x ) throws Exception {
		return fx( strExpressao, x.doubleValue() );
	}
	
	public static Double fx( String strExpressao, Float x ) throws Exception {
		return fx( strExpressao, x.doubleValue() );
	}
	
	private static Double calcularExpressao(String strExpressao) throws Exception {
		if( strExpressao.charAt(0) == '+' || strExpressao.charAt(0) == '-' )
			strExpressao = "0" + strExpressao;
		
		if (Pattern.matches(expressao, strExpressao)) {
			
			String expressoesSimples[] = { exprSimplesExp, exprSimplesMult, exprSimplesDiv, exprSimplesSub, exprSimplesSoma };
			
			for( String strExpressaoSimples: expressoesSimples ){
				
				Pattern pattern = Pattern.compile(strExpressaoSimples);
				Matcher matcher = pattern.matcher(strExpressao);
				
				//Enquanto encontrar strExpressaoSimples, calcule-as e atualize strExpressao
				while (matcher.find()) {
					strExpressao = ""
						+ strExpressao.substring(0, matcher.start())
						+ calculaExpressaoSimples(strExpressao.substring(
								matcher.start(), matcher.start()
								+ matcher.group().length()))
						+ strExpressao.substring(matcher.start()
								+ matcher.group().length());
					
					//operacoes para que nao ocorrer de ter dois operandos juntos
					strExpressao = strExpressao.replaceAll( "\\+\\+", "+"); 
					strExpressao = strExpressao.replaceAll( "\\+\\-", "-"); 
					strExpressao = strExpressao.replaceAll( "\\-\\+", "-"); 
					strExpressao = strExpressao.replaceAll( "\\-\\-", "+");
					
					matcher = pattern.matcher(strExpressao);
				}
			}

		} else {
			throw new Exception("Expressao invalida");
		}

		try{
			return Double.parseDouble( strExpressao );
		}catch( NumberFormatException numberFormatException ){
			throw new Exception("Nao foi possivel calcular expressao. Possivelmente alguma operacao nao foi implementada");
		}
	}
	
	
	/**
	 * 
	 * @param strExpressao expresao que deseja verificar os parenteses, ou colchetes
	 * @param charAbre 
	 * @param charFecha
	 * @return
	 */
	public static boolean hasParentesesOK( String strExpressao, Character charAbre, Character charFecha ){
		Stack<Character> pilha = new Stack<Character>();
		
		Pattern pattern = Pattern.compile( "(\\"+charAbre+"|\\"+charFecha+")" );
		Matcher matcher = pattern.matcher( strExpressao );
		
		//Enquanto existe caracteres de abertura e fechamento de expressao faca
		while( matcher.find() ){
			
			//Se encontrar um caracter de abertura, empilha
			if( matcher.group().equals( ""+charAbre ) )
				pilha.push( charAbre );
			
			//Se encontrar um caracter de fechamento, desempilha
			if( matcher.group().equals( ""+charFecha ) ){
				//Caso lanca uma excessao (pilha vazia), eh pq a abertura e fechamento dos caracteres nao casaram
				try{
					pilha.pop();
				}catch( EmptyStackException  emptyStackException ){
					return false;
				}
			}
		}
		
		//Se a pilha estiver vazia, eh pq todas as aberturas e fechamentos dos caracteres casaram
		//Caso contrario, nao houve casamento de pelo menos uma abetura e fechamento de caracter
		if( pilha.isEmpty() )
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * @param strExpressao string da expressao que deseja verificar se eh valida
	 * @return
	 */
	public static boolean isExpressaoValida( String strExpressao ){
		
		//verificar se os parenteses da expressao estao corretos
		if( !hasParentesesOK(strExpressao, '(', ')' ) )
			return false;
		
		//Para fins de verificacao, caso haja a variavel 'x', substitua por zero 
		strExpressao = strExpressao.replaceAll( "x", ""+0.0 );
		//Remover todos os espacos em branco
		strExpressao = strExpressao.replaceAll( "\\s", "" );
		
		//'Calcular' todas as expressoes dentro dos parenteses
		while( strExpressao.contains( "(" ) ){
			try{
				int indexAbreParentese = strExpressao.lastIndexOf( '(' )+1;
				int indexFechaParentese = strExpressao.indexOf( ')', strExpressao.lastIndexOf( '(' ) );
				
				strExpressao = strExpressao.substring(0, indexAbreParentese-1)
						+ 0.0 //Para fins de verificacao, o resultado de todas as expressoes dentro dos parenteses serah considerada como 0.0 
						+ strExpressao.substring(indexFechaParentese+1);
			}catch( IndexOutOfBoundsException indexOutOfBoundsException ){
				return false;
			}
		}
		
		if( Pattern.matches(expressao, strExpressao) )
			return true;
		else
			return false;
		
	}
	
}
