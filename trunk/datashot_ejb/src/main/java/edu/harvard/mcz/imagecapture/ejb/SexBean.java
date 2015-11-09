/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Sex;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author mole
 */
@Stateless
public class SexBean implements SexBeanLocal {

	public List<String> getSexValues() {
		ArrayList<String> valuesList = new ArrayList<String>();
		String values[] = Sex.getSexValues();
		if (values != null) {
			for (int x = 0; x < values.length; x++) {
				valuesList.add(values[x]);
			}
		}
		return valuesList;
	}
    
 
}
