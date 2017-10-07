/**
 * 
 */
package com.statictomato.util;

import com.statictomato.model.Emoticon;

/**
 * @author Thomas Asheim Smedmann
 *
 */
public abstract class Validator {

	public static boolean isValidEmoticon(final Emoticon emoticon) {
		
		return emoticon != null && emoticon.getIcon() != null && !emoticon.getIcon().isEmpty();
	}
	
}
