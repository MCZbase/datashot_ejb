/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Tag;
import java.util.List;
import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface TagFacadeLocal {

	void create(Tag tag);

	void edit(Tag tag);

	void remove(Tag tag);

	Tag find(Object id);

	List<Tag> findAll();

	List<Tag> findRange(int[] range);

	int count();

	public List<Tag> findByTagname(String tagname);

	public Tag findTagByTagname(String tagname) ;

}
