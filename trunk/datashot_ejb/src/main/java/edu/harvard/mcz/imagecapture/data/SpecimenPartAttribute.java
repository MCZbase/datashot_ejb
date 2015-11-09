package edu.harvard.mcz.imagecapture.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name = "Specimen_Part_Attribute", catalog = "lepidoptera", schema = "" ) 
public class SpecimenPartAttribute {

    @Id
    @GeneratedValue(generator="SpecimenPartAttributeSeq")
    @SequenceGenerator(name="SpecimenPartAttributeSeq",sequenceName="SPECIMEN_PART_ATTRIBUTE_SEQ", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "SpecimenPartAttributeId", nullable = false)
	private Long specimenPartAttributeId; 
	
    @ManyToOne
    @JoinColumn(name="SpecimenPartId")
    private SpecimenPart specimenPartId;
    
	@Column(name="Attribute_Type", length=30)
    private String attributeType = "caste";
	
	@Column(name="Attribute_Value", length=255)
	private String attributeValue;
	
	@Column(name="Attribute_Units",length=30)
	private String attributeUnits = "";
	
	@Column(name="Attribute_Remark",length=4000) 
	private String attributeRemark;
	
	@Column(name="Attribute_Determiner",length=255)
	private String attributeDeterminer; 
	  
	@Column(name = "Attribute_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date attributeDate;

	/**
	 * @return the specimenPartAttributeId
	 */
	public Long getSpecimenPartAttributeId() {
		return specimenPartAttributeId;
	}

	/**
	 * @param specimenPartAttributeId the specimenPartAttributeId to set
	 */
	public void setSpecimenPartAttributeId(Long specimenPartAttributeId) {
		this.specimenPartAttributeId = specimenPartAttributeId;
	}

	/**
	 * @return the specimenPartId
	 */
	public SpecimenPart getSpecimenPartId() {
		return specimenPartId;
	}

	/**
	 * @param specimenPartId the specimenPartId to set
	 */
	public void setSpecimenPartId(SpecimenPart specimenPartId) {
		this.specimenPartId = specimenPartId;
	}

	/**
	 * @return the attributeType
	 */
	public String getAttributeType() {
		return attributeType;
	}

	/**
	 * @param attributeType the attributeType to set
	 */
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	/**
	 * @return the attributeValue
	 */
	public String getAttributeValue() {
		return attributeValue;
	}

	/**
	 * @param attributeValue the attributeValue to set
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * @return the attributeUnits
	 */
	public String getAttributeUnits() {
		return attributeUnits;
	}

	/**
	 * @param attributeUnits the attributeUnits to set
	 */
	public void setAttributeUnits(String attributeUnits) {
		this.attributeUnits = attributeUnits;
	}

	/**
	 * @return the attributeRemark
	 */
	public String getAttributeRemark() {
		return attributeRemark;
	}

	/**
	 * @param attributeRemark the attributeRemark to set
	 */
	public void setAttributeRemark(String attributeRemark) {
		this.attributeRemark = attributeRemark;
	}

	/**
	 * @return the attributeDeterminer
	 */
	public String getAttributeDeterminer() {
		return attributeDeterminer;
	}

	/**
	 * @param attributeDeterminer the attributeDeterminer to set
	 */
	public void setAttributeDeterminer(String attributeDeterminer) {
		this.attributeDeterminer = attributeDeterminer;
	}

	/**
	 * @return the prepTypeAttributeDate
	 */
	public Date getPrepTypeAttributeDate() {
		return attributeDate;
	}

	/**
	 * @param prepTypeAttributeDate the prepTypeAttributeDate to set
	 */
	public void setPrepTypeAttributeDate(Date prepTypeAttributeDate) {
		this.attributeDate = prepTypeAttributeDate;
	}
	
	public boolean hasEditablePartAttributeType() {
		boolean result = false;
		if (this.attributeType.equals("scientific name")) { 
			result = true;
		}
		return result;
	}
	
	public List<String> getPicklistValuesForAttributeType() { 
		List<String> result = new ArrayList<String>();
		if (this.attributeType.equals("caste")) { 
			result.addAll(Arrays.asList(Caste.getCasteValues()));
		}
		if (attributeType.equals("sex")) { 
			result.addAll(Arrays.asList(Sex.getSexValues()));
		}
		if (attributeType.equals("life stage")) { 
			result.addAll(Arrays.asList(LifeStage.getLifeStageValues()));
		}
		if (attributeType.equals("associated taxon")) { 
			result.addAll(Arrays.asList(PartAssociation.getPartAssociationValues()));
		}
		return result;
	}
	
}
