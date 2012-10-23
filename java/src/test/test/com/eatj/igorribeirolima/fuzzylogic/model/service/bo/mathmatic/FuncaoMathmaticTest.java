package test.com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import org.junit.Before;
import org.junit.Test;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.Funcao;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.MockBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.FuncaoMathmaticBO;

import static org.junit.Assert.*;

public class FuncaoMathmaticTest {
	
	private FuncaoMathmaticBO funcaoMathmaticBO;
	
	@Before
	public void init(){
		Funcao funcao = (Funcao) MockBO.getMockTest( Funcao.class.getName(), "funcao.json" );
		funcaoMathmaticBO = new FuncaoMathmaticBO( funcao );
	}
	
	@Test
	public void testandoFuncao() throws Exception{
		assertEquals( new Double(1), funcaoMathmaticBO.fx( 0 ) );
		assertEquals( new Double(0), funcaoMathmaticBO.fx( 35 ) );
	}
	
	@Test( expected=Exception.class )
	public void calculandoFuncaoForaDoDominio() throws Exception{
		assertEquals( new Double(1), funcaoMathmaticBO.fx( 101 ) );
		assertEquals( new Double(1), funcaoMathmaticBO.fx( -1 ) );
	}
	
	
}
