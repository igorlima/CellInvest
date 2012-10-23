package com.eatj.igorribeirolima.fuzzylogic.model.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class Funcao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idFuncao;
	
	private String txFuncao;
	
	@ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn( name = "idDominio", referencedColumnName = "idDominio" )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private Dominio dominio;
	
	public Funcao(){
		
	}

	public Long getIdFuncao() {
		return idFuncao;
	}

	public void setIdFuncao(Long id) {
		this.idFuncao = id;
	}

	public String getTxFuncao() {
		return txFuncao;
	}

	public void setTxFuncao(String txFuncao) {
		this.txFuncao = txFuncao;
	}

	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idFuncao == null ) ? 0 : idFuncao.hashCode() );
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
		if ( !( obj instanceof Funcao ) ) {
			return false;
		}
		Funcao other = ( Funcao ) obj;
		if ( idFuncao == null ) {
			return false;
		}else if ( other.idFuncao == null ) {
			return false;
		}else if ( idFuncao.equals( other.idFuncao ) ) {
			return true;
		}
		return false;
	}
}
