package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 *
 * @author mole
 */
@Entity (name="HigherTaxon")
@Table(name = "HIGHER_TAXON", catalog = "lepidoptera", schema = "")
@NamedQueries({
    @NamedQuery(name = "HigherTaxon.findAll", query = "SELECT h FROM HigherTaxon h"),
    @NamedQuery(name = "HigherTaxon.findByFamily", query = "SELECT h FROM HigherTaxon h WHERE h.family = :family"),
    @NamedQuery(name = "HigherTaxon.findBySubfamily", query = "SELECT h FROM HigherTaxon h WHERE h.subfamily = :subfamily"),
    @NamedQuery(name = "HigherTaxon.findByTribe", query = "SELECT h FROM HigherTaxon h WHERE h.tribe = :tribe"),
    @NamedQuery(name = "HigherTaxon.findById", query = "SELECT h FROM HigherTaxon h WHERE h.id = :id")})
public class HigherTaxon implements Serializable {

    private static final long serialVersionUID = 4766596776951667813L;

    @Basic(optional = false)
    @Column(name = "family", nullable = false, length = 255)
    private String family;
    @Column(name = "subfamily", length = 255)
    private String subfamily;
    @Column(name = "tribe", length = 255)
    private String tribe;
    @Column(name = "hasCastes")
    private int castes;
    @Id
    @GeneratedValue(generator="HigherTaxonSeq")
    @SequenceGenerator(name="HigherTaxonSeq",sequenceName="HIGHER_TAXON_SEQ", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    public HigherTaxon() {
    }

    public HigherTaxon(Integer id) {
        this.id = id;
    }

    public HigherTaxon(Integer id, String family) {
        this.id = id;
        this.family = family;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSubfamily() {
        return subfamily;
    }

    public void setSubfamily(String subfamily) {
        this.subfamily = subfamily;
    }

    public String getTribe() {
        return tribe;
    }

    public void setTribe(String tribe) {
        this.tribe = tribe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
	 * @return the castes
	 */
	public int getCastes() {
		return castes;
	}

	/**
	 * @param castes the castes to set
	 */
	public void setCastes(int castes) {
		this.castes = castes;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HigherTaxon)) {
            return false;
        }
        HigherTaxon other = (HigherTaxon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.harvard.mcz.imagecapture.data.HigherTaxon[id=" + id + "]";
    }

}
