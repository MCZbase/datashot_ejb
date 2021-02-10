/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.mcz.imagecapture.data;


import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

/**
 *
 * @author mole
 */
@Entity
@Table(name = "Specimen", catalog = "lepidoptera", schema = "", uniqueConstraints = {
@UniqueConstraint(columnNames = {"Barcode"})})
@Cache( type=CacheType.NONE, expiry=0, alwaysRefresh=true )
@NamedQueries({
    @NamedQuery(name = "Specimen.findAll", query = "SELECT s FROM Specimen s"),
    @NamedQuery(name = "Specimen.findBySpecimenId", query = "SELECT s FROM Specimen s WHERE s.specimenId = :specimenId"),
    @NamedQuery(name = "Specimen.findByFamily", query = "SELECT s FROM Specimen s WHERE s.family = :family"),
    @NamedQuery(name = "Specimen.findBySubfamily", query = "SELECT s FROM Specimen s WHERE s.subfamily = :subfamily"),
    @NamedQuery(name = "Specimen.findByTribe", query = "SELECT s FROM Specimen s WHERE s.tribe = :tribe"),
    @NamedQuery(name = "Specimen.findByGenus", query = "SELECT s FROM Specimen s WHERE s.genus = :genus"),
    @NamedQuery(name = "Specimen.findByLastUpdatedBy", query = "SELECT s FROM Specimen s WHERE s.lastUpdatedBy = :lastUpdatedBy"),
    @NamedQuery(name = "Specimen.findByBarcode", query = "SELECT s FROM Specimen s WHERE s.barcode = :barcode"),
    @NamedQuery(name = "Specimen.findAllOrderRecentlyCreated", query = "SELECT s FROM Specimen s order by s.dateCreated desc "),
    @NamedQuery(name = "Specimen.findByCollector", query = "SELECT s FROM Collector c JOIN c.specimenId s WHERE c.collectorName = :collectorName")
})
    /* ,
    @NamedQuery(name = "Specimen.findByDrawerNumber", query = "SELECT s FROM Specimen s WHERE s.drawerNumber = :drawerNumber"),
    @NamedQuery(name = "Specimen.findByTypeStatus", query = "SELECT s FROM Specimen s WHERE s.typeStatus = :typeStatus"),
    @NamedQuery(name = "Specimen.findByTypeNumber", query = "SELECT s FROM Specimen s WHERE s.typeNumber = :typeNumber"),
    @NamedQuery(name = "Specimen.findByCitedInPublication1", query = "SELECT s FROM Specimen s WHERE s.citedInPublication = :citedInPublication"),
    @NamedQuery(name = "Specimen.findByFeatures", query = "SELECT s FROM Specimen s WHERE s.features = :features"),
    @NamedQuery(name = "Specimen.findByFamily", query = "SELECT s FROM Specimen s WHERE s.family = :family"),
    @NamedQuery(name = "Specimen.findBySubfamily", query = "SELECT s FROM Specimen s WHERE s.subfamily = :subfamily"),
    @NamedQuery(name = "Specimen.findByTribe", query = "SELECT s FROM Specimen s WHERE s.tribe = :tribe"),
    @NamedQuery(name = "Specimen.findByGenus", query = "SELECT s FROM Specimen s WHERE s.genus = :genus"),
    @NamedQuery(name = "Specimen.findBySpecificEpithet", query = "SELECT s FROM Specimen s WHERE s.specificEpithet = :specificEpithet"),
    @NamedQuery(name = "Specimen.findBySubspecificEpithet", query = "SELECT s FROM Specimen s WHERE s.subspecificEpithet = :subspecificEpithet"),
    @NamedQuery(name = "Specimen.findByInfraspecificEpithet", query = "SELECT s FROM Specimen s WHERE s.infraspecificEpithet = :infraspecificEpithet"),
    @NamedQuery(name = "Specimen.findByInfraspecificRank", query = "SELECT s FROM Specimen s WHERE s.infraspecificRank = :infraspecificRank"),
    @NamedQuery(name = "Specimen.findByAuthorship", query = "SELECT s FROM Specimen s WHERE s.authorship = :authorship"),
    @NamedQuery(name = "Specimen.findByUnNamedForm", query = "SELECT s FROM Specimen s WHERE s.unNamedForm = :unNamedForm"),
    @NamedQuery(name = "Specimen.findByIdentifiedBy", query = "SELECT s FROM Specimen s WHERE s.identifiedBy = :identifiedBy"),
    @NamedQuery(name = "Specimen.findByCountry", query = "SELECT s FROM Specimen s WHERE s.country = :country"),
    @NamedQuery(name = "Specimen.findByPrimaryDivison", query = "SELECT s FROM Specimen s WHERE s.primaryDivison = :primaryDivison"),
    @NamedQuery(name = "Specimen.findByVerbatimElevation", query = "SELECT s FROM Specimen s WHERE s.verbatimElevation = :verbatimElevation"),
    @NamedQuery(name = "Specimen.findByCollectingMethod", query = "SELECT s FROM Specimen s WHERE s.collectingMethod = :collectingMethod"),
    @NamedQuery(name = "Specimen.findByDateNOS", query = "SELECT s FROM Specimen s WHERE s.dateNOS = :dateNOS"),
    @NamedQuery(name = "Specimen.findByDateEmerged", query = "SELECT s FROM Specimen s WHERE s.dateEmerged = :dateEmerged"),
    @NamedQuery(name = "Specimen.findByDateEmergedIndicator", query = "SELECT s FROM Specimen s WHERE s.dateEmergedIndicator = :dateEmergedIndicator"),
    @NamedQuery(name = "Specimen.findByDateCollected", query = "SELECT s FROM Specimen s WHERE s.dateCollected = :dateCollected"),
    @NamedQuery(name = "Specimen.findByDateCollectedIndicator", query = "SELECT s FROM Specimen s WHERE s.dateCollectedIndicator = :dateCollectedIndicator"),
    @NamedQuery(name = "Specimen.findByCollection", query = "SELECT s FROM Specimen s WHERE s.collection = :collection"),
    @NamedQuery(name = "Specimen.findByLifeStage", query = "SELECT s FROM Specimen s WHERE s.lifeStage = :lifeStage"),
    @NamedQuery(name = "Specimen.findByPreparationType", query = "SELECT s FROM Specimen s WHERE s.preparationType = :preparationType"),
    @NamedQuery(name = "Specimen.findByHabitat", query = "SELECT s FROM Specimen s WHERE s.habitat = :habitat"),
    @NamedQuery(name = "Specimen.findByAssociatedTaxon", query = "SELECT s FROM Specimen s WHERE s.associatedTaxon = :associatedTaxon"),
    @NamedQuery(name = "Specimen.findByQuestions", query = "SELECT s FROM Specimen s WHERE s.questions = :questions"),
    @NamedQuery(name = "Specimen.findByInferences", query = "SELECT s FROM Specimen s WHERE s.inferences = :inferences"),
    @NamedQuery(name = "Specimen.findByLocationInCollection", query = "SELECT s FROM Specimen s WHERE s.locationInCollection = :locationInCollection"),
    @NamedQuery(name = "Specimen.findByWorkFlowStatus1", query = "SELECT s FROM Specimen s WHERE s.workFlowStatus = :workFlowStatus"),
    @NamedQuery(name = "Specimen.findByCreatedBy", query = "SELECT s FROM Specimen s WHERE s.createdBy = :createdBy"),
    @NamedQuery(name = "Specimen.findByDateCreated", query = "SELECT s FROM Specimen s WHERE s.dateCreated = :dateCreated"),
    @NamedQuery(name = "Specimen.findByDateLastUpdated", query = "SELECT s FROM Specimen s WHERE s.dateLastUpdated = :dateLastUpdated"),
    @NamedQuery(name = "Specimen.findByLastUpdatedBy", query = "SELECT s FROM Specimen s WHERE s.lastUpdatedBy = :lastUpdatedBy"),
    @NamedQuery(name = "Specimen.findByValidDistributionFlag1", query = "SELECT s FROM Specimen s WHERE s.validDistributionFlag = :validDistributionFlag"),
    @NamedQuery(name = "Specimen.findBySex", query = "SELECT s FROM Specimen s WHERE s.sex = :sex"),
    @NamedQuery(name = "Specimen.findByISODate", query = "SELECT s FROM Specimen s WHERE s.iSODate = :iSODate")
})
*/
public class Specimen implements Serializable {
    private final static Logger logger = Logger.getLogger(Specimen.class.getName());

    private static final long serialVersionUID = -1321141594439433313L;
    
    public static final String STATUS_NOT_A_TYPE = "Not a Type";
    @Id
    @GeneratedValue(generator="SpecimenSeq")
    @SequenceGenerator(name="SpecimenSeq",sequenceName="SEQ_SPECIMENID", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "SpecimenId", nullable = false)
    private Long specimenId;
    @Basic(optional = false)
    @Column(name = "Barcode", nullable = false, length = 20)
    private String barcode;
    @Column(name = "DrawerNumber", length = 10)
    private String drawerNumber;
    @Basic(optional = false)
    @Column(name = "TypeStatus", nullable = false, length = 50)
    private String typeStatus;
    @Column(name = "TypeNumber")
    private BigInteger typeNumber;
    @Column(name = "CitedInPublication", length = 900)
    private String citedInPublication;
    @Column(name = "Features", length = 50)
    private String features;
    @Column(name = "Family", length = 40)
    private String family;
    @Column(name = "Subfamily", length = 40)
    private String subfamily;
    @Column(name = "Tribe", length = 40)
    private String tribe;
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
    
    @Column(name = "DateIdentified", length = 32)
    private String dateIdentified;
    @Column(name = "IdentificationRemarks", length = 65535)
    private String identificationRemarks;
    @Column(name = "NatureOfId", length = 255)
    private String natureOfId;
    
    @Column(name = "higher_geography", length = 255)  // Value of geog_auth_rec.higher_geog
    private String higherGeography;
    @Column(name = "Country", length = 255)
    private String country;
    /**
     * [sic.]  PrimaryDivison (note mispelling, not Divis_i_on)
     */
    @Column(name = "PrimaryDivison", length = 255)
    private String primaryDivison;
    @Lob
    @Column(name = "SpecificLocality", length = 65535)
    private String specificLocality;
    @Lob
    @Column(name = "VerbatimLocality", length = 65535, nullable = false)
    private String verbatimLocality;
    //@Column(name = "VerbatimElevation", length = 255)  // removed
    //private String verbatimElevation;
    
    @Column(name = "VerbatimCollector", length = 2000, nullable = false)
    private String verbatimCollector;    
    @Column(name = "VerbatimCollection", length = 2000, nullable = false)
    private String verbatimCollection;   
    @Column(name = "VerbatimNumbers", length = 2000, nullable = false)
    private String verbatimNumbers;   
    @Lob
    @Column(name = "verbatimUnclassifiedText", length = 2000, nullable = false)
    private String verbatimUnclassifiedText;   
    
    @Column(name = "verbatimClusterIdentifier", length = 255, nullable = false)
    private String verbatimClusterIdentifier;   
    @Column(name = "externalWorkflowProcess", length = 900, nullable = false)
    private String externalWorkflowProcess; 
    @Column(name = "externalWorkflowDate", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date externalWorkflowDate;
    
    @Column(name = "minimum_elevation")
    private BigInteger minimum_elevation;
    @Column(name = "maximum_elevation", length = 255)
    private BigInteger maximum_elevation;
    @Column(name = "elev_units", length = 5)
    private String elev_units;
    
    @Column(name = "CollectingMethod", length = 255)
    private String collectingMethod;
    @Column(name = "DateNOS", length = 32)
    private String dateNOS;
    @Column(name = "DateEmerged", length = 32)
    private String dateEmerged;
    @Column(name = "DateEmergedIndicator", length = 50)
    private String dateEmergedIndicator;
    @Column(name = "DateCollected", length = 32)
    private String dateCollected;
    @Column(name = "DateCollectedIndicator", length = 50)
    private String dateCollectedIndicator;
    @Column(name = "Collection", length = 255)
    private String collection;
    @Lob
    @Column(name = "SpecimenNotes", length = 65535)
    private String specimenNotes;
    @Column(name = "LifeStage", length = 50)
    private String lifeStage;
    
    //@Column(name = "PreparationType", length = 50)
    //private String preparationType;
    
    @Column(name = "Habitat", length = 900)
    private String habitat;
    @Column(name = "Microhabitat", length = 900)
    private String microhabitat;
    @Column(name = "AssociatedTaxon", length = 900)
    private String associatedTaxon;
    @Column(name = "Questions", length = 900)
    private String questions;
    @Column(name = "Inferences", length = 900)
    private String inferences;
    @Column(name = "LocationInCollection", length = 255)
    private String locationInCollection;
    @Column(name = "WorkFlowStatus", length = 30)
    private String workFlowStatus;
    @Column(name = "CreatedBy", length = 255)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DateCreated", insertable=false, updatable=false)
	@Basic(optional=false)
    private Date dateCreated;

	@Column(name = "DateLastUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastUpdated;
    @Column(name = "LastUpdatedBy", length = 255)
    private String lastUpdatedBy;
    @Column(name = "ValidDistributionFlag")
    private Boolean validDistributionFlag;
    @Column(name = "flaginbulkloader")
    private Boolean flagInBulkloader;
    @Column(name = "flaginmczbase")
    private Boolean flagInMCZbase;
    @Column(name = "flagancilaryalsoinmczbase")
    private Boolean flagAncilaryAlsoInMCZbase;
    @Column(name = "Sex", length = 50)
    private String sex;
    @Column(name = "ISODate", length = 10)
    private String iSODate;
    @Column(name = "creatingPath", length = 900)
    private String creatingPath;   // A path for image file, denormalized from Image.path for JPA query without join to Image. 
    @Column(name = "creatingFilename", length = 255)
    private String creatingFilename; 
    @Column(name = "locality_id", nullable = true)
    private Long locality_id;
    @Column(name = "collecting_event_id", nullable = true)
    private Long collecting_event_id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specimenId", fetch=FetchType.EAGER, orphanRemoval=true)
    private Collection<Determination> determinationCollection;
    @OneToMany(mappedBy = "specimenId", fetch=FetchType.EAGER)
    private Collection<Image> imageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specimenId", fetch=FetchType.EAGER, orphanRemoval=true)
    private Collection<OtherNumbers> otherNumbersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specimenId", fetch=FetchType.EAGER, orphanRemoval=true)
    private Collection<Collector> collectorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specimenId", fetch=FetchType.EAGER)
    private Collection<Tracking> trackingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specimenId", fetch=FetchType.EAGER, orphanRemoval=true)
    private Collection<SpecimenPart> partCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "specimenId", fetch=FetchType.EAGER)
    private LatLong georeference;


	@PrePersist
    void updateDates() {
         dateLastUpdated = (Date) new Date().clone();
    }


    public Specimen() {
		setDefaults();
	}

	/** Set default values for a new specimen object with no other data.
	 *
	 */
	public final void setDefaults() {
		this.typeStatus = STATUS_NOT_A_TYPE;
		this.validDistributionFlag = true;
		//this.preparationType = "Pinned";
	}

	/** Clear the default values for a new specimen object, as in one that
	 * is to be used as the search criteria for a search by pattern.
	 */
	public void clearDefaults() {
		this.typeStatus = null;
		this.validDistributionFlag = null;
		//this.preparationType = null;
	}

    public Specimen(Long specimenId) {
        this.specimenId = specimenId;
    }

    public Specimen(Long specimenId, String barcode, String typeStatus, Date dateCreated) {
        this.specimenId = specimenId;
        this.barcode = barcode;
        this.typeStatus = typeStatus;
        if (dateCreated == null) {
            this.dateCreated = null;
        } else {
            this.dateCreated = (Date) dateCreated.clone();
        }
    }

    public Specimen(String barcode, String drawerNumber, String typeStatus,
            Long typeNumber, String citedInPublication, String features,
            String family, String subfamily, String tribe, String genus,
            String specificEpithet, String subspecificEpithet,
            String infraspecificEpithet, String infraspecificRank,
            String authorship, String unNamedForm,
            String identificationQualifier, String identifiedBy,
            String country, String primaryDivison, String specificLocality,
            String verbatimLocality, 
            BigInteger minimum_elevation, BigInteger maximum_elevation, String elev_units,
            String collectingMethod, String dateNos, String dateEmerged,
            String dateEmergedIndicator, String dateCollected,
            String dateCollectedIndicator, String collection,
            String specimenNotes, String lifeStage, String preparationType,
            String sex,
            String habitat, String associatedTaxon, String questions,
            String inferences, String locationInCollection,
            String workFlowStatus, String createdBy, Date dateCreated,
            Date dateLastUpdated, String lastUpdatedBy,
            Boolean validDistributionFlag, Collection<Collector> collectors,
            Collection<Determination> determinations, Collection<Tracking> trackings,
            Collection<OtherNumbers> numbers, Collection<Image> ICImages) {
        this.barcode = barcode;
        this.drawerNumber = drawerNumber;
        this.typeStatus = typeStatus;
        this.typeNumber = BigInteger.valueOf(typeNumber);
        this.citedInPublication = citedInPublication;
        this.features = features;
        setFamily(family);
        this.subfamily = subfamily;
        this.tribe = tribe;
        this.genus = genus;
        this.specificEpithet = specificEpithet;
        this.subspecificEpithet = subspecificEpithet;
        this.infraspecificEpithet = infraspecificEpithet;
        this.infraspecificRank = infraspecificRank;
        this.authorship = authorship;
        this.unNamedForm = unNamedForm;
        this.identifiedBy = identifiedBy;
        this.country = country;
        this.primaryDivison = primaryDivison;
        this.specificLocality = specificLocality;
        this.verbatimLocality = verbatimLocality;
        
        // Elevation
        this.maximum_elevation = maximum_elevation;
        this.minimum_elevation = minimum_elevation;
        this.elev_units = elev_units;
        
        this.collectingMethod = collectingMethod;
        this.dateNOS = dateNos;
        this.dateEmerged = dateEmerged;
        this.dateEmergedIndicator = dateEmergedIndicator;
        this.dateCollected = dateCollected;
        this.dateCollectedIndicator = dateCollectedIndicator;
        this.collection = collection;
        this.specimenNotes = specimenNotes;
        this.lifeStage = lifeStage;
        this.sex = sex;
        //this.preparationType = preparationType;
        this.habitat = habitat;
        this.associatedTaxon = associatedTaxon;
        this.questions = questions;
        this.inferences = inferences;
        this.locationInCollection = locationInCollection;
        this.workFlowStatus = workFlowStatus;
        this.createdBy = createdBy;
        if (dateCreated == null) {
            this.dateCreated = null;
        } else {
            this.dateCreated = (Date) dateCreated.clone();
        }
        if (dateLastUpdated == null) {
            this.dateLastUpdated = null;
        } else {
            this.dateLastUpdated = (Date) dateLastUpdated.clone();
        }
        this.lastUpdatedBy = lastUpdatedBy;
        this.validDistributionFlag = validDistributionFlag;
        this.collectorCollection = (Collection<Collector>)collectors;
        this.determinationCollection = (Collection<Determination>)determinations;
        this.trackingCollection = (Collection<Tracking>)trackings;
        this.otherNumbersCollection = (Collection<OtherNumbers>)numbers;
        this.imageCollection = (Collection<Image>)ICImages;
    }

    public Long getSpecimenId() {
        return specimenId;
    }

    public void setSpecimenId(Long specimenId) {
        this.specimenId = specimenId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

	/**
	 * non-zero padded catalog number extracted from the barcode, equivalent to 
	 * arctos/MCZbase cat_num for lepidoptera.
	 * 
	 * @return numeric part of barcode without zero padding
	 */
    public String getCatNum() {
		return Integer.toString(Integer.parseInt(barcode.substring(8, barcode.length())));
	}

    public String getDrawerNumber() {
        return drawerNumber;
    }

    public void setDrawerNumber(String drawerNumber) {
        this.drawerNumber = drawerNumber;
        if (this.drawerNumber != null && this.drawerNumber.length() > MetadataRetriever.getFieldLength(Specimen.class, "DrawerNumber")) {
            this.drawerNumber = this.drawerNumber.substring(0, MetadataRetriever.getFieldLength(Specimen.class, "DrawerNumber"));
        }
    }

    public String getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(String typeStatus) {
        this.typeStatus = typeStatus;
    }

    public BigInteger getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(BigInteger typeNumber) {
        this.typeNumber = typeNumber;
    }

    public String getCitedInPublication() {
        return citedInPublication;
    }

    public void setCitedInPublication(String citedInPublication) {
        this.citedInPublication = citedInPublication;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
        if (this.family != null && this.family.length() > MetadataRetriever.getFieldLength(Specimen.class, "Family")) {
            this.family = this.family.substring(0, MetadataRetriever.getFieldLength(Specimen.class, "Family"));
        }
        if (this.family != null) {
            this.family = this.family.trim();
        }
    }

    public String getSubfamily() {
        return subfamily;
    }

    public void setSubfamily(String subfamily) {
        this.subfamily = subfamily;
        if (this.subfamily != null) {
            this.subfamily = this.subfamily.trim();
        }
    }

    public String getTribe() {
        return tribe;
    }

    public void setTribe(String tribe) {
        this.tribe = tribe;
        if (this.tribe != null) {
            this.tribe = this.tribe.trim();
        }
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
        if (this.genus != null) {
            this.genus = this.genus.trim();
        }
    }

    public String getSpecificEpithet() {
        return specificEpithet;
    }

    public void setSpecificEpithet(String specificEpithet) {
        this.specificEpithet = specificEpithet;
        if (this.specificEpithet != null) {
            this.specificEpithet = this.specificEpithet.trim();
        }
    }

    public String getSubspecificEpithet() {
        return subspecificEpithet;
    }

    public void setSubspecificEpithet(String subspecificEpithet) {
        this.subspecificEpithet = subspecificEpithet;
        if (this.subspecificEpithet != null) {
            this.subspecificEpithet = this.subspecificEpithet.trim();
        }
    }

    public String getInfraspecificEpithet() {
        return infraspecificEpithet;
    }

    public void setInfraspecificEpithet(String infraspecificEpithet) {
        this.infraspecificEpithet = infraspecificEpithet;
        if (this.infraspecificEpithet != null) {
            this.infraspecificEpithet = this.infraspecificEpithet.trim();
        }
    }

    public String getInfraspecificRank() {
        return infraspecificRank;
    }

    public void setInfraspecificRank(String infraspecificRank) {
        this.infraspecificRank = infraspecificRank;
        if (this.infraspecificRank != null) {
            this.infraspecificRank = this.infraspecificRank.trim();
        }
    }

    public String getAuthorship() {
        return authorship;
    }

    public void setAuthorship(String authorship) {
        this.authorship = authorship;
        if (this.authorship != null) {
            this.authorship = this.authorship.trim();
        }
    }

	public String getCurrentScientificName() {
		StringBuffer result = new StringBuffer();
		if (genus!=null) { result.append(genus).append(" "); }
		if (specificEpithet!=null) { result.append(specificEpithet).append(" "); }
		if (subspecificEpithet!=null) { result.append(subspecificEpithet).append(" "); }
		if (infraspecificRank!=null) { result.append(infraspecificRank).append(" "); }
		if (infraspecificEpithet!=null) { result.append(infraspecificEpithet).append(" "); }
		if (authorship!=null) { result.append(authorship).append(" "); }
		return result.toString().trim();
	}

    public String getUnNamedForm() {
        return this.unNamedForm;
    }

    public void setUnNamedForm(String unNamedForm) {
        this.unNamedForm = unNamedForm;
        if (this.unNamedForm != null) {
            this.unNamedForm = this.unNamedForm.trim();
        }
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
	 * @return the identificationRemarks
	 */
	public String getIdentificationRemarks() {
		return identificationRemarks;
	}


	/**
	 * @param identificationRemarks the identificationRemarks to set
	 */
	public void setIdentificationRemarks(String identificationRemarks) {
		this.identificationRemarks = identificationRemarks;
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


	public String getIdentifiedBy() {
        return identifiedBy;
    }

    public void setIdentifiedBy(String identifiedBy) {
        this.identifiedBy = identifiedBy;
    }

    /**
	 * @return the higherGeography
	 */
	public String getHigherGeography() {
		return higherGeography;
	}


	/**
	 * @param higherGeography the higherGeography to set
	 */
	public void setHigherGeography(String higherGeography) {
		this.higherGeography = higherGeography;
	}


	public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPrimaryDivison() {
        return primaryDivison;
    }

    public void setPrimaryDivison(String primaryDivison) {
        this.primaryDivison = primaryDivison;
    }

    public String getSpecificLocality() {
        return specificLocality;
    }

    public void setSpecificLocality(String specificLocality) {
        this.specificLocality = specificLocality;
    }

    public String getVerbatimLocality() {
        return verbatimLocality;
    }

    public void setVerbatimLocality(String verbatimLocality) {
        this.verbatimLocality = verbatimLocality;
    }

 
    /**
	 * @return the locality_id
	 */
	public Long getLocality_id() {
		return locality_id;
	}


	/**
	 * @param locality_id the locality_id to set
	 */
	public void setLocality_id(Long locality_id) {
		this.locality_id = locality_id;
	}


	/**
	 * @return the collecting_event_id
	 */
	public Long getCollecting_event_id() {
		return collecting_event_id;
	}


	/**
	 * @param collecting_event_id the collecting_event_id to set
	 */
	public void setCollecting_event_id(Long collecting_event_id) {
		this.collecting_event_id = collecting_event_id;
	}


	/**
	 * @return the verbatimCollector
	 */
	public String getVerbatimCollector() {
		return verbatimCollector;
	}


	/**
	 * @param verbatimCollector the verbatimCollector to set
	 */
	public void setVerbatimCollector(String verbatimCollector) {
		this.verbatimCollector = verbatimCollector;
	}


	/**
	 * @return the verbatimCollection
	 */
	public String getVerbatimCollection() {
		return verbatimCollection;
	}


	/**
	 * @param verbatimCollection the verbatimCollection to set
	 */
	public void setVerbatimCollection(String verbatimCollection) {
		this.verbatimCollection = verbatimCollection;
	}


	/**
	 * @return the verbatimNumbers
	 */
	public String getVerbatimNumbers() {
		return verbatimNumbers;
	}


	/**
	 * @param verbatimNumbers the verbatimNumbers to set
	 */
	public void setVerbatimNumbers(String verbatimNumbers) {
		this.verbatimNumbers = verbatimNumbers;
	}


	/**
	 * @return the verbatimUnclassifiedText
	 */
	public String getVerbatimUnclassifiedText() {
		return verbatimUnclassifiedText;
	}


	/**
	 * @param verbatimUnclassifiedText the verbatimUnclassifiedText to set
	 */
	public void setVerbatimUnclassifiedText(String verbatimUnclassifiedText) {
		this.verbatimUnclassifiedText = verbatimUnclassifiedText;
	}    
    
    
//    public String getVerbatimElevation() {
//        return verbatimElevation;
//    }
//
//    public void setVerbatimElevation(String verbatimElevation) {
//        this.verbatimElevation = verbatimElevation;
//    }


	/**
	 * @return the verbatimClusterIdentifier
	 */
	public String getVerbatimClusterIdentifier() {
		return verbatimClusterIdentifier;
	}


	/**
	 * @param verbatimClusterIdentifier the verbatimClusterIdentifier to set
	 */
	public void setVerbatimClusterIdentifier(String verbatimClusterIdentifier) {
		this.verbatimClusterIdentifier = verbatimClusterIdentifier;
	}


	/**
	 * @return the externalWorkflowProcess
	 */
	public String getExternalWorkflowProcess() {
		return externalWorkflowProcess;
	}


	/**
	 * @param externalWorkflowProcess the externalWorkflowProcess to set
	 */
	public void setExternalWorkflowProcess(String externalWorkflowProcess) {
		this.externalWorkflowProcess = externalWorkflowProcess;
	}


	/**
	 * @return the externalWorkflowDate
	 */
	public Date getExternalWorkflowDate() {
		return externalWorkflowDate;
	}


	/**
	 * @param externalWorkflowDate the externalWorkflowDate to set
	 */
	public void setExternalWorkflowDate(Date externalWorkflowDate) {
		this.externalWorkflowDate = externalWorkflowDate;
	}


	/**
	 * @return the minimum_elevation
	 */
	public BigInteger getMinimum_elevation() {
		return minimum_elevation;
	}


	/**
	 * @param minimum_elevation the minimum_elevation to set
	 */
	public void setMinimum_elevation(BigInteger minimum_elevation) {
		this.minimum_elevation = minimum_elevation;
	}


	/**
	 * @return the maximum_elevation
	 */
	public BigInteger getMaximum_elevation() {
		return maximum_elevation;
	}


	/**
	 * @param maximum_elevation the maximum_elevation to set
	 */
	public void setMaximum_elevation(BigInteger maximum_elevation) {
		this.maximum_elevation = maximum_elevation;
	}


	/**
	 * @return the elev_units
	 */
	public String getElev_units() {
		return elev_units;
	}


	/**
	 * @param elev_units the elev_units to set
	 */
	public void setElev_units(String elev_units) {
		this.elev_units = elev_units;
	}


	public String getCollectingMethod() {
        return collectingMethod;
    }

    public void setCollectingMethod(String collectingMethod) {
        this.collectingMethod = collectingMethod;
    }

    public String getDateNOS() {
        return dateNOS;
    }

    public void setDateNOS(String dateNOS) {
        this.dateNOS = dateNOS;
    }

    public String getDateEmerged() {
        return dateEmerged;
    }

    public void setDateEmerged(String dateEmerged) {
        this.dateEmerged = dateEmerged;
    }

    public String getDateEmergedIndicator() {
        return dateEmergedIndicator;
    }

    public void setDateEmergedIndicator(String dateEmergedIndicator) {
        this.dateEmergedIndicator = dateEmergedIndicator;
    }

    public String getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(String dateCollected) {
        this.dateCollected = dateCollected;
    }

    public String getDateCollectedIndicator() {
        return dateCollectedIndicator;
    }

    public void setDateCollectedIndicator(String dateCollectedIndicator) {
        this.dateCollectedIndicator = dateCollectedIndicator;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getSpecimenNotes() {
        return specimenNotes;
    }

    public void setSpecimenNotes(String specimenNotes) {
        this.specimenNotes = specimenNotes;
    }

    public String getLifeStage() {
        return lifeStage;
    }

    public void setLifeStage(String lifeStage) {
        this.lifeStage = lifeStage;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    /**
	 * @return the microhabitat
	 */
	public String getMicrohabitat() {
		return microhabitat;
	}


	/**
	 * @param microhabitat the microhabitat to set
	 */
	public void setMicrohabitat(String microhabitat) {
		this.microhabitat = microhabitat;
	}


	/**
	 * @return the iSODate
	 */
	public String getiSODate() {
		return iSODate;
	}


	/**
	 * @param iSODate the iSODate to set
	 */
	public void setiSODate(String iSODate) {
		this.iSODate = iSODate;
	}


	public String getAssociatedTaxon() {
        return associatedTaxon;
    }

    public void setAssociatedTaxon(String associatedTaxon) {
        this.associatedTaxon = associatedTaxon;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getInferences() {
        return inferences;
    }

    public void setInferences(String inferences) {
        this.inferences = inferences;
    }

    public String getLocationInCollection() {
        return locationInCollection;
    }

    public void setLocationInCollection(String locationInCollection) {
        this.locationInCollection = locationInCollection;
    }

    public String getWorkFlowStatus() {
        return workFlowStatus;
    }

    public void setWorkFlowStatus(String workFlowStatus) {
        this.workFlowStatus = workFlowStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        if (dateCreated == null) {
            this.dateCreated = null;
        } else {
            this.dateCreated = (Date) dateCreated.clone();
        }
    }

    public Date getDateLastUpdated() {
        Date result = this.dateLastUpdated;
        return result;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        if (dateLastUpdated == null) {
            this.dateLastUpdated = null;
        } else {
            this.dateLastUpdated = (Date) dateLastUpdated.clone();
        }
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Boolean getValidDistributionFlag() {
        return validDistributionFlag;
    }

    public void setValidDistributionFlag(Boolean validDistributionFlag) {
		if (validDistributionFlag==null) {
			validDistributionFlag = true;
		}
        this.validDistributionFlag = validDistributionFlag;
    }


	public Boolean getFlagAncilaryAlsoInMCZbase() {
		return flagAncilaryAlsoInMCZbase;
	}

	public void setFlagAncilaryAlsoInMCZbase(Boolean flagAncilaryAlsoInMCZbase) {
		if (flagAncilaryAlsoInMCZbase == null) {
			flagAncilaryAlsoInMCZbase = false;
		}
		this.flagAncilaryAlsoInMCZbase = flagAncilaryAlsoInMCZbase;
	}

	public Boolean getFlagInBulkloader() {
		return flagInBulkloader;
	}

	public void setFlagInBulkloader(Boolean flagInBulkloader) {
		if (flagInBulkloader == null) {
			flagInBulkloader = false;
		}
		this.flagInBulkloader = flagInBulkloader;
	}

	public Boolean getFlagInMCZbase() {
		return flagInMCZbase;
	}

	public void setFlagInMCZbase(Boolean flagInMCZbase) {
		if (flagInMCZbase == null) {
			flagInMCZbase = false;
		}
		this.flagInMCZbase = flagInMCZbase;
	}

	public String getLoadFlags() {
		String result = "Unexpected State";
		if (flagInMCZbase==null) { flagInMCZbase = false; } 
		if (flagAncilaryAlsoInMCZbase==null) { flagAncilaryAlsoInMCZbase = false; } 
		if (flagInBulkloader==false && flagInMCZbase==false && flagAncilaryAlsoInMCZbase==false) {
           result = "In DataShot";
		}
		if (flagInBulkloader==true && flagInMCZbase==false && flagAncilaryAlsoInMCZbase==false) {
           result = "In Bulkloader";
		}
		if (flagInBulkloader==true && flagInMCZbase==true && flagAncilaryAlsoInMCZbase==false) {
           result = "Adding Image and Ids.";
		}
		if (flagInBulkloader==true && flagInMCZbase==true && flagAncilaryAlsoInMCZbase==true) {
           result = WorkFlowStatus.STAGE_DONE;
		}
		return result;
	}

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getISODate() {
        return iSODate;
    }

    public void setISODate(String iSODate) {
        this.iSODate = iSODate;
    }

    /**
	 * @return the creatingPath
	 */
	public String getCreatingPath() {
		return creatingPath;
	}


	/**
	 * @param path the creatingPath to set
	 */
	public void setCreatingPath(String path) {
		this.creatingPath = path;
	}


	/**
	 * @return the creatingFilename
	 */
	public String getCreatingFilename() {
		return creatingFilename;
	}


	/**
	 * @param creatingFilename the creatingFilename to set
	 */
	public void setCreatingFilename(String creatingFilename) {
		this.creatingFilename = creatingFilename;
	}


	public Collection<Determination> getDeterminationCollection() {
		logger.log(Level.INFO,this.determinationCollection.toString());
        return determinationCollection;
    }

    public void setDeterminationCollection(Collection<Determination> determinationCollection) {
        this.determinationCollection = (Collection<Determination>)determinationCollection;
		logger.log(Level.INFO,this.determinationCollection.toString());
    }
    
    /**
     * Remove a determination from the list of related determinations
     * 
     * @param detToRemove determination to remove from the collection.
     * @return true if successful
     */
    public boolean removeFromDeterminationCollection(Determination detToRemove) { 
    	return this.determinationCollection.remove(detToRemove);
    }

    public Collection<Image> getImageCollection() {
        return imageCollection;
    }

    public void setImageCollection(Collection<Image> imageCollection) {
        this.imageCollection = (Collection<Image>)imageCollection;
    }

    public Collection<OtherNumbers> getOtherNumbersCollection() {
        return (Collection<OtherNumbers>)otherNumbersCollection;
    }

    public void setOtherNumbersCollection(Collection<OtherNumbers> otherNumbersCollection) {
        this.otherNumbersCollection = (Collection<OtherNumbers>)otherNumbersCollection;
    }
    
    /**
     * Remove a number from the list of other numbers. 
     * 
     * @param numberToRemove number to remove from the collection
     * @return true on success.
     */
    public boolean removeFromOtherNumbersCollection(OtherNumbers numberToRemove) { 
    	return this.otherNumbersCollection.remove(numberToRemove);
    }

    public Collection<Collector> getCollectorCollection() {
        return collectorCollection;
    }

    public void setCollectorCollection(Collection<Collector> collectorCollection) {
        this.collectorCollection = (Collection<Collector>)collectorCollection;
    }
    
    /**
     * Remove a collector from the list of related collectors.
     * 
     * @param collectorToRemove collector to remove from the collection.
     * @return true on success.
     */
    public boolean removeFromCollectorCollection(Collector collectorToRemove) { 
    	return this.collectorCollection.remove(collectorToRemove);
    }

    public Collection<Tracking> getTrackingCollection() {
        return trackingCollection;
    }

    public void setTrackingCollection(Collection<Tracking> tracking1Collection) {
        this.trackingCollection = (Collection<Tracking>)tracking1Collection;
    }

    public Collection<SpecimenPart> getPartCollection() {
		return partCollection;
	}


	public void setPartCollection(Collection<SpecimenPart> partCollection) {
		this.partCollection = partCollection;
	}

	/**
	 * Remove a part from the list of parts for the specimen.
	 * 
	 * @param partToRemove part to remove from the list.
	 * @return true if successful
	 */
    public boolean removeFromPartCollection(SpecimenPart partToRemove) { 
    	return this.partCollection.remove(partToRemove);
    }	

	/**
	 * @return the georeference
	 */
	public LatLong getGeoreference() {
		//if (georeference==null) { 
		//	georeference = new LatLong();
		//	georeference.setSpecimenid(this);
		//}
		return georeference;
	}


	/**
	 * @param georeference the georeference to set
	 */
	public void setGeoreference(LatLong georeference) {
		this.georeference = georeference;
		if (this.getGeoreference()!=null) { 
			if (this.getGeoreference().getSpecimenid()!=null) { 
		        logger.log(Level.INFO,"Specimen " + this.getBarcode() + " [" +  this.getSpecimenId() + "] Georeference " + this.getGeoreference().getLatLongId() + " for specimen:" + this.getGeoreference().getSpecimenid().getSpecimenId()  );
			} else { 
		        logger.log(Level.INFO,"Specimen " + this.getBarcode() + " [" +  this.getSpecimenId() + "] Georeference " + this.getGeoreference().getLatLongId() + " with null for specimen " );
			}
		} else { 
		   logger.log(Level.INFO,"Specimen " + this.getBarcode() + " [" +  this.getSpecimenId() + "] Georeference is null " );
		}
	}
	
	/**
	 * @param georeference the georeference to set the georeference's values from.
	 */
	public void setGeoreferenceValues(LatLong replacement) {
		georeference.setAcceptedLatLongFg(replacement.getAcceptedLatLongFg());
		georeference.setDatum(replacement.getDatum());
		georeference.setDecLat(replacement.getDecLat());
		georeference.setDecLatMin(replacement.getDecLatMin());
		georeference.setDecLong(replacement.getDecLong());
		georeference.setDecLongMin(replacement.getDecLongMin());
		georeference.setDeterminedByAgent(replacement.getDeterminedByAgent());
		georeference.setDeterminedDate(replacement.getDeterminedDate());
		georeference.setExtent(replacement.getExtent());
		georeference.setFieldVerifiedFg(replacement.getFieldVerifiedFg());
		georeference.setGeorefmethod(replacement.getGeorefmethod());
		georeference.setGpsaccuracy(replacement.getGpsaccuracy());
		georeference.setLatDeg(replacement.getLatDeg());
		georeference.setLatDir(replacement.getLatDir());
		georeference.setLatLongForNnpFg(replacement.getLatLongForNnpFg());
		// georeference.setLatLongId(latLongId);
		georeference.setLatLongRefSource(replacement.getLatLongRefSource());
		georeference.setLatLongRemarks(replacement.getLatLongRemarks());
		georeference.setLatMin(replacement.getLatMin());
		georeference.setLatSec(replacement.getLatSec());
		georeference.setLongDeg(replacement.getLongDeg());
		georeference.setLongDir(replacement.getLongDir());
		georeference.setLongMin(replacement.getLongMin());
		georeference.setLongSec(replacement.getLongSec());
		georeference.setMaxErrorDistance(replacement.getMaxErrorDistance());
		georeference.setMaxErrorUnits(replacement.getMaxErrorUnits());
		georeference.setNearestNamedPlace(replacement.getNearestNamedPlace());
		georeference.setOrigLatLongUnits(replacement.getOrigLatLongUnits());
		// georeference.setSpecimenid(this);
		georeference.setUtmEw(replacement.getUtmEw());
		georeference.setUtmNs(replacement.getUtmNs());
		georeference.setUtmZone(replacement.getUtmZone());
		georeference.setVerificationstatus(replacement.getVerificationstatus());
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (specimenId != null ? specimenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specimen)) {
            return false;
        }
        Specimen other = (Specimen) object;
        if ((this.specimenId == null && other.specimenId != null) || (this.specimenId != null && !this.specimenId.equals(other.specimenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return barcode + " edu.harvard.mcz.imagecapture.data.Specimen[specimenId=" + specimenId + "]";
    }

	/**
	 * If verbatim locality is blank, copy the value of specific locality to verbatim locality,
	 * otherwise, if verbatim locality is not blank, and specific locality is blank, copy the
	 * value of verbatim locality to specific locality.
	 */
    public void copyLocalitySpecificVerbatim () {
		if (this.verbatimLocality==null || this.verbatimLocality.length()==0) {
			if (this.specificLocality!=null) {
		         this.verbatimLocality = this.specificLocality;
			}
		} else {
			if (this.specificLocality==null || this.specificLocality.length()==0) {
		         this.specificLocality = this.verbatimLocality;
			}
		}
	}

	public void copyDateEmergedDate() {
		if (this.dateEmerged==null || this.dateEmerged.length()==0) {
			if (this.dateNOS!=null) {
				this.dateEmerged = this.dateNOS;
			}
		} else {
			if (this.dateNOS==null || this.dateNOS.length()==0) {
			    this.dateNOS = this.dateEmerged;
			}
		}
	}

	public void copyDateCollectedDate() {
		if (this.dateCollected==null || this.dateCollected.length()==0) {
			if (this.dateNOS!=null) {
				this.dateCollected = this.dateNOS;
			}
		} else {
			if (this.dateNOS==null || this.dateNOS.length()==0) {
			    this.dateNOS = this.dateCollected;
			}
		}

	}

	/**
	 * Boolean test to see if this record is in state OCR.
	 *
	 * @see  WorkFlowStatus.STAGE_0
	 *
	 * @return true if workFlowStatus equals WorkFlowStatus.STAGE_0
	 */
	public boolean isStateOCR() {
		boolean result = false;
		if (this.workFlowStatus.equals(WorkFlowStatus.STAGE_0)) {
			result = true;
		}
		return result;
	}

	public boolean isStateTaxonEntered() {
		boolean result = false;
		if (this.workFlowStatus.equals(WorkFlowStatus.STAGE_1)) {
			result = true;
		}
		return result;
	}
	
	public boolean isStateVerbatim() { 
		boolean result = false;
		if (this.workFlowStatus.equals(WorkFlowStatus.STAGE_VERBATIM)) { 
			result = true;
		}
		return result;
	}
	
	public boolean isStateVerbatimClassified() { 
		boolean result = false;
		if (this.workFlowStatus.equals(WorkFlowStatus.STAGE_CLASSIFIED)) { 
			result = true;
		}
		return result;
	}	

	public boolean isStateTextEntered() {
		boolean result = false;
		if (this.workFlowStatus.equals(WorkFlowStatus.STAGE_2)) {
			result = true;
		}
		return result;
	}

	public boolean isStateQualityProblems() {
		boolean result = false;
		if (this.workFlowStatus.equals(WorkFlowStatus.STAGE_QC_FAIL)) {
			result = true;
		}
		return result;
	}

	public boolean isStateQualityReviewed() {
		boolean result = false;
		if (this.workFlowStatus.equals(WorkFlowStatus.STAGE_QC_PASS)) {
			result = true;
		}
		return result;
	}

	public boolean isStateSpecialistReviewed() {
		boolean result = false;
		if (this.workFlowStatus.equals(WorkFlowStatus.STAGE_CLEAN)) {
			result = true;
		}
		return result;
	}

	public boolean isStateNotDoneMigrated() { 
		return !isStateDoneMigrated();
	}
	
	public boolean isStateDoneMigrated() {
		boolean result = false;
		if (this.workFlowStatus.equals(WorkFlowStatus.STAGE_DONE)) {
			result = true;
		}
		return result;
	}

	public boolean isImageCollectionNonNull() {
		boolean result = false;
		if (this.imageCollection==null) {
		    logger.log(Level.INFO, "Imagecollection is null");
		} else {
		    logger.log(Level.INFO, "Imagecollection size:" + this.imageCollection.size());
		}
		if (this.imageCollection!=null & !this.imageCollection.isEmpty()) {
			result = true;
		}
		return result;
	}
	
	public String getFirstImageFilename() { 
		String result = "";
		if (this.imageCollection!=null) { 
			Iterator<Image> i =imageCollection.iterator();
			if (i.hasNext()) { 
				result = i.next().getFilename();
			}
		}
		return result;
	}
	
	public String getFirstImagePath() { 
		String result = "";
		if (this.imageCollection!=null) { 
			Iterator<Image> i =imageCollection.iterator();
			if (i.hasNext()) { 
				result = i.next().getPath();
			}
		}
		return result;
	}	

	/**
	 * Add a new default specimen part to this specimen.
	 */
	public void addSpecimenPart() { 
		SpecimenPart newPart = new SpecimenPart();
		this.partCollection.add(newPart);
	}
	
	/**
	 * Add a new specimen part with values appropriate for 
	 * one pinned insect to this specimen.
	 * 
	 */
	public void addPinnedInsect() { 
		SpecimenPart newPart = new SpecimenPart();
		newPart.setPartName("whole animal");
		newPart.setPreserveMethod("pinned");
		newPart.setLotCount(1);
		newPart.setSpecimenId(this);
		this.partCollection.add(newPart);
	}



}
