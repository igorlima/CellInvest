package com.eatj.igorribeirolima.fuzzylogic.model.service.bo;

import java.util.HashMap;
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

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Dominio;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.SistemaFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.SistemaFuzzyDAO;
import com.eatj.igorribeirolima.fuzzylogic.model.persistence.dao.UnivFuzzyDAO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.SistemaFuzzyMathmaticBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.UnivFuzzyMathmaticBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.to.ConjuntoFuzzySaidaTO;
import com.eatj.igorribeirolima.util.ConversaoDeDados;

@Named
public class SistemaFuzzyBO {

	@Inject
	@DAO( implementation = DAOImplementation.HIBERNATE )
	private SistemaFuzzyDAO dao;
	
	@Inject
	@DAO( implementation = DAOImplementation.HIBERNATE )
	private UnivFuzzyDAO univFuzzyDAO;

	@Transactional
	public ReturnTO saveOrUpdate( SistemaFuzzy sistemaFuzzy ) {
		sistemaFuzzy = dao.createOrUpdate( sistemaFuzzy );

		ReturnTO to = new ObjectAndMessageReturnTO<SistemaFuzzy>(
				sistemaFuzzy, "Componente salvo/atualizado com sucesso." );

		return to;
	}

	@Transactional
	public ReturnTO saveOrUpdate( List<SistemaFuzzy> sistemas ) {
		try {
			
			for ( SistemaFuzzy sistemaFuzzy : sistemas ) 
				sistemaFuzzy = dao.createOrUpdate( sistemaFuzzy );
			
			
			return new MessageReturnTO( Status.SUCCESS, "Componentes cadastrados com sucesso!" );
		} catch ( Exception e ) {

			return new MessageReturnTO( Status.ERROR, e.getMessage() );

		}
	}

	@Transactional
	public SistemaFuzzy retrieveById( Long id ) {
		return dao.retrieve( id );
	}
	
	@Transactional( readOnly=true )
	public SistemaFuzzy retrieve( String nome ) {
		return dao.retrieve( nome );
	}

	@Transactional
	public Set<SistemaFuzzy> retrieve() {
		return new HashSet<SistemaFuzzy>(dao.retrieve());
	}
	
	@Transactional
  public ReturnTO calcular(String nome, String input) {
    List<Double> dadosDeEntrada;
    try {
      dadosDeEntrada = ConversaoDeDados.string2listdouble(input);
    } catch (NumberFormatException e) {
      return new MessageReturnTO(Status.ERROR, "Dados de entrada inválidos.");
    }
    return calcular(nome, dadosDeEntrada);
  }
	
	@Transactional
	private ReturnTO calcular( String nome, List<Double> dadosDeEntrada ){
		if( nome == null || nome.trim().isEmpty() ) 
			return new MessageReturnTO( Status.ERROR, "Nome do sistema fuzzy vazio." );
		
		SistemaFuzzy sistemaFuzzy = retrieve(nome);
		if( sistemaFuzzy == null ) 
			return new MessageReturnTO( Status.ERROR, "Sistema fuzzy não existe." );
		
		try{
			SistemaFuzzyMathmaticBO sistemaFuzzyMathmaticBO = new SistemaFuzzyMathmaticBO(sistemaFuzzy);
			Map<String, Map<String, Double>> conjFuzzySaida = sistemaFuzzyMathmaticBO.conjuntoFuzzySaida(dadosDeEntrada);
			
			ConjuntoFuzzySaidaTO conjuntoFuzzySaidaTO = new ConjuntoFuzzySaidaTO( conjFuzzySaida, defuzzyficar( conjFuzzySaida ) );
			return new ObjectAndMessageReturnTO<ConjuntoFuzzySaidaTO>( conjuntoFuzzySaidaTO );
		}catch( Exception e ){
			e.printStackTrace();
			return new MessageReturnTO( Status.ERROR, "Não foi possível obter o conjunto fuzzy de saída." );
		}
	}
	
	public Map<String, Double> defuzzyficar( Map< String, Map<String, Double> > conjFuzzySaida ) throws Exception {
		double precisao = 1;
		
		Map<String, Double> retorno = new HashMap<String, Double>();
		for( String universoFuzzySaida : conjFuzzySaida.keySet() ){
			Dominio dominio = getUniversoDeDiscursoFuzzy( universoFuzzySaida ).getDominio();
			
			double i = dominio.isIncluiLimiteInferior() ? dominio.getLimiteInferior() : dominio.getLimiteInferior()+precisao;   
			
			double numerador = 0.0;
			double denominador = 0.0;
			while( dominio.isIncluiLimiteSuperior() ? i<=dominio.getLimiteSuperior() : i<dominio.getLimiteSuperior() ){
				
				double peso = maximo( minimo( i, universoFuzzySaida, conjFuzzySaida.get( universoFuzzySaida )) );
				numerador += i*peso;
				denominador += peso;
				
				i += precisao;
			}
			
			Double doubleDefuzzyficacao = numerador/denominador; 
			retorno.put( universoFuzzySaida, doubleDefuzzyficacao );
		}
		
		return retorno;
	}
	
	private UniversoDeDiscursoFuzzy getUniversoDeDiscursoFuzzy( String nmUniversoFuzzy ){
		return univFuzzyDAO.retrieve( nmUniversoFuzzy );
	}
	
	private Double maximo( Map<String, Double> conjFuzzy ){
		Double maximo = 0.0;
		
		Set<String> variaveisLinguisticas = conjFuzzy.keySet();
		for( String var : variaveisLinguisticas )
			maximo = Math.max( maximo, conjFuzzy.get(var) );
		
		return maximo;
	}
	
	private Map<String, Double> minimo( double i, String nmUniversoDiscurso, Map<String, Double> conjFuzzySaida ) throws Exception{
		Map<String, Double> fuzzyficacao = new UnivFuzzyMathmaticBO( getUniversoDeDiscursoFuzzy( nmUniversoDiscurso ) ).fuzzyficar( i ).get( nmUniversoDiscurso );
		
		Set<String> variaveisLinguisticas = fuzzyficacao.keySet();
		for( String variavelLinguistica : variaveisLinguisticas )
			fuzzyficacao.put( variavelLinguistica, Math.min( fuzzyficacao.get(variavelLinguistica), conjFuzzySaida.get(variavelLinguistica) ) );
		
		return fuzzyficacao;
	}
	
}
