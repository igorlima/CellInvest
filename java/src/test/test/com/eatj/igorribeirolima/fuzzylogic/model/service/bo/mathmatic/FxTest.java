package test.com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.Fx;

public class FxTest {
	
	@Test
	public void testandoSoma() throws Exception{
		assertEquals( new Double(4), Fx.fx( "x+2", 2 ) );
		assertEquals( new Double(5), Fx.fx( "x+3", 2 ) );
		assertEquals( new Double(6), Fx.fx( "x+x+2", 2 ) );
	}
	
	@Test
	public void testandoMultiplacao() throws Exception{
		assertEquals( new Double(4), Fx.fx( "x*2", 2 ) );
		assertEquals( new Double(6), Fx.fx( "x*3", 2 ) );
		assertEquals( new Double(8), Fx.fx( "x*x*2", 2 ) );
	}
	
	@Test
	public void testandoDivisao() throws Exception{
		assertEquals( new Double(2.0/2.0), Fx.fx( "x/2", 2 ) );
		assertEquals( new Double(2.0/3.0), Fx.fx( "x/3", 2 ) );
		assertEquals( new Double(2.0/2.0/2.0), Fx.fx( "x/x/2", 2 ) );
	}
	
	@Test
	public void testandoSubtracao() throws Exception{
		assertEquals( new Double(0), Fx.fx( "x-2", 2 ) );
		assertEquals( new Double(-1), Fx.fx( "x-3", 2 ) );
		assertEquals( new Double(-2), Fx.fx( "x-x-2", 2 ) );
	}
	
	@Test
	public void testandoExpoente() throws Exception{
		assertEquals( new Double(4), Fx.fx( "x^2", 2 ) );
		assertEquals( new Double(8), Fx.fx( "x^3", 2 ) );
		assertEquals( new Double(16), Fx.fx( "x^x^2", 2 ) );
	}
	
	@Test
	public void testandoExpressoesComplexas() throws Exception{
		assertEquals( new Double(-16), Fx.fx( "((2+2)^2-(x^2))-16", 4 ) );
		assertEquals( new Double(0), Fx.fx( "((2+2)^2-(x^2))*16", 4 ) );
		assertEquals( new Double(-64), Fx.fx( "((2+2)^2-(x^3))-16", 4 ) );
		
	}
	
}
