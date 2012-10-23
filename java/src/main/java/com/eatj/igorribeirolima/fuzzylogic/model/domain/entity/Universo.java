package com.eatj.igorribeirolima.fuzzylogic.model.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.id.UniversoId;

@IdClass( UniversoId.class )
@Entity( name = "RelSistemaFuzzyUniversoFuzzy" )
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class Universo implements Serializable, Comparable<Universo> {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idSistemaFuzzy;

	@Id
	private Long idUniversoDeDiscursoFuzzy;

	@ManyToOne( cascade = { CascadeType.ALL }, fetch = FetchType.EAGER )
	@JoinColumn( name = "idUniversoDeDiscursoFuzzy",
			referencedColumnName = "idUniversoDeDiscursoFuzzy",
			insertable = false,
			updatable = false )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private UniversoDeDiscursoFuzzy universoFuzzy;

	private Long prioridade;

	public Universo() {
	}

	public Long getIdSistemaFuzzy() {
		return idSistemaFuzzy;
	}

	public void setIdSistemaFuzzy( Long idSistemaFuzzy ) {
		this.idSistemaFuzzy = idSistemaFuzzy;
	}

	public Long getIdUniversoDeDiscursoFuzzy() {
		return idUniversoDeDiscursoFuzzy;
	}

	public void setIdUniversoDeDiscursoFuzzy( Long idUniversoDeDiscursoFuzzy ) {
		this.idUniversoDeDiscursoFuzzy = idUniversoDeDiscursoFuzzy;
	}

	public Long getPrioridade() {
		return prioridade;
	}

	public void setPrioridade( Long prioridade ) {
		this.prioridade = prioridade;
	}

	public UniversoDeDiscursoFuzzy getUniversoFuzzy() {
		return universoFuzzy;
	}

	public void setUniversoFuzzy( UniversoDeDiscursoFuzzy universoFuzzy ) {
		this.universoFuzzy = universoFuzzy;
	}

	@Override
	public int compareTo( Universo other ) {
		if ( other == null || other.getPrioridade() == null )
			return 1;
		else if ( getPrioridade() == null )
			return -1;
		else
			return getPrioridade().compareTo( other.getPrioridade() );
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idSistemaFuzzy == null ) ? 0 : idSistemaFuzzy.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( !( obj instanceof Universo ) ) {
			return false;
		}
		Universo other = ( Universo ) obj;
		if ( idSistemaFuzzy == null || idUniversoDeDiscursoFuzzy == null ) {
			return false;
		} else if ( other.idSistemaFuzzy == null || other.idUniversoDeDiscursoFuzzy == null ) {
			return false;
		} else if ( idSistemaFuzzy.equals( other.idSistemaFuzzy ) && idUniversoDeDiscursoFuzzy.equals( other.idUniversoDeDiscursoFuzzy ) ) {
			return true;
		}

		return false;

	}
}
