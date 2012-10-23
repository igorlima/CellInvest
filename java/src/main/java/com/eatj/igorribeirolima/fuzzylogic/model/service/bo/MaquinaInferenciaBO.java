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

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.MaquinaInferencia;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.MaquinaInferenciaDAO;

@Named
public class MaquinaInferenciaBO {

	@Inject
	@DAO( implementation = DAOImplementation.HIBERNATE )
	private MaquinaInferenciaDAO dao;

	@Transactional
	public ReturnTO saveOrUpdate( MaquinaInferencia maquinaInferencia ) {

		maquinaInferencia = dao.createOrUpdate( maquinaInferencia );

		ReturnTO to = new ObjectAndMessageReturnTO<MaquinaInferencia>(
				maquinaInferencia, "Componente salvo/atualizado com sucesso." );

		return to;

	}

	@Transactional
	public ReturnTO saveOrUpdate( List<MaquinaInferencia> maquinasDeInferencias ) {
		try {
			
			for ( MaquinaInferencia maquinaInferencia : maquinasDeInferencias ) 
				maquinaInferencia = dao.createOrUpdate( maquinaInferencia );
			
			
			return new MessageReturnTO( Status.SUCCESS, "Componentes cadastrados com sucesso!" );
		} catch ( Exception e ) {

			return new MessageReturnTO( Status.ERROR, e.getMessage() );

		}
	}
	
	@Transactional( readOnly=true )
  public MaquinaInferencia retrieve( String nome ) {
    return dao.retrieve( nome );
  }

	@Transactional
	public MaquinaInferencia retrieveById( Long id ) {
		return dao.retrieve( id );
	}

	@Transactional
	public Set<MaquinaInferencia> retrieve() {
		return new HashSet<MaquinaInferencia>(dao.retrieve());
	}
}
