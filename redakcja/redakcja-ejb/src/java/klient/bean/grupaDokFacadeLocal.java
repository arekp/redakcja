/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.grupaDok;

/**
 *
 * @author arekp
 */
@Local
public interface grupaDokFacadeLocal {

    void create(grupaDok grupaDok);

    void edit(grupaDok grupaDok);

    void remove(grupaDok grupaDok);

    grupaDok find(Object id);

    List<grupaDok> findAll();

}
