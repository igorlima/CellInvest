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
public class Alias implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idAlias;
	
	@Column( name="txAlias", nullable = false )
	private String nome;
	
	@Column( name="txValor", nullable = false )
	private String valor;
	
	public Alias(){
		
	}
	
	public Alias( String txAlias, String txValor ){
		this.nome = txAlias;
		this.valor = txValor;
	}

	public Long getIdAlias() {
		return idAlias;
	}

	public void setIdAlias(Long idAlias) {
		this.idAlias = idAlias;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idAlias == null ) ? 0 : idAlias.hashCode() );
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
		if ( !( obj instanceof Alias ) ) {
			return false;
		}
		Alias other = ( Alias ) obj;
		if ( idAlias == null ) {
			return false;
		}else if ( other.idAlias == null ) {
			return false;
		}else if ( idAlias.equals( other.idAlias ) ) {
			return true;
		}
		return false;
	}
}
