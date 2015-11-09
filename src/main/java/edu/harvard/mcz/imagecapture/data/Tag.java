/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author mole
 */
@Entity
@Table(name = "Tag")
@NamedQueries({
	@NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
	@NamedQuery(name = "Tag.findByTagname", query = "SELECT t FROM Tag t WHERE t.tagname = :tagname"),
	@NamedQuery(name = "Tag.findByTagid", query = "SELECT t FROM Tag t WHERE t.tagid = :tagid")})
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @Basic(optional = false)
	@Column(name = "tagid")
	private Long tagid;
	@Basic(optional = false)
    @Column(name = "tagname")
	private String tagname;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tagname", fetch=FetchType.EAGER)
    private Collection<LabelTag> labelTagCollection;

	public Tag() {
	}

	public Tag(String tagname) {
		this.tagname = tagname;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
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
		hash += (tagname != null ? tagname.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Tag)) {
			return false;
		}
		Tag other = (Tag) object;
		if ((this.tagname == null && other.tagname != null) || (this.tagname != null && !this.tagname.equals(other.tagname))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "edu.harvard.mcz.imagecapture.data.Tag[tagname=" + tagname + "]";
	}

}
