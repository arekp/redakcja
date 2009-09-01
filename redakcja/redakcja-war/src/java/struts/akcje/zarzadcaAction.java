/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package struts.akcje;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import klient.bean.KlientFacadeLocal;
import klient.bean.configFacadeLocal;
import klient.bean.zarzadcaFacadeLocal;
import klient.encje.config;
import klient.encje.zarzadca;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 *
 * @author arekp
 */
public class zarzadcaAction extends ActionSupport {

    private String ilosc;
    private String b;
    private zarzadca zarzad;
    private String ciag;
    private String id_licencji;
    private String id;
    private Collection<zarzadca> daneZarzadca;
    @EJB
    KlientFacadeLocal klientFac = (KlientFacadeLocal) KlientFacadeLocal();
    @EJB
    configFacadeLocal configFac = (configFacadeLocal) ConfigFacadeLocal();
    @EJB
    zarzadcaFacadeLocal zarzadcaFac = (zarzadcaFacadeLocal) ZarzadcaFacadeLocal();


    private String nazwa;
    private String info;
    private String klasaKlienta;
    private String adress;
    private String miasto;
    private String wojew;
    private String idZarzadcy;


    public zarzadcaAction() {
    }

    public String execute() throws Exception {
        Map attibutes = ActionContext.getContext().getSession();
        if (getB().equals("import")) //Importowanie zarzadcow
        {
            this.CzytajDanePortala();
            attibutes.put("body", "/zarzadca/import.jsp");
        } else if (getB().equals("zaproszenie")) //Wyswietlamy zarzadcow do wysalania zaproszenia
        {
            setDaneZarzadca(zarzadcaFac.doWysylki(Integer.parseInt(configFac.findWartosc("zarzadca.id.ost.wysla").getWartosc())));
            this.zaproszenie();
            return "zaproszenie";

        } else if (getB().equals("etykiety")) //Wyswietlamy zarzadcow do wysalania zaproszenia
        {
            setDaneZarzadca(zarzadcaFac.doWysylki(Integer.parseInt(configFac.findWartosc("zarzadca.id.ost.wysla").getWartosc())));
            this.etykieta();
            return "etykieta";

        } else if (getB().equals("wysylka")) //Wyswietlamy zarzadcow do wysalania zaproszenia
        {
            setId_licencji(configFac.findWartosc("zarzadca.id.ost.wysla").getWartosc());
            int a = Integer.parseInt(getId_licencji());
            List<zarzadca> dane = zarzadcaFac.doWysylki(a);
            setDaneZarzadca(dane);
            attibutes.put("body", "/zarzadca/listaZarzad.jsp");
        } else if (getB().equals("find")) //Szukanie danych z zarzadca
        {
            System.out.print("jestesmy w szukaj zarzadca " + getCiag());
            List<zarzadca> dane = zarzadcaFac.szukajZarzadce('%' + getCiag() + '%');
            setDaneZarzadca(dane);
            attibutes.put("body", "/zarzadca/lista.jsp");
        } else if (getB().equals("add")) //Szukanie danych z zarzadca
        {
            zarzadca zarz = zarzadcaFac.find(Long.valueOf(getId()).longValue());
            setNazwa(zarz.getImie()+" "+zarz.getNazwisko());
            setKlasaKlienta("Zarządca");
            setAdress(zarz.getAdres());
            setMiasto(zarz.getKod()+" "+zarz.getMiasto());
            setWojew(zarz.getWojew());
            setInfo("Numer Licencji: "+zarz.getNumerLicencji()+" przyznana "+zarz.getDataPrzyznania().toString());
//            System.out.print(getNazwa() + "--- "+getInfo());
            attibutes.put("body", "/klient/newKlient.jsp");
        }else if (getB().equals("nrLicencji")) //Szukanie danych z zarzadca
        {
            config conf = configFac.findWartosc("zarzadca.id.ost.wysla");
            conf.setWartosc(getId_licencji());//wstawiamy ilosc ostatniego bloku
            configFac.edit(conf);
            int a = Integer.parseInt(getId_licencji());
             List<zarzadca> dane = zarzadcaFac.doWysylki(a);
            setDaneZarzadca(dane);
            attibutes.put("body", "/zarzadca/listaZarzad.jsp");
        }

        return "success";
    }

    private void zaproszenie() {
        try {
            String katalog = configFac.findWartosc("raporty.lokalizacja.etykiety").getWartosc();

            JasperCompileManager.compileReportToFile(
                    katalog + "zarzadcy.jrxml",
                    katalog + "zarzadcy.jasper");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void etykieta() {
        try {
            String katalog = configFac.findWartosc("raporty.lokalizacja.etykiety").getWartosc();

            JasperCompileManager.compileReportToFile(
                    katalog + "etykietaZ.jrxml",
                    katalog + "etykietaZ.jasper");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CzytajDanePortala() {
        String idOstatZarzadcy = configFac.findWartosc("zarzadca.id.ost.zaimport").getWartosc();
        String dataOstatImport = configFac.findWartosc("zarzadca.data.import").getWartosc();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int KONIEC = Integer.parseInt(configFac.findWartosc("zarzadca.ost.blok").getWartosc());
        int MAXhits = Integer.parseInt(configFac.findWartosc("zarzadca.maxhits").getWartosc());

        HttpURLConnection connection = null;
        URL serverAddress = null;
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;

        String id = "";
        String data = "";
        String imie = "";
        String nazwisko = "";
        String imionaRodzicow = "";
        String kod = "";
        String miasto = "";
        String ulica = "";
        String wojew = "";
        int iloscWZewnetrznejBazieHits = 99999;

        try {
            int KROK = 200;
            int a = 0;// liczymy ilosc zaimportowanych zarzadcow
            //****************************************************** sprawdzamy czy jest cos do pobrania
            String adres = "http://www.mi.gov.pl/2-482c0ddde037d.htm-ds2e_svc-rejestrmi-nieruchomosci_svc-,,,lic," + Integer.toString(KROK) + "," + Integer.toString(KONIEC) + ".html";
            serverAddress = new URL(adres);
            System.out.print(adres);
            connection = null;
            connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(10000);
            connection.connect();
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            while ((line = rd.readLine()) != null) { //Czytamy linie po lini
                String[] temp;
                temp = line.split("\t");// dzielimy na pozycje
                if (temp.length == 2) {
                    if (temp[0].equals("maxhits")) {
                        iloscWZewnetrznejBazieHits = Integer.parseInt(temp[1]);
                    }
                    System.out.print("-------------> mamay do pobranie  " + iloscWZewnetrznejBazieHits + "++" + MAXhits);
                    if (MAXhits <= iloscWZewnetrznejBazieHits) {
                    }
                }
            }
            //******************************************************

            for (int i = 0; i < 98; i++) {
                adres = "http://www.mi.gov.pl/2-482c0ddde037d.htm-ds2e_svc-rejestrmi-nieruchomosci_svc-,,,lic," + Integer.toString(KROK) + "," + Integer.toString(KONIEC) + ".html";
                serverAddress = new URL(adres);
                System.out.print(adres);
                connection = null;
                connection = (HttpURLConnection) serverAddress.openConnection();
                connection.setRequestMethod("POST");
                connection.setReadTimeout(10000);
                connection.connect();
                rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                while ((line = rd.readLine()) != null) { //Czytamy linie po lini
                    String[] temp;
                    temp = line.split("\t");// dzielimy na pozycje
//                    System.out.print("-------------> w lini mamy pozycji " + temp.length);
                    if (temp.length == 9) {
                        id = temp[0];
                        idOstatZarzadcy = configFac.findWartosc("zarzadca.id.ost.zaimport").getWartosc();
                        // Sprawdzamy czy numer licencji jest wyzszy od ostatnio dodanego
                        if (Integer.valueOf(id).intValue() > Integer.valueOf(idOstatZarzadcy).intValue()) {
                            a++;
                            data = temp[1];
                            imie = temp[2];
                            nazwisko = temp[3];
                            imionaRodzicow = temp[4];
                            kod = temp[5];
                            String adresMiasto = temp[6];
                            String[] temp1;
                            temp1 = adresMiasto.split("ul.");
                            if (temp1.length == 2) {
                                miasto = temp1[0];
                                ulica = "ul." + temp1[1];
//                        System.out.print("------------->  " + temp1.length +" mamy "+temp1[0]+"-- ul."+temp1[1]);
                            } else {//wstawiamy w pole adres wszystko
                                miasto = temp1[0];
                                System.out.print("------------->  " + temp1.length + " mamy " + temp1[0]);
                            }
                            wojew = temp[7];
                            try {
                                zarzadca zarz = new zarzadca(Integer.parseInt(id), (Date) formatter.parse(data), imie, nazwisko, kod, ulica, miasto, wojew);
                                zarzadcaFac.create(zarz);
                                //zabezpieczamy sie przed zbyt duza iloscia pobran
                                config conf = configFac.findWartosc("zarzadca.id.ost.zaimport");
                                conf.setWartosc(id);//wstawiamy ostatni pobrany numer licencji
                                configFac.edit(conf);
                            } catch (ParseException ex) {
                                Logger.getLogger(zarzadcaAction.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    } else if (temp.length == 2) {
                        if (temp[0].equals("maxhits")) {
                            iloscWZewnetrznejBazieHits = Integer.parseInt(temp[1]);
                        }
                        System.out.print("-------------> mamay do pobranie  " + iloscWZewnetrznejBazieHits);
                        if (MAXhits == iloscWZewnetrznejBazieHits) {
                            i = 99;
                            System.out.print("nie ma wiecej anizeli pobralismy ostatnio");
                        }

                    }

                }
                connection.disconnect();//rozłanczamy sesje
                KONIEC = KONIEC + KROK;
            }//Koniec petli
            config conf = configFac.findWartosc("zarzadca.maxhits");
            conf.setWartosc(Integer.toString(iloscWZewnetrznejBazieHits));//wstawiamy ilosc ostatniego bloku
            configFac.edit(conf);
            conf = configFac.findWartosc("zarzadca.data.import");
            conf.setWartosc(new Date().toString());//wstawiamy ostatni date pobierania
            configFac.edit(conf);
            conf = configFac.findWartosc("zarzadca.ost.blok");
            conf.setWartosc(Integer.toString(KONIEC - KROK));
            configFac.edit(conf);
            ilosc = Integer.toString(a);  //wyswietlimy ile sie zaimportowalo
//            System.out.print(ilosc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close the connection, set all objects to null
            connection.disconnect();
            rd = null;
            sb = null;
            wr = null;
            connection = null;
        }
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
 @AroundInvoke
    private configFacadeLocal ConfigFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (configFacadeLocal) c.lookup("redakcja/configFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
 @AroundInvoke
    private zarzadcaFacadeLocal ZarzadcaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (zarzadcaFacadeLocal) c.lookup("redakcja/zarzadcaFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the ilosc
     */
    public String getIlosc() {
        return ilosc;
    }

    /**
     * @param ilosc the ilosc to set
     */
    public void setIlosc(String ilosc) {
        this.ilosc = ilosc;
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

    /**
     * @return the zarzad
     */
    public zarzadca getZarzad() {
        return zarzad;
    }

    /**
     * @param zarzad the zarzad to set
     */
    public void setZarzad(zarzadca zarzad) {
        this.zarzad = zarzad;
    }

    /**
     * @return the daneZarzadca
     */
    public Collection<zarzadca> getDaneZarzadca() {
        return daneZarzadca;
    }

    /**
     * @param daneZarzadca the daneZarzadca to set
     */
    public void setDaneZarzadca(Collection<zarzadca> daneZarzadca) {
        this.daneZarzadca = daneZarzadca;
    }

    /**
     * @return the ciag
     */
    public String getCiag() {
        return ciag;
    }

    /**
     * @param ciag the ciag to set
     */
    public void setCiag(String ciag) {
        this.ciag = ciag;
    }

    /**
     * @return the id_licencji
     */
    public String getId_licencji() {
        return id_licencji;
    }

    /**
     * @param id_licencji the id_licencji to set
     */
    public void setId_licencji(String id_licencji) {
        this.id_licencji = id_licencji;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nazwa
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * @param nazwa the nazwa to set
     */
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return the klasaKlienta
     */
    public String getKlasaKlienta() {
        return klasaKlienta;
    }

    /**
     * @param klasaKlienta the klasaKlienta to set
     */
    public void setKlasaKlienta(String klasaKlienta) {
        this.klasaKlienta = klasaKlienta;
    }

    /**
     * @return the adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * @param adress the adress to set
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }

    /**
     * @return the miasto
     */
    public String getMiasto() {
        return miasto;
    }

    /**
     * @param miasto the miasto to set
     */
    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    /**
     * @return the wojew
     */
    public String getWojew() {
        return wojew;
    }

    /**
     * @param wojew the wojew to set
     */
    public void setWojew(String wojew) {
        this.wojew = wojew;
    }

    /**
     * @return the idZarzadcy
     */
    public String getIdZarzadcy() {
        return idZarzadcy;
    }

    /**
     * @param idZarzadcy the idZarzadcy to set
     */
    public void setIdZarzadcy(String idZarzadcy) {
        this.idZarzadcy = idZarzadcy;
    }
}