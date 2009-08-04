/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import klient.encje.adres;

/**
 *
 * @author arekp
 */
@Stateless
public class adresFacade implements adresFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(adres adres) {
        em.persist(adres);
    }

    public void edit(adres adres) {
        em.merge(adres);
    }

    public void remove(adres adres) {
        em.remove(em.merge(adres));
    }

    public adres find(Object id) {
        return em.find(adres.class, id);
    }

    public List<adres> findAll() {
        return em.createQuery("select object(o) from adres as o").getResultList();
    }

}
