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
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class UniversoDeDiscursoFuzzy implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idUniversoDeDiscursoFuzzy;
	
	@Column( unique=true )
	private String nome;
	
	@ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn( name = "idDominio", referencedColumnName = "idDominio" )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private Dominio dominio;
	
	@ManyToMany( cascade={CascadeType.ALL}, fetch=FetchType.EAGER )
  @JoinTable(
      name="RelConjFuzzyUniversoFuzzy",
      joinColumns=@JoinColumn(name="idUniversoDeDiscursoFuzzy"),
      inverseJoinColumns=@JoinColumn(name="idConjFuzzy")
  )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private Set<ConjFuzzy> conjuntosFuzzy;
	
	
	public UniversoDeDiscursoFuzzy(){
	}
	
	public UniversoDeDiscursoFuzzy( String nome ){
		setNome(nome);
	}
	
	public UniversoDeDiscursoFuzzy( String nome, Set<ConjFuzzy> conjuntosFuzzy, Dominio dominio){
		setNome(nome);
		setConjuntosFuzzy(conjuntosFuzzy);
		setDominio(dominio);
	}

	public Long getIdUniversoDeDiscursoFuzzy() {
		return idUniversoDeDiscursoFuzzy;
	}

	public void setIdUniversoDeDiscursoFuzzy(Long idUniversoDeDiscursoFuzzy) {
		this.idUniversoDeDiscursoFuzzy = idUniversoDeDiscursoFuzzy;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<ConjFuzzy> getConjuntosFuzzy() {
		return conjuntosFuzzy;
	}

	public void setConjuntosFuzzy(Set<ConjFuzzy> conjuntosFuzzy) {
		this.conjuntosFuzzy = conjuntosFuzzy;
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
		result = prime * result + ( ( idUniversoDeDiscursoFuzzy == null ) ? 0 : idUniversoDeDiscursoFuzzy.hashCode() );
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
		if ( !( obj instanceof UniversoDeDiscursoFuzzy ) ) {
			return false;
		}
		UniversoDeDiscursoFuzzy other = ( UniversoDeDiscursoFuzzy ) obj;
		if ( idUniversoDeDiscursoFuzzy == null ) {
			return false;
		}else if ( other.idUniversoDeDiscursoFuzzy == null ) {
			return false;
		}else if ( idUniversoDeDiscursoFuzzy.equals( other.idUniversoDeDiscursoFuzzy ) ) {
			return true;
		}
		
		return false;
	}
}