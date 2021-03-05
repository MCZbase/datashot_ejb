/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import java.util.List;
import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface LocationInCollectionBeanLocal {

	List <String> getValues();
    
}
