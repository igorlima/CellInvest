package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.hibernate;

import javax.inject.Named;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.persistence.dao.hibernate.HibernateDAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.SistemaFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.SistemaFuzzyDAO;

@Named
@DAO( implementation = DAOImplementation.HIBERNATE )
public class SistemaFuzzyHibernateDAO extends HibernateDAO<SistemaFuzzy, Long> implements SistemaFuzzyDAO {

	public SistemaFuzzy createOrUpdate( SistemaFuzzy sistemaFuzzy ) {
		this.getSession().saveOrUpdate( sistemaFuzzy );
		this.getSession().flush();
		return sistemaFuzzy;
	}

	public void evict( SistemaFuzzy sistemaFuzzy ) {
		this.getSession().evict( sistemaFuzzy );
	}

	public void delete( SistemaFuzzy sistemaFuzzy ) {
		this.getSession().delete( sistemaFuzzy );
	}
	
	public SistemaFuzzy retrieve( String nome ){
		SistemaFuzzy sistemaFuzzy = new SistemaFuzzy( nome );
		try{
			return retrieve( sistemaFuzzy ).get(0);
		}catch( IndexOutOfBoundsException  indexOutOfBoundsException ) {
			return null;
		}
	}

}
