/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.config;

/**
 *
 * @author arekp
 */
@Local
public interface configFacadeLocal {

    void create(config config);

    void edit(config config);

    void remove(config config);

    config find(Object id);

    List<config> findAll();

    public klient.encje.config findWartosc(java.lang.String klucz);

}
