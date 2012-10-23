package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.hibernate;

import javax.inject.Named;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.persistence.dao.hibernate.HibernateDAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.MaquinaInferencia;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.MaquinaInferenciaDAO;

@Named
@DAO( implementation = DAOImplementation.HIBERNATE )
public class MaquinaInferenciaHibernateDAO extends HibernateDAO<MaquinaInferencia, Long> implements MaquinaInferenciaDAO {

	public MaquinaInferencia createOrUpdate( MaquinaInferencia maquinaInferencia ) {
		this.getSession().saveOrUpdate( maquinaInferencia );
		this.getSession().flush();
		return maquinaInferencia;
	}

	public void evict( MaquinaInferencia maquinaInferencia ) {
		this.getSession().evict( maquinaInferencia );
	}

	public void delete( MaquinaInferencia maquinaInferencia ) {
		this.getSession().delete( maquinaInferencia );
	}
	
	public MaquinaInferencia retrieve( String nome ){
	  MaquinaInferencia maquinaInferencia = new MaquinaInferencia( nome );
    try{
      return retrieve( maquinaInferencia ).get(0);
    }catch( IndexOutOfBoundsException  indexOutOfBoundsException ) {
      return null;
    }
  }
	
}
