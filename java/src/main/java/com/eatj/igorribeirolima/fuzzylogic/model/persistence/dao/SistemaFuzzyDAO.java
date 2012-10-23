package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao;

import br.ufla.lemaf.commons.model.persistence.dao.DAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.SistemaFuzzy;

public interface SistemaFuzzyDAO extends DAO<SistemaFuzzy, Long> {

	public void evict( SistemaFuzzy sistemaFuzzy );
	
	public SistemaFuzzy retrieve( String nome );
}
