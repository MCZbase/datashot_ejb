package edu.harvard.mcz.imagecapture.data;

// Generated Jan 23, 2009 8:12:35 AM by Hibernate Tools 3.2.2.GA

/**
 * Number generated by hbm2java
 * @deprecated
 */
public class Number implements java.io.Serializable {

	private static final long serialVersionUID = -7325097435841353006L;
	
	private Long numberId;
	private Specimen specimen;
	private String number;
	private String numberType;

	public Number() {
	}

	public Number(Specimen specimen) {
		this.specimen = specimen;;
	}

	public Number(Specimen specimen, String number, String numberType) {
		this.specimen = specimen;
		this.number = number;
		this.numberType = numberType;
	}

	public Long getNumberId() {
		return this.numberId;
	}

	public void setNumberId(Long numberId) {
		this.numberId = numberId;
	}

	public Specimen getSpecimen() {
		return this.specimen;
	}

	public void setSpecimen(Specimen specimen) {
		this.specimen = specimen;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumberType() {
		return this.numberType;
	}

	public void setNumberType(String numberType) {
		this.numberType = numberType;
	}

}