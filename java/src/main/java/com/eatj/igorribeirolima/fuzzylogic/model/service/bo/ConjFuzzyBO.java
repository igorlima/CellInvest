package com.eatj.igorribeirolima.fuzzylogic.model.service.bo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ObjectAndMessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO.Status;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.ConjFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.ConjFuzzyDAO;

@Named
public class ConjFuzzyBO {

	@Inject
	@DAO( implementation = DAOImplementation.HIBERNATE )
	private ConjFuzzyDAO dao;

	@Transactional
	public ReturnTO saveOrUpdate( ConjFuzzy conjFuzzy ) {
		try {

			conjFuzzy = dao.createOrUpdate( conjFuzzy );

			ReturnTO to = new ObjectAndMessageReturnTO<ConjFuzzy>(
					conjFuzzy, "Componente salvo/atualizado com sucesso." );

			return to;

		} catch ( Exception e ) {

			e.printStackTrace();
			return new MessageReturnTO( Status.ERROR, "Não foi possível salvar o componente." );

		}
	}

	@Transactional
	public ReturnTO saveOrUpdate( List<ConjFuzzy> conjuntosFuzzys ) {
		try {
			
			for ( ConjFuzzy conjFuzzy : conjuntosFuzzys ) 
				conjFuzzy = dao.createOrUpdate( conjFuzzy );
			
			
			return new MessageReturnTO( Status.SUCCESS, "Componentes cadastrados com sucesso!" );
		} catch ( Exception e ) {

			return new MessageReturnTO( Status.ERROR, e.getMessage() );

		}
	}
	
	@Transactional( readOnly=true )
  public ConjFuzzy retrieve( String nome ) {
    return dao.retrieve( nome );
  }

	@Transactional
	public ConjFuzzy retrieveById( Long id ) {
		return dao.retrieve( id );
	}

	@Transactional
	public Set<ConjFuzzy> retrieve() {
		return new HashSet<ConjFuzzy>( dao.retrieve() );
	}
}
