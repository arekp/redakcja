/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author arekp
 */
@Entity
public class newsLettera implements Serializable {

    @TableGenerator(name="newsLettera_GENERATOR",table="newsLettera",schema="redakcja",pkColumnName="ID")

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String adres;
    @Lob
    private String body;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataWpisu;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof newsLettera)) {
            return false;
        }
        newsLettera other = (newsLettera) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.newsLettera[id=" + id + "]";
    }

    /**
     * @return the adres
     */
    public String getAdres() {
        return adres;
    }

    /**
     * @param adres the adres to set
     */
    public void setAdres(String adres) {
        this.adres = adres;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the dataWpisu
     */
    public Date getDataWpisu() {
        return dataWpisu;
    }

    /**
     * @param dataWpisu the dataWpisu to set
     */
    public void setDataWpisu(Date dataWpisu) {
        this.dataWpisu = dataWpisu;
    }

}
