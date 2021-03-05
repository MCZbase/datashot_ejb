/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.PositionTemplate;
import edu.harvard.mcz.imagecapture.data.Template;
import edu.harvard.mcz.imagecapture.exceptions.BadTemplateException;
import edu.harvard.mcz.imagecapture.exceptions.NoSuchTemplateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
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
public class TemplateFacade extends AbstractFacade<Template> implements TemplateFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;


	protected EntityManager getEntityManager() {
		return em;
	}

	public TemplateFacade() {
		super(Template.class);
	}

	@Override
	@PermitAll
	public Template find(Object id) {
		return super.find(id);
	}
	
    public PositionTemplate getPositionTemplateByID(String templateId) { 
    	PositionTemplate result = new PositionTemplate();
    	if (!templateId.equals(result.getTemplateId())) {
    		boolean found = false;
    		Query q = em.createNamedQuery("Template.findByTemplateId");
    		q.setParameter("templateId", templateId);
    		List<Template> results = q.getResultList();
    		Iterator<Template> i = results.iterator();
    		if (i.hasNext()) {
    			Template templateInstance = i.next();
    			if (templateInstance != null) {
						result = new PositionTemplate(templateInstance);
						found = true;
    			}
    		}
    		if (!found) { 
    			try {
					result = new PositionTemplate(templateId);
				} catch (NoSuchTemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	return result;
    }


	public List<String> getTemplateNames() {
		String[] templates = { PositionTemplate.TEMPLATE_TEST_1, PositionTemplate.TEMPLATE_DEFAULT, PositionTemplate.TEMPLATE_NO_COMPONENT_PARTS  };

		List<String> temp = Arrays.asList(templates);
		ArrayList<String> templateIdList = new ArrayList<String>();
		for (int i=0; i<temp.size(); i++) {
			templateIdList.add(temp.get(i));
		}
		EntityManager em = getEntityManager();
		Query q = em.createQuery("Select distinct t.templateId from Template t where t.templateId is not null order by t.templateId");
		List<String> results = q.getResultList();
        Iterator<String> i = results.iterator();
		while (i.hasNext()) {
			 String value = i.next();
			 templateIdList.add(value);
		}
		return templateIdList;
	}
	
	
}
