/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.MCZbaseAuthAgentName;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mole
 */
@Stateless
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public class MCZbaseAuthAgentNameFacade extends AbstractFacade<MCZbaseAuthAgentName> implements MCZbaseAuthAgentNameFacadeLocal {
	
	private final static Logger logger = Logger.getLogger(MCZbaseAuthAgentNameFacade.class.getName());
	
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public MCZbaseAuthAgentNameFacade() {
		super(MCZbaseAuthAgentName.class);
	}

	public List<MCZbaseAuthAgentName> findAgents(Map<String, String> filters, int maxResults) {
		List<MCZbaseAuthAgentName> result = new ArrayList<MCZbaseAuthAgentName>();
		if (filters==null || filters.isEmpty()) { 
		     Query q = em.createNamedQuery("MCZbaseAuthAgentName.findAll");
		     q.setMaxResults(maxResults);
		     result = q.getResultList();
		} else {
			logger.log(Level.INFO, Integer.toString(filters.size()));
			Iterator<String> keys = filters.keySet().iterator();
			while (keys.hasNext()) { 
				String key = keys.next();
				logger.log(Level.INFO, key + ":" + filters.get(key));
			}
			int[] range = { 0, maxResults };
			String[] sortFields = { "agent_name" };
			boolean sortOrder = false;
		    result = this.findRangeQuery(range, sortFields, sortOrder, filters);
		}
		return result;
	}

}
