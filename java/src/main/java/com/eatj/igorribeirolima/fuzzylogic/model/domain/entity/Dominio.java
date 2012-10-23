package com.eatj.igorribeirolima.fuzzylogic.model.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class Dominio implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idDominio;
	
	@Column( nullable = false )
	private Double limiteInferior;
	
	@Column( nullable = false )
	private Double limiteSuperior;
	
	@Column( nullable = false )
	private Boolean incluiLimiteInferior;
	
	@Column( nullable = false )
	private Boolean incluiLimiteSuperior;
	
	public Dominio(){
		
	}
	
	public Dominio( double limiteInferior, double limiteSuperior, boolean incluiLimiteInferior, boolean incluiLimiteSuperior ) throws Exception{
		this.limiteInferior = limiteInferior;
		this.limiteSuperior = limiteSuperior;
		
		this.incluiLimiteInferior = incluiLimiteInferior;
		this.incluiLimiteSuperior = incluiLimiteSuperior;
		
		if( limiteInferior > limiteSuperior )
			throw new Exception( "Limite inferior do dominio eh maior que o limite Superior" );
	}

	public Long getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(Long idDominio) {
		this.idDominio = idDominio;
	}

	public Double getLimiteInferior() {
		return limiteInferior;
	}

	public void setLimiteInferior(Double limiteInferior) {
		this.limiteInferior = limiteInferior;
	}

	public Double getLimiteSuperior() {
		return limiteSuperior;
	}

	public void setLimiteSuperior(Double limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}

	public Boolean isIncluiLimiteInferior() {
		return incluiLimiteInferior;
	}

	public void setIncluiLimiteInferior(Boolean incluiLimiteInferior) {
		this.incluiLimiteInferior = incluiLimiteInferior;
	}

	public Boolean isIncluiLimiteSuperior() {
		return incluiLimiteSuperior;
	}

	public void setIncluiLimiteSuperior(Boolean incluiLimiteSuperior) {
		this.incluiLimiteSuperior = incluiLimiteSuperior;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idDominio == null ) ? 0 : idDominio.hashCode() );
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
		if ( !( obj instanceof Dominio ) ) {
			return false;
		}
		Dominio other = ( Dominio ) obj;
		if ( idDominio == null ) {
			return false;
		}else if ( other.idDominio == null ) {
			return false;
		}else if ( idDominio.equals( other.idDominio ) ) {
			return true;
		}
		return false;
	}
}
