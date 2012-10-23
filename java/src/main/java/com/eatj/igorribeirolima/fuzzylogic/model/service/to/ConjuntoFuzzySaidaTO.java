package com.eatj.igorribeirolima.fuzzylogic.model.service.to;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ConjuntoFuzzySaidaTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Map<String, Map<String, Double>> conjuntosFuzzySaida;
	private Map<String, Double> defuzzyficacao;
	
	public ConjuntoFuzzySaidaTO(){
		conjuntosFuzzySaida = new HashMap<String, Map<String,Double>>();
		defuzzyficacao = new HashMap<String, Double>();
	}
	
	public ConjuntoFuzzySaidaTO( Map<String, Map<String, Double>> conjuntosFuzzySaida, Map<String, Double> defuzzyficacao ){
		setConjuntosFuzzySaida( conjuntosFuzzySaida );
		setDefuzzyficacao( defuzzyficacao );
	}

	
	public Map<String, Map<String, Double>> getConjuntosFuzzySaida() {
		return conjuntosFuzzySaida;
	}

	
	public void setConjuntosFuzzySaida( Map<String, Map<String, Double>> conjuntosFuzzySaida ) {
		this.conjuntosFuzzySaida = conjuntosFuzzySaida;
	}

	
	public Map<String, Double> getDefuzzyficacao() {
		return defuzzyficacao;
	}

	
	public void setDefuzzyficacao( Map<String, Double> defuzzyficacao ) {
		this.defuzzyficacao = defuzzyficacao;
	}
	
	
	
}
