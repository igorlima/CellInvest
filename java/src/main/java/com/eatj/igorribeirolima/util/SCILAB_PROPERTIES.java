package com.eatj.igorribeirolima.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SCILAB_PROPERTIES {
	private static final String BUNDLE_NAME = "scilab";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private SCILAB_PROPERTIES() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
