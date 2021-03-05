/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.UnitTrayLabel;
import java.util.List;
import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface UnitTrayLabelFacadeLocal {

	void create(UnitTrayLabel unitTrayLabel);

	void edit(UnitTrayLabel unitTrayLabel);

	void remove(UnitTrayLabel unitTrayLabel);

	UnitTrayLabel find(Object id);

	List<UnitTrayLabel> findAll();

	List<UnitTrayLabel> findRange(int[] range);

	int count();

}
