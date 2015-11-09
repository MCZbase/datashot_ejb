package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The persistent class for the LAT_LONG database table.
 * 
 */
@Entity
@Table(name="LAT_LONG")
@NamedQuery(name="LatLong.findAll", query="SELECT l FROM LatLong l")
public class LatLong implements Serializable {
	private static final long serialVersionUID = 2593048456524846375L;
	
    private final static Logger logger = Logger.getLogger(LatLong.class.getName());
    
	@Id
	@Column(name="LAT_LONG_ID")
    @GeneratedValue(generator="LatLongSeq")
    @SequenceGenerator(name="LatLongSeq",sequenceName="SEQ_LAT_LONG", allocationSize=1)	
	private Long latLongId;

	@Column(name="ACCEPTED_LAT_LONG_FG")
	private Boolean acceptedLatLongFg = true;

	@Column(name="DATUM")
	private String datum = "unknown";

	@Column(name="DEC_LAT")
	private BigDecimal decLat;

	@Column(name="DEC_LAT_MIN")
	private BigDecimal decLatMin;

	@Column(name="DEC_LONG")
	private BigDecimal decLong;

	@Column(name="DEC_LONG_MIN")
	private BigDecimal decLongMin;

	@Column(name="DETERMINED_BY_AGENT")
	private String determinedByAgent;

	@Temporal(TemporalType.DATE)
	@Column(name="DETERMINED_DATE")
	private Date determinedDate = new Date();

	@Column(name="EXTENT")
	private BigDecimal extent;

	@Column(name="FIELD_VERIFIED_FG")
	private Boolean fieldVerifiedFg;

	@Column(name="GEOREFMETHOD")
	private String georefmethod = "unknown";

	@Column(name="GPSACCURACY")
	private BigDecimal gpsaccuracy;

	@Column(name="LAT_DEG")
	private Integer latDeg;

	@Column(name="LAT_DIR")
	private String latDir;

	@Column(name="LAT_LONG_FOR_NNP_FG")
	private Boolean latLongForNnpFg;

	@Column(name="LAT_LONG_REF_SOURCE")
	private String latLongRefSource = "unknown";

	@Column(name="LAT_LONG_REMARKS")
	private String latLongRemarks;

	@Column(name="LAT_MIN")
	private Integer latMin;

	@Column(name="LAT_SEC")
	private BigDecimal latSec;

	@Column(name="LONG_DEG")
	private Integer longDeg;

	@Column(name="LONG_DIR")
	private String longDir;

	@Column(name="LONG_MIN")
	private Integer longMin;

	@Column(name="LONG_SEC")
	private BigDecimal longSec;

	@Column(name="MAX_ERROR_DISTANCE")
	private int maxErrorDistance;

	@Column(name="MAX_ERROR_UNITS")
	private String maxErrorUnits;

	@Column(name="NEAREST_NAMED_PLACE")
	private String nearestNamedPlace;

	@Column(name="ORIG_LAT_LONG_UNITS")
	private String origLatLongUnits = "decimal degrees";
	
	/* Valid values for origLatLongUnits:
decimal degrees
deg. min. sec.
degrees dec. minutes
unknown	 
	 */
	
	public static final String UNITS_DECDEGREES = "decimal degrees";
	public static final String UNITS_DECMIN = "degrees dec. minutes";
	public static final String UNITS_DEGMINSEC = "deg. min. sec.";
	public static final String UNITS_UNKNOWN = "unknown";

    @JoinColumn(name = "SpecimenId", referencedColumnName = "SpecimenId", nullable = false)
    @OneToOne(optional = false)
	private Specimen specimenId;

	@Column(name="UTM_EW")
	private int utmEw;

	@Column(name="UTM_NS")
	private int utmNs;

	@Column(name="UTM_ZONE")
	private String utmZone;

	@Column(name="VERIFICATIONSTATUS")
	private String verificationstatus = "unknown";

	public LatLong() {
	}

	public Long getLatLongId() {
		return this.latLongId;
	}

	public void setLatLongId(Long latLongId) {
		this.latLongId = latLongId;
	}

	public Boolean getAcceptedLatLongFg() {
		return this.acceptedLatLongFg;
	}

	public void setAcceptedLatLongFg(Boolean acceptedLatLongFg) {
		this.acceptedLatLongFg = acceptedLatLongFg;
	}

	public String getDatum() {
		return this.datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

    public String getDecLatFormatted() {
    	if (decLat==null) { 
    		return "";
    	} else { 
	       return String.format("%.6f", this.decLat.floatValue());
    	}
    }
	public String getDecLongFormatted() {
		if (this.decLong==null) { 
			return "";
		} else { 
	       return String.format("%.6f", this.decLong.floatValue());
		}
	}

	public BigDecimal getDecLat() {
		return this.decLat;
	}

	public void setDecLat(BigDecimal decLat) {
		this.decLat = decLat;
	}

	public BigDecimal getDecLatMin() {
		return this.decLatMin;
	}

	public void setDecLatMin(BigDecimal decLatMin) {
		this.decLatMin = decLatMin;
		calculate();
	}

	public BigDecimal getDecLong() {
		return this.decLong;
	}

	public void setDecLong(BigDecimal decLong) {
		this.decLong = decLong;
	}

	public BigDecimal getDecLongMin() {
		return this.decLongMin;
	}

	public void setDecLongMin(BigDecimal decLongMin) {
		this.decLongMin = decLongMin;
		calculate();
	}

	public String getDeterminedByAgent() {
		return this.determinedByAgent;
	}

	public void setDeterminedByAgent(String determinedByAgent) {
		this.determinedByAgent = determinedByAgent;
	}

	public Date getDeterminedDate() {
		return this.determinedDate;
	}

	public void setDeterminedDate(Date determinedDate) {
		this.determinedDate = determinedDate;
	}

	public BigDecimal getExtent() {
		return this.extent;
	}

	public void setExtent(BigDecimal extent) {
		this.extent = extent;
	}

	public Boolean getFieldVerifiedFg() {
		return this.fieldVerifiedFg;
	}

	public void setFieldVerifiedFg(Boolean fieldVerifiedFg) {
		this.fieldVerifiedFg = fieldVerifiedFg;
	}

	public String getGeorefmethod() {
		return this.georefmethod;
	}

	public void setGeorefmethod(String georefmethod) {
		this.georefmethod = georefmethod;
	}

	public BigDecimal getGpsaccuracy() {
		return this.gpsaccuracy;
	}

	public void setGpsaccuracy(BigDecimal gpsaccuracy) {
		this.gpsaccuracy = gpsaccuracy;
	}

	public Integer getLatDeg() {
		return this.latDeg;
	}

	public void setLatDeg(Integer latDeg) {
		this.latDeg = latDeg;
		calculate();
	}

	public String getLatDir() {
		return this.latDir;
	}

	public void setLatDir(String latDir) {
		this.latDir = latDir;
		calculate();
	}

	public Boolean getLatLongForNnpFg() {
		return this.latLongForNnpFg;
	}

	public void setLatLongForNnpFg(Boolean latLongForNnpFg) {
		this.latLongForNnpFg = latLongForNnpFg;
	}

	public String getLatLongRefSource() {
		return this.latLongRefSource;
	}

	public void setLatLongRefSource(String latLongRefSource) {
		this.latLongRefSource = latLongRefSource;
	}

	public String getLatLongRemarks() {
		return this.latLongRemarks;
	}

	public void setLatLongRemarks(String latLongRemarks) {
		this.latLongRemarks = latLongRemarks;
	}

	public Integer getLatMin() {
		return this.latMin;
	}

	public void setLatMin(Integer latMin) {
		this.latMin = latMin;
		calculate();
	}

	public BigDecimal getLatSec() {
		return this.latSec;
	}

	public void setLatSec(BigDecimal latSec) {
		this.latSec = latSec;
		calculate();
	}

	public Integer getLongDeg() {
		return this.longDeg;
	}

	public void setLongDeg(Integer longDeg) {
		this.longDeg = longDeg;
		calculate();
	}

	public String getLongDir() {
		return this.longDir;
	}

	public void setLongDir(String longDir) {
		this.longDir = longDir;
		calculate();
	}

	public Integer getLongMin() {
		return this.longMin;
	}

	public void setLongMin(Integer longMin) {
		this.longMin = longMin;
		calculate();
	}

	public BigDecimal getLongSec() {
		return this.longSec;
	}

	public void setLongSec(BigDecimal longSec) {
		this.longSec = longSec;
		calculate();
	}

	public int getMaxErrorDistance() {
		return this.maxErrorDistance;
	}

	public void setMaxErrorDistance(int maxErrorDistance) {
		this.maxErrorDistance = maxErrorDistance;
	}

	public String getMaxErrorUnits() {
		if (maxErrorUnits==null || maxErrorUnits.length()==0) { 
			maxErrorUnits = "m";
		}		
		return this.maxErrorUnits;
	}

	public void setMaxErrorUnits(String maxErrorUnits) {
		this.maxErrorUnits = maxErrorUnits;
	}

	public String getNearestNamedPlace() {
		return this.nearestNamedPlace;
	}

	public void setNearestNamedPlace(String nearestNamedPlace) {
		this.nearestNamedPlace = nearestNamedPlace;
	}

	public String getOrigLatLongUnits() {
		return this.origLatLongUnits;
	}

	public void setOrigLatLongUnits(String origLatLongUnits) {
		this.origLatLongUnits = origLatLongUnits;
	}

	public Specimen getSpecimenid() {
		return this.specimenId;
	}

	public void setSpecimenid(Specimen specimenid) {
		this.specimenId = specimenid;
	}

	public int getUtmEw() {
		return this.utmEw;
	}

	public void setUtmEw(int utmEw) {
		this.utmEw = utmEw;
	}

	public int getUtmNs() {
		return this.utmNs;
	}

	public void setUtmNs(int utmNs) {
		this.utmNs = utmNs;
	}

	public String getUtmZone() {
		return this.utmZone;
	}

	public void setUtmZone(String utmZone) {
		this.utmZone = utmZone;
	}

	public String getVerificationstatus() {
		return this.verificationstatus;
	}

	public void setVerificationstatus(String verificationstatus) {
		this.verificationstatus = verificationstatus;
	}
	
	public boolean isEnableDecimalDegrees() { 
		if (this.origLatLongUnits.equals(UNITS_DECDEGREES) || this.origLatLongUnits.equals(UNITS_UNKNOWN)) { 
			return true;
		} else {
			return false;
		}
	}	
	
	public boolean isEnableDegrees() { 
		if (this.origLatLongUnits.equals(UNITS_DEGMINSEC) || this.origLatLongUnits.equals(UNITS_DECMIN)) { 
			return true;
		} else {
			return false;
		}
	}	
	
	public boolean isUnitsDecimalDegrees() { 
		if (this.origLatLongUnits.equals(UNITS_DECDEGREES)) { 
			return true;
		} else {
			return false;
		}
	}

	public boolean isUnitsDegMinSec() { 
		if (this.origLatLongUnits.equals(UNITS_DEGMINSEC)) { 
			return true;
		} else {
			return false;
		}
	}	
	
	public boolean isUnitsDecimalMin() { 
		if (this.origLatLongUnits.equals(UNITS_DECMIN)) { 
			return true;
		} else {
			return false;
		}
	}	
	
	/** 
	 * Set the values of decimal latitude and decimal longitude from 
	 * atomic lat/long fields based on origLatLongUnits
	 */
	public void calculate() { 
		BigDecimal sixty = new BigDecimal(60);
		BigDecimal thirtysixhundred = new BigDecimal(3600);
		logger.log(Level.INFO, this.origLatLongUnits);
		switch (origLatLongUnits) {
		case UNITS_DECMIN:
			if (this.latDir!=null && this.decLatMin!=null) { 
				if (latDir.equals("N")) { 
					decLat = new BigDecimal(latDeg).add(decLatMin.divide(sixty, 6, RoundingMode.HALF_UP));
				} else { 
					decLat = new BigDecimal(latDeg).add(decLatMin.divide(sixty, 6, RoundingMode.HALF_UP));
					decLat = decLat.negate();
				}
			}
			if (this.longDir!=null && this.decLongMin!=null) { 
				if (longDir.equals("W")) { 
					decLong = new BigDecimal(Math.abs(longDeg)).add(decLongMin.divide(sixty, 6, RoundingMode.HALF_UP));
				} else { 
					decLong = new BigDecimal(Math.abs(longDeg)).add(decLongMin.divide(sixty, 6, RoundingMode.HALF_UP));
					decLong = decLong.negate();
				}
			}	
			logger.log(Level.INFO, UNITS_DECMIN + " " + this.getDecLatFormatted() + " " + this.getDecLongFormatted());
		case UNITS_DEGMINSEC:
			if (this.latDir!=null) { 
				if (latDir.equals("N")) { 
					decLat = new BigDecimal(Math.abs(latDeg)).add(new BigDecimal(latMin).divide(sixty, 6, RoundingMode.HALF_UP)).add(latSec.divide(thirtysixhundred, 4, RoundingMode.HALF_UP));
				} else { 
					decLat = new BigDecimal(Math.abs(latDeg)).add(new BigDecimal(latMin).divide(sixty, 6, RoundingMode.HALF_UP)).add(latSec.divide(thirtysixhundred, 4, RoundingMode.HALF_UP));
					decLat = decLat.negate();
				}
			}
			if (this.longDir!=null) { 
				if (longDir.equals("W")) { 
					decLong = new BigDecimal(Math.abs(longDeg)).add(new BigDecimal(longMin).divide(sixty, 6, RoundingMode.HALF_UP)).add(longSec.divide(thirtysixhundred, 4, RoundingMode.HALF_UP));
					decLong = decLong.negate();
				} else { 
					decLong = new BigDecimal(Math.abs(longDeg)).add(new BigDecimal(longMin).divide(sixty, 6, RoundingMode.HALF_UP)).add(longSec.divide(thirtysixhundred, 4, RoundingMode.HALF_UP));
				}
			}			
			logger.log(Level.INFO, UNITS_DEGMINSEC + " " + this.getDecLatFormatted() + " " + this.getDecLongFormatted());
		case UNITS_DECDEGREES:
		default:
			// no action
			logger.log(Level.INFO, UNITS_DECDEGREES + " " + this.getDecLatFormatted() + " " + this.getDecLongFormatted());
			break;
		}
	}
	
}