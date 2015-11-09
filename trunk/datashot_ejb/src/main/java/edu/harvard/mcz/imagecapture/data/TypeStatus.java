/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.data;

import edu.harvard.mcz.imagecapture.interfaces.ValueLister;

/**
 *
 * @author mole
 */
public class TypeStatus implements ValueLister {

	public static String[] getTypeStatusValues() {
		String[] lifestages = {"Not a Type","Holotype","Paratype","Lectotype","Allotype","Syntype","Neotype","Paralectotype","Topotype","Cotype","Type"};
		return lifestages;
	}

	public String[] getValues() {
		return getTypeStatusValues();
	}



}
