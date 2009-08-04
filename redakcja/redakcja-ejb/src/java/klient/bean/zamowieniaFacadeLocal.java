/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.zamowienia;

/**
 *
 * @author arekp
 */
@Local
public interface zamowieniaFacadeLocal {

    void create(zamowienia zamowienia);

    void edit(zamowienia zamowienia);

    void remove(zamowienia zamowienia);

    zamowienia find(Object id);

    List<zamowienia> findAll();

}
