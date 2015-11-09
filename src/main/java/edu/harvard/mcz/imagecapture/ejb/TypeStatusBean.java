/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.TypeStatus;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author mole
 */
@Stateless
public class TypeStatusBean implements  TypeStatusBeanLocal {

	public List<String> getValues() {
		ArrayList<String> valuesList = new ArrayList<String>();
		String values[] = TypeStatus.getTypeStatusValues();
		if (values != null) {
			for (int x = 0; x < values.length; x++) {
				valuesList.add(values[x]);
			}
		}
		return valuesList;
	}
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
 
}
