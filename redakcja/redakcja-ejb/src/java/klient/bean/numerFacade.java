/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klient.bean;

import bean.spisTresci;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
import klient.encje.dokument;
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


  public List<dokument> ListaSpisTresci(Long idnumer) {
//        lista dokumentow danego kontrahenta
        Query q = em.createNativeQuery("select * from dokument k where idnumer = :idnumer and idRodzica is null and typ like 'Art%' order by idgrupy,nrstrony desc",dokument.class);
        q.setParameter("idnumer", idnumer);
        List <dokument> dokument1 = q.getResultList();
        System.out.print(dokument1.size());
        return dokument1;
    }
}
