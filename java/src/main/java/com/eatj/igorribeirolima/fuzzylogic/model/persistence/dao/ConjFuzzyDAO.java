package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao;

import br.ufla.lemaf.commons.model.persistence.dao.DAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.ConjFuzzy;

public interface ConjFuzzyDAO extends DAO<ConjFuzzy, Long> {

	public void evict( ConjFuzzy conjFuzzy );
	public ConjFuzzy retrieve( String nome );
	
}
