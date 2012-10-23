package com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.ConjFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Funcao;

public class ConjFuzzyMathmaticBO {
	private String nm;
	private ConjFuzzy conjFuzzy;
	private List<Funcao> funcoes;
	
	public ConjFuzzyMathmaticBO( ConjFuzzy conjFuzzy ) throws Exception{
		this.conjFuzzy = conjFuzzy;
		this.nm = conjFuzzy.getNome();
		this.funcoes = new ArrayList<Funcao>( conjFuzzy.getFuncoes() );
		
		if( funcoes == null )
			throw new Exception( "As funcoes de pertinencia do conjunto fuzzy nao PODE ser null." );
	}
	
	public String getNm() {
		return nm;
	}

	public ConjFuzzy getConjFuzzy() {
		return conjFuzzy;
	}

	public Map<String, Double> getGrauPertinencia( Double x ) throws Exception{
		Map<String, Double > grauPertinencia = new HashMap<String, Double>();
		
		FuncaoMathmaticBO funcaoMathmaticBO; 
		for( Funcao funcao : funcoes ){
			funcaoMathmaticBO = new FuncaoMathmaticBO( funcao );
			if( funcaoMathmaticBO.pertenceAoDominio( x ) ){
				grauPertinencia.put( this.nm, funcaoMathmaticBO.fx(x) );
				return grauPertinencia;
			}
		}
		
		grauPertinencia.put( this.nm, 0.0 );
		return grauPertinencia;
	}
	
	public Map<String, Double> getGrauPertinencia( Float x ) throws Exception{
		return getGrauPertinencia( new Double(x) );
	}
	
	public Map<String, Double> getGrauPertinencia( Integer x ) throws Exception{
		return getGrauPertinencia( new Double(x) );
	}
	
	public Map<String, Double> getGrauPertinencia( Long x ) throws Exception{
		return getGrauPertinencia( new Double(x) );
	}
	
	public String toString(){
		return this.nm+"|"+funcoes.toString();
	}
	
}
