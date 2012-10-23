package com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.ConjFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Dominio;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;

public class UnivFuzzyMathmaticBO {
	private UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy;
	
	public UnivFuzzyMathmaticBO( UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy ) throws Exception{
		setUniversoDeDiscursoFuzzy(universoDeDiscursoFuzzy);
	}

	public UniversoDeDiscursoFuzzy getUniversoDeDiscursoFuzzy() {
		return universoDeDiscursoFuzzy;
	}

	public void setUniversoDeDiscursoFuzzy(
			UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy) throws Exception {
		if( universoDeDiscursoFuzzy == null )
			throw new Exception( "Universo Fuzzy nulo." );
		
		this.universoDeDiscursoFuzzy = universoDeDiscursoFuzzy;
		
		if( CollectionUtils.isEmpty( universoDeDiscursoFuzzy.getConjuntosFuzzy() ) )
			throw new Exception( "No universo em questao DEVE conter pelo menos um conjunto fuzzy." );
		
		if( universoDeDiscursoFuzzy.getDominio() == null )
			throw new Exception( "O dominio do universo naum pode ser null." );
	}

	public String getNm() {
		return universoDeDiscursoFuzzy.getNome();
	}

	public Dominio getDominio() {
		return universoDeDiscursoFuzzy.getDominio();
	}

	public boolean containVariavelLinguistica( String strVariavelLinguistca ){
		for( ConjFuzzy conjFuzzy : universoDeDiscursoFuzzy.getConjuntosFuzzy() )
			if( conjFuzzy.getNome().equals(strVariavelLinguistca) )
				return true;
		
		return false;
	}
	
	public Map<String, Map<String, Double>> fuzzyficar( Double x ) throws Exception{
		Dominio dominio = universoDeDiscursoFuzzy.getDominio();
		if( dominio.getLimiteInferior() > x || x > dominio.getLimiteSuperior() )
			throw new Exception( "O valor nao pertence ao dominio do Universo de Discurso." );
		
		Map<String, Double > grauPertinencia = new HashMap<String, Double>();
		
		for( ConjFuzzy conjFuzzy : universoDeDiscursoFuzzy.getConjuntosFuzzy() )
			grauPertinencia.putAll( new ConjFuzzyMathmaticBO( conjFuzzy ).getGrauPertinencia(x) );
		
		Map<String, Map<String,Double>> retorno = new HashMap<String, Map<String,Double>>();
		retorno.put( universoDeDiscursoFuzzy.getNome(), grauPertinencia );
		return retorno;
	}
	
	public Map<String, Map<String, Double>> fuzzyficar( Float x ) throws Exception{
		return fuzzyficar( new Double(x) );
	}
	
	public Map<String, Map<String, Double>> fuzzyficar( Long x ) throws Exception{
		return fuzzyficar( new Double(x) );
	}
	
	public Map< String, Map<String, Double>> fuzzyficar( Integer x ) throws Exception{
		return fuzzyficar( new Double(x) );
	}
	
	public static void imprimirConjFuzzySaida( Map< String, Map<String, Double>> conjFuzzySaida ){
		Set<String> nmUniversoUniversoCollection = conjFuzzySaida.keySet();
		
		for( String nmUniversoUniverso : nmUniversoUniversoCollection ){
			Set<String> variaveisLinguisticas = conjFuzzySaida.get(nmUniversoUniverso).keySet();
			System.out.println( ">Conjunto Fuzzy de Saida<" );
			System.out.println( "Universo de discurso: " + nmUniversoUniverso );
			for( String variavelLinguistica : variaveisLinguisticas ){
				System.out.print( variavelLinguistica + "=" );
				System.out.println( conjFuzzySaida.get( nmUniversoUniverso ).get(variavelLinguistica) );
			}
			System.out.println();
		}
	}
	
}
