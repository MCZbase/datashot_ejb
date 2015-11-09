/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author mole
 */
@Entity
@Table(name = "Determination", catalog = "lepidoptera", schema = "")
@NamedQueries({
    @NamedQuery(name = "Determination.findAll1", query = "SELECT d FROM Determination d"),
    @NamedQuery(name = "Determination.findByDeterminationId", query = "SELECT d FROM Determination d WHERE d.determinationId = :determinationId"),
    @NamedQuery(name = "Determination.findByGenus", query = "SELECT d FROM Determination d WHERE d.genus = :genus"),
    @NamedQuery(name = "Determination.findBySpecificEpithet", query = "SELECT d FROM Determination d WHERE d.specificEpithet = :specificEpithet"),
    @NamedQuery(name = "Determination.findBySubspecificEpithet", query = "SELECT d FROM Determination d WHERE d.subspecificEpithet = :subspecificEpithet"),
    @NamedQuery(name = "Determination.findByInfraspecificEpithet", query = "SELECT d FROM Determination d WHERE d.infraspecificEpithet = :infraspecificEpithet"),
    @NamedQuery(name = "Determination.findByInfraspecificRank1", query = "SELECT d FROM Determination d WHERE d.infraspecificRank = :infraspecificRank"),
    @NamedQuery(name = "Determination.findByAuthorship", query = "SELECT d FROM Determination d WHERE d.authorship = :authorship"),
    @NamedQuery(name = "Determination.findByUnNamedForm", query = "SELECT d FROM Determination d WHERE d.unNamedForm = :unNamedForm"),
    @NamedQuery(name = "Determination.findByIdentifiedBy", query = "SELECT d FROM Determination d WHERE d.identifiedBy = :identifiedBy"),
    @NamedQuery(name = "Determination.findByTypeStatus", query = "SELECT d FROM Determination d WHERE d.typeStatus = :typeStatus"),
    @NamedQuery(name = "Determination.findBySpeciesNumber", query = "SELECT d FROM Determination d WHERE d.speciesNumber = :speciesNumber")})
public class Determination implements Serializable {

    private static final long serialVersionUID = -2287734987773048627L;
    
    @Id
    @GeneratedValue(generator="DeterminationSeq")
    @SequenceGenerator(name="DeterminationSeq",sequenceName="SEQ_DETERMINATIONID", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "DeterminationId", nullable = false)
    private Long determinationId;
    @Column(name = "Genus", length = 40)
    private String genus;
    @Column(name = "SpecificEpithet", length = 40)
    private String specificEpithet;
    @Column(name = "SubspecificEpithet", length = 255)
    private String subspecificEpithet;
    @Column(name = "InfraspecificEpithet", length = 40)
    private String infraspecificEpithet;
    @Column(name = "InfraspecificRank", length = 40)
    private String infraspecificRank;
    @Column(name = "Authorship", length = 255)
    private String authorship;
    @Column(name = "UnNamedForm", length = 50)
    private String unNamedForm;
    @Column(name = "IdentifiedBy", length = 255)
    private String identifiedBy;
    @Column(name = "TypeStatus", length = 50)
    private String typeStatus;
    @Column(name = "SpeciesNumber", length = 50)
    private String speciesNumber;
	@Column(name = "VerbatimText", length = 50)
	private String verbatimText;
	
    @Column(name = "DateIdentified", length = 32)
    private String dateIdentified;
    @Column(name = "Remarks", length = 65535)
    private String remarks;
    @Column(name = "NatureOfId", length = 255)
    private String natureOfId;	
	
    @JoinColumn(name = "SpecimenId", referencedColumnName = "SpecimenId", nullable = false)
    @ManyToOne(optional = false)
    private Specimen specimenId;

    public Determination() {
    }

    public Determination(Long determinationId) {
        this.determinationId = determinationId;
    }

    public Determination(Specimen specimen) {
        this.specimenId = specimen;
        typeStatus = Specimen.STATUS_NOT_A_TYPE;
    }

    public Determination(Specimen specimen, String genus,
            String specificEpithet, String subspecificEpithet,
            String infraspecificEpithet, String infraspecificRank,
            String authorship, String unNamedForm,
            String identificationQualifier, String identifiedBy, String typeStatus, String speciesNumber) {
        this.specimenId = specimen;
        this.genus = genus;
        this.specificEpithet = specificEpithet;
        this.subspecificEpithet = subspecificEpithet;
        this.infraspecificEpithet = infraspecificEpithet;
        this.infraspecificRank = infraspecificRank;
        this.authorship = authorship;
        this.unNamedForm = unNamedForm;
        this.identifiedBy = identifiedBy;
        this.typeStatus = typeStatus;
        this.speciesNumber = speciesNumber;
    }

    public Long getDeterminationId() {
        return determinationId;
    }

    public void setDeterminationId(Long determinationId) {
        this.determinationId = determinationId;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecificEpithet() {
        return specificEpithet;
    }

    public void setSpecificEpithet(String specificEpithet) {
        this.specificEpithet = specificEpithet;
    }

    public String getSubspecificEpithet() {
        return subspecificEpithet;
    }

    public void setSubspecificEpithet(String subspecificEpithet) {
        this.subspecificEpithet = subspecificEpithet;
    }

    public String getInfraspecificEpithet() {
        return infraspecificEpithet;
    }

    public void setInfraspecificEpithet(String infraspecificEpithet) {
        this.infraspecificEpithet = infraspecificEpithet;
    }

    public String getInfraspecificRank() {
        return infraspecificRank;
    }

    public void setInfraspecificRank(String infraspecificRank) {
        this.infraspecificRank = infraspecificRank;
    }

    public String getAuthorship() {
        return authorship;
    }

    public void setAuthorship(String authorship) {
        this.authorship = authorship;
    }

    public String getUnNamedForm() {
        return unNamedForm;
    }

    public void setUnNamedForm(String unNamedForm) {
        this.unNamedForm = unNamedForm;
    }

    public String getIdentifiedBy() {
        return identifiedBy;
    }

    public void setIdentifiedBy(String identifiedBy) {
        this.identifiedBy = identifiedBy;
    }

    public String getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(String typeStatus) {
        this.typeStatus = typeStatus;
    }

    /**
     * @return the speciesNumber
     */
    public String getSpeciesNumber() {
        return speciesNumber;
    }

    /**
     * @param speciesNumber the speciesNumber to set
     */
    public void setSpeciesNumber(String speciesNumber) {
        this.speciesNumber = speciesNumber;
    }

	public String getVerbatimText() {
		return verbatimText;
	}

	public void setVerbatimText(String verbatimText) {
		this.verbatimText = verbatimText;
	}

    /**
	 * @return the dateIdentified
	 */
	public String getDateIdentified() {
		return dateIdentified;
	}

	/**
	 * @param dateIdentified the dateIdentified to set
	 */
	public void setDateIdentified(String dateIdentified) {
		this.dateIdentified = dateIdentified;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the natureOfId
	 */
	public String getNatureOfId() {
		return natureOfId;
	}

	/**
	 * @param natureOfId the natureOfId to set
	 */
	public void setNatureOfId(String natureOfId) {
		this.natureOfId = natureOfId;
	}

	public Specimen getSpecimenId() {
        return specimenId;
    }

    public void setSpecimenId(Specimen specimenId) {
        this.specimenId = specimenId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (determinationId != null ? determinationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Determination)) {
            return false;
        }
        Determination other = (Determination) object;
        if ((this.determinationId == null && other.determinationId != null) || (this.determinationId != null && !this.determinationId.equals(other.determinationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.harvard.mcz.imagecapture.data.Determination[determinationId=" + determinationId + "]";
    }
}
