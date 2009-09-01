/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import klient.encje.numer;

/**
 *
 * @author arekp
 */
@Stateless
public class numerFacade implements numerFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(numer numer) {
        em.persist(numer);
    }

    public void edit(numer numer) {
        em.merge(numer);
    }

    public void remove(numer numer) {
        em.remove(em.merge(numer));
    }

    public numer find(Object id) {
        return em.find(numer.class, id);
    }

    public List<numer> findAll() {
        return em.createQuery("select object(o) from numer as o order by o.data desc").getResultList();
    }

}
