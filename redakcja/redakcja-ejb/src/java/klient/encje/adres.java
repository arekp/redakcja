/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.encje;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

/**
 *
 * @author arekp
 */
@Entity
@TableGenerator(name="ADRES_GENERATOR",table="ADRES",
schema="redakcja",pkColumnName="ID"
)
public class adres implements Serializable {
    @ManyToOne
    @JoinColumn(name="klient_id")
    private Klient klient;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//private Long idKlienta;
private String adress;
private String miasto;
private String wojew;
private String tel;
private String email;
private String typ;
private String info;

private String lat;
private String lon;
//private Long klient_id;

public adres(){};
public adres(String adress,String miasto,String wojew,String tel,String email,String typ,String info){
this.adress= adress;
this.miasto= miasto;
this.wojew =  wojew;
this.tel = tel;
this.email = email;
this.typ = typ;
this.info = info;
};
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
        if (!(object instanceof adres)) {
            return false;
        }
        adres other = (adres) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.adres[id=" + id + "]";
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
     * @return the adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * @param adress the adress to set
     */
    public void setAdress(String adress) {
        this.adress = adress;
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
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the typ
     */
    public String getTyp() {
        return typ;
    }

    /**
     * @param typ the typ to set
     */
    public void setTyp(String typ) {
        this.typ = typ;
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
     * @return the lat
     */
    public String getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * @return the lon
     */
    public String getLon() {
        return lon;
    }

    /**
     * @param lon the lon to set
     */
    public void setLon(String lon) {
        this.lon = lon;
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
