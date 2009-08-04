/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.dokument;

/**
 *
 * @author arekp
 */
@Local
public interface dokumentFacadeLocal {

    void create(dokument dokument);

    void edit(dokument dokument);

    void remove(dokument dokument);

    dokument find(Object id);

    List<dokument> findAll();

}
