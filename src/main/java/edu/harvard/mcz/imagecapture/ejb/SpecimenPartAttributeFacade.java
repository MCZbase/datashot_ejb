package edu.harvard.mcz.imagecapture.ejb;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import edu.harvard.mcz.imagecapture.data.SpecimenPartAttribute;

/**
 * Session Bean implementation class SpecimenPartAttributeFacade
 */
@Stateless(mappedName = "SpecimenPartAttributeFacade")
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public class SpecimenPartAttributeFacade extends AbstractFacade<SpecimenPartAttribute> implements SpecimenPartAttributeFacadeLocal {

	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

    @RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
	protected EntityManager getEntityManager() {
		return em;
	}	
	
    /**
     * Default constructor. 
     */
    public SpecimenPartAttributeFacade() {
    	super(SpecimenPartAttribute.class);
    }
       
}
