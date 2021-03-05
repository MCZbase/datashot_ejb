/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;
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

/**
 *
 * @author mole
 */
@Entity
@Table(name = "OTHER_NUMBERS", catalog = "lepidoptera", schema = "")
@NamedQueries({
    @NamedQuery(name = "OtherNumbers.findAll", query = "SELECT o FROM OtherNumbers o"),
    @NamedQuery(name = "OtherNumbers.findByNumberId", query = "SELECT o FROM OtherNumbers o WHERE o.numberId = :numberId"),
    @NamedQuery(name = "OtherNumbers.findByOtherNumber", query = "SELECT o FROM OtherNumbers o WHERE o.otherNumber = :otherNumber"),
    @NamedQuery(name = "OtherNumbers.findByNumberType", query = "SELECT o FROM OtherNumbers o WHERE o.numberType = :numberType")
})
public class OtherNumbers implements Serializable {
    private static final long serialVersionUID = -7325097435841353006L;

    @Id
    @GeneratedValue(generator="NumberSeq")
    @SequenceGenerator(name="NumberSeq",sequenceName="SEQ_NUMBERID", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "NumberId", nullable = false)
    private Long numberId;
    @Column(name = "OTHER_NUMBER", length = 50)
    private String otherNumber;
    @Column(name = "NumberType", length = 50)
    private String numberType;
    @JoinColumn(name = "SpecimenId", referencedColumnName = "SpecimenId", nullable = false)
    @ManyToOne(optional = false)
    private Specimen specimenId;

    public OtherNumbers() {
    }

    public OtherNumbers(Long numberId) {
        this.numberId = numberId;
    }

    public Long getNumberId() {
        return numberId;
    }

    public void setNumberId(Long numberId) {
        this.numberId = numberId;
    }

    public String getOtherNumber() {
        return otherNumber;
    }

    public void setOtherNumber(String otherNumber) {
        this.otherNumber = otherNumber;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
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
        hash += (numberId != null ? numberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtherNumbers)) {
            return false;
        }
        OtherNumbers other = (OtherNumbers) object;
        if ((this.numberId == null && other.numberId != null) || (this.numberId != null && !this.numberId.equals(other.numberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.harvard.mcz.imagecapture.data.OtherNumbers[numberId=" + numberId + "]";
    }

}
