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
public class noweZamowienia implements Serializable {
        @TableGenerator(name="noweZamowienia_GENERATOR",table="noweZamowienia",schema="redakcja",pkColumnName="ID")

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
        if (!(object instanceof noweZamowienia)) {
            return false;
        }
        noweZamowienia other = (noweZamowienia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.noweZamowienia[id=" + id + "]";
    }

}
