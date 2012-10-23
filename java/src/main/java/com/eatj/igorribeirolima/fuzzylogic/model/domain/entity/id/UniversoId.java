package com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.id;

import java.io.Serializable;

public class UniversoId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long idSistemaFuzzy;  
	
	private Long idUniversoDeDiscursoFuzzy;
	
	public UniversoId(){
		
	}

	public Long getIdSistemaFuzzy() {
		return idSistemaFuzzy;
	}

	public void setIdSistemaFuzzy(Long idSistemaFuzzy) {
		this.idSistemaFuzzy = idSistemaFuzzy;
	}

	public Long getIdUniversoDeDiscursoFuzzy() {
		return idUniversoDeDiscursoFuzzy;
	}

	public void setIdUniversoDeDiscursoFuzzy(Long idUniversoDeDiscursoFuzzy) {
		this.idUniversoDeDiscursoFuzzy = idUniversoDeDiscursoFuzzy;
	}

}
