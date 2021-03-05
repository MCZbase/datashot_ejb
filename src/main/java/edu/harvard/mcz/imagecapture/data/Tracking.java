/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author mole
 */
@Entity
@Table(name = "Tracking", catalog = "lepidoptera", schema = "")
@NamedQueries({
    @NamedQuery(name = "Tracking.findAll", query = "SELECT t FROM Tracking t"),
    @NamedQuery(name = "Tracking.findByTrackingId", query = "SELECT t FROM Tracking t WHERE t.trackingId = :trackingId"),
    @NamedQuery(name = "Tracking.findByUsername", query = "SELECT t FROM Tracking t WHERE t.username = :username"),
    @NamedQuery(name = "Tracking.findByEventType", query = "SELECT t FROM Tracking t WHERE t.eventType = :eventType"),
    @NamedQuery(name = "Tracking.findByEventDateTime", query = "SELECT t FROM Tracking t WHERE t.eventDateTime = :eventDateTime")})
public class Tracking implements Serializable {

    private static final long serialVersionUID = 3045144335474980280L;
    @Id
    @GeneratedValue(generator="TrackingSeq")
    @SequenceGenerator(name="TrackingSeq",sequenceName="SEQ_TRACKINGID", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "TrackingId", nullable = false)
    private Long trackingId;
    @Column(name = "USERNAME", length = 255)
    private String username;
    @Column(name = "eventType", length = 40)
    private String eventType;
    @Basic(optional = false)
    @Column(name = "eventDateTime", nullable = false, insertable=false, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDateTime;
    @JoinColumn(name = "SpecimenId", referencedColumnName = "SpecimenId", nullable = false)
    @ManyToOne(optional = false)
    private Specimen specimenId;

    public Tracking() {
    }

    public Tracking(Long trackingId) {
        this.trackingId = trackingId;
    }

    public Tracking(Specimen specimen, Date eventDateTime) {
        this.specimenId = specimen;
        if (eventDateTime == null) {
            this.eventDateTime = null;
        } else {
            this.eventDateTime = (Date) eventDateTime.clone();
        }
    }

    public Tracking(Specimen specimen, String user, String eventType,
            Date eventDateTime) {
        this.specimenId = specimen;
        this.username = user;
        this.eventType = eventType;
        if (eventDateTime == null) {
            this.eventDateTime = null;
        } else {
            this.eventDateTime = (Date) eventDateTime.clone();
        }
    }

    public Tracking(Long trackingId, Date eventDateTime) {
        this.trackingId = trackingId;
        this.eventDateTime = (Date) eventDateTime.clone();
    }

    public Long getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Long trackingId) {
        this.trackingId = trackingId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getEventDateTime() {
        Date result = this.eventDateTime;
	return result;
    }

    public void setEventDateTime(Date eventDateTime) {
        if (eventDateTime == null) {
            this.eventDateTime = null;
        } else {
            this.eventDateTime = (Date) eventDateTime.clone();
        }
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
        hash += (trackingId != null ? trackingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tracking)) {
            return false;
        }
        Tracking other = (Tracking) object;
        if ((this.trackingId == null && other.trackingId != null) || (this.trackingId != null && !this.trackingId.equals(other.trackingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.harvard.mcz.imagecapture.data.Tracking1[trackingId=" + trackingId + "]";
    }
}
