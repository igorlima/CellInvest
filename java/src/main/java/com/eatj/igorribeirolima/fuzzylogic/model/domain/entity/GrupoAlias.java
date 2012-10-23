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
public class GrupoAlias implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idGrupoAlias;
	
	@Column( name="nmGrupoAlias", nullable = false, unique = true )
	private String nome;
	
	@ManyToMany( cascade={CascadeType.ALL}, fetch=FetchType.EAGER )
  @JoinTable(
      name="RelAlias_GrupoAlias",
      joinColumns=@JoinColumn(name="idGrupoAlias"),
      inverseJoinColumns=@JoinColumn(name="idAlias")
  )
	@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private Set<Alias> aliasCollection;
	
	public GrupoAlias(){
		
	}

	public Long getIdGrupoAlias() {
		return idGrupoAlias;
	}

	public void setIdGrupoAlias(Long idGrupoAlias) {
		this.idGrupoAlias = idGrupoAlias;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Alias> getAliasCollection() {
		return aliasCollection;
	}

	public void setAliasCollection(Set<Alias> aliasCollection) {
		this.aliasCollection = aliasCollection;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idGrupoAlias == null ) ? 0 : idGrupoAlias.hashCode() );
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
		if ( !( obj instanceof GrupoAlias ) ) {
			return false;
		}
		GrupoAlias other = ( GrupoAlias ) obj;
		if ( idGrupoAlias == null ) {
			return false;
		}else if ( other.idGrupoAlias == null ) {
			return false;
		}else if ( idGrupoAlias.equals( other.idGrupoAlias ) ) {
			return true;
		}
		
		return false;
	}
}
