package org.destro.space.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;

/**
 * Classe utilitária para tratamento de strings
 * 
 * @author destro
 */
public class StringUtils {

	/**
	 * Normaliza string
	 * 
	 * @param s
	 * @return
	 */
	public static String normalize(String s) {
		return Normalizer.normalize(s, Form.NFD).toLowerCase()
				.replaceAll("\\p{P}", "").replaceAll("\\s+", "-")
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
}
