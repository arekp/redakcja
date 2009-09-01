/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.plik;

/**
 *
 * @author arekp
 */
@Local
public interface plikFacadeLocal {

    void create(plik plik);

    void edit(plik plik);

    void remove(plik plik);

    plik find(Object id);

    List<plik> findAll();

    public  List<plik> findPliki(java.lang.Long idDok, java.lang.String typ);

    public java.lang.String ilePlikow(java.lang.String typ, java.lang.String url, java.lang.String fileName);

    public klient.encje.plik findPlik(java.lang.Long idDok, java.lang.String typ);

}
