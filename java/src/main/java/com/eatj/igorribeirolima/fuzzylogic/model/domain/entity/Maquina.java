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

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.id.MaquinaId;

@IdClass( MaquinaId.class )
@Entity( name = "RelSistemaFuzzyMaquinaInferencia" )
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class Maquina implements Serializable, Comparable<Maquina> {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idSistemaFuzzy;

	@Id
	private Long idMaquinaInferencia;

	@ManyToOne( cascade = { CascadeType.ALL }, fetch = FetchType.EAGER )
	@JoinColumn( name = "idMaquinaInferencia",
			referencedColumnName = "idMaquinaInferencia",
			insertable = false,
			updatable = false )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private MaquinaInferencia maquinaInferencia;

	private Long prioridade;

	public Maquina() {

	}

	public Long getIdSistemaFuzzy() {
		return idSistemaFuzzy;
	}

	public void setIdSistemaFuzzy( Long idSistemaFuzzy ) {
		this.idSistemaFuzzy = idSistemaFuzzy;
	}

	public Long getIdMaquinaInferencia() {
		return idMaquinaInferencia;
	}

	public void setIdMaquinaInferencia( Long idMaquinaInferencia ) {
		this.idMaquinaInferencia = idMaquinaInferencia;
	}

	public Long getPrioridade() {
		return prioridade;
	}

	public void setPrioridade( Long prioridade ) {
		this.prioridade = prioridade;
	}

	public MaquinaInferencia getMaquinaInferencia() {
		return maquinaInferencia;
	}

	public void setMaquinaInferencia( MaquinaInferencia maquinaInferencia ) {
		this.maquinaInferencia = maquinaInferencia;
	}

	@Override
	public int compareTo( Maquina other ) {
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
		if ( !( obj instanceof Maquina ) ) {
			return false;
		}
		Maquina other = ( Maquina ) obj;
		if ( idSistemaFuzzy == null || idMaquinaInferencia == null ) {
			return false;
		} else if ( other.idSistemaFuzzy == null || other.idMaquinaInferencia == null ) {
			return false;
		} else if ( idSistemaFuzzy.equals(other.idSistemaFuzzy) && idMaquinaInferencia.equals(other.idMaquinaInferencia) ) {
			return true;
		}

		return false;

	}
}
