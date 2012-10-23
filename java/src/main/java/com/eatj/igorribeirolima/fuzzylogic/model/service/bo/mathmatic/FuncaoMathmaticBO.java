package com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Dominio;
import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Funcao;

public class FuncaoMathmaticBO {
	private Funcao funcao;
	private Dominio dominio;
	
	public FuncaoMathmaticBO( Funcao funcao ){
		this.funcao = funcao;
		this.dominio = funcao.getDominio();
	}
	
	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
		this.dominio = funcao.getDominio();
	}

	public Dominio getDominio() {
		return dominio;
	}

	public Double fx( Double x ) throws Exception{
		if( pertenceAoDominio(x) ){
			return Fx.fx( this.funcao.getTxFuncao(), x);
		}else{
			throw new Exception( "O valor nao pertence ao dominio da funcao" );
		}
	}
	
	public Double fx( Float x ) throws Exception{
		if( pertenceAoDominio(x) ){
			return Fx.fx( this.funcao.getTxFuncao(), x);
		}else{
			throw new Exception( "O valor nao pertence ao dominio da funcao" );
		}
	}
	
	public Double fx( Integer x ) throws Exception{
		if( pertenceAoDominio(x) ){
			return Fx.fx( this.funcao.getTxFuncao(), x);
		}else{
			throw new Exception( "O valor nao pertence ao dominio da funcao" );
		}
	}
	
	public Double fx( Long x ) throws Exception{
		if( pertenceAoDominio(x) ){
			return Fx.fx( this.funcao.getTxFuncao(), x);
		}else{
			throw new Exception( "O valor nao pertence ao dominio da funcao" );
		}
	}
	
	public boolean hasIntercecao( Dominio dominio ){
		//Verificando limite inferior
		if( this.dominio.isIncluiLimiteInferior() && this.dominio.isIncluiLimiteSuperior() && this.dominio.getLimiteInferior() <= dominio.getLimiteInferior() && dominio.getLimiteInferior() <= this.dominio.getLimiteSuperior() ){
			return true;
		}else if( this.dominio.isIncluiLimiteInferior() && !this.dominio.isIncluiLimiteSuperior() && this.dominio.getLimiteInferior() <= dominio.getLimiteInferior() && dominio.getLimiteInferior() < this.dominio.getLimiteSuperior() ){
			return true;
		}else if( !this.dominio.isIncluiLimiteInferior() && this.dominio.isIncluiLimiteSuperior() && this.dominio.getLimiteInferior() < dominio.getLimiteInferior() && dominio.getLimiteInferior() <= this.dominio.getLimiteSuperior() ){
			return true;
		}else if( !this.dominio.isIncluiLimiteInferior() && !this.dominio.isIncluiLimiteSuperior() && this.dominio.getLimiteInferior() < dominio.getLimiteInferior() && dominio.getLimiteInferior() < this.dominio.getLimiteSuperior() ){
			return true;
		//Verificando limite superior
		}else if( this.dominio.isIncluiLimiteInferior() && this.dominio.isIncluiLimiteSuperior() && this.dominio.getLimiteInferior() <= dominio.getLimiteSuperior() && dominio.getLimiteSuperior() <= this.dominio.getLimiteSuperior() ){
			return true;
		}else if( this.dominio.isIncluiLimiteInferior() && !this.dominio.isIncluiLimiteSuperior() && this.dominio.getLimiteInferior() <= dominio.getLimiteSuperior() && dominio.getLimiteSuperior() < this.dominio.getLimiteSuperior() ){
			return true;
		}else if( !this.dominio.isIncluiLimiteInferior() && this.dominio.isIncluiLimiteSuperior() && this.dominio.getLimiteInferior() < dominio.getLimiteSuperior() && dominio.getLimiteSuperior() <= this.dominio.getLimiteSuperior() ){
			return true;
		}else if( !this.dominio.isIncluiLimiteInferior() && !this.dominio.isIncluiLimiteSuperior() && this.dominio.getLimiteInferior() < dominio.getLimiteSuperior() && dominio.getLimiteSuperior() < this.dominio.getLimiteSuperior() ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean pertenceAoDominio( Double x ){
		if( dominio.isIncluiLimiteInferior() && dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() <= x && x <= dominio.getLimiteSuperior() ){
			return true;
		}else if( dominio.isIncluiLimiteInferior() && !dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() <= x && x < dominio.getLimiteSuperior() ){
			return true;
		}else if( !dominio.isIncluiLimiteInferior() && dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() < x && x <= dominio.getLimiteSuperior() ){
			return true;
		}else if( !dominio.isIncluiLimiteInferior() && !dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() < x && x < dominio.getLimiteSuperior() ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean pertenceAoDominio( Float x ){
		if( dominio.isIncluiLimiteInferior() && dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() <= x && x <= dominio.getLimiteSuperior() ){
			return true;
		}else if( dominio.isIncluiLimiteInferior() && !dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() <= x && x < dominio.getLimiteSuperior() ){
			return true;
		}else if( !dominio.isIncluiLimiteInferior() && dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() < x && x <= dominio.getLimiteSuperior() ){
			return true;
		}else if( !dominio.isIncluiLimiteInferior() && !dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() < x && x < dominio.getLimiteSuperior() ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean pertenceAoDominio( Long x ){
		if( dominio.isIncluiLimiteInferior() && dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() <= x && x <= dominio.getLimiteSuperior() ){
			return true;
		}else if( dominio.isIncluiLimiteInferior() && !dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() <= x && x < dominio.getLimiteSuperior() ){
			return true;
		}else if( !dominio.isIncluiLimiteInferior() && dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() < x && x <= dominio.getLimiteSuperior() ){
			return true;
		}else if( !dominio.isIncluiLimiteInferior() && !dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() < x && x < dominio.getLimiteSuperior() ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean pertenceAoDominio( Integer x ){
		if( dominio.isIncluiLimiteInferior() && dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() <= x && x <= dominio.getLimiteSuperior() ){
			return true;
		}else if( dominio.isIncluiLimiteInferior() && !dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() <= x && x < dominio.getLimiteSuperior() ){
			return true;
		}else if( !dominio.isIncluiLimiteInferior() && dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() < x && x <= dominio.getLimiteSuperior() ){
			return true;
		}else if( !dominio.isIncluiLimiteInferior() && !dominio.isIncluiLimiteSuperior() && dominio.getLimiteInferior() < x && x < dominio.getLimiteSuperior() ){
			return true;
		}else{
			return false;
		}
	}
	
	public String toString(){
		return this.funcao.getTxFuncao()+";"+this.funcao.getDominio().toString();
	}
}
