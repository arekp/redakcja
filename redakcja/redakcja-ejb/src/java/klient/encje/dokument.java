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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
//    private Long idKontrahenta;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idKontrahenta")
    private kontrahent kont;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idNumer")
    private numer nume;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idRedaktora")
    private kontrahent redaktor;// id z tablicy autor z flaga redaktor osoba przypisana do redagowania tekstu
    private String typ;//tablica DIC_FLAGA Flaga(Artukul,reklama,michalki)
    private String tytul;
    private String status;//Status(nowy,redagowany,skonczony,zlozony_artukuł,zamknięty)
    private int iloscZnakow;
    private String nrStrony;
    private double powierzchnia;
    private double cena;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idgrupy")
    private grupaDok grupa;

    public dokument() {
    }

    public dokument(dokument _dokument) {
        this.idRodzica = _dokument.idRodzica;
        this.kont = _dokument.kont;
        this.nume = _dokument.nume;
        this.redaktor = _dokument.redaktor;// id z tablicy autor z flaga redaktor osoba przypisana do redagowania tekstu
        this.typ = _dokument.typ;//tablica DIC_FLAGA Flaga(Artukul,reklama,michalki)
        this.tytul = _dokument.tytul;
        this.status = _dokument.status;//Status(nowy,redagowaniu,zlozony_artukuł,Zamknięty)
        this.iloscZnakow = _dokument.iloscZnakow;
        this.nrStrony = _dokument.nrStrony;
        this.powierzchnia = _dokument.powierzchnia;
        this.cena = _dokument.cena;

    }

    public dokument(String tytul, double cena, Long idRodzica, int iloscZnakow, String nrStrony, double powierzchnia, String status, String typ) {
        this.idRodzica = idRodzica;
//this.kont=kont;
//    this.nume=nume;
//    this.redaktor=redaktor;// id z tablicy autor z flaga redaktor osoba przypisana do redagowania tekstu
        this.typ = typ;//tablica DIC_FLAGA Flaga(Artukul,reklama,michalki)
        this.tytul = tytul;
        this.status = status;//Status(nowy,redagowaniu,zlozony_artukuł,zamknięty)
        this.iloscZnakow = iloscZnakow;
        this.nrStrony = nrStrony;
        this.powierzchnia = powierzchnia;
        this.cena = cena;
    }
//    private String url;
//    @OneToMany(mappedBy = "id_opisu", fetch = FetchType.EAGER,  cascade = { CascadeType.ALL })
////    @JoinColumn(name="klient_id",insertable = true, updatable = true, nullable = true)
//    private Collection<plik> plik = new ArrayList<plik>();

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
    public double getPowierzchnia() {
        return powierzchnia;
    }

    /**
     * @param powierzchnia the powierzchnia to set
     */
    public void setPowierzchnia(double powierzchnia) {
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

    /**
     * @return the grupa
     */
    public grupaDok getGrupa() {
        return grupa;
    }

    /**
     * @param grupa the grupa to set
     */
    public void setGrupa(grupaDok grupa) {
        this.grupa = grupa;
    }
}
