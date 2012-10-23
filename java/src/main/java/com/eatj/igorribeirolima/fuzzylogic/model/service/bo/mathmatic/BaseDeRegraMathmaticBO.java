package com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseDeRegraMathmaticBO {
	
	public static String getUniversoFuzzyDeSaida( String regra ){
		regra = regra.replaceAll( " ", "" );
		Pattern pattern = Pattern.compile( "[<][T][H][E][N][>]"+OpFuzzyMathmaticBO.letras+"[=][=]"+OpFuzzyMathmaticBO.letras );
		Matcher matcher = pattern.matcher( regra );
		matcher.find();
		
		String consequenteDaRegra = matcher.group(); 
		pattern = Pattern.compile( OpFuzzyMathmaticBO.letras );
		matcher = pattern.matcher( consequenteDaRegra );
		matcher.find();
		matcher.find();
		
		//retorna o universo fuzzy de saida
		return matcher.group();
	}
}
