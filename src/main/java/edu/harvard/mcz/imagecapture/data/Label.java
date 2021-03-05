/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 *
 * @author mole
 */
@Entity
@Table(name = "Label")
@NamedQueries({
	@NamedQuery(name = "Label.findAll", query = "SELECT l FROM Label l"),
	@NamedQuery(name = "Label.findByLabelid", query = "SELECT l FROM Label l WHERE l.labelid = :labelid"),
	@NamedQuery(name = "Label.findByImageid", query = "SELECT l FROM Label l WHERE l.imageid = :imageid"),
	@NamedQuery(name = "Label.findByOffsettop", query = "SELECT l FROM Label l WHERE l.offsettop = :offsettop"),
	@NamedQuery(name = "Label.findByOffsetleft", query = "SELECT l FROM Label l WHERE l.offsetleft = :offsetleft"),
	@NamedQuery(name = "Label.findByWidth", query = "SELECT l FROM Label l WHERE l.width = :width"),
	@NamedQuery(name = "Label.findByHeight", query = "SELECT l FROM Label l WHERE l.height = :height"),
	@NamedQuery(name = "Label.findByVerbatimtext", query = "SELECT l FROM Label l WHERE l.verbatimtext = :verbatimtext")})
public class Label implements Serializable {
	private static final long serialVersionUID = 2921393084576382972L;

	@Id
    @GeneratedValue(generator="LabelSeq")
    @SequenceGenerator(name="LabelSeq",sequenceName="LABEL_SEQ", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "labelid")
	private Long labelid;
	@JoinColumn(name = "ImageId", referencedColumnName = "ImageId")
	@ManyToOne
	private Image imageid;
	@Column(name = "offsettop")
	private Integer offsettop;
	@Column(name = "offsetleft")
	private Integer offsetleft;
	@Column(name = "width")
	private Integer width;
	@Column(name = "height")
	private Integer height;
	@Column(name = "verbatimtext")
	private String verbatimtext;
	@Lob
    @Column(name = "interpretation")
	private String interpretation;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "labelid", fetch=FetchType.EAGER)
    private Collection<LabelTag> labelTagCollection;

	public Label() {
	}

	public Label(Long labelid) {
		this.labelid = labelid;
	}

	public Label(Long labelid, Image imageid) {
		this.labelid = labelid;
		this.imageid = imageid;
	}

	public Long getLabelid() {
		return labelid;
	}

	public void setLabelid(Long labelid) {
		this.labelid = labelid;
	}

	public Image getImageid() {
		return imageid;
	}

	public void setImageid(Image imageid) {
		this.imageid = imageid;
	}

	public Integer getOffsettop() {
		return offsettop;
	}

	public void setOffsettop(Integer offsettop) {
		this.offsettop = offsettop;
	}

	public Integer getOffsetleft() {
		return offsetleft;
	}

	public void setOffsetleft(Integer offsetleft) {
		this.offsetleft = offsetleft;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getVerbatimtext() {
		return verbatimtext;
	}

	public void setVerbatimtext(String verbatimtext) {
		this.verbatimtext = verbatimtext;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	public Collection<LabelTag> getLabelTagCollection() {
		return labelTagCollection;
	}

	public void setLabelTagCollection(Collection<LabelTag> labelTagCollection) {
		this.labelTagCollection = labelTagCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (labelid != null ? labelid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Label)) {
			return false;
		}
		Label other = (Label) object;
		if ((this.labelid == null && other.labelid != null) || (this.labelid != null && !this.labelid.equals(other.labelid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "edu.harvard.mcz.imagecapture.data.Label[labelid=" + labelid + "]";
	}

}
