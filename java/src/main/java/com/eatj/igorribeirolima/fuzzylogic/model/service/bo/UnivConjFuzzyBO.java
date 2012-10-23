package com.eatj.igorribeirolima.fuzzylogic.model.service.bo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.UnivFuzzyDAO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.UnivFuzzyMathmaticBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.to.DadosGraficoTO;

@Named
public class UnivConjFuzzyBO {

	@Inject
	@DAO( implementation = DAOImplementation.HIBERNATE )
	private UnivFuzzyDAO dao;

	@Transactional
	public ReturnTO saveOrUpdate( UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy ) {
			
		universoDeDiscursoFuzzy = dao.createOrUpdate( universoDeDiscursoFuzzy );

		ReturnTO to = new ObjectAndMessageReturnTO<UniversoDeDiscursoFuzzy>(
				universoDeDiscursoFuzzy, "Componente salvo/atualizado com sucesso." );

		return to;

	}

	@Transactional
	public ReturnTO saveOrUpdate( List<UniversoDeDiscursoFuzzy> universosFuzzy ) {
		try {
			
			for ( UniversoDeDiscursoFuzzy univFuzzy : universosFuzzy ) 
				univFuzzy = dao.createOrUpdate( univFuzzy );
			
			
			return new MessageReturnTO( Status.SUCCESS, "Componentes cadastrados com sucesso!" );
		} catch ( Exception e ) {

			return new MessageReturnTO( Status.ERROR, e.getMessage() );

		}
	}

	@Transactional
	public UniversoDeDiscursoFuzzy retrieveById( Long id ) {
		return dao.retrieve( id );
	}
	
	@Transactional( readOnly=true )
	public UniversoDeDiscursoFuzzy retrieve( String nome ) {
		return dao.retrieve( nome );
	}
	
	@Transactional( readOnly=true )
	public DadosGraficoTO getDadosGrafico( String nome ) throws Exception {
		UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy = retrieve(nome);
		if( universoDeDiscursoFuzzy == null ) return null;
		nome = universoDeDiscursoFuzzy.getNome(); //atribuição para evitar que a comparação de um string seja falsa por causa do case sensitive.
		
		UnivFuzzyMathmaticBO univFuzzyMathmaticBO = new UnivFuzzyMathmaticBO(universoDeDiscursoFuzzy);
		Double limiteInferior = universoDeDiscursoFuzzy.getDominio().getLimiteInferior();
		Double limiteSuperior = universoDeDiscursoFuzzy.getDominio().getLimiteSuperior();
		Double increase = getIncrease(limiteInferior, limiteSuperior);
		
		if( !universoDeDiscursoFuzzy.getDominio().isIncluiLimiteInferior() ) limiteInferior+=increase;
		if( !universoDeDiscursoFuzzy.getDominio().isIncluiLimiteSuperior() ) limiteSuperior-=increase;
		
		DadosGraficoTO dadosGraficoTO = new DadosGraficoTO();
		while( limiteInferior <= limiteSuperior ){
			dadosGraficoTO.add( limiteInferior, univFuzzyMathmaticBO.fuzzyficar( limiteInferior ).get( nome ) );
			limiteInferior+=increase;
		}
		
		return dadosGraficoTO;
	}
	
	private Double getIncrease( Double limiteInferior, Double limiteSuperior ){
	  return ((limiteSuperior-limiteInferior)/500);
	}
	
	@Transactional
	public Set<UniversoDeDiscursoFuzzy> retrieve() {
		return new HashSet<UniversoDeDiscursoFuzzy>( dao.retrieve() );
	}
	
	@Transactional
	public ReturnTO calcular( String nome, Double dadoDeEntrada ){
		if( nome == null || nome.trim().isEmpty() ) 
			return new MessageReturnTO( Status.ERROR, "Nome do sistema fuzzy vazio." );
		
		UniversoDeDiscursoFuzzy universoDeDiscursoFuzzy = retrieve(nome);
		if( universoDeDiscursoFuzzy == null ) 
			return new MessageReturnTO( Status.ERROR, "Universo fuzzy não existe." );
		
		try{
			Map<String, Map<String, Double>> conjuntofuzzysaida = new UnivFuzzyMathmaticBO(universoDeDiscursoFuzzy).fuzzyficar( dadoDeEntrada );
			return new ObjectAndMessageReturnTO<Map<String, Map<String, Double>>>( conjuntofuzzysaida );
		}catch( Exception e ){
			e.printStackTrace();
			return new MessageReturnTO( Status.ERROR, "Não foi possível obter o conjunto fuzzy de saída. Erro: " + e.getMessage() + "." );
		}
	}
}
