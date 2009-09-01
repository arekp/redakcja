/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package struts.akcje;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import klient.bean.KlientFacade;
import klient.bean.KlientFacadeLocal;
import klient.bean.adresFacadeLocal;
import klient.bean.dokumentFacadeLocal;
import klient.bean.kontrahentFacadeLocal;
import klient.bean.zamowieniaFacadeLocal;
import klient.encje.Klient;
import klient.encje.adres;
import klient.encje.kontrahent;
import klient.encje.zamowienia;


/**
 *
 * @author arekp
 */
public class formula extends ActionSupport {

    @EJB
    KlientFacadeLocal klientFac = (KlientFacadeLocal) KlientFacadeLocal();
    @EJB
    zamowieniaFacadeLocal zamFac = (zamowieniaFacadeLocal) zamowieniaFacadeLocal();
    @EJB
    adresFacadeLocal adrFac = (adresFacadeLocal) adresFacadeLocal();
    @EJB
    kontrahentFacadeLocal kontrahentFac = (kontrahentFacadeLocal) kontrahentFacadeLocal();

    public formula() {
    }
    private File file;
    private String contentType;
    private String filename;
    private int iloscKont;
    private String b;

    public void setUpload(File file) {
        this.file = file;
    }

    public void setUploadContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUploadFileName(String filename) {
        this.filename = filename;
    }

    public String getMyDocFileName() {
        return filename;
    }

    public int getIloscKont() {
        return iloscKont;
    }

    public Long getSizeFile() {
        return file.getTotalSpace();
    }

    @Override
    public String execute() throws Exception {
        Map attibutes = ActionContext.getContext().getSession();
        System.out.print("START " + getB());

//         KlientJpaController klientJpaController = new KlientJpaController();
//         iloscKont=klientJpaController.getKlientCount();
        if (getB().equals("in")) {
            attibutes.put("body", "/admin/importDanych.jsp");
        }
        if (getB().equals("wczytaj")) {
            System.out.print("pobrało " + file.getTotalSpace());
            System.out.print("nazwa  " + filename);
            CzytajPlik(file);
            attibutes.put("body", "/admin/ok.jsp");
        }if (getB().equals("kontrahent")) {
            System.out.print("pobrało " + file.getTotalSpace());
            System.out.print("nazwa  " + filename);
            Czytajkontrahent(file);
            attibutes.put("body", "/admin/ok.jsp");
        }
        return "sukcess";
    }
 public void Czytajkontrahent(File aFile) throws ParseException {

        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!


            BufferedReader input = new BufferedReader(new FileReader(aFile));
            try {
                String line = null; //not declared within while loop


                int j = 0;
                while ((line = input.readLine()) != null) {
                  kontrahent _kontrahent = new kontrahent();
                    String[] temp;
                    temp = line.split(";");
                    String nazwisko  = temp[0];
                    String imie = temp[1];
                    String miasto = temp[2];
                    String ulica = temp[3];
                    String info = temp[4];
                    String tel = temp[5];
                    String nip = temp[6];
                    String pesel = temp[7];
                     info = info+" " + temp[8];
_kontrahent.setNazwisko(nazwisko);
_kontrahent.setImie(imie);
_kontrahent.setMiasto(miasto);
_kontrahent.setUlica(ulica);
_kontrahent.setInfo(info);
_kontrahent.setTel(tel);
_kontrahent.setNip(nip);
_kontrahent.setPesel(pesel);
kontrahentFac.create(_kontrahent);
                    System.out.println("--------------------------------------" + j++);
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void CzytajPlik(File aFile) throws ParseException {

        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!


            BufferedReader input = new BufferedReader(new FileReader(aFile));
            try {
                String line = null; //not declared within while loop


                int j = 0;
                while ((line = input.readLine()) != null) {
                  Klient klient = new Klient();
  adres adresT = new adres();
   zamowienia zam = new zamowienia();

                    String[] temp;
                    temp = line.split(";");
                    String nazwa = temp[0];
                    String ulica = temp[1];
                    String miasto = temp[2];
                    String ilosc = temp[3];
                    String info = temp[4];
                    String prenOd = temp[5];
                    String prenDo = temp[6];
                    String typ = temp[7];//typ prenumeraty Pelna,ulgowa -- klient,zamowienie
                    String dataPrzedl = temp[8];
                    String typ_pren = temp[9];//Przedluzenie
                    String wojewodztwo = temp[10];
                    String klasaKlienta =temp[11];//tbs/spółdzielnia -- klienta
                    String statusPren = temp[12];//stala--klienta
//        ZapiszDoBazy(nazwa, ulica, miasto, wojewodztwo, typ, info, prenOd, prenDo, ilosc, dataPrzedl, typ_pren);
//                   System.out.print(temp[0]+temp[1]+ temp[2]+temp[3]+ temp[4]+ temp[5]+ temp[6]+ temp[7]+ temp[8]+ temp[9]+temp[10]+temp[11]+temp[12]);
                    
                    klient.setNazwa(nazwa);
                    klient.setTyp(typ);
                    klient.setIlosc(new Integer(ilosc));
                    klient.setInfo(info);
                    klient.setPrenDo(new SimpleDateFormat("yyyy-MM-dd").parse(prenDo));
                    klient.setStatusPren(statusPren);
                    klient.setKlasaKlienta(klasaKlienta);
                  
                    adresT.setAdress(ulica);
                    adresT.setMiasto(miasto);
                    adresT.setWojew(wojewodztwo);
                    adresT.setKlient(klient);
                    

                   
                    zam.setIlosc(new Integer(ilosc));
                    zam.setPrenDo(new SimpleDateFormat("yyyy-MM-dd").parse(prenDo));
                    zam.setPrenOd(new SimpleDateFormat("yyyy-MM-dd").parse(prenOd));
                    zam.setTypPren(typ_pren);
                    zam.setDataPrzedl(new SimpleDateFormat("yyyy-MM-dd").parse(dataPrzedl));
                    zam.setTyp(typ);
                    zam.setKlient(klient);

                    klientFac.create(klient);
                    adrFac.create(adresT);
//                    klient.setIdAdresWysylki(adresT.getId());
                    zamFac.create(zam);

//                    for (int i = 0; i < temp.length; i++) {
//                        System.out.println(i + " --> " + temp[i]);
//                    }
                    System.out.println("--------------------------------------" + j++);
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return the b
     */
    public String getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(String b) {
        this.b = b;
    }
 @AroundInvoke
    private KlientFacadeLocal KlientFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (KlientFacadeLocal) c.lookup("redakcja/KlientFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private kontrahentFacadeLocal kontrahentFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (kontrahentFacadeLocal) c.lookup("redakcja/kontrahentFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
 @AroundInvoke
    private adresFacadeLocal adresFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (adresFacadeLocal) c.lookup("redakcja/adresFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
 @AroundInvoke
    private zamowieniaFacadeLocal zamowieniaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (zamowieniaFacadeLocal) c.lookup("redakcja/zamowieniaFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}