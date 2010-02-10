/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author arekp
 */
@Stateless
public class newsLetteraFacade implements newsLetteraFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(newsLettera newsLettera) {
        em.persist(newsLettera);
    }

    public void edit(newsLettera newsLettera) {
        em.merge(newsLettera);
    }

    public void remove(newsLettera newsLettera) {
        em.remove(em.merge(newsLettera));
    }

    public newsLettera find(Object id) {
        return em.find(newsLettera.class, id);
    }

    public List<newsLettera> findAll() {
        return em.createQuery("select object(o) from newsLettera as o").getResultList();
    }

}
