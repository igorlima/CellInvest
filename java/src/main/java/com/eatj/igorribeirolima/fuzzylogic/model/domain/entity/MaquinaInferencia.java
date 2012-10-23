package com.eatj.igorribeirolima.fuzzylogic.model.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class MaquinaInferencia implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idMaquinaInferencia;
	
	@Column( name="nmMaquinaInferencia", nullable = false, unique = true )
	private String nome;
	
	@ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn( name = "idGrupoAlias", referencedColumnName = "idGrupoAlias" )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private GrupoAlias grupoAlias;
	
	@ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn( name = "idBaseDeRegra", referencedColumnName = "idBaseDeRegra" )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private BaseDeRegra baseDeRegra;
	
	public MaquinaInferencia(){
		
	}
	
	public MaquinaInferencia( String nome ){
	  setNome(nome);
	}

	public Long getIdMaquinaInferencia() {
		return idMaquinaInferencia;
	}

	public void setIdMaquinaInferencia(Long idMaquinaInferencia) {
		this.idMaquinaInferencia = idMaquinaInferencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrupoAlias getGrupoAlias() {
		return grupoAlias;
	}

	public void setGrupoAlias(GrupoAlias grupoAlias) {
		this.grupoAlias = grupoAlias;
	}

	public BaseDeRegra getBaseDeRegra() {
		return baseDeRegra;
	}

	public void setBaseDeRegra(BaseDeRegra baseDeRegra) {
		this.baseDeRegra = baseDeRegra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idMaquinaInferencia == null ) ? 0 : idMaquinaInferencia.hashCode() );
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
		if ( !( obj instanceof MaquinaInferencia ) ) {
			return false;
		}
		MaquinaInferencia other = ( MaquinaInferencia ) obj;
		if ( idMaquinaInferencia == null ) {
			return false;
		}else if ( other.idMaquinaInferencia == null ) {
			return false;
		}else if ( idMaquinaInferencia.equals( other.idMaquinaInferencia ) ) {
			return true;
		}
		
		return false;
	}
}
