package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao;

import br.ufla.lemaf.commons.model.persistence.dao.DAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.MaquinaInferencia;

public interface MaquinaInferenciaDAO extends DAO<MaquinaInferencia, Long> {

	public void evict( MaquinaInferencia maquinaInferencia );
	public MaquinaInferencia retrieve( String nome );
	
}
