/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.zarzadca;

/**
 *
 * @author arekp
 */
@Local
public interface zarzadcaFacadeLocal {

    void create(zarzadca zarzadca);

    void edit(zarzadca zarzadca);

    void remove(zarzadca zarzadca);

    zarzadca find(Object id);

    List<zarzadca> findAll();

    public zarzadca findLicencja(int numerLicencji);

    public List<zarzadca> doWysylki(int numer);

    public List<zarzadca> szukajZarzadce(String ciag);

}
