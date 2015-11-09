/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.PositionTemplate;
import edu.harvard.mcz.imagecapture.exceptions.NoSuchTemplateException;
import javax.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface PositionTemplateFacadeLocal {

	public PositionTemplate findPositionTemplate(String templateToUse);

}
