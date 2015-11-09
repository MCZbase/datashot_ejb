/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class NumberTypeBean implements  NumberTypeBeanLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;
    
	public List<String> getValues() {
		ArrayList<String> valuesList = new ArrayList<String>();
		//valuesList.add("");
		valuesList.add("Unknown");
		Query q = em.createQuery("Select distinct o.numberType from OtherNumbers o where o.numberType is not null order by o.numberType");
		List<String> results = q.getResultList();
        Iterator<String> i = results.iterator();
		while (i.hasNext()) {
			 String value = i.next();
			 if (!value.equals("Unknown")) {
			     valuesList.add(value);
			}
		}
		return valuesList;
	}
 
}
