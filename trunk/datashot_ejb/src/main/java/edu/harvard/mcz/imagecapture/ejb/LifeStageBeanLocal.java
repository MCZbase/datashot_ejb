/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface LifeStageBeanLocal {

	List<String> getValues();
    
}
