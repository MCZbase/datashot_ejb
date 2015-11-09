package edu.harvard.mcz.imagecapture.ejb;
import java.util.List;

import javax.ejb.Local;

import edu.harvard.mcz.imagecapture.data.SpecimenPart;

@Local
public interface SpecimenPartFacadeLocal {

	void create(SpecimenPart part);

	void edit(SpecimenPart part);

	void remove(SpecimenPart part);

	SpecimenPart find(Object id);

	List<SpecimenPart> findAll();

	List<SpecimenPart> findRange(int[] range);

	int count();	
	
	
}
