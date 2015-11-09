/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.PositionTemplate;
import edu.harvard.mcz.imagecapture.data.Image;
import edu.harvard.mcz.imagecapture.data.MCZbaseGeogAuthRec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author mole
 */
@Stateless
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public class MCZbaseGeogAuthRecFacade extends AbstractFacade<MCZbaseGeogAuthRec> implements MCZbaseGeogAuthRecFacadeLocal {
	
	private final static Logger logger = Logger.getLogger(MCZbaseGeogAuthRecFacade.class.getName());
	
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public MCZbaseGeogAuthRecFacade() {
		super(MCZbaseGeogAuthRec.class);
	}

	public List<MCZbaseGeogAuthRec> findHigherGeographies(Map<String, String> filters) {
		List<MCZbaseGeogAuthRec> result = new ArrayList<MCZbaseGeogAuthRec>();
		if (filters==null || filters.isEmpty()) { 
		     Query q = em.createNamedQuery("MCZbaseGeogAuthRec.findAll");
		     result = q.getResultList();
		} else {
			logger.log(Level.INFO, Integer.toString(filters.size()));
			Iterator<String> keys = filters.keySet().iterator();
			while (keys.hasNext()) { 
				String key = keys.next();
				logger.log(Level.INFO, key + ":" + filters.get(key));
			}
			int[] range = { 0, 100 };
			String[] sortFields = { "higher_geog" };
			boolean sortOrder = true;
		    result = this.findRangeQuery(range, sortFields, sortOrder, filters);
		}
		return result;
	}

}
