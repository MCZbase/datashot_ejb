package edu.harvard.mcz.imagecapture.ejb;
import java.util.List;

import jakarta.ejb.Local;

import edu.harvard.mcz.imagecapture.data.SpecimenPartAttribute;

@Local
public interface SpecimenPartAttributeFacadeLocal {

	void create(SpecimenPartAttribute partAttribute);

	void edit(SpecimenPartAttribute partAttribute);

	void remove(SpecimenPartAttribute partAttribute);

	SpecimenPartAttribute find(Object id);

	List<SpecimenPartAttribute> findAll();

	List<SpecimenPartAttribute> findRange(int[] range);

	int count();		
	
}
