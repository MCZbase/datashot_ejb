/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Label;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mole
 */
@Stateless
public class LabelFacade extends AbstractFacade<Label> implements LabelFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public LabelFacade() {
		super(Label.class);
	}

}
