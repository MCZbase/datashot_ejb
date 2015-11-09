/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Tracking;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mole
 */
@Stateless
@DeclareRoles("Administrator")
@RolesAllowed("Administrator")
public class TrackingFacade extends AbstractFacade<Tracking> implements TrackingFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public TrackingFacade() {
		super(Tracking.class);
	}

	@DenyAll
	public void edit(Tracking tracking) {

	}

	@DenyAll
	public void remove(Tracking tracking) {

	}



}
