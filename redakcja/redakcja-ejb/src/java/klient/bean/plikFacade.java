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
import klient.encje.plik;

/**
 *
 * @author arekp
 */
@Stateless
public class plikFacade implements plikFacadeLocal {

    @PersistenceContext
    private EntityManager em;

    public void create(plik plik) {
        em.persist(plik);
    }

    public void edit(plik plik) {
        em.merge(plik);
    }

    public void remove(plik plik) {
        em.remove(em.merge(plik));
    }

    public plik find(Object id) {
        return em.find(plik.class, id);
    }

    public List<plik> findAll() {
        return em.createQuery("select object(o) from plik as o").getResultList();
    }

    public List<plik> findPliki(Long idDok, String typ) {
        Query q = em.createQuery("select o from plik as o where o.id_opisu = :idDok and typ= :typ");
        q.setParameter("idDok", idDok);
        q.setParameter("typ", typ);
        return q.getResultList();
    }
    public plik findPlik(Long idDok, String typ) {
        Query q = em.createQuery("select o from plik as o where o.id_opisu = :idDok and typ= :typ");
        q.setParameter("idDok", idDok);
        q.setParameter("typ", typ);
        return (plik)q.getSingleResult();
    }
        public String ilePlikow(String typ,String url, String fileName) {
        Query q = em.createQuery("select count(o) from plik as o where typ= :typ and url=:url and filename like :filename");
        q.setParameter("filename", fileName);
        q.setParameter("url", url);
        q.setParameter("typ", typ);
        return q.getSingleResult().toString();
    }
}
