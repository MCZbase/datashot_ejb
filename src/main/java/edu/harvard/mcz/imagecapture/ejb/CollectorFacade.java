/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Collector;
import edu.harvard.mcz.imagecapture.utility.CountValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mole
 */
@Stateless
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public class CollectorFacade extends AbstractFacade<Collector> implements CollectorFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

    @RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
	protected EntityManager getEntityManager() {
		return em;
	}

	public CollectorFacade() {
		super(Collector.class);
	}

	/**
	 * Return a list of the distinct collector names and their frequencies
	 *
	 * @return a list of collectorname, count pairs as a list of CountValue objects.
	 */
	public List<CountValue> getCollectorValues(boolean orderByFrequency, String filter) {
		ArrayList<CountValue> valuesList = new ArrayList<CountValue>();
	    String whereBit = "";
		if (filter!=null && filter.length()>0) {
			whereBit = " and c.collectorName like '%" + filter +"%' ";
		}
		String queryString = "Select count(c), c.collectorName from Collector c where c.collectorName is not null "+whereBit+" group by c.collectorName order by c.collectorName";
		if (orderByFrequency) {
		   queryString = "Select count(c.collectorId), c.collectorName from Collector c where c.collectorName is not null "+whereBit+" group by c.collectorName order by count(c.collectorId) desc, c.collectorName ";
		}
		Query q = em.createQuery(queryString);
		List<Object[]> results = q.getResultList();
        Iterator<Object[]> i = results.iterator();
		while (i.hasNext()) {
			 String value = (String)i.next()[1];
			 int count = ((Long)i.next()[0]).intValue();
			 valuesList.add(new CountValue(count,value));
		}
		return valuesList;
	}

	/**
	 * Return a subset of the distinct collector names, from start to end.
	 *
	 * @param start ordinal position of first collector name to retrieve.
	 * @param end ordinal position of last collector name to retrieve
	 * @return a list of collectorname, count pairs as a list of CountValue objects.
	 */
	public List<CountValue> getCollectorValuesRange(int start, int end, boolean orderByFrequency, String filter) {
		ArrayList<CountValue> valuesList = new ArrayList<CountValue>();
	    String whereBit = "";
		if (filter!=null && filter.length()>0) {
			whereBit = " and c.collectorName like '%" + filter +"%' ";
		}
		String queryString = "Select count(c), c.collectorName from Collector c where c.collectorName is not null " + whereBit + " group by c.collectorName order by c.collectorName";
		if (orderByFrequency) {
		    queryString = "Select count(c.collectorId), c.collectorName from Collector c where c.collectorName is not null "+whereBit+" group by c.collectorName order by count(c.collectorId) desc, c.collectorName ";
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
	 * Count the number of distinct collector names.
	 *
	 * @return
	 */
	public int getCollectorValuesCount() {
		int result = 0;
		Query q = em.createQuery("Select count(distinct c.collectorName) from Collector c where c.collectorName is not null");
		List<Long> results = q.getResultList();
        Iterator<Long> i = results.iterator();
		while (i.hasNext()) {
			result = i.next().intValue();
		}
		return result;
	}

	/**
	 * Mass replace function for collector names.
	 * Sets all values of Collector.collectorName that are equal to oldCollector
	 * to newCollector.
	 *
	 * @param oldCollector
	 * @param newCollector
	 * @return number of records affected
	 */
    @RolesAllowed({"Administrator","Full Access","Chief Editor"})
	public int updateAll(String oldCollector, String newCollector) {
		int result = 0;
		if (oldCollector!=null && newCollector!=null && newCollector.length()>0) {
        Query q = em.createQuery("update Collector c set c.collectorName = '" + newCollector + "' where c.collectorName = :old");
		q.setParameter("old", oldCollector);
		result = q.executeUpdate();
		}
		return result;
	}

}
