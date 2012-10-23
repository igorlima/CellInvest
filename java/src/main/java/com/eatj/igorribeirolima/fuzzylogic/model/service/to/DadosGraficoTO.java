package com.eatj.igorribeirolima.fuzzylogic.model.service.to;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;


public class DadosGraficoTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<String> eixox;
	private List<Map<String, Double>> conjuntosFuzzy;
	
	@JsonIgnore
	private DecimalFormat df = new DecimalFormat("#,###.0");
	
	public DadosGraficoTO(){
		eixox = new ArrayList<String>();
		conjuntosFuzzy = new ArrayList<Map<String,Double>>();
	}
	
	public void add( Double x, Map<String,Double> saidaFuzzy ){
		
		eixox.add( df.format( x ) );
		conjuntosFuzzy.add( saidaFuzzy );
		
	}

	
	public List<String> getEixox() {
		return eixox;
	}

	
	public void setEixox( List<String> eixox ) {
		this.eixox = eixox;
	}

	
	public List<Map<String, Double>> getConjuntosFuzzy() {
		return conjuntosFuzzy;
	}

	
	public void setConjuntosFuzzy( List<Map<String, Double>> conjuntosFuzzy ) {
		this.conjuntosFuzzy = conjuntosFuzzy;
	}
	
	
}
