/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import javax.ejb.Local;

/**
 *
 * @author ArekP
 */
@Local
public interface ImportBeanLocal {
       public boolean wstawKlienta(String nazwa,String ulica,String miasto,String wojewodztwo,
               String typ,String info, String prenOd,String prenDo,String ilosc,String dataPrzedl,String typ_pren) throws Exception;
}
