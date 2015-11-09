/**
 * 
 */
package edu.harvard.mcz.imagecapture.data;

import edu.harvard.mcz.imagecapture.interfaces.ValueLister;

/**
 * Sex provides an authority file of values for the field Sex that can be used to populate picklists.
 * @author mole
 *
 */
public class Sex implements ValueLister {

	public String[] getValues() {
		return getSexValues();
	}
	
	public static String[] getSexValues() {
		String[] values = {"", "Male", "Female", "NotApplicable", "Gynandromorph", "Intersex","unknown"};
		return values;
	}

}
