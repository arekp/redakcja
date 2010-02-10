/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author arekp
 */
@Stateless
public class noweZamowieniaFacade implements noweZamowieniaFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(noweZamowienia noweZamowienia) {
        em.persist(noweZamowienia);
    }

    public void edit(noweZamowienia noweZamowienia) {
        em.merge(noweZamowienia);
    }

    public void remove(noweZamowienia noweZamowienia) {
        em.remove(em.merge(noweZamowienia));
    }

    public noweZamowienia find(Object id) {
        return em.find(noweZamowienia.class, id);
    }

    public List<noweZamowienia> findAll() {
        return em.createQuery("select object(o) from noweZamowienia as o").getResultList();
    }

}
