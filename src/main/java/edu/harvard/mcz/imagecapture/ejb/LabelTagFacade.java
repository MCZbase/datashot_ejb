/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.buisness.FieldCount;
import edu.harvard.mcz.imagecapture.data.LabelTag;
import edu.harvard.mcz.imagecapture.data.Tag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 *
 * @author mole
 */
@Stateless
public class LabelTagFacade extends AbstractFacade<LabelTag> implements LabelTagFacadeLocal {
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

	@EJB
	TagFacadeLocal tagFacade;

	protected EntityManager getEntityManager() {
		return em;
	}

	public LabelTagFacade() {
		super(LabelTag.class);
	}


	public List<FieldCount> getTagCounts() {
		ArrayList<FieldCount> result = new ArrayList<FieldCount>();
		Query q = em.createQuery("select count(t.labeltagid), t.tagname from LabelTag t group by t.tagname");
		List<Object[]> results = q.getResultList();
        Iterator<Object[]> i = results.iterator();
		while (i.hasNext()) {
			 Object[] values = i.next();
			 FieldCount ct = new FieldCount((String)values[1], (Long)values[0]) ;
			 result.add(ct);
		}
	    return result;
	}

	/**
	 * For a given  tag name return the count of LabelTag rows that use that tag.
	 *
	 * @param tagname
	 * @return
	 */
	public long getCountForTag(String tagname) {
		long result = 0;
		Query q = em.createQuery("select count(t.labeltagid), t.tagname from LabelTag t where t.tagname = :tagname group by t.tagname");
		Tag t = tagFacade.findTagByTagname(tagname);
		q.setParameter("tagname", t);
		List<Object[]> results = q.getResultList();
        Iterator<Object[]> i = results.iterator();
		while (i.hasNext()) {
			 Object[] values = i.next();
			 result = Long.parseLong(values[0].toString());
		}
	    return result;
	}

}
