/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "Collector", catalog = "lepidoptera", schema = "")
@NamedQueries({
    @NamedQuery(name = "Collector.findAll", query = "SELECT c FROM Collector c"),
    @NamedQuery(name = "Collector.findByCollectorId", query = "SELECT c FROM Collector c WHERE c.collectorId = :collectorId"),
    @NamedQuery(name = "Collector.findByCollectorName", query = "SELECT c FROM Collector c WHERE c.collectorName = :collectorName")})
public class Collector implements Serializable {

    private static final long serialVersionUID = -4448447489508894793L;

    @Id
    @GeneratedValue(generator="CollectorSeq")
    @SequenceGenerator(name="CollectorSeq",sequenceName="SEQ_COLLECTORID", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "CollectorId", nullable = false)
    private Long collectorId;
    @Column(name = "CollectorName", length = 255)
    private String collectorName;
    @JoinColumn(name = "SpecimenId", referencedColumnName = "SpecimenId", nullable = false)
    @ManyToOne(optional = false)
    private Specimen specimenId;

    public Collector() {
    }

    public Collector(Long collectorId) {
        this.collectorId = collectorId;
    }

    public Collector(Specimen specimen) {
        this.specimenId = specimen;
    }

    public Collector(Specimen specimen, String collectorName) {
        this.specimenId = specimen;
        this.collectorName = collectorName;
    }

    public Long getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Long collectorId) {
        this.collectorId = collectorId;
    }

    public String getCollectorName() {
        return collectorName;
    }

    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
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
        hash += (collectorId != null ? collectorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collector)) {
            return false;
        }
        Collector other = (Collector) object;
        if ((this.collectorId == null && other.collectorId != null) || (this.collectorId != null && !this.collectorId.equals(other.collectorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.harvard.mcz.imagecapture.data.Collector1[collectorId=" + collectorId + "]";
    }

}
