package com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Maquina;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.MaquinaInferencia;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.SistemaFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Universo;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;

public class SistemaFuzzyMathmaticBO {
	private SistemaFuzzy sistemaFuzzy;
	
	public SistemaFuzzyMathmaticBO( SistemaFuzzy sistemaFuzzy ) throws Exception{
		this.sistemaFuzzy = sistemaFuzzy;
		
		if( this.sistemaFuzzy == null )
			throw new Exception( "Sistema Fuzzy nulo." );
		
		if( CollectionUtils.isEmpty( sistemaFuzzy.getMaquinas() ) )
			throw new Exception( "Sistema Fuzzy sem Maquina de Inferencia." );
		
		if( CollectionUtils.isEmpty( sistemaFuzzy.getUniversos() ) )
			throw new Exception( "Sistema Fuzzy sem Universo Fuzzy para fuzzyficacao." );
		
	}
	
	public Map< String, Map<String, Double> > conjuntoFuzzySaida( List<Double> dadosDeEntrada ) throws Exception{
		if( dadosDeEntrada == null )
			throw new Exception( "Dado de entrada vazio" );
		
		Map<String, Map<String,Double>> conjFuzzyEntrada = new HashMap<String, Map<String,Double>>();
		int indexDadosEntrada = 0;
		for( Universo univ : this.sistemaFuzzy.getUniversos() ){
			UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy = univ.getUniversoFuzzy();
			Double x = dadosDeEntrada.get(indexDadosEntrada);
			conjFuzzyEntrada.putAll( new UnivFuzzyMathmaticBO(universoDeDiscursoFuzzy).fuzzyficar( x ) );
			indexDadosEntrada++;
		}
			
		Map<String, Map<String,Double>> conjFuzzySaida = new HashMap<String, Map<String,Double>>();
		for( Maquina maquina : this.sistemaFuzzy.getMaquinas() ){
			MaquinaInferencia maquinaInferencia = maquina.getMaquinaInferencia();
			conjFuzzySaida = new MaqInferenciaMathmaticBO(maquinaInferencia).conjuntoFuzzySaida(conjFuzzyEntrada);
			conjFuzzyEntrada.putAll( conjFuzzySaida );
		}
			
		return conjFuzzySaida;
	}
	
}
