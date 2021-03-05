/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Determination;
import java.util.List;
import java.util.Map;
import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface DeterminationFacadeLocal {

	void create(Determination determination);

	void edit(Determination determination);

	void remove(Determination determination);

	Determination find(Object id);

	List<Determination> findAll();

	List<Determination> findRange(int[] range);

	int count();

	List<Determination> findRangeQuery(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters);

	public List<Determination> findRangeQueryAndOr(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters, boolean useAnd);

	int countFiltered(Map<String, String> filters);

}
