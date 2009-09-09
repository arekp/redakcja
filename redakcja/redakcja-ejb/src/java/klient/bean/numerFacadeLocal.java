/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.dokument;
import klient.encje.numer;

/**
 *
 * @author arekp
 */
@Local
public interface numerFacadeLocal {

    void create(numer numer);

    void edit(numer numer);

    void remove(numer numer);

    numer find(Object id);

    List<numer> findAll();

    public java.util.List<dokument> ListaSpisTresci(java.lang.Long idnumer);

}
