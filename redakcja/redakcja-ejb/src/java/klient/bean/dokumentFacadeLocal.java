/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klient.bean;

import java.util.List;
import javax.ejb.Local;
import klient.encje.dokument;

/**
 *
 * @author arekp
 */
@Local
public interface dokumentFacadeLocal {

    void create(dokument dokument);

    void edit(dokument dokument);

    void remove(dokument dokument);

    dokument find(Object id);

    List<dokument> findAll();

    public java.util.List<klient.encje.dokument> ListaDokumentowKontra(klient.encje.kontrahent kontra);

    public java.util.List<klient.encje.dokument> ListaDokumentowNumeru(klient.encje.numer numer);

    public java.util.List<klient.encje.dokument> ListaDokumentowWolna();

    public java.util.List<klient.encje.dokument> ListaDokumentowDzieci(java.lang.Long idRodzica);

    public boolean ZmienNumer(java.lang.Long idDokumentu, java.lang.Long _numer);

    public java.lang.Long MAXIdDokumentuDziecka(java.lang.Long idRodzica);

    public java.util.List<klient.encje.dokument> ListaDokumentowRedaktora(klient.encje.kontrahent kontra);
}
