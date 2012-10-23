package test.com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.eatj.igorribeirolima.fuzzylogic.model.domain.entity.ConjFuzzy;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.MockBO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.bo.mathmatic.ConjFuzzyMathmaticBO;

public class ConjFuzzyMathmaticTest {
	
	private ConjFuzzyMathmaticBO conjFuzzyMathmaticBO;
	
	@Before
	public void init() throws Exception{
		ConjFuzzy conjFuzzy = (ConjFuzzy) MockBO.getMockTest( ConjFuzzy.class.getName(), "conjuntofuzzy.json" );
		conjFuzzyMathmaticBO = new ConjFuzzyMathmaticBO( conjFuzzy );
	}
	
	@Test
	public void testandoConjuntoFuzzy() throws Exception{
		assertEquals( new Double(0.2), conjFuzzyMathmaticBO.getGrauPertinencia(38).get( "Neutro" ) );
	}
	
}
