package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao;

import br.ufla.lemaf.commons.model.persistence.dao.DAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;

public interface UnivFuzzyDAO extends DAO<UniversoDeDiscursoFuzzy, Long> {

	public void evict( UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy );
	
	public UniversoDeDiscursoFuzzy retrieve( String nome );
	
}
