package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao;

import br.ufla.lemaf.commons.model.persistence.dao.DAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Funcao;

public interface FuncaoDAO extends DAO<Funcao, Long> {

	public void evict( Funcao funcao );
	
}
