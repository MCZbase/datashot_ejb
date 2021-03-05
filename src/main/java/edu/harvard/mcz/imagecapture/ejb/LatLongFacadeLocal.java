/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.LatLong;

import java.util.List;

import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface LatLongFacadeLocal {

	void create(LatLong georeference);

	void edit(LatLong georeference);

	void remove(LatLong georeference);
	
	void flush(LatLong georeference);
	
	boolean isManaged(LatLong georeference);

	LatLong find(Object id);

	List<LatLong> findAll();

	int count();

}
