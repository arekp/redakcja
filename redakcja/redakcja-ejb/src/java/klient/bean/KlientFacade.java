/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klient.bean;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import klient.encje.Klient;

/**
 *
 * @author arekp
 */
@Stateless
public class KlientFacade implements KlientFacadeLocal {

    @PersistenceContext
    private EntityManager em;

    public void create(Klient klient) {
        em.persist(klient);
    }

    public void edit(Klient klient) {
        em.merge(klient);
    }

    public void remove(Klient klient) {
        em.remove(em.merge(klient));
    }

    public Klient find(Object id) {
        Klient k = em.find(Klient.class, id);
        System.out.print(k.getZamowienia().size() + " -- w sesji ");
        return k;
    }

    public List<Klient> findAll() {
        return em.createQuery("select object(o) from Klient as o").getResultList();
    }

    public List<Klient> findKlient(String ciag) {
//        Query q = em.createQuery( "select k from Klient k where  k.nazwa like :ciag or k.info like :ciag" );
        Query q = em.createQuery("select k from Klient k, IN (k.adresy) a where" +
                "(k.nazwa like :ciag or k.info like :ciag or k.klasaKlienta like :ciag or k.statusPren like :ciag or a.adress like :ciag " +
                "or a.miasto like :ciag or  a.email like :ciag or  a.tel like :ciag " +
                "or  a.info like :ciag or  a.wojew like :ciag) order by nazwa, ilosc desc");
        q.setParameter("ciag", '%' + ciag + '%');
        List klienci = q.getResultList();
        return klienci;
    }

    public List<Klient> WysylkaALL(Date data) {
        Query q = em.createQuery("select k from Klient k where k.prenDo > :data order by ilosc, nazwa desc");
        q.setParameter("data", data);
        List klienci = q.getResultList();
        return klienci;
    }

    public List<Klient> WysylkaMiesiac(int miesiac,int rok) {
        Query q = em.createQuery("select k from Klient k where MONTH(k.prenDo) = :miesiac and YEAR(k.prenDo)= :rok order by ilosc, nazwa desc");
        System.out.print(miesiac + " -- "+rok);
        q.setParameter("miesiac", miesiac);
        q.setParameter("rok", rok);
        List klienci = q.getResultList();
        return klienci;
    }
    public List<Klient> WysylkaZrezygnowani(Date data) {
//        lista klientow z nieprzedluzona prenumerata
        Query q = em.createQuery("select k from Klient k where k.prenDo < :data order by ilosc,nazwa desc");
        q.setParameter("data", data);
        List klienci = q.getResultList();
        return klienci;
    }

    public String IloscEgzempl(Date data) {
        Query q = em.createQuery("SELECT SUM(k.ilosc) FROM Klient k where k.prenDo >= :data");
        q.setParameter("data", data);
        String dataD = q.getSingleResult().toString();
        return dataD;
    }
}
