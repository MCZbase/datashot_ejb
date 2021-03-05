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
import edu.harvard.mcz.imagecapture.data.Tracking;
import edu.harvard.mcz.imagecapture.data.Users;
import edu.harvard.mcz.imagecapture.utility.CountValue;
import edu.harvard.mcz.imagecapture.utility.CountValueValue;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.CopyGroup;


/**
 *
 * @author mole
 */
@Stateless(name="specimenFacade")
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public class SpecimenFacade extends AbstractFacade<Specimen> implements SpecimenFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;
	
	@EJB
	private CollectorFacadeLocal collectorFacade;
	@EJB
	private DeterminationFacadeLocal determinationFacade;
	@EJB
	private OtherNumbersFacadeLocal otherNumbersFacade;

	private final static Logger logger = Logger.getLogger(SpecimenFacade.class.getName());

	protected EntityManager getEntityManager() {
		return em;
	}

	public SpecimenFacade() {
		super(Specimen.class);
	}

	public List<Specimen> findBySpecimenId(long specimenId) {
		Query q = em.createNamedQuery("Specimen.findBySpecimenId");
                q.setParameter("specimenId",specimenId);
		List<Specimen> result = q.getResultList();
		return result;
	}

	public List<Specimen> findByCollector(String collector) {
		Query q = em.createNamedQuery("Specimen.findByCollector");
                q.setParameter("collectorName",collector);
		List<Specimen> result = q.getResultList();
		return result;
	}

	public boolean isCollectorsInMCZbase(long specimenId) {
		boolean result = false;
		try {
			// Try native MySQL query
			Query q = em.createNativeQuery("select is_collectors_in_mczbase(" + specimenId + ") ");
			// Puts an ORA-00923 into the server log from EclipseLink when running over Oracle
			List<Integer> results = q.getResultList();
			Iterator<Integer> i = results.iterator();
			while (i.hasNext()) {
				Integer value = i.next();
				if (value.toString().equals("1")) {
					result = true;
				}
			}
		} catch (Exception e) {
			// Try native oracle query
			Query q = em.createNativeQuery("select is_collectors_in_mczbase(" + specimenId + ") from dual");
			logger.log(Level.INFO, "Handling ORA-00923.  Retrying query for oracle with 'from dual' appended");
			List<BigDecimal> results = q.getResultList();
			Iterator<BigDecimal> i = results.iterator();
			while (i.hasNext()) {
				BigDecimal value = i.next();
				if (value.toString().equals("1")) {
					result = true;
				}
			}
		}
		return result;
	}

	public List<StatusCount> getWorkflowStatusCounts() {
		ArrayList<StatusCount> result = new ArrayList<StatusCount>();
		Query q = em.createQuery("select count(s.specimenId), s.workFlowStatus from Specimen s where s.workFlowStatus <> 'Moved to MCZbase' or (s.workFlowStatus = 'Moved to MCZbase' and s.flagInMCZbase = 1)  group by s.workFlowStatus");
		List<Object[]> results = q.getResultList();
        Iterator<Object[]> i = results.iterator();
		while (i.hasNext()) {
			 Object[] values = i.next();
			 StatusCount ct = new StatusCount((String)values[1], (Long)values[0]) ;
			 // find subcount for ants
			 Query antQ = null;
			 if (((String)values[1]).equals("Moved To MCZbase")) { 
			    antQ = em.createQuery("select count(s.specimenId), s.workFlowStatus from Specimen s where s.workFlowStatus = 'Moved to MCZbase' and s.flagInMCZbase = 1 and s.family = 'Formicidae' group by s.workFlowStatus");
			 } else {
			    antQ = em.createQuery("select count(s.specimenId), s.workFlowStatus from Specimen s where s.workFlowStatus = '"+ (String)values[1] +"' and s.family = 'Formicidae' group by s.workFlowStatus");
			 }
			 List<Object[]> antResults = antQ.getResultList();
			 Iterator<Object[]> it = antResults.iterator();
			 if (it.hasNext()) { 
				 Object[] antValues = it.next();
				 ct.setGroupCount1((Long)antValues[0]);
			 }
			 result.add(ct);
		}
		
		Query qbl = em.createQuery("select count(s.specimenId), s.workFlowStatus, s.flagInMCZbase from Specimen s where s.workFlowStatus = 'Moved to MCZbase' and s.flagInMCZbase = 0 group by s.workFlowStatus, s.flagInMCZbase");
		List<Object[]> resultsbl = qbl.getResultList();
        Iterator<Object[]> ibl = resultsbl.iterator();
		while (ibl.hasNext()) {
			 Object[] values = ibl.next();
			 StatusCount ct = new StatusCount("In Bulkloader", (Long)values[0]) ;
			 // find subcount for ants
			 Query antQ = em.createQuery("select count(s.specimenId), s.workFlowStatus, s.flagInMCZbase from Specimen s where s.workFlowStatus = '"+ (String)values[1] +"' and s.flagInMCZbase = 0 and s.family = 'Formicidae' group by s.workFlowStatus, s.flagInMCZbase");
			 List<Object[]> antResults = antQ.getResultList();
			 Iterator<Object[]> it = antResults.iterator();
			 if (it.hasNext()) { 
				 Object[] antValues = it.next();
				 ct.setGroupCount1((Long)antValues[0]);
			 }
			 result.add(ct);
		}		
	    return result;
	}

	public List<String> getCollectionValues() {
		ArrayList<String> valuesList = new ArrayList<String>();
		Query q = em.createQuery("Select distinct s.collection from Specimen s where s.collection is not null order by s.collection");
		List<String> results = q.getResultList();
        Iterator<String> i = results.iterator();
		while (i.hasNext()) {
			 String value = i.next();
			     valuesList.add(value);
		}
		return valuesList;
	}

	/** Get the number of distinct values in the country field.
	 *
	 * @return
	 */
	public int getCountryValuesCount() {
		int result = 0;
		Query q = em.createQuery("Select count(distinct s.country) from Specimen s where s.country is not null");
		List<Long> results = q.getResultList();
        Iterator<Long> i = results.iterator();
		while (i.hasNext()) {
			result = i.next().intValue();
		}
		return result;
	}

	/**
	 * Get a page of a list of unique values for country name and their frequencies.
	 * Runs a select count(*), country from Specimen group by country query.
	 *
	 * @param start
	 * @param end
	 * @param orderByFrequency  if true, sort by frequency rather than alphabetically by country name.
	 * @param filter if not null or blank, a substring search criterion to apply to the list
	 * @return
	 */
	public List<CountValue> getCountryValuesRange(int start, int end, boolean orderByFrequency, String filter) {
		ArrayList<CountValue> valuesList = new ArrayList<CountValue>();
	    String whereBit = "";
		if (filter!=null && filter.length()>0) {
			whereBit = " and s.country like '%" + filter +"%' ";
		}
		String queryString = "Select count(s), s.country from Specimen s where s.country is not null " + whereBit + " group by s.country order by s.country";
		if (orderByFrequency) {
		    queryString = "Select count(s.specimenId), s.country from Specimen s where s.country is not null "+whereBit+" group by s.country order by count(s.specimenId) desc, s.country ";
		}
		Query q = em.createQuery(queryString);
		q.setFirstResult(start);
		q.setMaxResults(end-start);
		List<Object[]> results = q.getResultList();
        Iterator<Object[]> i = results.iterator();
		while (i.hasNext()) {
			 Object[] arr = i.next();
			 String value = (String)arr[1];
			 int count = ((Long)arr[0]).intValue();
			 valuesList.add(new CountValue(count,value));
		}
		return valuesList;
	}

	/**
	 * For all specimens having the country equal to the old value, set it to the new value.
	 *
	 * @param oldValue
	 * @param newValue
	 * @return  the number of rows updated.
	 */
	public int updateAllCountries(String oldValue, String newValue) {
		int result = 0;
		logger.log(Level.INFO, "Updating Specimen " + oldValue + " to " + newValue);
		if (oldValue!=null && newValue!=null && newValue.length()>0) {
           Query q = em.createQuery("update Specimen s set s.country = '" + newValue + "' where s.country = :old");
		   q.setParameter("old", oldValue);
		   result = q.executeUpdate();
		}
		return result;
	}

	/** Get the number of distinct values in the country and state fields.
	 *
	 * @return count of number of distinct values.
	 */
	public int getCountryStateValuesCount() {
		int result = 0;
		Query q = em.createQuery("select distinct s.country, s.primaryDivison from " +
				  " Specimen s where s.country is not null and s.primaryDivison is not null ");
		//List<Long> results = q.getResultList();
		result = q.getResultList().size();
        //Iterator<Long> i = results.iterator();
		//while (i.hasNext()) {
		//	result = i.next().intValue();
		//}
		return result;
	}

	/**
	 * Get a page of a list of unique values for country name, primary division name
	 * and their frequencies.
	 * Runs a select count(*), country, primarydivison from Specimen group by country, primarydivison query.
	 *
	 * Note misspelling of field name primaryDivison in schema.
	 *
	 * @param start
	 * @param end
	 * @param orderByFrequency  if true, sort by frequency rather than alphabetically by country name.
	 * @param primaryFilter if not null or blank, a substring search criterion to apply to the list
	 * @param countryFilter if not null or blank, a substring search criterion to apply to the list
	 * @return
	 */
	public List<CountValueValue> getCountryStateValuesRange(int start, int end, boolean orderByFrequency, String primaryFilter, String countryFilter) {
		ArrayList<CountValueValue> valuesList = new ArrayList<CountValueValue>();
	    String whereBit = "";
		if (countryFilter!=null && countryFilter.length()>0) {
			whereBit = " and s.country like '%" + countryFilter +"%' ";
		}
		if (primaryFilter!=null && primaryFilter.length()>0) {
			whereBit += " and s.primaryDivison like '%" + primaryFilter +"%' ";
		}
		String queryString = "Select count(s), s.country, s.primaryDivison from Specimen s " +
				"where s.primaryDivison is not null and s.country is not null " + whereBit +
				" group by s.country, s.primaryDivison " +
				" order by s.country, s.primaryDivison";
		if (orderByFrequency) {
		    queryString = "Select count(s.specimenId), s.country, s.primaryDivison from Specimen s " +
					" where s.primaryDivison is not null and s.country is not null " + whereBit +
					" group by s.country, s.primaryDivison " +
					" order by count(s.specimenId) desc, s.country, s.primaryDivison ";
		}
		Query q = em.createQuery(queryString);
		q.setFirstResult(start);
		q.setMaxResults(end-start);
		List<Object[]> results = q.getResultList();
        Iterator<Object[]> i = results.iterator();
		while (i.hasNext()) {
			 Object[] arr = i.next();
			 String value =  (String)arr[1];  // country
			 String value1 = (String)arr[2];  // state/province
			 int count = ((Long)arr[0]).intValue();
			 valuesList.add(new CountValueValue(count,value,value1));
		}
		return valuesList;
	}

	/**
	 * For all specimens having the country equal to the supplied value and the 
	 * primary division equal to the old value, set primary division to the new value.
	 *
	 * @param countryLimit the country for which the oldValue applies.
	 * @param oldValue the existing value for primary division.
	 * @param newValue the new value for primary division.
	 *
	 * @return  the number of rows updated.
	 */
	public int updateAllStates(String countryLimit, String oldValue, String newValue) {
		int result = 0;
		logger.log(Level.INFO, "Updating Specimen setting state " + oldValue + " to " + newValue + " for country " + countryLimit);
		if (oldValue!=null && newValue!=null && newValue.length()>0) {
           Query q = em.createQuery("update Specimen s set s.primaryDivison = '" + newValue + "' where s.primaryDivison = :old and s.country = :country");
		   q.setParameter("old", oldValue);
		   q.setParameter("country", countryLimit);
		   result = q.executeUpdate();
		}
		return result;
	}

	/**
	 * Get a page of a list of unique values for genus and their frequencies.
	 * Runs a select count(*), genus from Specimen group by genus query.
	 *
	 * @param start
	 * @param end
	 * @param orderByFrequency  if true, sort by frequency rather than alphabetically by generic name.
	 * @param filter if not null or blank, a substring search criterion to apply to the list
	 * @return
	 */
	public List<CountValue> getGenusValuesRange(int start, int end, boolean orderByFrequency, String filter, String workflowFilter) {
		ArrayList<CountValue> valuesList = new ArrayList<CountValue>();
	    String whereBit = "";
		if (filter!=null && filter.length()>0) {
			whereBit = " and s.genus like '%" + filter +"%' ";
		}
		if (workflowFilter!=null && workflowFilter.length()>0) {
			whereBit = " and s.workFlowStatus = '" + workflowFilter +"' ";
		}		
		String queryString = "Select count(s), s.genus from Specimen s where s.genus is not null " + whereBit + " group by s.genus order by s.genus";
		if (orderByFrequency) {
		    queryString = "Select count(s.specimenId), s.genus from Specimen s where s.genus is not null "+whereBit+" group by s.genus order by count(s.specimenId) desc, s.genus ";
		}
		Query q = em.createQuery(queryString);
		q.setFirstResult(start);
		q.setMaxResults(end-start);
		List<Object[]> results = q.getResultList();
        Iterator<Object[]> i = results.iterator();
		while (i.hasNext()) {
			 Object[] arr = i.next();
			 String value = (String)arr[1];
			 int count = ((Long)arr[0]).intValue();
			 valuesList.add(new CountValue(count,value));
		}
		return valuesList;
	}
	
	
	/**
	 * Given a Specimen, return a cloned detatched copy of that Specimen.
	 * Use EntityManager.persist() to persist the clone, rather than 
	 * EntityManager.merge(), as the cloned copy is detatched.  The detached
	 * copy does not include the tracking records of the original source.
	 * 
	 * @param source the Specimen to clone.
	 * @return a detached Specimen containing values matching that of
	 * source, except for the absence of primary key values.
	 * 
	 */
	public Specimen cloneDetatch(Specimen source) { 
        CopyGroup group = new CopyGroup();
        group.setShouldResetPrimaryKey(true);
        group.cascadeAllParts();
        Specimen result = (Specimen)getEntityManager().unwrap(JpaEntityManager.class).copy(source, group);
        result.setTrackingCollection((Collection<Tracking>)new ArrayList<Tracking>());
        return result;
	}

	/** Replace the current collector collection with another collection that 
	 * takes its values from a supplied replacement collection.  Persists the new 
	 * collectors and merges target specimen.
	 *  
	 * @param target collection to which to add collectors from replacement, after removing
	 * any existing collectors.
	 * @param replacement collection of collectors from which to extract the values of 
	 * collector.collectorName and add them to the target collection of collectors.
	 */
	public void replaceCollectorCollection(Specimen target, Collection<Collector> replacement) {
		
		Iterator<Collector> ic = target.getCollectorCollection().iterator();
		while (ic.hasNext()) { 
			 Collector remove = ic.next();
		     collectorFacade.remove(remove);
		}
		target.getCollectorCollection().removeAll(target.getCollectorCollection());
		em.merge(target);
try { 
		em.flush();
} catch (Exception e) { 
	logger.log(Level.SEVERE, e.getMessage(), e);
	if (target.getGeoreference()!=null) { 
	    logger.log(Level.INFO, "Georeference: " +  target.getGeoreference().toString());
	} else { 
	    logger.log(Level.INFO, "Georeference is null " );
	}
}
		// Collector, unlike the other tables related to specimen has a unique constraint
		// on a pair of fields, just merge empty, persist new collectors, merge filled collection.
		// works fine with MySQL, but not with oracle, so adding in flush and a query to force
		// removal of the related collectors.
		Query q = em.createNativeQuery("delete from Collector where specimenid = " + Long.toString(target.getSpecimenId()));
		q.executeUpdate();
		Iterator<Collector> add = replacement.iterator();
		while (add.hasNext()) { 
			Collector coll = new Collector();
			coll.setSpecimenId(target);
			coll.setCollectorName(add.next().getCollectorName());
			target.getCollectorCollection().add(coll);
			em.persist(coll);
			em.flush();
		}
		em.merge(target);
		em.flush();
	}

	

	/** Replace the current determination collection with another collection that 
	 * takes its values from a supplied replacement collection.  Persists the new 
	 * determination and merges target specimen.
	 *  
	 * @param target collection to which to add determinations from replacement, after removing
	 * any existing determinations.
	 * @param replacement collection of determination from which to extract the values of 
	 * determination.{non-PK fields} and add them to the target collection of determinations.
	 */
	public void replaceDeterminationCollection(Specimen target, Collection<Determination> replacement) {
		Iterator<Determination> ic = target.getDeterminationCollection().iterator();
		while (ic.hasNext()) { 
			 Determination remove = ic.next();
		     determinationFacade.remove(remove);
		}
		target.getDeterminationCollection().removeAll(target.getDeterminationCollection());
		em.merge(target);
		Iterator<Determination> add = replacement.iterator();
		while (add.hasNext()) { 
			Determination next = add.next();
			Determination det = new Determination();
			det.setSpecimenId(target);
			det.setVerbatimText(next.getVerbatimText());
			
			det.setAuthorship(next.getAuthorship());
			det.setGenus(next.getGenus());
			det.setIdentifiedBy(next.getIdentifiedBy());
			det.setInfraspecificEpithet(next.getInfraspecificEpithet());
			det.setInfraspecificRank(next.getInfraspecificRank());
			det.setSpeciesNumber(next.getSpeciesNumber());
			det.setSpecificEpithet(next.getSpecificEpithet());
			det.setSubspecificEpithet(next.getSubspecificEpithet());
			det.setTypeStatus(next.getTypeStatus());
			det.setUnNamedForm(next.getUnNamedForm());
			det.setNatureOfId(next.getNatureOfId());
			det.setDateIdentified(next.getDateIdentified());
			
			target.getDeterminationCollection().add(det);
			em.persist(det);
		}
		em.merge(target);
	}	
	
	/** Replace the current other number collection with another collection that 
	 * takes its values from a supplied replacement collection.  Persists the new 
	 * OtherNumbers and merges target specimen.
	 *  
	 * @param target collection to which to add OtherNumbers from replacement, after removing
	 * any existing other numbers.
	 * @param replacement collection of OtherNumbers from which to extract the values of 
	 * otherNumbers.numberType and otherNumbers.otherNumber and add them to the target 
	 * collection of otherNumbers.
	 */
	public void replaceOtherNumbersCollection(Specimen target, Collection<OtherNumbers> replacement) {
		Iterator<OtherNumbers> ic = target.getOtherNumbersCollection().iterator();
		while (ic.hasNext()) { 
			 OtherNumbers remove = ic.next();
		     otherNumbersFacade.remove(remove);
		}
		target.getOtherNumbersCollection().removeAll(target.getOtherNumbersCollection());
		em.merge(target);
		Iterator<OtherNumbers> add = replacement.iterator();
		while (add.hasNext()) { 
			OtherNumbers next = add.next();
			OtherNumbers num = new OtherNumbers();
			num.setSpecimenId(target);
			num.setNumberType(next.getNumberType());
			num.setOtherNumber(next.getOtherNumber());
			
			target.getOtherNumbersCollection().add(num);
			em.persist(num);
		}
		em.merge(target);
	}		
	
	/** 
	 * Add a change tracking record to a specimen.  Adds the current user and the current
	 * timestamp as a tracking record.
	 * 
	 * @param target The specimen to which to add a change tracking record. 
	 */
	public void trackChange(Specimen target) { 
		String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		Users user = userBean.findByName(username);
		target.setLastUpdatedBy(user.getFullname());
		target.setDateLastUpdated(new Date());		
		Tracking newTracking = new Tracking();
		newTracking.setEventType(((Specimen) target).getWorkFlowStatus());
		newTracking.setUsername(((Specimen) target).getLastUpdatedBy());
		newTracking.setSpecimenId((Specimen) target);
		getEntityManager().persist(newTracking);
		getEntityManager().flush();
		getEntityManager().refresh(newTracking);
	}
	
}
