package edu.harvard.mcz.imagecapture.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
