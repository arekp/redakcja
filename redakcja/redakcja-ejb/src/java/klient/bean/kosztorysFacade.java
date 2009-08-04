/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import klient.encje.kosztorys;

/**
 *
 * @author arekp
 */
@Stateless
public class kosztorysFacade implements kosztorysFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(kosztorys kosztorys) {
        em.persist(kosztorys);
    }

    public void edit(kosztorys kosztorys) {
        em.merge(kosztorys);
    }

    public void remove(kosztorys kosztorys) {
        em.remove(em.merge(kosztorys));
    }

    public kosztorys find(Object id) {
        return em.find(kosztorys.class, id);
    }

    public List<kosztorys> findAll() {
        return em.createQuery("select object(o) from kosztorys as o").getResultList();
    }

}
