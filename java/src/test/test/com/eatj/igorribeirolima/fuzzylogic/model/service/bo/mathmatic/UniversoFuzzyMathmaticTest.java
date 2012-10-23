package test.com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.UniversoDeDiscursoFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.MockBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.UnivFuzzyMathmaticBO;

public class UniversoFuzzyMathmaticTest {
	
	private UnivFuzzyMathmaticBO univFuzzyMathmaticBO;
	
	@Before
	public void init() throws Exception{
		UniversoDeDiscursoFuzzy universoFuzzy = (UniversoDeDiscursoFuzzy) MockBO.getMockTest( UniversoDeDiscursoFuzzy.class.getName(), "universodediscursofuzzy.json" );
		univFuzzyMathmaticBO = new UnivFuzzyMathmaticBO( universoFuzzy );
	}
	
	@Test
	public void testandoConjuntoFuzzy() throws Exception{
		assertEquals( "{MACD={Neutro=0.0, Baixo=1.0, MuitoBaixo=0.0, Alto=0.0, MuitoAlto=0.0}}", univFuzzyMathmaticBO.fuzzyficar(35).toString() );
	}
	
	@Test( expected=Exception.class )
	public void lancarExcecaoCasoEstejaForaDoDominio() throws Exception{
		univFuzzyMathmaticBO.fuzzyficar(101).toString();
	}
	
}
