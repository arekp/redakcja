/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author arekp
 */
@Local
public interface noweZamowieniaFacadeLocal {

    void create(noweZamowienia noweZamowienia);

    void edit(noweZamowienia noweZamowienia);

    void remove(noweZamowienia noweZamowienia);

    noweZamowienia find(Object id);

    List<noweZamowienia> findAll();

}
