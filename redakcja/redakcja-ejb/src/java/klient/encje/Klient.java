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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
//import java.lang.Integer;


/**
 *
 * @author arekp
 */
@Entity

public class Klient implements Serializable {
    
@TableGenerator(name="KLIENT_GENERATOR",table="KLIENT",schema="redakcja",pkColumnName="ID")
   
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nazwa;
    private Long idAdresWysylki;
    private String typ;//pelna,ulgowa,
    private String info;
    private Long idAdresKorespondencji;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date prenDo;
    private int ilosc;
    private String klasaKlienta;//TBS,spoldzielnia
    private String statusPren;//stala
    private String nip;

     @OneToMany(mappedBy = "klient",fetch =FetchType.EAGER)
//    @JoinColumn(name="klient_id",insertable = true, updatable = true, nullable = true), fetch = FetchType.EAGER
private Collection <adres> adresy = new ArrayList<adres>();

 @OneToMany(mappedBy = "klient",fetch=FetchType.LAZY)
//    @JoinColumn(name="klient_id",insertable = true, updatable = true, nullable = true)
private Collection <zamowienia> zamowienia = new ArrayList<zamowienia>();

    public Klient(){}
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
        if (!(object instanceof Klient)) {
            return false;
        }
        Klient other = (Klient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.Klient[id=" + id + "]";
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
     * @return the idAdresWysylki
     */
    public Long getIdAdresWysylki() {
        return idAdresWysylki;
    }

    /**
     * @param idAdresWysylki the idAdresWysylki to set
     */
    public void setIdAdresWysylki(Long idAdresWysylki) {
        this.idAdresWysylki = idAdresWysylki;
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
     * @return the idAdresKorespondencji
     */
    public Long getIdAdresKorespondencji() {
        return idAdresKorespondencji;
    }

    /**
     * @param idAdresKorespondencji the idAdresKorespondencji to set
     */
    public void setIdAdresKorespondencji(Long idAdresKorespondencji) {
        this.idAdresKorespondencji = idAdresKorespondencji;
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
     * @return the adresy
     */
    public Collection<adres> getAdresy() {
        return adresy;
    }

    /**
     * @param adresy the adresy to set
     */
    public void setAdresy(Collection<adres> adresy) {
        this.adresy = adresy;
    }

    /**
     * @return the zamowienia
     */
    public Collection<zamowienia> getZamowienia() {
        return zamowienia;
    }

    /**
     * @param zamowienia the zamowienia to set
     */
    public void setZamowienia(Collection<zamowienia> zamowienia) {
        this.zamowienia = zamowienia;
    }

    /**
     * @return the klasaKlienta
     */
    public String getKlasaKlienta() {
        return klasaKlienta;
    }

    /**
     * @param klasaKlienta the klasaKlienta to set
     */
    public void setKlasaKlienta(String klasaKlienta) {
        this.klasaKlienta = klasaKlienta;
    }

    /**
     * @return the statusPren
     */
    public String getStatusPren() {
        return statusPren;
    }

    /**
     * @param statusPren the statusPren to set
     */
    public void setStatusPren(String statusPren) {
        this.statusPren = statusPren;
    }

    /**
     * @return the nip
     */
    public String getNip() {
        return nip;
    }

    /**
     * @param nip the nip to set
     */
    public void setNip(String nip) {
        this.nip = nip;
    }


}
