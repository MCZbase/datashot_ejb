/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.data;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "UNIT_TRAY_LABEL", catalog = "lepidoptera", schema = "")
@NamedQueries({
    @NamedQuery(name = "UnitTrayLabel.findAll", query = "SELECT u FROM UnitTrayLabel u"),
    @NamedQuery(name = "UnitTrayLabel.findById", query = "SELECT u FROM UnitTrayLabel u WHERE u.id = :id"),
    @NamedQuery(name = "UnitTrayLabel.findByFamily", query = "SELECT u FROM UnitTrayLabel u WHERE u.family = :family"),
    @NamedQuery(name = "UnitTrayLabel.findBySubfamily", query = "SELECT u FROM UnitTrayLabel u WHERE u.subfamily = :subfamily"),
    @NamedQuery(name = "UnitTrayLabel.findByTribe", query = "SELECT u FROM UnitTrayLabel u WHERE u.tribe = :tribe"),
    @NamedQuery(name = "UnitTrayLabel.findByGenus", query = "SELECT u FROM UnitTrayLabel u WHERE u.genus = :genus"),
    @NamedQuery(name = "UnitTrayLabel.findBySpecificEpithet", query = "SELECT u FROM UnitTrayLabel u WHERE u.specificEpithet = :specificEpithet"),
    @NamedQuery(name = "UnitTrayLabel.findBySubspecificEpithet", query = "SELECT u FROM UnitTrayLabel u WHERE u.subspecificEpithet = :subspecificEpithet"),
    @NamedQuery(name = "UnitTrayLabel.findByInfraspecificEpithet", query = "SELECT u FROM UnitTrayLabel u WHERE u.infraspecificEpithet = :infraspecificEpithet"),
    @NamedQuery(name = "UnitTrayLabel.findByInfraspecificRank", query = "SELECT u FROM UnitTrayLabel u WHERE u.infraspecificRank = :infraspecificRank"),
    @NamedQuery(name = "UnitTrayLabel.findByAuthorship", query = "SELECT u FROM UnitTrayLabel u WHERE u.authorship = :authorship"),
    @NamedQuery(name = "UnitTrayLabel.findByDrawerNumber", query = "SELECT u FROM UnitTrayLabel u WHERE u.drawerNumber = :drawerNumber"),
    @NamedQuery(name = "UnitTrayLabel.findByUnNamedForm", query = "SELECT u FROM UnitTrayLabel u WHERE u.unNamedForm = :unNamedForm"),
    @NamedQuery(name = "UnitTrayLabel.findByPrinted", query = "SELECT u FROM UnitTrayLabel u WHERE u.printed = :printed"),
    @NamedQuery(name = "UnitTrayLabel.findByNumberToPrint", query = "SELECT u FROM UnitTrayLabel u WHERE u.numberToPrint = :numberToPrint"),
    @NamedQuery(name = "UnitTrayLabel.findByDateCreated", query = "SELECT u FROM UnitTrayLabel u WHERE u.dateCreated = :dateCreated"),
    @NamedQuery(name = "UnitTrayLabel.findByDateLastUpdated", query = "SELECT u FROM UnitTrayLabel u WHERE u.dateLastUpdated = :dateLastUpdated")})
public class UnitTrayLabel implements Serializable {

    private static final long serialVersionUID = -3323580869225673104L;

    @Id
    @GeneratedValue(generator="UnitTrayLabelSeq")
    @SequenceGenerator(name="UnitTrayLabelSeq",sequenceName="UNIT_TRAY_LABEL_ID_SEQ", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "Family", length = 40)
    private String family;
    @Column(name = "Subfamily", length = 40)
    private String subfamily;
    @Column(name = "Tribe", length = 40)
    private String tribe;
    @Column(name = "Genus", length = 40)
    private String genus;
    @Column(name = "SpecificEpithet", length = 40)
    private String specificEpithet;
    @Column(name = "SubspecificEpithet", length = 255)
    private String subspecificEpithet;
    @Column(name = "InfraspecificEpithet", length = 40)
    private String infraspecificEpithet;
    @Column(name = "InfraspecificRank", length = 40)
    private String infraspecificRank;
    @Column(name = "Authorship", length = 255)
    private String authorship;
    @Column(name = "DrawerNumber", length = 10)
    private String drawerNumber;
    @Column(name = "UnNamedForm", length = 40)
    private String unNamedForm;
    @Basic(optional = false)
    @Column(name = "Printed", nullable = false)
    private int printed;
    @Basic(optional = false)
    @Column(name = "NumberToPrint", nullable = false)
    private int numberToPrint;
    @Basic(optional = false)
    @Column(name = "DateCreated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "DateLastUpdated")
    @Temporal(TemporalType.DATE)
    private Date dateLastUpdated;
    @Column(name = "Collection", length = 255)
    private String collection;  // collection from which the material came
    @Column(name = "ordinal", length = 10)
    private Integer ordinal;     // order in which to print


    public UnitTrayLabel() {
    }

    public UnitTrayLabel(Integer id) {
        this.id = id;
    }

    public UnitTrayLabel(Integer id, int printed, int numberToPrint, Date dateCreated) {
        this.id = id;
        this.printed = printed;
        this.numberToPrint = numberToPrint;
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecificEpithet() {
        return specificEpithet;
    }

    public void setSpecificEpithet(String specificEpithet) {
        this.specificEpithet = specificEpithet;
    }

    public String getSubspecificEpithet() {
        return subspecificEpithet;
    }

    public void setSubspecificEpithet(String subspecificEpithet) {
        this.subspecificEpithet = subspecificEpithet;
    }

    public String getInfraspecificEpithet() {
        return infraspecificEpithet;
    }

    public void setInfraspecificEpithet(String infraspecificEpithet) {
        this.infraspecificEpithet = infraspecificEpithet;
    }

    public String getInfraspecificRank() {
        return infraspecificRank;
    }

    public void setInfraspecificRank(String infraspecificRank) {
        this.infraspecificRank = infraspecificRank;
    }

    public String getAuthorship() {
        return authorship;
    }

    public void setAuthorship(String authorship) {
        this.authorship = authorship;
    }

    public String getDrawerNumber() {
        return drawerNumber;
    }

    public void setDrawerNumber(String drawerNumber) {
        this.drawerNumber = drawerNumber;
    }

    public String getUnNamedForm() {
        return unNamedForm;
    }

    public void setUnNamedForm(String unNamedForm) {
        this.unNamedForm = unNamedForm;
    }

    public int getPrinted() {
        return printed;
    }

    public void setPrinted(int printed) {
        this.printed = printed;
    }

    public int getNumberToPrint() {
        return numberToPrint;
    }

    public void setNumberToPrint(int numberToPrint) {
        this.numberToPrint = numberToPrint;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}


    /**
     * Retuns a JSON encoding of the list of fields that can appear on a unit tray label using
     * key-value pairs where the keys are f,b,t,g,s,u,i,r,a,d, and optionally c, and the values
     * are respectively for the family, subfamily,tribe, genus, specificepithet, subspecificepithet,
     * infraspecificepithet, infraspecificrank, authorship, drawernumber and optionally collection.
     *
     * @return String containing JSON in the form { "f":"familyname", .... }
     * @see createFromJSONString
     */
    public String toJSONString() {
        StringBuffer result = new StringBuffer();
        result.append("{");
        result.append( " \"f\":\"").append(family).append("\"");
        result.append(", \"b\":\"").append(subfamily).append("\"");
        result.append(", \"t\":\"").append(tribe).append("\"");
        result.append(", \"g\":\"").append(genus).append("\"");
        result.append(", \"s\":\"").append(specificEpithet).append("\"");
        result.append(", \"u\":\"").append(subspecificEpithet).append("\"");
        result.append(", \"i\":\"").append(infraspecificEpithet).append("\"");
        result.append(", \"r\":\"").append(infraspecificRank).append("\"");
        result.append(", \"a\":\"").append(authorship).append("\"");
        result.append(", \"d\":\"").append(drawerNumber).append("\"");
        if (collection != null) {
        if (!collection.isEmpty()) {
            result.append(", \"c\":\"").append(collection).append("\"");
        }
        }
        result.append(" }");
        return result.toString();
    }

    /** Factory method, given a JSON encoded string, as encoded with toJSONString(), extract the values from that
     * string into a new instance of UnitTrayLabel so that they can be obtained by the appropriate returner
     * interface (taxonnameReturner, drawernumberReturner, collectionReturner).
     *
     * @param jsonEncodedLabel the JSON to decode.
     * @return a new UnitTrayLabel populated with the values found in the supplied jsonEncodedLabel text.
     * @see toJSONString
     */
    public static UnitTrayLabel createFromJSONString(String jsonEncodedLabel) {
        UnitTrayLabel result = null;
        if (jsonEncodedLabel.matches("\\{.*\\}")) {
            String originalJsonEncodedLabel = new String(jsonEncodedLabel.toString());
            jsonEncodedLabel = jsonEncodedLabel.replaceFirst("^\\{", "");  // Strip off leading  {
            jsonEncodedLabel = jsonEncodedLabel.replaceFirst("\\}$", "");  // Strip off trailing }
            if (jsonEncodedLabel.contains("}")) {
                // nested json, not expected.
                Logger.getLogger(UnitTrayLabel.class.getName()).log(Level.SEVERE,"JSON for UnitTrayLabel contains unexpected nesting { { } }.  JSON is: " + originalJsonEncodedLabel);
            } else {
                Logger.getLogger(UnitTrayLabel.class.getName()).log(Level.INFO,jsonEncodedLabel);
                result = new UnitTrayLabel();
                // Beginning and end are special case for split on '", "'
                // remove leading ' "' and trailing '" '
                jsonEncodedLabel = jsonEncodedLabel.replaceFirst("^ \"", "");  // Strip off leading space quote
                jsonEncodedLabel = jsonEncodedLabel.replaceFirst("\" $", "");  // Strip off trailing quote space
                // split into key value parts by '", "'
                String[] pairs = jsonEncodedLabel.split("\", \"");
                for (int x=0; x< pairs.length; x++) {
                    // split each key value pair
                    String[] keyValuePair = pairs[x].split("\":\"");
                    if (keyValuePair.length==2) {
                        String key = keyValuePair[0];
                        String value = keyValuePair[1];
                        Logger.getLogger(UnitTrayLabel.class.getName()).log(Level.INFO,"key=["+ key + "], value=["+ value +"]");

                        // Note: Adding values here isn't sufficient to populate specimen records,
                        // you still need to invoke the relevant returner interface on the parser.
                        if (key.equals("f")) { result.setFamily(value); }
                        if (key.equals("b")) { result.setSubfamily(value); }
                        if (key.equals("t")) { result.setTribe(value); }
                        if (key.equals("g")) { result.setGenus(value); }
                        if (key.equals("s")) { result.setSpecificEpithet(value); }
                        if (key.equals("u")) { result.setSubspecificEpithet(value); }
                        if (key.equals("i")) { result.setInfraspecificEpithet(value); }
                        if (key.equals("r")) { result.setInfraspecificRank(value); }
                        if (key.equals("a")) { result.setAuthorship(value); }
                        if (key.equals("d")) { result.setDrawerNumber(value); }
                        if (key.equals("c")) {
                            result.setCollection(value);
                            Logger.getLogger(UnitTrayLabel.class.getName()).log(Level.INFO,result.getCollection());
                        }
                    }
                }
            }
        } else {
            Logger.getLogger(UnitTrayLabel.class.getName()).log(Level.INFO,"JSON not matched to { }");
        }
        return result;
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
        if (!(object instanceof UnitTrayLabel)) {
            return false;
        }
        UnitTrayLabel other = (UnitTrayLabel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.harvard.mcz.imagecapture.data.UnitTrayLabel[id=" + id + "]";
    }

}
