/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Tracking;
import java.util.List;
import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface TrackingFacadeLocal {

	void create(Tracking tracking);

	void edit(Tracking tracking);

	void remove(Tracking tracking);

	Tracking find(Object id);

	List<Tracking> findAll();

	List<Tracking> findRange(int[] range);

	int count();

}
