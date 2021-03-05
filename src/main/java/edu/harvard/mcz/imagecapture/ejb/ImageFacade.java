/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.PositionTemplate;
import edu.harvard.mcz.imagecapture.data.Image;
import edu.harvard.mcz.imagecapture.data.WorkFlowStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

/**
 *
 * @author mole
 */
@Stateless
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public class ImageFacade extends AbstractFacade<Image> implements ImageFacadeLocal {
	
	private final static Logger logger = Logger.getLogger(ImageFacade.class.getName());
	
	@PersistenceContext(unitName = "bu_test_ejbPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public ImageFacade() {
		super(Image.class);
	}

	@PermitAll
	public List<Image> findByImageId(long imageId) {
		Query q = em.createNamedQuery("Image.findByImageId");
                q.setParameter("imageId",imageId);
		List<Image> result = q.getResultList();
		return result;
	}

	public List<Image> findNullBarcodes(Map<String, String> filters, ArrayList<String> sortFields) {
		// TODO: Add filters and sort to query
		Query q = em.createNamedQuery("Image.findNullBarcodes");
		q.setMaxResults(10);
		List<Image> result = q.getResultList();
		return result;
	}

	public List<Image> findUntemplatedImages(Map<String, String> filters, ArrayList<String> sortFields) {
		// TODO: Add filters and sort to query
		Query q = em.createNamedQuery("Image.findByTemplateId");
		q.setMaxResults(10);
        q.setParameter("templateId",PositionTemplate.TEMPLATE_NO_COMPONENT_PARTS);
		List<Image> result = q.getResultList();
		return result;
	}
	
	public List<String>findUniquePaths() { 
		return findUniquePaths(true);
	}
	
	public List<String> findUniquePaths(boolean includeBlank) { 
		TypedQuery<String> q = em.createQuery("select DISTINCT i.path from Image i where i.path IS NOT NULL order by i.path ", String.class);
		List<String> results = q.getResultList();
		//logger.log(Level.INFO, Integer.toString(results.size()));
		if (results==null) { 
			results = new ArrayList<String>();
		}
		if (includeBlank) { 
		   results.add(0, Image.BLANKPATHFILTER);
		}
		return results;
	}
	
    @RolesAllowed(value = {"Administrator","Chief Editor"})
	public List<String> findBatch(String path) { 
		ArrayList<String> retval = new ArrayList<String>();
		Query q = em.createQuery("select i.path, i.filename, s.barcode, s.workFlowStatus from Image i join i.specimenId s where i.path = :path");
		q.setParameter("path", path);
		List<Object[]> results = q.getResultList();
	    Iterator<Object[]> i =results.iterator();
	    while (i.hasNext()) { 
	    	Object[] result = i.next();
	    	retval.add(result[0].toString() +  result[1].toString() + ": " + result[2].toString() + " " + result[3].toString()); 
	    }
		return retval;
	}

    @RolesAllowed(value = {"Administrator","Chief Editor"})
	public List<String> findBatchWithOtherImages(String path) { 
	    String sqlString = "select filename from Image where specimenid in (select specimenid from Image where path = ?) and path <> ? ";
		Query q = em.createNativeQuery(sqlString);
		q.setParameter(1, path);
		q.setParameter(2, path);
		return (List<String>)q.getResultList();
	}
    
    @RolesAllowed(value = {"Administrator","Chief Editor"})
	public List<String> findBatchFilesPostTaxon(String path) {
		ArrayList<String> retval = new ArrayList<String>();
		Query q = em.createQuery("select i.path, i.filename, s.barcode, s.workFlowStatus from Image i join i.specimenId s where i.path = :path and s.workFlowStatus <> '"+WorkFlowStatus.STAGE_0+"' and s.workFlowStatus <> '"+WorkFlowStatus.STAGE_1+"'");
		q.setParameter("path", path);
		List<Object[]> results = q.getResultList();
	    Iterator<Object[]> i =results.iterator();
	    while (i.hasNext()) { 
	    	Object[] result = i.next();
	    	retval.add(result[0].toString() +  result[1].toString() + ": " + result[2].toString() + " " + result[3].toString()); 
	    }
		return retval;
	}    

	
	/* Delete the records, order of statements matters.
	 * 
	 */
    @RolesAllowed(value = {"Administrator","Chief Editor"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String deleteBatch(String path) { 
    	StringBuilder result = new StringBuilder();
    	String sqlString = "delete from Tracking where specimenid in (select specimenid from Image where path = '" + path + "')";
    	Query q = em.createNativeQuery(sqlString);
    	sqlString = "delete from Specimen_Part where specimenid in (select specimenid from Image where path = '" + path + "')";
    	Query q1 = em.createNativeQuery(sqlString);
    	sqlString = "delete from LAT_LONG where specimenid in (select specimenid from Image where path = '" + path + "')";
    	Query q2 = em.createNativeQuery(sqlString);
    	sqlString = "delete from Specimen where specimenid in (select specimenid from Image where path = '" + path + "')";	
    	Query q3 = em.createNativeQuery(sqlString);
    	sqlString = "delete from Image where path = '" + path + "'";
    	Query q4 = em.createNativeQuery(sqlString);
    	
    	try { 
    	int trackingRows = q.executeUpdate();
    	int partRows = q1.executeUpdate();
    	int geoRows = q2.executeUpdate();
    	int specRows = q3.executeUpdate();
    	int imgRows = q4.executeUpdate();
    	em.flush();
    	    result.append("Deleted records for ").append(path).append(" Tracking:").append(trackingRows);
    	    result.append(" Parts:").append(partRows);
    	    result.append(" Lat/Longs:").append(geoRows);
    	    result.append(" Specimens:").append(specRows);
    	    result.append(" Images:").append(imgRows);
    	} catch (Exception e) {
    		result.append("Error: Failed to delete batch. " + e.getMessage());
    	}
    	return result.toString();
    }
}
