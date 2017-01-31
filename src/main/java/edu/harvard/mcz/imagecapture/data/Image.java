/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.mcz.imagecapture.data;

import edu.harvard.mcz.imagecapture.ImageCaptureProperties;
import edu.harvard.mcz.imagecapture.PositionTemplate;
import edu.harvard.mcz.imagecapture.exceptions.NoSuchTemplateException;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**  Proxy object for table holding image metadata, including location of image file on filesystem.
 *
 * @author mole
 */
@Entity
@Table(name = "Image", catalog = "lepidoptera", schema = "")
@NamedQueries({
	@NamedQuery(name = "Image.findAll1", query = "SELECT i FROM Image i"),
	@NamedQuery(name = "Image.findByImageId", query = "SELECT i FROM Image i WHERE i.imageId = :imageId"),
	@NamedQuery(name = "Image.findNullBarcodes", query = "SELECT i FROM Image i where i.rawBarcode is null and i.rawExifBarcode is null"),
	@NamedQuery(name = "Image.findByTemplate", query = "SELECT i FROM Image i where i.templateId = :templateId"),
	@NamedQuery(name = "Image.findByTemplateId", query = "SELECT i FROM Image i where i.templateId = :templateId")
})
/*
,
@NamedQuery(name = "Image.findByRawBarcode", query = "SELECT i FROM Image i WHERE i.rawBarcode = :rawBarcode"),
@NamedQuery(name = "Image.findByFilename", query = "SELECT i FROM Image i WHERE i.filename = :filename"),
@NamedQuery(name = "Image.findByPath", query = "SELECT i FROM Image i WHERE i.path = :path"),
@NamedQuery(name = "Image.findByUri", query = "SELECT i FROM Image i WHERE i.uri = :uri"),
@NamedQuery(name = "Image.findByRawExifBarcode", query = "SELECT i FROM Image i WHERE i.rawExifBarcode = :rawExifBarcode"),
@NamedQuery(name = "Image.findByTemplateId", query = "SELECT i FROM Image i WHERE i.templateId = :templateId"),
@NamedQuery(name = "Image.findByDrawernumber", query = "SELECT i FROM Image i WHERE i.drawernumber = :drawernumber")})
 * 
 */
public class Image implements Serializable {
	
    private final static Logger logger = Logger.getLogger(Image.class.getName());
    
    public static final String BLANKPATHFILTER = "[any]";

	private static final long serialVersionUID = -1103043397967995028L;
	@Id
	@GeneratedValue(generator = "ImageSeq")
	@SequenceGenerator(name = "ImageSeq", sequenceName = "SEQ_IMAGEID", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "ImageId", nullable = false)
	private Long imageId;
	@Column(name = "RawBarcode", length = 255)
	private String rawBarcode;
	@Column(name = "Filename", length = 255)
	private String filename;
	@Lob
	@Column(name = "RawOCR", length = 65535)
	private String rawOCR;
	@Column(name = "Path", length = 900)
	private String path;
	@Column(name = "URI", length = 255)
	private String uri;
	@Column(name = "RawExifBarcode", length = 255)
	private String rawExifBarcode;
	// Note: Can't simply make this a @ManyToOne, as some templates are hard coded.
	@Column(name = "TemplateId", length = 255)
	private String templateId;
	@Column(name = "DRAWERNUMBER", length = 10)
	private String drawernumber;
	@Column(name = "MD5SUM", length = 900) 
	private String md5sum;
	@JoinColumn(name = "SpecimenId", referencedColumnName = "SpecimenId")
	@ManyToOne
	private Specimen specimenId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "imageid", fetch=FetchType.EAGER)
    private Collection<Label> labelCollection;

	public Image() {
	}

	public Image(Long imageId) {
		this.imageId = imageId;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getRawBarcode() {
		return rawBarcode;
	}

	public void setRawBarcode(String rawBarcode) {
		this.rawBarcode = rawBarcode;
	    if (this.rawBarcode!=null && this.rawBarcode.length() > MetadataRetriever.getFieldLength(Image.class, "rawBarcode")) {
	        this.rawBarcode = this.rawBarcode.substring(0, MetadataRetriever.getFieldLength(Image.class, "rawBarcode"));
	    }
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRawOCR() {
		return rawOCR;
	}

	public void setRawOCR(String rawOCR) {
		this.rawOCR = rawOCR;
	    if (this.rawOCR!=null && this.rawOCR.length() > MetadataRetriever.getFieldLength(Image.class, "rawOCR")) {
	        this.rawOCR = this.rawOCR.substring(0, MetadataRetriever.getFieldLength(Image.class, "rawOCR"));
	    }
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRawExifBarcode() {
		return rawExifBarcode;
	}

	public void setRawExifBarcode(String rawExifBarcode) {
		this.rawExifBarcode = rawExifBarcode;
	    if (this.rawExifBarcode!=null && this.rawExifBarcode.length() > MetadataRetriever.getFieldLength(Image.class, "rawExifBarcode")) {
	        this.rawExifBarcode = this.rawExifBarcode.substring(0, MetadataRetriever.getFieldLength(Image.class, "rawExifBarcode"));
	    }
	}

	/**
	 * Get the PositionTemplate for the templateId of this Image.
	 * 
	 * @return a PositionTemplate for the template indicated
	 * by the templateId of this Image, or null if no such template is 
	 * found.
	 */
	public PositionTemplate getPositionTemplate() {
		PositionTemplate positionTemplate = null;
		if (templateId != null) { 
			 return null; 
		} else { 
			try {
				positionTemplate = new PositionTemplate(templateId);
			} catch (NoSuchTemplateException e) {
				e.printStackTrace();
				logger.log(Level.SEVERE, e.getMessage());
			}
		}
		return positionTemplate;
	}
	
	public String getTemplateId() {
		return templateId;
	}	

	public void setTemplateId(String templateId) {
		logger.log(Level.INFO, templateId);
		if (templateId!=null && templateId.length()>0) {
			// don't allow setting of a templateId to null/blank
		    this.templateId = templateId;
		}
	}

	public String getDrawernumber() {
		return drawernumber;
	}

	public void setDrawernumber(String drawerNumber) {
		this.drawernumber = drawerNumber;
	    if (this.drawernumber!=null && this.drawernumber.length() > MetadataRetriever.getFieldLength(Image.class, "drawerNumber")) {
	        this.drawernumber = this.drawernumber.substring(0, MetadataRetriever.getFieldLength(Image.class, "drawerNumber"));
	    }
	}

	public Specimen getSpecimenId() {
		return specimenId;
	}

	public void setSpecimenId(Specimen specimenId) {
		this.specimenId = specimenId;
	}

	public Collection<Label> getLabelCollection() {
		return labelCollection;
	}

	public void setLabelCollection(Collection<Label> labelCollection) {
		this.labelCollection = (Collection<Label>)labelCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (imageId != null ? imageId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Image)) {
			return false;
		}
		Image other = (Image) object;
		if ((this.imageId == null && other.imageId != null) || (this.imageId != null && !this.imageId.equals(other.imageId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "edu.harvard.mcz.imagecapture.data.Image[imageId=" + imageId + "]";
	}

//	public static String getPathToImageBase() {
//		return imagePath;
//	}

	public String fileStatus() {
		String result = "OK";
		String targetFilename = ImageCaptureProperties.assemblePathWithBase(getPath(), getFilename());
		Logger.getLogger(Image.class.getName()).log(Level.INFO, "Checking File Status of: " + targetFilename);
		File file = new File(targetFilename);
		if (file.exists()) {
			if (!file.canRead()) {
		        Logger.getLogger(Image.class.getName()).log(Level.WARNING, "Can't read: " + targetFilename);
				result = "Can't read file.";
			}
		} else {
			result = "File not found.";
		    Logger.getLogger(Image.class.getName()).log(Level.WARNING, "Can't find: " + targetFilename);
		}
		return result;
	}


    /**Test if template has component parts (e.g. pin labels).
	 *
	 * @return true if image is only the whole image, false if image has a template that lets
	 * it be divided into component parts.
	 */
	public boolean isWholeImageOnly() {
		boolean result = false;
		if (templateId==null) {
			logger.log(Level.SEVERE,"Null temlateID encountered");
			// if no template is set, treat as if no component parts.
			result = true;
		} else {
		    if (templateId.equals(PositionTemplate.TEMPLATE_NO_COMPONENT_PARTS)) {
			    result = true;
		    }
		}
		return result;
	}

	public boolean isFileReadable() {
		boolean result = false;
		String filename = ImageCaptureProperties.assemblePathWithBase(this.getPath(), this.getFilename());
	    File file = new File(filename);
		if (file.canRead()) {
			result = true;
		}
		return result;
	}
	
	/**
	 * Test of whether the image file exists at the expected location or not.
	 * 
	 * @return true if the base/pathbelowbase/filename for this image in the local configuration for base exists.
	 */
	public boolean isFileExists() {
		boolean result = false;
		String filename = ImageCaptureProperties.assemblePathWithBase(this.getPath(), this.getFilename());
	    File file = new File(filename);
		if (file.exists()) {
			result = true;
		}
		return result;
	}	
	
	/**
	 * @return the md5sum
	 */
	public String getMd5sum() {
		return md5sum;
	}

	/**
	 * @param md5sum the md5sum to set
	 */
	public void setMd5sum(String md5sum) {
		this.md5sum = md5sum;
	}	
	
}
