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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author mole
 */
@Entity
@Table(name = "LabelTag")
@NamedQueries({
	@NamedQuery(name = "LabelTag.findAll", query = "SELECT l FROM LabelTag l"),
	@NamedQuery(name = "LabelTag.findByLabeltagid", query = "SELECT l FROM LabelTag l WHERE l.labeltagid = :labeltagid"),
	@NamedQuery(name = "LabelTag.findByLabelid", query = "SELECT l FROM LabelTag l WHERE l.labelid = :labelid"),
	@NamedQuery(name = "LabelTag.findByValue", query = "SELECT l FROM LabelTag l WHERE l.value = :value"),
	@NamedQuery(name = "LabelTag.findByTagname", query = "SELECT l FROM LabelTag l WHERE l.tagname = :tagname")})
public class LabelTag implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "labeltagid")
	private Long labeltagid;
	@JoinColumn(name = "labelid", referencedColumnName = "labelid")
	@ManyToOne
	private Label labelid;
	@Basic(optional = false)
    @Column(name = "value")
	private String value;
	@JoinColumn(name = "tagname", referencedColumnName = "tagname")
	@ManyToOne
	private Tag tagname;

	public LabelTag() {
	}

	public LabelTag(Long labeltagid) {
		this.labeltagid = labeltagid;
	}

	public LabelTag(Long labeltagid, Label labelid, String value, Tag tagname) {
		this.labeltagid = labeltagid;
		this.labelid = labelid;
		this.value = value;
		this.tagname = tagname;
	}

	public Long getLabeltagid() {
		return labeltagid;
	}

	public void setLabeltagid(Long labeltagid) {
		this.labeltagid = labeltagid;
	}

	public Label getLabelid() {
		return labelid;
	}

	public void setLabelid(Label labelid) {
		this.labelid = labelid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Tag getTagname() {
		return tagname;
	}

	public void setTagname(Tag tagname) {
		this.tagname = tagname;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (labeltagid != null ? labeltagid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof LabelTag)) {
			return false;
		}
		LabelTag other = (LabelTag) object;
		if ((this.labeltagid == null && other.labeltagid != null) || (this.labeltagid != null && !this.labeltagid.equals(other.labeltagid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "edu.harvard.mcz.imagecapture.data.LabelTag[labeltagid=" + labeltagid + "]";
	}

}
