/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.kosztorys;

/**
 *
 * @author arekp
 */
@Local
public interface kosztorysFacadeLocal {

    void create(kosztorys kosztorys);

    void edit(kosztorys kosztorys);

    void remove(kosztorys kosztorys);

    kosztorys find(Object id);

    List<kosztorys> findAll();

}
