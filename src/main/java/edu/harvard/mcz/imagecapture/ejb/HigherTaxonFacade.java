/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import java.util.Iterator;
import java.util.List;

import edu.harvard.mcz.imagecapture.data.HigherTaxon;
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
public class HigherTaxonFacade extends AbstractFacade<HigherTaxon> implements HigherTaxonFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

    @RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
	protected EntityManager getEntityManager() {
		return em;
	}

	public HigherTaxonFacade() {
		super(HigherTaxon.class);
	}
	
    /**
     * Determine if a family contains any taxa that are recorded as having castes.
     * 
     * @param family name to test for hasCastes flag
     * 
     * returns true if there is any higher taxon entry with the provided family and a true hasCastes flag,
     * otherwise returns false 
     */
    @RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
	public boolean isFamilyWithCastes(String family) {
		boolean result = false;
		if (family!=null) { 
			Query q = em.createQuery("select count(distinct h.family) from HigherTaxon h where h.castes = 1 and h.family = :family ");
			q.setParameter("family", family);
			List<Long> results = q.getResultList();
			Iterator<Long> i = results.iterator();
			while (i.hasNext()) {
				Long value = i.next();
				if (value.intValue() > 0) {
					result = true;
				}
			}
		}
		return result;
	}
	

}
