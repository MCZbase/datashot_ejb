/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;
import java.math.BigInteger;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author mole
 */
@Entity
@Table(name = "Template", catalog = "lepidoptera", schema = "")
@NamedQueries({
    @NamedQuery(name = "Template.findAll", query = "SELECT t FROM Template t"),
    @NamedQuery(name = "Template.findByTemplateId", query = "SELECT t FROM Template t WHERE t.templateId = :templateId"),
    @NamedQuery(name = "Template.findByTemplateName", query = "SELECT t FROM Template t WHERE t.templateName = :templateName"),
    @NamedQuery(name = "Template.findByImageSizeX", query = "SELECT t FROM Template t WHERE t.imageSizeX = :imageSizeX"),
    @NamedQuery(name = "Template.findByImageSizeY", query = "SELECT t FROM Template t WHERE t.imageSizeY = :imageSizeY"),
    @NamedQuery(name = "Template.findByBarcodePositionX", query = "SELECT t FROM Template t WHERE t.barcodePositionX = :barcodePositionX"),
    @NamedQuery(name = "Template.findByBarcodePositionY", query = "SELECT t FROM Template t WHERE t.barcodePositionY = :barcodePositionY"),
    @NamedQuery(name = "Template.findByBarcodeSizeX", query = "SELECT t FROM Template t WHERE t.barcodeSizeX = :barcodeSizeX"),
    @NamedQuery(name = "Template.findByBarcodeSizeY", query = "SELECT t FROM Template t WHERE t.barcodeSizeY = :barcodeSizeY"),
    @NamedQuery(name = "Template.findBySpecimenPositionX", query = "SELECT t FROM Template t WHERE t.specimenPositionX = :specimenPositionX"),
    @NamedQuery(name = "Template.findBySpecimenPositionY", query = "SELECT t FROM Template t WHERE t.specimenPositionY = :specimenPositionY"),
    @NamedQuery(name = "Template.findBySpecimenSizeX", query = "SELECT t FROM Template t WHERE t.specimenSizeX = :specimenSizeX"),
    @NamedQuery(name = "Template.findBySpecimenSizeY", query = "SELECT t FROM Template t WHERE t.specimenSizeY = :specimenSizeY"),
    @NamedQuery(name = "Template.findByTextPositionX", query = "SELECT t FROM Template t WHERE t.textPositionX = :textPositionX"),
    @NamedQuery(name = "Template.findByTextPositionY", query = "SELECT t FROM Template t WHERE t.textPositionY = :textPositionY"),
    @NamedQuery(name = "Template.findByTextSizeX", query = "SELECT t FROM Template t WHERE t.textSizeX = :textSizeX"),
    @NamedQuery(name = "Template.findByTextSizeY", query = "SELECT t FROM Template t WHERE t.textSizeY = :textSizeY"),
    @NamedQuery(name = "Template.findByLabelPositionX", query = "SELECT t FROM Template t WHERE t.labelPositionX = :labelPositionX"),
    @NamedQuery(name = "Template.findByLabelPositionY", query = "SELECT t FROM Template t WHERE t.labelPositionY = :labelPositionY"),
    @NamedQuery(name = "Template.findByLabelSizeX", query = "SELECT t FROM Template t WHERE t.labelSizeX = :labelSizeX"),
    @NamedQuery(name = "Template.findByLabelSizeY", query = "SELECT t FROM Template t WHERE t.labelSizeY = :labelSizeY"),
    @NamedQuery(name = "Template.findByUtLabelPositionX", query = "SELECT t FROM Template t WHERE t.utLabelPositionX = :utLabelPositionX"),
    @NamedQuery(name = "Template.findByUtLabelPositionY", query = "SELECT t FROM Template t WHERE t.utLabelPositionY = :utLabelPositionY"),
    @NamedQuery(name = "Template.findByUtLabelSizeX", query = "SELECT t FROM Template t WHERE t.utLabelSizeX = :utLabelSizeX"),
    @NamedQuery(name = "Template.findByUtLabelSizeY", query = "SELECT t FROM Template t WHERE t.utLabelSizey = :utLabelSizey"),
    @NamedQuery(name = "Template.findByEditable", query = "SELECT t FROM Template t WHERE t.editable = :editable"),
    @NamedQuery(name = "Template.findByReferenceImage", query = "SELECT t FROM Template t WHERE t.referenceImage = :referenceImage"),
    @NamedQuery(name = "Template.findByUtBarcodePositionX", query = "SELECT t FROM Template t WHERE t.utBarcodePositionX = :utBarcodePositionX"),
    @NamedQuery(name = "Template.findByUtBarcodePositionY", query = "SELECT t FROM Template t WHERE t.utBarcodePositionY = :utBarcodePositionY"),
    @NamedQuery(name = "Template.findByUtBarcodeSizeX", query = "SELECT t FROM Template t WHERE t.utBarcodeSizeX = :utBarcodeSizeX"),
    @NamedQuery(name = "Template.findByUtBarcodeSizeY", query = "SELECT t FROM Template t WHERE t.utBarcodeSizeY = :utBarcodeSizeY")})
public class Template implements Serializable {

    private static final long serialVersionUID = -1561662901244505199L;

    @Id
    @Basic(optional = false)
    @Column(name = "TemplateId", nullable = false, length = 50)
    private String templateId;
    @Column(name = "Template_Name", length = 255)
    private String templateName;
    @Column(name = "ImageSizeX")
    private Integer imageSizeX;
    @Column(name = "ImageSizeY")
    private Integer imageSizeY;
    @Column(name = "BarcodePositionX")
    private Integer barcodePositionX;
    @Column(name = "BarcodePositionY")
    private Integer barcodePositionY;
    @Column(name = "BarcodeSizeX")
    private Integer barcodeSizeX;
    @Column(name = "BarcodeSizeY")
    private Integer barcodeSizeY;
    @Column(name = "SpecimenPositionX")
    private Integer specimenPositionX;
    @Column(name = "SpecimenPositionY")
    private Integer specimenPositionY;
    @Column(name = "SpecimenSizeX")
    private Integer specimenSizeX;
    @Column(name = "SpecimenSizeY")
    private Integer specimenSizeY;
    @Column(name = "TextPositionX")
    private Integer textPositionX;
    @Column(name = "TextPositionY")
    private Integer textPositionY;
    @Column(name = "TextSizeX")
    private Integer textSizeX;
    @Column(name = "TextSizeY")
    private Integer textSizeY;
    @Column(name = "LabelPositionX")
    private Integer labelPositionX;
    @Column(name = "LabelPositionY")
    private Integer labelPositionY;
    @Column(name = "LabelSizeX")
    private Integer labelSizeX;
    @Column(name = "LabelSizeY")
    private Integer labelSizeY;
    @Column(name = "UtLabelPositionX")
    private Integer utLabelPositionX;
    @Column(name = "UtLabelPositionY")
    private Integer utLabelPositionY;
    @Column(name = "UtLabelSizeX")
    private Integer utLabelSizeX;
    @Column(name = "UtLabelSizey")
    private Integer utLabelSizey;
    @Basic(optional = false)
    @Column(name = "Editable", nullable = false)
    private boolean editable;
    @Column(name = "ReferenceImage")
    private BigInteger referenceImage;
    @Column(name = "UtBarcodePositionX")
    private Integer utBarcodePositionX;
    @Column(name = "UtBarcodePositionY")
    private Integer utBarcodePositionY;
    @Column(name = "UtBarcodeSizeX")
    private Integer utBarcodeSizeX;
    @Column(name = "UtBarcodeSizeY")
    private Integer utBarcodeSizeY;

    public Template() {
    }

    public Template(String templateId) {
        this.templateId = templateId;
    }

    public Template(String templateId, boolean editable) {
        this.templateId = templateId;
        this.editable = editable;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getImageSizeX() {
        return imageSizeX;
    }

    public void setImageSizeX(Integer imageSizeX) {
        this.imageSizeX = imageSizeX;
    }

    public Integer getImageSizeY() {
        return imageSizeY;
    }

    public void setImageSizeY(Integer imageSizeY) {
        this.imageSizeY = imageSizeY;
    }

    public Integer getBarcodePositionX() {
        return barcodePositionX;
    }

    public void setBarcodePositionX(Integer barcodePositionX) {
        this.barcodePositionX = barcodePositionX;
    }

    public Integer getBarcodePositionY() {
        return barcodePositionY;
    }

    public void setBarcodePositionY(Integer barcodePositionY) {
        this.barcodePositionY = barcodePositionY;
    }

    public Integer getBarcodeSizeX() {
        return barcodeSizeX;
    }

    public void setBarcodeSizeX(Integer barcodeSizeX) {
        this.barcodeSizeX = barcodeSizeX;
    }

    public Integer getBarcodeSizeY() {
        return barcodeSizeY;
    }

    public void setBarcodeSizeY(Integer barcodeSizeY) {
        this.barcodeSizeY = barcodeSizeY;
    }

    public Integer getSpecimenPositionX() {
        return specimenPositionX;
    }

    public void setSpecimenPositionX(Integer specimenPositionX) {
        this.specimenPositionX = specimenPositionX;
    }

    public Integer getSpecimenPositionY() {
        return specimenPositionY;
    }

    public void setSpecimenPositionY(Integer specimenPositionY) {
        this.specimenPositionY = specimenPositionY;
    }

    public Integer getSpecimenSizeX() {
        return specimenSizeX;
    }

    public void setSpecimenSizeX(Integer specimenSizeX) {
        this.specimenSizeX = specimenSizeX;
    }

    public Integer getSpecimenSizeY() {
        return specimenSizeY;
    }

    public void setSpecimenSizeY(Integer specimenSizeY) {
        this.specimenSizeY = specimenSizeY;
    }

    public Integer getTextPositionX() {
        return textPositionX;
    }

    public void setTextPositionX(Integer textPositionX) {
        this.textPositionX = textPositionX;
    }

    public Integer getTextPositionY() {
        return textPositionY;
    }

    public void setTextPositionY(Integer textPositionY) {
        this.textPositionY = textPositionY;
    }

    public Integer getTextSizeX() {
        return textSizeX;
    }

    public void setTextSizeX(Integer textSizeX) {
        this.textSizeX = textSizeX;
    }

    public Integer getTextSizeY() {
        return textSizeY;
    }

    public void setTextSizeY(Integer textSizeY) {
        this.textSizeY = textSizeY;
    }

    public Integer getLabelPositionX() {
        return labelPositionX;
    }

    public void setLabelPositionX(Integer labelPositionX) {
        this.labelPositionX = labelPositionX;
    }

    public Integer getLabelPositionY() {
        return labelPositionY;
    }

    public void setLabelPositionY(Integer labelPositionY) {
        this.labelPositionY = labelPositionY;
    }

    public Integer getLabelSizeX() {
        return labelSizeX;
    }

    public void setLabelSizeX(Integer labelSizeX) {
        this.labelSizeX = labelSizeX;
    }

    public Integer getLabelSizeY() {
        return labelSizeY;
    }

    public void setLabelSizeY(Integer labelSizeY) {
        this.labelSizeY = labelSizeY;
    }

    public Integer getUtLabelPositionX() {
        return utLabelPositionX;
    }

    public void setUtLabelPositionX(Integer utLabelPositionX) {
        this.utLabelPositionX = utLabelPositionX;
    }

    public Integer getUtLabelPositionY() {
        return utLabelPositionY;
    }

    public void setUtLabelPositionY(Integer utLabelPositionY) {
        this.utLabelPositionY = utLabelPositionY;
    }

    public Integer getUtLabelSizeX() {
        return utLabelSizeX;
    }

    public void setUtLabelSizeX(Integer utLabelSizeX) {
        this.utLabelSizeX = utLabelSizeX;
    }

    public Integer getUtLabelSizey() {
        return utLabelSizey;
    }

    public void setUtLabelSizey(Integer utLabelSizey) {
        this.utLabelSizey = utLabelSizey;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public BigInteger getReferenceImage() {
        return referenceImage;
    }

    public void setReferenceImage(BigInteger referenceImage) {
        this.referenceImage = referenceImage;
    }

    public Integer getUtBarcodePositionX() {
        return utBarcodePositionX;
    }

    public void setUtBarcodePositionX(Integer utBarcodePositionX) {
        this.utBarcodePositionX = utBarcodePositionX;
    }

    public Integer getUtBarcodePositionY() {
        return utBarcodePositionY;
    }

    public void setUtBarcodePositionY(Integer utBarcodePositionY) {
        this.utBarcodePositionY = utBarcodePositionY;
    }

    public Integer getUtBarcodeSizeX() {
        return utBarcodeSizeX;
    }

    public void setUtBarcodeSizeX(Integer utBarcodeSizeX) {
        this.utBarcodeSizeX = utBarcodeSizeX;
    }

    public Integer getUtBarcodeSizeY() {
        return utBarcodeSizeY;
    }

    public void setUtBarcodeSizeY(Integer utBarcodeSizeY) {
        this.utBarcodeSizeY = utBarcodeSizeY;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (templateId != null ? templateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Template)) {
            return false;
        }
        Template other = (Template) object;
        if ((this.templateId == null && other.templateId != null) || (this.templateId != null && !this.templateId.equals(other.templateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.harvard.mcz.imagecapture.data.Template[templateId=" + templateId + "]";
    }

}
