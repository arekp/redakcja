/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import klient.encje.kontrahent;

/**
 *
 * @author arekp
 */
@Stateless
public class kontrahentFacade implements kontrahentFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(kontrahent kontrahent) {
        em.persist(kontrahent);
    }

    public void edit(kontrahent kontrahent) {
        em.merge(kontrahent);
    }

    public void remove(kontrahent kontrahent) {
        em.remove(em.merge(kontrahent));
    }

    public kontrahent find(Object id) {
        return em.find(kontrahent.class, id);
    }

    public List<kontrahent> findAll() {
        return em.createQuery("select object(o) from kontrahent as o").getResultList();
    }

}
