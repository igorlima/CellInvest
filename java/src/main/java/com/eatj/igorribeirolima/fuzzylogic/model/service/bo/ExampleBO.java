package com.eatj.igorribeirolima.fuzzylogic.model.service.bo;

import java.util.List;

import javax.inject.Named;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ObjectAndMessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.service.to.ExampleTO;
import com.eatj.igorribeirolima.fuzzylogic.model.service.to.ExamplesTO;

@Named
public class ExampleBO {

	public ReturnTO listAll() {
		try {
			ExamplesTO obj = (ExamplesTO) MockBO.getExample( ExamplesTO.class.getName(), "allExamples.json" );
			ReturnTO to = new ObjectAndMessageReturnTO<List<ExampleTO>>( obj.getExamples() );

			return to;
		} catch ( Exception e ) {
			e.printStackTrace();
			return new MessageReturnTO( ReturnTO.Status.ERROR, "Não existe exemplo json com o nome especificado." );
		}
	}
	
	public ReturnTO getJsonExample( String type, String fileJson ) {
		try {
			Object obj = MockBO.getExample( type, fileJson );
			ReturnTO to = new ObjectAndMessageReturnTO<Object>( obj );
			
			return to;
		} catch ( Exception e ) {
			e.printStackTrace();
			return new MessageReturnTO( ReturnTO.Status.ERROR, "Não existe exemplo json com o nome especificado." );
		}
	}
	
}
