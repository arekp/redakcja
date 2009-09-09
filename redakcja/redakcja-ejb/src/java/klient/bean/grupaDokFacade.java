/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import klient.encje.grupaDok;

/**
 *
 * @author arekp
 */
@Stateless
public class grupaDokFacade implements grupaDokFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(grupaDok grupaDok) {
        em.persist(grupaDok);
    }

    public void edit(grupaDok grupaDok) {
        em.merge(grupaDok);
    }

    public void remove(grupaDok grupaDok) {
        em.remove(em.merge(grupaDok));
    }

    public grupaDok find(Object id) {
        return em.find(grupaDok.class, id);
    }

    public List<grupaDok> findAll() {
        return em.createQuery("select object(o) from grupaDok as o").getResultList();
    }

}
