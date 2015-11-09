/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Label;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface LabelFacadeLocal {

	void create(Label label);

	void edit(Label label);

	void remove(Label label);

	Label find(Object id);

	List<Label> findAll();

	List<Label> findRange(int[] range);

	int count();

	int countFiltered(Map<String, String> filters);

	List<Label> findRangeQuery(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters);

    List<Label> findRangeQueryAndOr(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters, boolean useAnd);

}
