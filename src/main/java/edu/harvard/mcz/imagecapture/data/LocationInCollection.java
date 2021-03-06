/**
 * 
 */
package edu.harvard.mcz.imagecapture.data;

/**Controled vocabulary for Specimen.locationInCollection, storage and workflow indicator 
 * for types, general collection, slides, etc.
 * @author mole
 *
 */
public class LocationInCollection {

	public static final String GENERAL = "General Lepidoptera Collection";
	
	public static String[] getLocationInCollectionValues() { 
		String [] values = {GENERAL, "General Ant Collection", "Type Collection", "Nabokov Collection", "Slide Collection"}; 
		return values;
	}
}
