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
import klient.encje.zarzadca;

/**
 *
 * @author arekp
 */
@Stateless
public class zarzadcaFacade implements zarzadcaFacadeLocal {

    @PersistenceContext
    private EntityManager em;

    public void create(zarzadca zarzadca) {
        em.persist(zarzadca);
    }

    public void edit(zarzadca zarzadca) {
        em.merge(zarzadca);
    }

    public void remove(zarzadca zarzadca) {
        em.remove(em.merge(zarzadca));
    }

    public zarzadca find(Object id) {
        return em.find(zarzadca.class, id);
    }

    public List<zarzadca> findAll() {
        return em.createQuery("select object(o) from zarzadca as o").getResultList();
    }

    public zarzadca findLicencja(int numerLicencji) {
        Query q = em.createQuery("select o from zarzadca as o where o.numerLicencji = :numerLicencji");
        q.setParameter("numerLicencji", numerLicencji);
        zarzadca zarz = (zarzadca) q.getSingleResult();
        return zarz;
    }

    public List<zarzadca> doWysylki(int numer) {
        Query q = em.createQuery("select o from zarzadca as o where o.numerLicencji > :numer");
        q.setParameter("numer", numer);
        List zarz = q.getResultList();
        return zarz;
    }

    public List<zarzadca> szukajZarzadce(String ciag) {
                Query q = em.createQuery("select o from zarzadca as o where o.imie like :ciag or o.nazwisko like :ciag or o.adres like :ciag or o.miasto like :ciag or o.wojew like :ciag");
        q.setParameter("ciag", ciag);
        List zarz = q.getResultList();
        return zarz;
    }
}
