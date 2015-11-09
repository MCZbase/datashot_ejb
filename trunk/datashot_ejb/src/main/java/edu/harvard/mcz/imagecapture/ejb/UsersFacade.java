/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Tracking;
import edu.harvard.mcz.imagecapture.data.Users;
import edu.harvard.mcz.imagecapture.utility.HashUtility;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
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
public class UsersFacade extends AbstractFacade<Users> implements UsersFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public UsersFacade() {
		super(Users.class);
	}

	public List<Users> authenticate(String username, String password) {
	        Query q = em.createNamedQuery("Users.authenticate");
                String hash = HashUtility.getSHA1Hash(password);
                q.setParameter("username",username);
                q.setParameter("passwordhash",hash);
		List<Users> result = q.getResultList();
		return result;
	}

	public Users findByName(String username) {
		Users result = null;
		Query q = em.createNamedQuery("Users.findByUsername");
		q.setParameter("username", username);
		List<Users> userlist = q.getResultList();
		if (!userlist.isEmpty()) {
			result = userlist.get(0);
	        }
		return result;
	}

	@DenyAll
	public void remove(Tracking tracking) {

	}

    public List<String> getUserRoleValues() {
		List<String> result = new ArrayList<String>();
		for (int x = 0; x < Users.ROLES.length; x++) {
		    result.add(Users.ROLES[x]);
		}
		return result;
	}

}
