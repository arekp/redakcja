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
import javax.persistence.TableGenerator;

/**
 *
 * @author arekp
 */
@Entity
public class grupaDok implements Serializable {
     @TableGenerator(name = "GrupaDok_GENERATOR", table = "GRUPADOK", schema = "redakcja", pkColumnName = "ID")

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   private Long idKolejnosc;
    private String nazwa;

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
        if (!(object instanceof grupaDok)) {
            return false;
        }
        grupaDok other = (grupaDok) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.grupaDok[id=" + id + "]";
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
     * @return the idKolejnosc
     */
    public Long getIdKolejnosc() {
        return idKolejnosc;
    }

    /**
     * @param idKolejnosc the idKolejnosc to set
     */
    public void setIdKolejnosc(Long idKolejnosc) {
        this.idKolejnosc = idKolejnosc;
    }

}
