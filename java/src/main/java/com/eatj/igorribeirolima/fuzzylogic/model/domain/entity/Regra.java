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
public class Regra implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idRegra;
	
	@Column( name="txRegra", nullable = false )
	private String regra;
	
	public Regra(){
		
	}
	
	public Regra( String txRegra ){
		this.regra = txRegra;
	}

	public Long getIdRegra() {
		return idRegra;
	}

	public void setIdRegra(Long idRegra) {
		this.idRegra = idRegra;
	}

	public String getRegra() {
		return regra;
	}

	public void setRegra(String regra) {
		this.regra = regra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idRegra == null ) ? 0 : idRegra.hashCode() );
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
		if ( !( obj instanceof Regra ) ) {
			return false;
		}
		Regra other = ( Regra ) obj;
		if ( idRegra == null ) {
			return false;
		}else if ( other.idRegra == null ) {
			return false;
		}else if ( idRegra.equals( other.idRegra ) ) {
			return true;
		}
		
		return false;
	}
}
