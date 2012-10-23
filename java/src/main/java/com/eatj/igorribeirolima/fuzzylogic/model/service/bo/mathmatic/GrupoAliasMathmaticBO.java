package com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Alias;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.GrupoAlias;

public class GrupoAliasMathmaticBO {
	
	private GrupoAlias grupoAlias;
	
	public GrupoAliasMathmaticBO( GrupoAlias grupoAlias ) throws Exception{
		if( grupoAlias == null || grupoAlias.getAliasCollection() == null || grupoAlias.getAliasCollection().isEmpty() )
			throw new Exception( "GrupoAlias NAO pode ser nulo e o conjunto de Alias NAO pode ser vazio." );
		
		this.grupoAlias = grupoAlias;
	}
	
	public String getTxValor( String txAlias ) throws Exception{
		for( Alias alias : grupoAlias.getAliasCollection() )
			if( alias.getNome().equals( txAlias.trim() ) )
				return alias.getValor();
		
		throw new Exception( "Nao existe neste grupo o alias especificado." );
	}
	
}
