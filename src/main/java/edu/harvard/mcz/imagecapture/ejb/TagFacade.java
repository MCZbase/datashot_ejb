/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Tag;
import java.util.List;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 *
 * @author mole
 */
@Stateless
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public class TagFacade extends AbstractFacade<Tag> implements TagFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public TagFacade() {
		super(Tag.class);
	}

	public List<Tag> findByTagname(String tagname) {
		Query q = em.createNamedQuery("Tag.findByTagname");
                q.setParameter("tagname",tagname);
		List<Tag> result = q.getResultList();
		return result;
	}

	public Tag findTagByTagname(String tagname) {
		Tag result = null;
		List<Tag> tags = findByTagname(tagname);
		if (!tags.isEmpty()) {
			result = tags.get(0);
		}
	    return result;
	}
}
