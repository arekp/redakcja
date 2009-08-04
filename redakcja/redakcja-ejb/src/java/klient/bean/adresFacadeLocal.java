/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.adres;

/**
 *
 * @author arekp
 */
@Local
public interface adresFacadeLocal {

    void create(adres adres);

    void edit(adres adres);

    void remove(adres adres);

    adres find(Object id);

    List<adres> findAll();

}
