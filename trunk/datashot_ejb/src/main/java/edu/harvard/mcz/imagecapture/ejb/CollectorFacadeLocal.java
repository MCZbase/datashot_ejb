/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Collector;
import edu.harvard.mcz.imagecapture.utility.CountValue;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface CollectorFacadeLocal {

	void create(Collector collector);

	void edit(Collector collector);

	void remove(Collector collector);

	Collector find(Object id);

	List<Collector> findAll();

	List<Collector> findRange(int[] range);

	int count();

	public List<CountValue> getCollectorValues(boolean orderByFrequency, String filter);

	public List<CountValue> getCollectorValuesRange(int start, int end, boolean orderByFrequency, String filter);

	public int getCollectorValuesCount();

	public int updateAll(String oldCollector, String newCollector);

}
