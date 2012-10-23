package com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.hibernate;

import javax.inject.Named;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.persistence.dao.hibernate.HibernateDAO;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Funcao;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.FuncaoDAO;

@Named
@DAO( implementation = DAOImplementation.HIBERNATE )
public class FuncaoHibernateDAO extends HibernateDAO<Funcao, Long> implements FuncaoDAO {

	public Funcao createOrUpdate( Funcao component ) {
		this.getSession().saveOrUpdate( component );
		this.getSession().flush();
		return component;
	}

	public void evict( Funcao component ) {
		this.getSession().evict( component );
	}

	public void delete( Funcao component ) {
		this.getSession().delete( component );
	}

}
