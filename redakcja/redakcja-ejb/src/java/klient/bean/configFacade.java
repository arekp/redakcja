/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import klient.encje.config;

/**
 *
 * @author arekp
 */
@Stateless
public class configFacade implements configFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(config config) {
        em.persist(config);
    }

    public void edit(config config) {
        em.merge(config);
    }

    public void remove(config config) {
        em.remove(em.merge(config));
    }

    public config find(Object id) {
        return em.find(config.class, id);
    }

    public List<config> findAll() {
        return em.createQuery("select object(o) from config as o").getResultList();
    }
    public config findWartosc(String klucz) {
        Query q = em.createQuery("select object(o) from config as o where o.klucz = :klucz");
        q.setParameter("klucz", klucz);
        config con = (config) q.getSingleResult();
        return con;
    }
};
