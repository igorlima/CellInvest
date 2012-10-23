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

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Funcao;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.FuncaoDAO;

@Named
public class FuncaoBO {

	@Inject
	@DAO( implementation = DAOImplementation.HIBERNATE )
	private FuncaoDAO dao;

	@Transactional
	public ReturnTO saveOrUpdate( Funcao funcao ) {
		try {

			funcao = dao.createOrUpdate( funcao );

			ReturnTO to = new ObjectAndMessageReturnTO<Funcao>(
					funcao, "Componente salvo/atualizado com sucesso." );

			return to;

		} catch ( Exception e ) {

			e.printStackTrace();
			return new MessageReturnTO( Status.ERROR, "Não foi possível salvar o componente." );

		}
	}

	@Transactional
	public ReturnTO saveOrUpdate( List<Funcao> funcoes ) {
		try {
			
			for ( Funcao funcao : funcoes ) 
				funcao = dao.createOrUpdate( funcao );
			
			
			return new MessageReturnTO( Status.SUCCESS, "Componentes cadastrados com sucesso!" );
		} catch ( Exception e ) {

			return new MessageReturnTO( Status.ERROR, e.getMessage() );

		}
	}

	@Transactional
	public Funcao retrieveById( Long id ) {
		return dao.retrieve( id );
	}

	@Transactional
	public Set<Funcao> retrieve() {
		return new HashSet<Funcao>( dao.retrieve() );
	}
}
