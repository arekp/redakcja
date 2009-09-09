/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.io.Serializable;
import javax.persistence.Embeddable;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author arekp
 */
  @SqlResultSetMapping(name = "spis",
    entities = {
        @EntityResult(entityClass = bean.spisTresci.class, fields = {
            @FieldResult(name = "nazwa", column = "nazwa"),
            @FieldResult(name = "tytul", column = "tytul"),
            @FieldResult(name = "nrstrony", column = "nrstrony")})})


@Embeddable
public class spisTresci implements Serializable  {

    @Id
    private String nazwa;
    private String tytul;
    private String nrstrony;

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
     * @return the nrstrony
     */
    public String getNrstrony() {
        return nrstrony;
    }

    /**
     * @param nrstrony the nrstrony to set
     */
    public void setNrstrony(String nrstrony) {
        this.nrstrony = nrstrony;
    }




}
