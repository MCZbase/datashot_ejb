/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.LocationInCollection;
import java.util.ArrayList;
import java.util.List;
import jakarta.ejb.Stateless;

/**
 *
 * @author mole
 */
@Stateless
public class LocationInCollectionBean implements  LocationInCollectionBeanLocal {

	public List <String> getValues() {
		ArrayList<String> valuesList = new ArrayList<String>();
		String values[] = LocationInCollection.getLocationInCollectionValues();
		if (values != null) {
			for (int x = 0; x < values.length; x++) {
				valuesList.add(values[x]);
			}
		}
		return valuesList;
	}
    
 
}
