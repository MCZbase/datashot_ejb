/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface ImageFacadeLocal {

	void create(Image image);

	void edit(Image image);

	void remove(Image image);

	Image find(Object id);

	List<Image> findAll();

	List<Image> findRange(int[] range);

	int count();
	
	int countFiltered(Map<String, String> filters);	

	public List<Image> findByImageId(long imageId);

	public List<Image> findNullBarcodes(Map<String, String> filters, ArrayList<String> sortFields);

	public List<Image> findUntemplatedImages(Map<String, String> filters, ArrayList<String> sortFields);

	public List findRangeQuery(int[] range, String[] toArray, boolean b, Map<String, String> filters);

	public List findRangeQueryAndOr(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters, boolean useAnd);

	public List<String> findUniquePaths();
	
	public List<String> findUniquePaths(boolean includeBlank);
	
	public List<String> findBatch(String path);

	public List<String> findBatchWithOtherImages(String path);	
	
	public List<String> findBatchFilesPostTaxon(String path);
	
	public String deleteBatch(String path);
	
}
