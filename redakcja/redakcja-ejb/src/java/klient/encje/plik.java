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
public class plik implements Serializable {

       @TableGenerator(name = "PLIK_GENERATOR", table = "PLIK", schema = "redakcja", pkColumnName = "ID")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contentType;//  typ pliku jaki mamy
    private String filename;
    private Long wielkoscpliku;
    private String url;
    private String typ; //Czy to ma byc do dokumentu czy do numeru
    private Long id_opisu;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data; // data dodanie

    public plik() {
    }

    public plik(String contentType, Date data, String filename, Long idDokumentu, String typ, String url, Long wielkoscpliku) {
           this.contentType=contentType;//  typ pliku jaki mamy
    this.filename=filename;
    this.wielkoscpliku=wielkoscpliku;
    this.url=url;
    this.typ=typ; //Czy to ma byc do dokumentu czy do numeru
    this.id_opisu=idDokumentu;
    this.data=data;
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
        if (!(object instanceof plik)) {
            return false;
        }
        plik other = (plik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.plik[id=" + id + "]";
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the wielkoscpliku
     */
    public Long getWielkoscpliku() {
        return wielkoscpliku;
    }

    /**
     * @param wielkoscpliku the wielkoscpliku to set
     */
    public void setWielkoscpliku(Long wielkoscpliku) {
        this.wielkoscpliku = wielkoscpliku;
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
     * @return the id_opisu
     */
    public Long getId_opisu() {
        return id_opisu;
    }

    /**
     * @param id_opisu the id_opisu to set
     */
    public void setId_opisu(Long id_opisu) {
        this.id_opisu = id_opisu;
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

}
