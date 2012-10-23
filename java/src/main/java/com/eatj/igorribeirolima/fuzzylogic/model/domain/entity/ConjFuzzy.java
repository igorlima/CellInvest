package com.eatj.igorribeirolima.fuzzylogic.model.domain.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
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
public class ConjFuzzy implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idConjFuzzy;
	
	private String nome;
	
	@ManyToMany( cascade={CascadeType.ALL}, fetch=FetchType.EAGER )
  @JoinTable(
      name="RelFuncaoConjFuzzy",
      joinColumns=@JoinColumn(name="idConjFuzzy"),
      inverseJoinColumns=@JoinColumn(name="idFuncao")
  )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private Set<Funcao> funcoes;

	
	public ConjFuzzy(){
		
	}
	
	public ConjFuzzy( String nome ){
    setNome(nome);
  }
	
	public Long getIdConjFuzzy() {
		return idConjFuzzy;
	}

	public void setIdConjFuzzy(Long id) {
		this.idConjFuzzy = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Funcao> getFuncoes() {
		return funcoes;
	}

	
	public void setFuncoes( Set<Funcao> funcoes ) {
		this.funcoes = funcoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idConjFuzzy == null ) ? 0 : idConjFuzzy.hashCode() );
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
		if ( !( obj instanceof ConjFuzzy ) ) {
			return false;
		}
		ConjFuzzy other = ( ConjFuzzy ) obj;
		if ( idConjFuzzy == null ) {
			return false;
		}else if ( other.idConjFuzzy == null ) {
			return false;
		}else if ( idConjFuzzy.equals( other.idConjFuzzy ) ) {
			return true;
		}
		return false;
	}
}
