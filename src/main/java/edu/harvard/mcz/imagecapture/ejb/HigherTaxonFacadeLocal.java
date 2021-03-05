/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.HigherTaxon;
import java.util.List;
import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface HigherTaxonFacadeLocal {

	void create(HigherTaxon higherTaxon);

	void edit(HigherTaxon higherTaxon);

	void remove(HigherTaxon higherTaxon);

	HigherTaxon find(Object id);

	List<HigherTaxon> findAll();

	List<HigherTaxon> findRange(int[] range);

	int count();
	
	public boolean isFamilyWithCastes(String family);

}
