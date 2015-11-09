/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.OtherNumbers;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface OtherNumbersFacadeLocal {

	void create(OtherNumbers otherNumbers);

	void edit(OtherNumbers otherNumbers);

	void remove(OtherNumbers otherNumbers);

	OtherNumbers find(Object id);

	List<OtherNumbers> findAll();

	List<OtherNumbers> findRange(int[] range);

	int count();

}
