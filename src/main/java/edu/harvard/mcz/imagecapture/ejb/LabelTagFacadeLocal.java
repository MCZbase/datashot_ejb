/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.LabelTag;
import java.util.List;
import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface LabelTagFacadeLocal {

	void create(LabelTag labelTag);

	void edit(LabelTag labelTag);

	void remove(LabelTag labelTag);

	LabelTag find(Object id);

	List<LabelTag> findAll();

	List<LabelTag> findRange(int[] range);

	int count();

	public long getCountForTag(String tagname);

}
