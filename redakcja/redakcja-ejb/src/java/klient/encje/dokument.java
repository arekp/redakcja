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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author ArekP
 */
@Entity
public class dokument implements Serializable {

    @TableGenerator(name = "DOKUMENT_GENERATOR", table = "DOKUMENT", schema = "redakcja", pkColumnName = "ID")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long idRodzica;

    @ManyToOne
    @JoinColumn(name = "idKontrahenta")
    private kontrahent kont;

    @ManyToOne
    @JoinColumn(name = "idNumer")
    private numer nume;

    @ManyToOne
    @JoinColumn(name = "idRedaktora")
    private kontrahent redaktor;// id z tablicy autor z flaga redaktor osoba przypisana do redagowania tekstu
   
    private String typ;//tablica DIC_FLAGA Flaga(Artukul,reklama,michalki)
       private String tytul;
    private String status;//Status(nowy,redagowaniu,zlozony_artukuł)
    private int iloscZnakow;
    private String nrStrony;
    private String powierzchnia;
    private double cena;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    private String url;

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
        if (!(object instanceof dokument)) {
            return false;
        }
        dokument other = (dokument) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.dokument[id=" + id + "]";
    }

    /**
     * @return the idRodzica
     */
    public Long getIdRodzica() {
        return idRodzica;
    }

    /**
     * @param idRodzica the idRodzica to set
     */
    public void setIdRodzica(Long idRodzica) {
        this.idRodzica = idRodzica;
    }

    /**
     * @return the kont
     */
    public kontrahent getKont() {
        return kont;
    }

    /**
     * @param kont the kont to set
     */
    public void setKont(kontrahent kont) {
        this.kont = kont;
    }

    /**
     * @return the tytul
     */
    public String getTytul() {
        return tytul;
    }

    /**
     * @param tytul the tytul to set
     */
    public void setTytul(String tytul) {
        this.tytul = tytul;
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
     * @return the iloscZnakow
     */
    public int getIloscZnakow() {
        return iloscZnakow;
    }

    /**
     * @param iloscZnakow the iloscZnakow to set
     */
    public void setIloscZnakow(int iloscZnakow) {
        this.iloscZnakow = iloscZnakow;
    }

    /**
     * @return the nrStrony
     */
    public String getNrStrony() {
        return nrStrony;
    }

    /**
     * @param nrStrony the nrStrony to set
     */
    public void setNrStrony(String nrStrony) {
        this.nrStrony = nrStrony;
    }

    /**
     * @return the powierzchnia
     */
    public String getPowierzchnia() {
        return powierzchnia;
    }

    /**
     * @param powierzchnia the powierzchnia to set
     */
    public void setPowierzchnia(String powierzchnia) {
        this.powierzchnia = powierzchnia;
    }

    /**
     * @return the cena
     */
    public double getCena() {
        return cena;
    }

    /**
     * @param cena the cena to set
     */
    public void setCena(double cena) {
        this.cena = cena;
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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the nume
     */
    public numer getNume() {
        return nume;
    }

    /**
     * @param nume the nume to set
     */
    public void setNume(numer nume) {
        this.nume = nume;
    }

    /**
     * @return the redaktor
     */
    public kontrahent getRedaktor() {
        return redaktor;
    }

    /**
     * @param redaktor the redaktor to set
     */
    public void setRedaktor(kontrahent redaktor) {
        this.redaktor = redaktor;
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
}
