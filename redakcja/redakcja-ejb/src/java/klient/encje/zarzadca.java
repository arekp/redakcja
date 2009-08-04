/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.encje;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author arekp
 */
@Entity
public class zarzadca implements Serializable {

@TableGenerator(name="ZARZADCA_GENERATOR",table="ZARZADCA",schema="redakcja",pkColumnName="ID")

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
private int numerLicencji;
    @Temporal(javax.persistence.TemporalType.DATE)
private Date dataPrzyznania;
private String imie;
private String nazwisko;
private String kod;
private String adres;
private String miasto;
private String wojew;
private Long id_klienta;

public zarzadca(){
    }
public zarzadca(int numerLicencji,Date dataPrzyznania,String imie, String nazwisko,String kod,String adres,String miasto,String wojew){
    this.adres = adres;
    this.dataPrzyznania = dataPrzyznania;
    this.imie = imie;
    this.nazwisko=nazwisko;
    this.kod = kod;
    this.numerLicencji = numerLicencji;
    this.miasto = miasto;
    this.wojew=wojew;
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
        if (!(object instanceof zarzadca)) {
            return false;
        }
        zarzadca other = (zarzadca) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.zarzadca[id=" + id + "]";
    }

    /**
     * @return the numerLicencji
     */
    public int getNumerLicencji() {
        return numerLicencji;
    }

    /**
     * @param numerLicencji the numerLicencji to set
     */
    public void setNumerLicencji(int numerLicencji) {
        this.numerLicencji = numerLicencji;
    }

    /**
     * @return the dataPrzyznania
     */
    public Date getDataPrzyznania() {
        return dataPrzyznania;
    }

    /**
     * @param dataPrzyznania the dataPrzyznania to set
     */
    public void setDataPrzyznania(Date dataPrzyznania) {
        this.dataPrzyznania = dataPrzyznania;
    }

    /**
     * @return the imie
     */
    public String getImie() {
        return imie;
    }

    /**
     * @param imie the imie to set
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * @return the nazwisko
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * @param nazwisko the nazwisko to set
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * @return the kod
     */
    public String getKod() {
        return kod;
    }

    /**
     * @param kod the kod to set
     */
    public void setKod(String kod) {
        this.kod = kod;
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
     * @return the miasto
     */
    public String getMiasto() {
        return miasto;
    }

    /**
     * @param miasto the miasto to set
     */
    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    /**
     * @return the wojew
     */
    public String getWojew() {
        return wojew;
    }

    /**
     * @param wojew the wojew to set
     */
    public void setWojew(String wojew) {
        this.wojew = wojew;
    }

    /**
     * @return the id_klienta
     */
    public Long getId_klienta() {
        return id_klienta;
    }

    /**
     * @param id_klienta the id_klienta to set
     */
    public void setId_klienta(Long id_klienta) {
        this.id_klienta = id_klienta;
    }

}
