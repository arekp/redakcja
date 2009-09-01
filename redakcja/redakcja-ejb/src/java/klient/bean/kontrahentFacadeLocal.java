/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.kontrahent;

/**
 *
 * @author arekp
 */
@Local
public interface kontrahentFacadeLocal {

    void create(kontrahent kontrahent);

    void edit(kontrahent kontrahent);

    void remove(kontrahent kontrahent);

    kontrahent find(Object id);

    List<kontrahent> findAll();

    public java.util.List<klient.encje.kontrahent> findKontrahent(java.lang.String ciag);

    public java.util.List<klient.encje.kontrahent> findTypKontrah(java.lang.String typ);

}
