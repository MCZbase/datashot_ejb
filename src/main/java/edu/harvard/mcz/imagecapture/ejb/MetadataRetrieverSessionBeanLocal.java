/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.mcz.imagecapture.ejb;

import jakarta.ejb.Local;
import javax.swing.InputVerifier;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author mole
 */
@Local
public interface MetadataRetrieverSessionBeanLocal {

	public String getFieldHelp(Class aTableClass, String fieldname);

	public MaskFormatter getMask(Class aTableClass, String fieldname);

	public int getFieldLength(Class aTableClass, String fieldname);

	public InputVerifier getInputVerifier(Class aTableClass, String fieldname);
	
    public String getRegex(Class aTableClass, String fieldname);
    
}
