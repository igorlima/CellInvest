package com.eatj.igorribeirolima.fuzzylogic.model.service.to;

import java.io.Serializable;
import java.util.List;


public class ExamplesTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ExampleTO> examples;
	
	public ExamplesTO(){
		
	}

	public List<ExampleTO> getExamples() {
		return examples;
	}
	
	public void setExamples( List<ExampleTO> examples ) {
		this.examples = examples;
	}
	
}
