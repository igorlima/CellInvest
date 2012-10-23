package com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.CollectionUtils;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.BaseDeRegra;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.GrupoAlias;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.MaquinaInferencia;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Regra;

public class MaqInferenciaMathmaticBO {
	public static final String tagNome = "[<][n][o][m][e][>]"+OpFuzzyMathmaticBO.letras+"[<][\\\\][n][o][m][e][>]";
	public static final String tagAlias = "[<][a][l][i][a][s][>]"+OpFuzzyMathmaticBO.letras+"[<][\\\\][a][l][i][a][s][>]";
	public static final String tagBaseRegra = "[<][b][a][s][e][R][e][g][r][a][>]"+   OpFuzzyMathmaticBO.letras   +"[<][\\\\][b][a][s][e][R][e][g][r][a][>]";
	public static final String regexIsMaquinaInferencia = tagNome+tagAlias+tagBaseRegra;
	
	
	private GrupoAliasMathmaticBO grupoAliasBusiness;
	private GrupoAlias grupoAlias;
	private BaseDeRegra baseDeRegra;
	
	public MaqInferenciaMathmaticBO( GrupoAlias grupoAlias, BaseDeRegra baseDeRegra ) throws Exception{
		
		if( CollectionUtils.isEmpty( baseDeRegra.getRegras() ) )
			throw new Exception( "Base de Regras invalida." );
		
		if( grupoAlias == null || grupoAlias.getAliasCollection() == null || grupoAlias.getAliasCollection().isEmpty() )
			throw new Exception( "Grupo Alias invalido." );

		
		this.grupoAlias = grupoAlias;
		grupoAliasBusiness = new GrupoAliasMathmaticBO(grupoAlias);
		
		this.baseDeRegra = baseDeRegra;
	}
	
	public MaqInferenciaMathmaticBO( MaquinaInferencia maquinaInferencia ) throws Exception{
		
		if( maquinaInferencia.getBaseDeRegra() == null || CollectionUtils.isEmpty( maquinaInferencia.getBaseDeRegra().getRegras() ) )
			throw new Exception( "Base de Regras invalida." );
		
		if( maquinaInferencia.getGrupoAlias() == null || maquinaInferencia.getGrupoAlias().getAliasCollection() == null || maquinaInferencia.getGrupoAlias().getAliasCollection().isEmpty() )
			throw new Exception( "Grupo Alias invalido." );

		
		this.grupoAlias = maquinaInferencia.getGrupoAlias();
		grupoAliasBusiness = new GrupoAliasMathmaticBO(maquinaInferencia.getGrupoAlias());
		
		this.baseDeRegra = maquinaInferencia.getBaseDeRegra();
	}
	
	public GrupoAlias getGrupoAlias() {
		return grupoAlias;
	}

	public void setGrupoAlias(GrupoAlias grupoAlias) throws Exception {
		if( grupoAlias == null || grupoAlias.getAliasCollection() == null || grupoAlias.getAliasCollection().isEmpty() )
			throw new Exception( "Grupo Alias invalido." );
		
		this.grupoAlias = grupoAlias;
		grupoAliasBusiness = new GrupoAliasMathmaticBO(grupoAlias);
	}

	public BaseDeRegra getBaseDeRegra() {
		return baseDeRegra;
	}

	public void setBaseDeRegra(BaseDeRegra baseDeRegra) throws Exception {
		if( CollectionUtils.isEmpty( baseDeRegra.getRegras() ) )
			throw new Exception( "Base de Regras invalida." );
		
		this.baseDeRegra = baseDeRegra;
	}
	
	public Map< String, Map<String, Double> > conjuntoFuzzySaida( Map<String, Map<String,Double>> conjuFuzzyEntrada ) throws Exception{
		Map<String, Double> conjFuzzySaida = new HashMap<String, Double>();
		Map<String, List<Double>> conjFuzzySaidaAux = new HashMap<String, List<Double>>();
		String universoFuzzyDeSaida = "";
		
		//Para todas as regras faca
		for( Regra regra : baseDeRegra.getRegras()){
			//captura o antecedente da regra corrente
			String antecedenteRegra = regra.getRegra().split( "[<][T][H][E][N][>]" )[0].split( "[<][I][F][>]" )[1];
			//captura o antecedente da regra corrente
			String variavelLinguisticaSaida = regra.getRegra().split( "[<][T][H][E][N][>]" )[1].split( "[=][=]" )[1] ;
			variavelLinguisticaSaida = grupoAliasBusiness.getTxValor(variavelLinguisticaSaida);
			
			Pattern pattern = Pattern.compile( OpFuzzyMathmaticBO.elementoRegraFuzzy );
			Matcher matcher = pattern.matcher( antecedenteRegra );
			String valoresOperacaoAntecedente = "";
			//Calculando antecedente da regra corrente
			while( matcher.find() ){
				String compElementoRegraFuzy[] = matcher.group().split( "[=][=]" );
				Double valor = 0.0;
				//Caso a base de regras necessita de um dado que nao tenha no conjutno fuzzy de entrada, serah lancada uma excessao
				try{
					String universoFuzzyDeDiscursao = grupoAliasBusiness.getTxValor(compElementoRegraFuzy[0]);
					String variavelLinguisticaDoUniversoDeDiscurso = grupoAliasBusiness.getTxValor( compElementoRegraFuzy[1] );
					valor = conjuFuzzyEntrada.get(universoFuzzyDeDiscursao).get(variavelLinguisticaDoUniversoDeDiscurso);
					
					if( valor == null )
						throw new Exception( "Falta dados no conjunto fuzzy de entrada." );
						
				}catch( Exception exception ){
					throw new Exception( "Falta dados no conjunto fuzzy de entrada." );
				}
				
				//valor da operacao no antecedente da regra
				if( valoresOperacaoAntecedente.equals( "" ) )
					valoresOperacaoAntecedente = antecedenteRegra.replace( matcher.group(), "" + valor );
				else
					valoresOperacaoAntecedente = valoresOperacaoAntecedente.replace( matcher.group(), "" + valor );
			}
			
			//Armazenamento do valor do antecedente da regra, para cada tipo de variavel linguistica de saida
			Double result = OpFuzzyMathmaticBO.calcular( valoresOperacaoAntecedente );
			List<Double> resultado = new ArrayList<Double>();
			resultado.add(result);
			if( conjFuzzySaidaAux.get( variavelLinguisticaSaida ) == null )
				conjFuzzySaidaAux.put( variavelLinguisticaSaida, resultado );
			else{
				conjFuzzySaidaAux.get( variavelLinguisticaSaida ).addAll( resultado );
			}
			
			if( universoFuzzyDeSaida.equals( "" ) ){
				universoFuzzyDeSaida = BaseDeRegraMathmaticBO.getUniversoFuzzyDeSaida(regra.getRegra());
				universoFuzzyDeSaida = grupoAliasBusiness.getTxValor(universoFuzzyDeSaida);
				
				if( universoFuzzyDeSaida == null )
					throw new Exception( "Nao existe alias pra o universo fuzzy de saida." );
			}
		}
		
		//O conjunto fuzzy de saida terah o maior valor da Lista de double 
		Set<String> variaveisLinguisticas = conjFuzzySaidaAux.keySet();
		for( String var : variaveisLinguisticas )
			conjFuzzySaida.put( var, maiorValor( conjFuzzySaidaAux.get(var) ) );
		
		
		Map<String, Map<String,Double>> retorno = new HashMap<String, Map<String,Double>>();
		retorno.put( universoFuzzyDeSaida, conjFuzzySaida );
		return retorno;
	}
	
	private Double maiorValor( List<Double> listDouble ){
		if( listDouble==null || listDouble.isEmpty() )
			return null;
		if( listDouble.size() == 1 )
			return listDouble.get(0);
		else
			return Math.max( listDouble.get(0), maiorValor( listDouble.subList( 1, listDouble.size() ) ) );
	}
	
}
