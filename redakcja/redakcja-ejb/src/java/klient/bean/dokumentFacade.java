/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klient.bean;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import klient.encje.dokument;
import klient.encje.kontrahent;
import klient.encje.numer;

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

    public List<dokument> ListaDokumentowKontra(kontrahent kontra) {
//        lista dokumentow danego kontrahenta
        Query q = em.createQuery("select k from dokument k where kont = :kontra and idRodzica is null order by id desc");
        q.setParameter("kontra", kontra);
        List dokument = q.getResultList();
        return dokument;
    }

    public List<dokument> ListaDokumentowRedaktora(kontrahent kontra) {
//        lista dokumentow danego kontrahenta
        Query q = em.createQuery("select k from dokument k where redaktor = :kontra order by id desc");
        q.setParameter("kontra", kontra);
        List dokument = q.getResultList();
        return dokument;
    }

    public List<dokument> ListaDokumentowNumeru(numer numer) {
//        lista dokumentow danego kontrahenta
        Query q = em.createQuery("select k from dokument k where nume = :numer and idRodzica is null order by id desc");
        q.setParameter("numer", numer);
        List dokument = q.getResultList();
        return dokument;
    }

    public List<dokument> ListaDokumentowWolna() {
//        lista dokumentow danego kontrahenta
        Query q = em.createNativeQuery("select * from dokument where idNumer is null and idRodzica is null order by id desc", dokument.class);
        //createQuery("select k from dokument k where nume = null a order by id desc");
//        q.setParameter("kontra", kontra);
        List dokument = q.getResultList();
        return dokument;
    }

    public List<dokument> ListaDokumentowDzieci(Long idRodzica) {
//        lista dokumentow danego kontrahenta
        Query q = em.createQuery("select k from dokument k where idRodzica = :idRodzica order by id desc");
        q.setParameter("idRodzica", idRodzica);
        List dokument = q.getResultList();
        return dokument;
    }

    public boolean ZmienNumer(Long idDokumentu, Long _numer) {
        System.out.print("jstesmy w zmien " + idDokumentu + " " + _numer);
        boolean wynik;
        Query q = em.createNativeQuery("update dokument set idNumer = :_numer where idRodzica = :idDokumentu");
        q.setParameter("_numer", _numer);
        q.setParameter("idDokumentu", idDokumentu);
        System.out.print("Przed wykonaniem przypisano zmienne");
        int ilosc_updateow = q.executeUpdate();
        System.out.print("po wykoniu" + ilosc_updateow);
//         em.getTransaction().commit();
        System.out.print("po commit");
        System.out.print("Zmieniono numer dla dokumentow " + ilosc_updateow);
        if (ilosc_updateow == 0) {
            wynik = false;
        } else {
            wynik = true;
        }
        return wynik;
    }

    public Long MAXIdDokumentuDziecka(Long idRodzica) {
        Query q = em.createQuery("SELECT max(d.id) FROM dokument d where d.idRodzica= :idRodzica");
        q.setParameter("idRodzica", idRodzica);
        return (Long) q.getSingleResult();
    }

    public double  SumaPowierzchniNumer(long idNumeru) {
//        Query q = em.createQuery("select sum(powierzchnia) from dokument where nume = :_numer and redaktor is null");
        Query q = em.createNativeQuery("select sum(powierzchnia) from dokument where idnumer = :_numer and idredaktora is null");
        q.setParameter("_numer", idNumeru);
         double sum = (Double)q.getSingleResult();
        return sum;
    }
  public List<dokument> ListaSpisTresci(Long idnumer) {
//        lista dokumentow danego kontrahenta
        Query q = em.createNativeQuery("select * from dokument k,grupadok g where g.id = k.idgrupy and idnumer = :idnumer and idRodzica is null and typ like 'Art%' order by g.idKolejnosc,nrstrony desc",dokument.class);
        q.setParameter("idnumer", idnumer);
        List <dokument> dokument1 = q.getResultList();
        System.out.print(dokument1.size());
        return dokument1;
    }
  public List<dokument> ListaHonoraria(Long idnumer) {
//        lista dokumentow danego kontrahenta
        Query q = em.createNativeQuery("select * from dokument k where idnumer = :idnumer and idRodzica is null and typ like 'Art%' order by idKontrahenta,nrstrony desc",dokument.class);
        q.setParameter("idnumer", idnumer);
        List <dokument> dokument1 = q.getResultList();
        System.out.print(dokument1.size());
        return dokument1;
    }

}
