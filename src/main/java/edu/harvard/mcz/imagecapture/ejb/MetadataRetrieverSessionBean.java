/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.MetadataRetriever;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import javax.swing.InputVerifier;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author mole
 */
@Stateless
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public class MetadataRetrieverSessionBean implements  MetadataRetrieverSessionBeanLocal {

	public String getFieldHelp(Class aTableClass, String fieldname) {
		return MetadataRetriever.getFieldHelp(aTableClass, fieldname);
	}

	 public MaskFormatter getMask(Class aTableClass, String fieldname) {
                return MetadataRetriever.getMask(aTableClass, fieldname);
	 }
    
	 public int getFieldLength(Class aTableClass, String fieldname) {
                return MetadataRetriever.getFieldLength(aTableClass, fieldname);
	 }

	  public InputVerifier getInputVerifier(Class aTableClass, String fieldname) {
                //return MetadataRetriever.getInputVerifier(aTableClass, fieldname, null);
		  return null;
	  }
 
    public String getRegex(Class aTableClass, String fieldname) {
    	return MetadataRetriever.getRegex(aTableClass, fieldname);
    }
	  
}
