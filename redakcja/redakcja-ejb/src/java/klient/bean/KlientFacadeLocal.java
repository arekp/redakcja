/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import klient.encje.Klient;

/**
 *
 * @author arekp
 */
@Local
public interface KlientFacadeLocal {

    void create(Klient klient);

    void edit(Klient klient);

    void remove(Klient klient);

    Klient find(Object id);

    List<Klient> findAll();

    public java.util.List<klient.encje.Klient> findKlient(java.lang.String ciag);
      public String IloscEgzempl(Date data);

    public java.util.List<klient.encje.Klient> WysylkaALL(java.util.Date data);

    public java.util.List<klient.encje.Klient> WysylkaMiesiac(int miesiac,int rok);

    public java.util.List<klient.encje.Klient> WysylkaZrezygnowani(java.util.Date data);

}
