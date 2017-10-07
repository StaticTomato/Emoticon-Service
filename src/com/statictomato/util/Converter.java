/**
 * 
 */
package com.statictomato.util;

/**
 * @author Thomas Asheim Smedmann
 *
 */
public class Converter {

	public static Integer convertToInteger(final String s) {
		try {
			return Integer.valueOf(s);
		} catch(Exception e) {
			return null;
		}
	}
}
