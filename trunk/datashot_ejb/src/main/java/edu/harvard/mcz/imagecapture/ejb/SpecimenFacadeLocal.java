/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.buisness.StatusCount;
import edu.harvard.mcz.imagecapture.data.Collector;
import edu.harvard.mcz.imagecapture.data.Determination;
import edu.harvard.mcz.imagecapture.data.OtherNumbers;
import edu.harvard.mcz.imagecapture.data.Specimen;
import edu.harvard.mcz.imagecapture.utility.CountValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface SpecimenFacadeLocal {

	void create(Specimen specimen);

	void edit(Specimen specimen);

	void remove(Specimen specimen);

	Specimen find(Object id);

	List<Specimen> findAll();

	List<Specimen> findRange(int[] range);

	List<Specimen> findRangeQuery(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters);

	public List findRangeQueryAndOr(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters, boolean useAnd);


	int count();

	int countFiltered(Map<String, String> filters);

	public List<Specimen> findBySpecimenId(long specimenId);

	public List<StatusCount> getWorkflowStatusCounts();

	public List<String> getCollectionValues();

	public int getCountryValuesCount();

	public List getCountryValuesRange(int pageFirstItem, int pageLastItem, boolean useFrequency, String countryFilterCriterion);

	public int updateAllCountries(String oldValue, String newValue);

	public int getCountryStateValuesCount();

	public List getCountryStateValuesRange(int pageFirstItem, int pageLastItem, boolean useFrequency, String primaryFilter, String countryFilter);

	public int updateAllStates(String countryLimit, String oldValue, String newValue);

	public boolean isCollectorsInMCZbase(long specimenId);
	
	public List<CountValue> getGenusValuesRange(int start, int end, boolean orderByFrequency, String filter, String workflowFilter) ;
	
	public Specimen cloneDetatch(Specimen source);
	
	public void replaceCollectorCollection(Specimen target, Collection<Collector> replacement) ;
	
	public void replaceDeterminationCollection(Specimen target, Collection<Determination> replacement) ;
	
	public void replaceOtherNumbersCollection(Specimen target, Collection<OtherNumbers> replacement) ;	

	public void trackChange(Specimen target) ;
	
	public void flush(Specimen target);
}
