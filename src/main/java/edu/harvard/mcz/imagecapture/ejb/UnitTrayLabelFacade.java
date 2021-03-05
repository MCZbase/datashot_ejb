/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.UnitTrayLabel;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author mole
 */
@Stateless
@DeclareRoles("Administrator")
@RolesAllowed("Administrator")
public class UnitTrayLabelFacade extends AbstractFacade<UnitTrayLabel> implements UnitTrayLabelFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public UnitTrayLabelFacade() {
		super(UnitTrayLabel.class);
	}

}
