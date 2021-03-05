/**
 * 
 */
package edu.harvard.mcz.imagecapture.data;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import edu.harvard.mcz.imagecapture.ejb.UsersFacadeLocal;

/**
 * @author mole
 *
 *
 */
@Entity
@Table (name = "Specimen_Part", catalog = "lepidoptera", schema = "" ) 
public class SpecimenPart {
	
	public static final String[] PARTNAMES = {
		"whole animal", "partial animal",
		"partial animal (abdomen)","partial animal (body)","partial animal (legs)","partial animal (wings)",
	   "cocoon","frass", "frass chain", "genitalia", "head capsule", 
	   "head capsule hat", "larval case", "larval shelter",
	   "molt", "other", "pupal exuvia", "pupal shelter", "puparium", 
	   "sphragis" 
	   } ;	
	
    @Id
    @GeneratedValue(generator="SpecimenPartSeq")
    @SequenceGenerator(name="SpecimenPartSeq",sequenceName="SPECIMEN_PART_SEQ", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "SpecimenPartId", nullable = false)
	private Long specimenPartId; 
	
    @ManyToOne
    @JoinColumn(name="SpecimenId")
    private Specimen specimenId;
    
    @Column (name="Part_Name", length=255)
    private String partName = "whole animal";
    
    @Column (name="Preserve_Method", length=60)
    private String preserveMethod = "pinned";
    
    @Column (name="Lot_Count")
    private int lotCount = 1;  // Coll_Object.lot_count
    
    @Column (name="Lot_Count_Modifier", length=5)
    private String lotCountModifier;  // Coll_Object.lot_count_modifier 
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specimenPartId", fetch=FetchType.EAGER)
    private Collection<SpecimenPartAttribute> attributeCollection;

	/**
	 * @return the specimenPartId
	 */
	public Long getSpecimenPartId() {
		return specimenPartId;
	}

	/**
	 * @param specimenPartId the specimenPartId to set
	 */
	public void setSpecimenPartId(Long specimenPartId) {
		this.specimenPartId = specimenPartId;
	}

	/**
	 * @return the specimenId
	 */
	public Specimen getSpecimenId() {
		return specimenId;
	}

	/**
	 * @param specimenId the specimenId to set
	 */
	public void setSpecimenId(Specimen specimenId) {
		this.specimenId = specimenId;
	}

	/**
	 * @return the partName
	 */
	public String getPartName() {
		return partName;
	}

	/**
	 * @param partName the partName to set
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}

	/**
	 * @return the preserveMethod
	 */
	public String getPreserveMethod() {
		return preserveMethod;
	}

	/**
	 * @param preserveMethod the preserveMethod to set
	 */
	public void setPreserveMethod(String preserveMethod) {
		this.preserveMethod = preserveMethod;
	}

	/**
	 * @return the lotCount
	 */
	public int getLotCount() {
		return lotCount;
	}

	/**
	 * @param lotCount the lotCount to set
	 */
	public void setLotCount(int lotCount) {
		this.lotCount = lotCount;
	}

	/**
	 * @return the lotCountModifier
	 */
	public String getLotCountModifier() {
		return lotCountModifier;
	}

	/**
	 * @param lotCountModifier the lotCountModifier to set
	 */
	public void setLotCountModifier(String lotCountModifier) {
		this.lotCountModifier = lotCountModifier;
	}

	/**
	 * @return the attributeCollection
	 */
	public Collection<SpecimenPartAttribute> getAttributeCollection() {
		if (attributeCollection==null) { 
			attributeCollection = new ArrayList<SpecimenPartAttribute>();
		}
		return attributeCollection;
	}

	/**
	 * @param attributeCollection the attributeCollection to set
	 */
	public void setAttributeCollection(
			Collection<SpecimenPartAttribute> attributeCollection) {
		this.attributeCollection = attributeCollection;
	}    
    
	/**
	 * Adds a new attribute with default values to this specimen part.
	 */
	public void addAttribute() { 
		SpecimenPartAttribute newAttribute = new SpecimenPartAttribute();
		attributeCollection.add(newAttribute);
	}
	
	public void addCasteAttribute(String determiner) { 
		SpecimenPartAttribute newAttribute = new SpecimenPartAttribute();
		newAttribute.setAttributeType("caste");
		newAttribute.setPrepTypeAttributeDate(new Date());
		newAttribute.setSpecimenPartId(this);
		newAttribute.setAttributeDeterminer(determiner);
		getAttributeCollection().add(newAttribute);
	}
	
	/**
	 * Obtain human readable list of attribute types and values for the 
	 * specimen part attributes associated with this specimen part.
	 * 
	 * @return string containing concatenated list of attribute types, values, and units.  
	 * If attribute collection is empty, returns an empty string.
	 */
	public String getPartAttributeValuesConcat() { 
		StringBuffer result = new StringBuffer();
		Iterator<SpecimenPartAttribute> i = getAttributeCollection().iterator();
		int counter = 0;
		while (i.hasNext()) { 
			SpecimenPartAttribute attribute = i.next();
			if (counter>0) { 
				result.append(", ");
			}
			result.append(attribute.getAttributeType()).append(':').append(attribute.getAttributeValue()).append(attribute.getAttributeUnits());
			counter++;
		}
		return result.toString();
	}	
}