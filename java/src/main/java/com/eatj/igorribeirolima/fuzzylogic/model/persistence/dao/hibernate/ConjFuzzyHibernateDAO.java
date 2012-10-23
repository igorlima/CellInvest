package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.hibernate;

import javax.inject.Named;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.persistence.dao.hibernate.HibernateDAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.ConjFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.ConjFuzzyDAO;

@Named
@DAO( implementation = DAOImplementation.HIBERNATE )
public class ConjFuzzyHibernateDAO extends HibernateDAO<ConjFuzzy, Long> implements ConjFuzzyDAO {

	public ConjFuzzy createOrUpdate( ConjFuzzy conjFuzzy ) {
		this.getSession().saveOrUpdate( conjFuzzy );
		this.getSession().flush();
		return conjFuzzy;
	}

	public void evict( ConjFuzzy conjFuzzy ) {
		this.getSession().evict( conjFuzzy );
	}

	public void delete( ConjFuzzy conjFuzzy ) {
		this.getSession().delete( conjFuzzy );
	}

	public ConjFuzzy retrieve( String nome ){
	  ConjFuzzy conjFuzzy = new ConjFuzzy( nome );
    try{
      return retrieve( conjFuzzy ).get(0);
    }catch( IndexOutOfBoundsException  indexOutOfBoundsException ) {
      return null;
    }
  }
	
}
