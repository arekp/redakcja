/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import klient.encje.zamowienia;

/**
 *
 * @author arekp
 */
@Stateless
public class zamowieniaFacade implements zamowieniaFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(zamowienia zamowienia) {
        em.persist(zamowienia);
    }

    public void edit(zamowienia zamowienia) {
        em.merge(zamowienia);
    }

    public void remove(zamowienia zamowienia) {
        em.remove(em.merge(zamowienia));
    }

    public zamowienia find(Object id) {
        return em.find(zamowienia.class, id);
    }

    public List<zamowienia> findAll() {
        return em.createQuery("select object(o) from zamowienia as o").getResultList();
    }

}
