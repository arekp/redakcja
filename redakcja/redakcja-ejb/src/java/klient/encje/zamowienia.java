/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.encje;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author arekp
 */
@Entity
@TableGenerator(name="ZAMOWIENIA_GENERATOR",table="ZAMOWIENIA",
schema="redakcja",pkColumnName="ID"
)
public class zamowienia implements Serializable {
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="klient_id")
    private Klient klient;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    private Long idKlienta;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date prenOd;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date prenDo;
    private int ilosc;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPrzedl;
    private String typPren;
    private String typ;
//    private Long klient_id;

public zamowienia(){}

public zamowienia(Date prenOd,Date prenDo,int ilosc,Date dataPrzedl,String typPren)
{this.prenOd= prenOd;
 this.prenDo = prenDo;
 this.ilosc = ilosc;
 this.dataPrzedl = dataPrzedl;
 this.typPren = typPren;
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
        if (!(object instanceof zamowienia)) {
            return false;
        }
        zamowienia other = (zamowienia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.zamowienia[id=" + id + "]";
    }

//    /**
//     * @return the idKlienta
//     */
//    public Long getIdKlienta() {
//        return idKlienta;
//    }
//
//    /**
//     * @param idKlienta the idKlienta to set
//     */
//    public void setIdKlienta(Long idKlienta) {
//        this.idKlienta = idKlienta;
//    }

    /**
     * @return the prenOd
     */
    public Date getPrenOd() {
        return prenOd;
    }

    /**
     * @param prenOd the prenOd to set
     */
    public void setPrenOd(Date prenOd) {
        this.prenOd = prenOd;
    }

    /**
     * @return the prenDo
     */
    public Date getPrenDo() {
        return prenDo;
    }

    /**
     * @param prenDo the prenDo to set
     */
    public void setPrenDo(Date prenDo) {
        this.prenDo = prenDo;
    }

    /**
     * @return the ilosc
     */
    public int getIlosc() {
        return ilosc;
    }

    /**
     * @param ilosc the ilosc to set
     */
    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    /**
     * @return the dataPrzedl
     */
    public Date getDataPrzedl() {
        return dataPrzedl;
    }

    /**
     * @param dataPrzedl the dataPrzedl to set
     */
    public void setDataPrzedl(Date dataPrzedl) {
        this.dataPrzedl = dataPrzedl;
    }

    /**
     * @return the typPren
     */
    public String getTypPren() {
        return typPren;
    }

    /**
     * @param typPren the typPren to set
     */
    public void setTypPren(String typPren) {
        this.typPren = typPren;
    }

    /**
     * @return the klient
     */
    public Klient getKlient() {
        return klient;
    }

    /**
     * @param klient the klient to set
     */
    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    /**
     * @return the status
     */
    public String getTyp() {
        return typ;
    }

    /**
     * @param status the status to set
     */
    public void setTyp(String typ) {
        this.typ = typ;
    }

//    /**
//     * @return the klient_id
//     */
//    public Long getKlient_id() {
//        return klient_id;
//    }
//
//    /**
//     * @param klient_id the klient_id to set
//     */
//    public void setKlient_id(Long klient_id) {
//        this.klient_id = klient_id;
//    }

}
