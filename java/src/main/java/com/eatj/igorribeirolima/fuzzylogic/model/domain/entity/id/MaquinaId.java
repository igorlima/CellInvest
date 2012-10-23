package com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.id;

import java.io.Serializable;

public class MaquinaId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idSistemaFuzzy;  
	
	private Long idMaquinaInferencia;
	
	public MaquinaId(){
		
	}
	
	public Long getIdSistemaFuzzy() {
		return idSistemaFuzzy;
	}

	public void setIdSistemaFuzzy(Long idSistemaFuzzy) {
		this.idSistemaFuzzy = idSistemaFuzzy;
	}

	public Long getIdMaquinaInferencia() {
		return idMaquinaInferencia;
	}

	public void setIdMaquinaInferencia(Long idMaquinaInferencia) {
		this.idMaquinaInferencia = idMaquinaInferencia;
	}
	
}
