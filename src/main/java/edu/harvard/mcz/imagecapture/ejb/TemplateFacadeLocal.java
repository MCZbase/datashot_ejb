/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.PositionTemplate;
import edu.harvard.mcz.imagecapture.data.Template;
import java.util.List;
import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface TemplateFacadeLocal {

	void create(Template template);

	void edit(Template template);

	void remove(Template template);

	Template find(Object id);

	List<Template> findAll();

	List<Template> findRange(int[] range);

	int count();

	public List<String> getTemplateNames();
	
	public String toString();
	
	public PositionTemplate getPositionTemplateByID(String templateId);	

}
