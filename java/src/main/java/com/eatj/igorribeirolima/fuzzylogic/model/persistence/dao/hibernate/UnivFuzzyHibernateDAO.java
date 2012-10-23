package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.hibernate;

import javax.inject.Named;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.persistence.dao.hibernate.HibernateDAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.UnivFuzzyDAO;

@Named
@DAO( implementation = DAOImplementation.HIBERNATE )
public class UnivFuzzyHibernateDAO extends HibernateDAO<UniversoDeDiscursoFuzzy, Long> implements UnivFuzzyDAO {

	public UniversoDeDiscursoFuzzy createOrUpdate( UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy ) {
		this.getSession().saveOrUpdate( universoDeDiscursoFuzzy );
		this.getSession().flush();
		return universoDeDiscursoFuzzy;
	}

	public void evict( UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy ) {
		this.getSession().evict( universoDeDiscursoFuzzy );
	}

	public void delete( UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy ) {
		this.getSession().delete( universoDeDiscursoFuzzy );
	}
	
	public UniversoDeDiscursoFuzzy retrieve( String nome ){
		UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy = new UniversoDeDiscursoFuzzy( nome );
		try{
			return retrieve( universoDeDiscursoFuzzy ).get(0);
		}catch( IndexOutOfBoundsException  indexOutOfBoundsException ) {
			return null;
		}
	}

}
