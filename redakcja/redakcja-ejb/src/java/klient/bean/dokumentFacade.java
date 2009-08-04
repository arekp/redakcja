/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import klient.encje.dokument;

/**
 *
 * @author arekp
 */
@Stateless
public class dokumentFacade implements dokumentFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(dokument dokument) {
        em.persist(dokument);
    }

    public void edit(dokument dokument) {
        em.merge(dokument);
    }

    public void remove(dokument dokument) {
        em.remove(em.merge(dokument));
    }

    public dokument find(Object id) {
        return em.find(dokument.class, id);
    }

    public List<dokument> findAll() {
        return em.createQuery("select object(o) from dokument as o").getResultList();
    }

}
