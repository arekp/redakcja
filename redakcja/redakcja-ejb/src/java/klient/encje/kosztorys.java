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

/**
 *
 * @author arekp
 */
@Entity
public class kosztorys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
        @ManyToOne
    @JoinColumn(name = "idNumer")
    private numer nume;
    private String opis;
    private double wartość;
    private String jednostka;
    private String typ;
        

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
        if (!(object instanceof kosztorys)) {
            return false;
        }
        kosztorys other = (kosztorys) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.kosztorys[id=" + id + "]";
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
     * @return the opis
     */
    public String getOpis() {
        return opis;
    }

    /**
     * @param opis the opis to set
     */
    public void setOpis(String opis) {
        this.opis = opis;
    }

    /**
     * @return the wartość
     */
    public double getWartość() {
        return wartość;
    }

    /**
     * @param wartość the wartość to set
     */
    public void setWartość(double wartość) {
        this.wartość = wartość;
    }

    /**
     * @return the jednostka
     */
    public String getJednostka() {
        return jednostka;
    }

    /**
     * @param jednostka the jednostka to set
     */
    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
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
