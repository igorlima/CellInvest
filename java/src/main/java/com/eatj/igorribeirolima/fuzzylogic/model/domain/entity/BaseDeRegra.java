package com.eatj.igorribeirolima.fuzzylogic.model.domain.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class BaseDeRegra implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idBaseDeRegra;
	
	@Column( name="nmBaseDeRegra", nullable = false, unique = true )
	private String nome;

	@ManyToMany( cascade={CascadeType.ALL}, fetch=FetchType.EAGER )
  @JoinTable(
      name="RelRegra_BaseDeRegra",
      joinColumns=@JoinColumn(name="idBaseDeRegra"),
      inverseJoinColumns=@JoinColumn(name="idRegra")
  )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private Set<Regra> regras;
	
	public BaseDeRegra(){
		
	}

	public Long getIdBaseDeRegra() {
		return idBaseDeRegra;
	}

	public void setIdBaseDeRegra(Long idBaseDeRegra) {
		this.idBaseDeRegra = idBaseDeRegra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Regra> getRegras() {
		return regras;
	}

	public void setRegras(Set<Regra> regras) {
		this.regras = regras;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idBaseDeRegra == null ) ? 0 : idBaseDeRegra.hashCode() );
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
		if ( !( obj instanceof BaseDeRegra ) ) {
			return false;
		}
		BaseDeRegra other = ( BaseDeRegra ) obj;
		if ( idBaseDeRegra == null ) {
			return false;
		}else if ( other.idBaseDeRegra == null ) {
			return false;
		}else if ( idBaseDeRegra.equals( other.idBaseDeRegra ) ) {
			return true;
		}
		return false;
	}
}
