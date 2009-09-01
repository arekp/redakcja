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
    public List<kontrahent> findKontrahent(String ciag) {
        System.out.print("kontrahent --> "+ciag);
//        Query q = em.createQuery( "select k from Klient k where  k.nazwa like :ciag or k.info like :ciag" );
        Query q = em.createQuery("select k from kontrahent as k where k.typ like :ciag or k.imie like :ciag or k.nazwisko like :ciag or "
                +"k.ulica like :ciag or k.miasto like :ciag or k.email like :ciag or k.tel like :ciag or k.nip like :ciag or "+
                " k.pesel like :ciag or k.urzadSkarbowy like :ciag or k.numerKonta like :ciag or k.info like :ciag");
        q.setParameter("ciag", '%' + ciag + '%');
        List klienci = q.getResultList();
        return klienci;
    }
        public List<kontrahent> findTypKontrah(String typ) {
        Query q = em.createQuery( "select k from kontrahent k where  k.typ = :typ" );
        q.setParameter("typ", typ);
        List klienci = q.getResultList();
        return klienci;
    }
}
