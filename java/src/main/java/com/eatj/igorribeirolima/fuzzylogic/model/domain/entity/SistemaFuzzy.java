package com.eatj.igorribeirolima.fuzzylogic.model.domain.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class SistemaFuzzy implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idSistemaFuzzy;
	
	@Column( name="nmSistemaFuzzy", nullable = false, unique = true )
	private String nome;
	
  @OrderBy(value="prioridade")
  @OneToMany( mappedBy="idSistemaFuzzy", cascade=CascadeType.ALL, fetch=FetchType.EAGER )
  @Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private Set<Maquina> maquinas;
	
  @OrderBy(value="prioridade")
  @OneToMany( mappedBy="idSistemaFuzzy", cascade=CascadeType.ALL, fetch=FetchType.EAGER )
  @Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
	private Set<Universo> universos;
	
	public SistemaFuzzy(){
		
	}
	
	public SistemaFuzzy( String nome ){
		setNome(nome);
	}

	public Long getIdSistemaFuzzy() {
		return idSistemaFuzzy;
	}

	public void setIdSistemaFuzzy(Long idSistemaFuzzy) {
		this.idSistemaFuzzy = idSistemaFuzzy;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LinkedHashSet<Maquina> getMaquinas() {
		return new LinkedHashSet<Maquina>( maquinas );
	}

	public void setMaquinas(Set<Maquina> maquinas) {
		this.maquinas = maquinas;
	}

	public LinkedHashSet<Universo> getUniversos() {
		return new LinkedHashSet<Universo>( universos );
	}

	public void setUniversos(Set<Universo> universos) {
		this.universos = universos;
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
		if ( !( obj instanceof SistemaFuzzy ) ) {
			return false;
		}
		SistemaFuzzy other = ( SistemaFuzzy ) obj;
		if ( idSistemaFuzzy == null ) {
			return false;
		}else if( other.idSistemaFuzzy == null ){
			return false;
		}else if( idSistemaFuzzy.equals( other.idSistemaFuzzy ) ) {
			return true;
		}
		
		return false;
	}
}
