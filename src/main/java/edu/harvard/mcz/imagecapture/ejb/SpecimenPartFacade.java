package edu.harvard.mcz.imagecapture.ejb;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import edu.harvard.mcz.imagecapture.data.SpecimenPart;

/**
 * Session Bean implementation class SpecimenPartFacade
 */
@Stateless(mappedName = "SpecimenPartFacade")
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public class SpecimenPartFacade extends AbstractFacade<SpecimenPart> implements SpecimenPartFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

    @RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
	protected EntityManager getEntityManager() {
		return em;
	}

    /**
     * Default constructor. 
     */
    public SpecimenPartFacade() {
    	super(SpecimenPart.class);
    }
       
}
