/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klient.encje;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author ArekP
 */
@Entity
public class numer implements Serializable {

    @TableGenerator(name = "NUMER_GENERATOR", table = "NUMER", schema = "redakcja", pkColumnName = "ID")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nazwa;
//    private String url;
    private String info;
    private String status;
//        private String contentType;
    private int naklad;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    @OneToMany(mappedBy = "id_opisu", fetch = FetchType.EAGER,  cascade = { CascadeType.ALL })
//    @JoinColumn(name="klient_id",insertable = true, updatable = true, nullable = true)
    private Collection<plik> plik = new ArrayList<plik>();

    public numer(String nazwa, String url, String info, String status, int naklad, Date data) {
        this.data = data;
        this.naklad = naklad;
        this.status = status;
        this.info = info;
//        this.url=url;
        this.nazwa = nazwa;
    }

    public numer() {
    }

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
        if (!(object instanceof numer)) {
            return false;
        }
        numer other = (numer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.numer[id=" + id + "]";
    }

    /**
     * @return the nazwa
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * @param nazwa the nazwa to set
     */
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return the naklad
     */
    public int getNaklad() {
        return naklad;
    }

    /**
     * @param naklad the naklad to set
     */
    public void setNaklad(int naklad) {
        this.naklad = naklad;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the plik
     */
    public Collection<plik> getPlik() {
        return plik;
    }

    /**
     * @param plik the plik to set
     */
    public void setPlik(Collection<plik> plik) {
        this.plik = plik;
    }

}
