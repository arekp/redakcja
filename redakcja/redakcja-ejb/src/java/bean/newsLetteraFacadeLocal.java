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
public interface newsLetteraFacadeLocal {

    void create(newsLettera newsLettera);

    void edit(newsLettera newsLettera);

    void remove(newsLettera newsLettera);

    newsLettera find(Object id);

    List<newsLettera> findAll();

}
