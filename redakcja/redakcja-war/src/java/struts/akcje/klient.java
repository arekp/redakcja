/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package struts.akcje;

import com.opensymphony.xwork2.ActionSupport;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import klient.bean.KlientFacadeLocal;
import klient.encje.Klient;
import org.apache.struts2.interceptor.SessionAware;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import klient.bean.adresFacadeLocal;
import klient.bean.zamowieniaFacadeLocal;

import klient.encje.adres;
import klient.encje.zamowienia;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.jmesa.limit.Limit;

import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;

import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;

import static org.jmesa.limit.ExportType.JEXCEL;
//import org.apache.struts2.dispatcher.StrutsResultSupport;
//import org.apache.struts2.views.jasperreports.JasperReportsResult;

/**
 *
 * @author arekp
 */
//@Validation
public class klient extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {

    private Map session;
    private String b;
    private String c;
    private String ciag;
    private String id;
    private String idadr;
    private Klient klient;
    private adres aadres;
    private String tabelkaHTML;
    private Collection<Klient> daneKlient;
    private Collection<Klient> WysylkaPrzeter;
    private Collection<Klient> WysylkaMiesiacOst;
    private Collection<Klient> WysylkaMiesiacPrzed;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @EJB
    KlientFacadeLocal klientFac = (KlientFacadeLocal) KlientFacadeLocal();
    @EJB
    zamowieniaFacadeLocal zamFac = (zamowieniaFacadeLocal) zamowieniaFacadeLocal();
    @EJB
    adresFacadeLocal adrFac = (adresFacadeLocal) adresFacadeLocal();
//    zamowienia
    private Date prenOd;
    private String okres;
    private Date prenDo;
    private String ilosc;
    private Date dataPrzedl;
    private Date numer;
    private String typ;
    private String klasa;
//edycja klienta
    private String nazwa;
    private String info;
    private String statusPren;
    private String klasaKlienta;
    private String nip;
    private String adress;
    private String miasto;
    private String wojew;
    private String tel;
    private String email;
    private String infoAdres;
    private String typAdres;
//private String typ;
//private String info;

    @Override
   public String execute() throws Exception {

        if (getB().equals("addZ")) //Dodajemy zamowienie
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(prenOd);
            cal.add(Calendar.MONTH, Integer.parseInt(getOkres()));
            cal.add(Calendar.MONTH, -1); // Data rozpoczecia jest numerem pierwszym do wysyłki wieć trzeba odjąc jeden miesiac zeby nie wysłac 13 numerów
            setPrenDo(cal.getTime());
            Klient kl = klientFac.find(Long.parseLong(getId()));
            zamowienia zam = new zamowienia(prenOd, prenDo, Integer.parseInt(ilosc), dataPrzedl, typ);
            zam.setKlient(kl);
            zam.setTyp(getKlasa());
            kl.setPrenDo(prenDo);
            kl.setTyp(getKlasa());
            kl.setIlosc(Integer.parseInt(ilosc));
            zamFac.create(zam);
            klientFac.edit(kl);

            kl = klientFac.find(Long.parseLong(getId()));
            setKlient(kl);

            getSession().remove("body");
            getSession().put("body", "/klient/klientId.jsp");
        } else if (getB().equals("id")) //Szukamy i wyswietlamy dane konta
        {
            setKlient(klientFac.find(Long.parseLong(getId())));

            getSession().remove("body");
            getSession().put("body", "/klient/klientId.jsp");
        } else if (getB().equals("find"))//Szukamy klientow wyswietlamy liste
        {
            System.out.print("jestesmy w szukaj klienta "+ getCiag());
            setDaneKlient(klientFac.findKlient(getCiag()));
            this.tabelka();
            getSession().put("body", "/klient/listaZnal.jsp");
        } else if (getB().equals("adressEdit"))//Formularz do edycji adresu
        {System.out.print(getIdadr());
            setAadres(adrFac.find(Long.parseLong(getIdadr())));
//            setKlient(klientFac.find(Long.parseLong(getId())));
            getSession().put("body", "/klient/adresEdit.jsp");
        } else if (getB().equals("editA"))//edytujemy dane adresowe
        {
            adres a = adrFac.find(Long.parseLong(getIdadr()));
            a.setAdress(getAdress());
            a.setEmail(getEmail());
            a.setInfo(getInfoAdres());
            a.setMiasto(getMiasto());
            a.setTel(getTel());
            a.setWojew(getWojew());
            adrFac.edit(a);
            setKlient(klientFac.find(a.getKlient().getId()));
            getSession().put("body", "/klient/klientId.jsp");
        } else if (getB().equals("EditkIN"))//Formularz do edycji klienta
        {
            setKlient(klientFac.find(Long.parseLong(getId())));
            getSession().put("body", "/klient/klientEdit.jsp");
        } else if (getB().equals("editK")) //edytujemy dane klienta
        {
            Klient kl = klientFac.find(Long.parseLong(getId()));
            kl.setNazwa(getNazwa());
//            kl.setTyp(getTyp());
            kl.setInfo(getInfo());
            kl.setKlasaKlienta(getKlasaKlienta());
            kl.setStatusPren(getStatusPren());
            kl.setNip(getNip());
            klientFac.edit(kl);
            setKlient(kl);
            getSession().put("body", "/klient/klientId.jsp");
        } else if (getB().equals("newKlient"))//Formularz do wprowadzania nowego klienta
        {
            getSession().put("body", "/klient/newKlient.jsp");
        } else if (getB().equals("newKlientZapisz"))// wprowadzenie nowego klienta
        {
            Klient kl = new Klient();
            kl.setNazwa(getNazwa());
            kl.setInfo(getInfo());
            kl.setKlasaKlienta(getKlasaKlienta());
            kl.setStatusPren(getStatusPren());
            kl.setNip(getNip());
            kl.setTyp(getTyp());
            adres adr = new adres(getAdress(), getMiasto(), getWojew(), getTel(), getEmail(), getTypAdres(), getInfoAdres());
            adr.setKlient(kl);
            klientFac.create(kl);
            adrFac.create(adr);

            setKlient(klientFac.find(kl.getId()));
            getSession().remove("body");
            getSession().put("body", "/klient/klientId.jsp");
        } else if (getB().equals("wysylka"))//Przygotowanie strony z danymi do wysylki
            //nie uzywana funkcjinalność przeniesione do klasy wysylka.java
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(getPrenOd());
            int miesiac = cal.get(Calendar.MONTH) + 1;
            int rok = cal.get(Calendar.YEAR);
//            System.out.print(miesiac + " -- " + rok + " all " + cal.toString() + " --> " + getPrenOd().toString());
            setWysylkaMiesiacOst(klientFac.WysylkaMiesiac(miesiac, rok));//bierzacy miesiac
//            System.out.print(getWysylkaMiesiacOst().size() + " setWysylkaMiesiacOst");
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(getPrenOd());
            cal1.set(Calendar.DAY_OF_MONTH,1);// ustawiamy zawsze pierwszy danego miesiaca zaby dzien nie miał wplywu na nieprzedluzonych
            setWysylkaPrzeter(klientFac.WysylkaZrezygnowani(cal1.getTime())); //nie przedluzyli
            System.out.print("data do nieprzedluzyli "+cal1.getTime());
            cal.add(Calendar.MONTH, 1);
            miesiac = cal.get(Calendar.MONTH) + 1;
            rok = cal.get(Calendar.YEAR);
//            System.out.print(miesiac + " -- " + rok + " all " + cal.toString());
            setWysylkaMiesiacPrzed(klientFac.WysylkaMiesiac(miesiac, rok)); // przed ostatni
//            System.out.print(getWysylkaMiesiacPrzed().size() + " WysylkaMiesiacPrzed");
            setDaneKlient(klientFac.WysylkaALL(cal.getTime())); //pozostali

//            System.out.print(getDaneKlient().size() + "DaneKlient");
            getSession().put("body", "/klient/wysylka.jsp");

        } else if (getB().equals("numer"))//ustawiamy date dla numeru nad kturym pracujemy
        {
            if (getSession().get("numer") == null) {
                System.out.print("brak zmiennej numer " + getSession().get("numer"));
                setNumer(new Date());
                getSession().put("numer", getNumer());
            }
            if (getSession().get("numer") != null) {
                System.out.print("Numer nie jest null " + getSession().get("numer"));
//                setNumer(new Date());
            }
            if (getNumer() != null) {
                getSession().put("numer", getNumer());
            }
        } 
        else {
            getSession().put("body", "/body.jsp");
        }
//        System.out.print("Body ma " + getSession().get("body"));
        return "success";
    }

    private void tabelka() {
        TableFacade tableFacade = TableFacadeFactory.createStruts2TableFacade("klient_id", getServletRequest());
        tableFacade.setColumnProperties("nazwa", "adresy[0].adress", "adresy[0].miasto", "info", "ilosc", "prenDo");
        tableFacade.setItems(daneKlient);
        tableFacade.setExportTypes(getServletResponse(), JEXCEL);
        Limit limit = tableFacade.getLimit();
        if (limit.isExported()) {
            export(tableFacade);
//            return null; // In Spring returning null tells the controller not to do anything.
        } else {

//         tableFacade.setMaxRows(6);
            HtmlTable table = (HtmlTable) tableFacade.getTable();
            table.setCaption("Wynik wyszukiwania");
            HtmlRow row = table.getRow();
            HtmlColumn id_nazwa = row.getColumn("nazwa");
            Column adres = row.getColumn("adresy[0].adress");
            adres.setTitle("Adres");
            Column miasto = row.getColumn("adresy[0].miasto");
            miasto.setTitle("Miasto");
            id_nazwa.getCellRenderer().setCellEditor(new CellEditor() {

                public Object getValue(Object item, String property, int rowcount) {
                    Object value = new BasicCellEditor().getValue(item, "id", rowcount);
                    Object valueOpis = new BasicCellEditor().getValue(item, "nazwa", rowcount);
                    HtmlBuilder html = new HtmlBuilder();
                    html.a().href().quote().append("/redakcja/klient/klient_id.action?id=" + value.toString()).quote().close();
                    //  html.ahref("idReleasu", value.toString());
                    html.append(valueOpis);
                    html.aEnd();
                    return html.toString();
                }
            });
            setTabelkaHTML(tableFacade.render());
        }
    }

    private void export(TableFacade tableFacade) {
        // set the column properties
        tableFacade.setColumnProperties("nazwa", "adresy[0].adress", "adresy[0].miasto", "ilosc", "info", "prenDo", "typ", "adresy[0].wojew", "klasaKlienta", "statusPren");
        tableFacade.setItems(daneKlient);
        Table table = tableFacade.getTable();
        table.setCaption("Wyniki");
        Row row = table.getRow();
        Column firstName = row.getColumn("nazwa");
        firstName.setTitle("Nazwa usera");
        Column lastName = row.getColumn("info");
        lastName.setTitle("Informacje o kliencie");
        setTabelkaHTML(tableFacade.render()); // Will write the export data out to the response.
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
    private zamowieniaFacadeLocal zamowieniaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (zamowieniaFacadeLocal) c.lookup("redakcja/zamowieniaFacade/local");
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

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
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
     * @return the daneKlient
     */
    public Collection getDaneKlient() {
        return daneKlient;
    }

    /**
     * @param daneKlient the daneKlient to set
     */
    public void setDaneKlient(Collection daneKlient) {
        this.daneKlient = daneKlient;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getServletRequest() {
        return request;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletResponse getServletResponse() {
        return response;
    }

    /**
     * @return the tabelkaHTML
     */
    public String getTabelkaHTML() {
        return tabelkaHTML;
    }

    /**
     * @param tabelkaHTML the tabelkaHTML to set
     */
    public void setTabelkaHTML(String tabelkaHTML) {
        this.tabelkaHTML = tabelkaHTML;
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
     * @return the klient
     */
    public Klient getKlient() {
        return klient;
    }

    /**
     * @param klient the klient to set
     */
    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    /**
     * @return the prenOd
     */
    public Date getPrenOd() {
        return prenOd;
    }

    /**
     * @param prenOd the prenOd to set
     */
    public void setPrenOd(Date prenOd) {
        this.prenOd = prenOd;
    }

    /**
     * @return the okres
     */
    public String getOkres() {
        return okres;
    }

    /**
     * @param okres the okres to set
     */
    public void setOkres(String okres) {
        this.okres = okres;
    }

    /**
     * @return the prenDo
     */
    public Date getPrenDo() {
        return prenDo;
    }

    /**
     * @param prenDo the prenDo to set
     */
    public void setPrenDo(Date prenDo) {
        this.prenDo = prenDo;
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
     * @return the dataPrzedl
     */
    public Date getDataPrzedl() {
        return dataPrzedl;
    }

    /**
     * @param dataPrzedl the dataPrzedl to set
     */
    public void setDataPrzedl(Date dataPrzedl) {
        this.dataPrzedl = dataPrzedl;
    }

    /**
     * @return the typ
     */
    public String getTyp() {
        return typ;
    }

    /**
     * @param typ the typ to set
     */
    public void setTyp(String typ) {
        this.typ = typ;
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
     * @return the klasa
     */
    public String getKlasa() {
        return klasa;
    }

    /**
     * @param klasa the klasa to set
     */
    public void setKlasa(String klasa) {
        this.klasa = klasa;
    }

    /**
     * @return the statusPren
     */
    public String getStatusPren() {
        return statusPren;
    }

    /**
     * @param statusPren the statusPren to set
     */
    public void setStatusPren(String statusPren) {
        this.statusPren = statusPren;
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
     * @return the nip
     */
    public String getNip() {
        return nip;
    }

    /**
     * @param nip the nip to set
     */
    public void setNip(String nip) {
        this.nip = nip;
    }

    /**
     * @return the WysylkaPrzeter
     */
    public Collection<Klient> getWysylkaPrzeter() {
        return WysylkaPrzeter;
    }

    /**
     * @param WysylkaPrzeter the WysylkaPrzeter to set
     */
    public void setWysylkaPrzeter(Collection<Klient> WysylkaPrzeter) {
        this.WysylkaPrzeter = WysylkaPrzeter;
    }

    /**
     * @return the WysylkaMiesiacOst
     */
    public Collection<Klient> getWysylkaMiesiacOst() {
        return WysylkaMiesiacOst;
    }

    /**
     * @param WysylkaMiesiacOst the WysylkaMiesiacOst to set
     */
    public void setWysylkaMiesiacOst(Collection<Klient> WysylkaMiesiacOst) {
        this.WysylkaMiesiacOst = WysylkaMiesiacOst;
    }

    /**
     * @return the WysylkaMiesiacPrzed
     */
    public Collection<Klient> getWysylkaMiesiacPrzed() {
        return WysylkaMiesiacPrzed;
    }

    /**
     * @param WysylkaMiesiacPrzed the WysylkaMiesiacPrzed to set
     */
    public void setWysylkaMiesiacPrzed(Collection<Klient> WysylkaMiesiacPrzed) {
        this.WysylkaMiesiacPrzed = WysylkaMiesiacPrzed;
    }

    /**
     * @return the c
     */
    public String getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(String c) {
        this.c = c;
    }

    /**
     * @return the numer
     */
    public Date getNumer() {
        return numer;
    }

    /**
     * @param numer the numer to set
     */
    public void setNumer(Date numer) {
        this.numer = numer;
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
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the infoAdres
     */
    public String getInfoAdres() {
        return infoAdres;
    }

    /**
     * @param infoAdres the infoAdres to set
     */
    public void setInfoAdres(String infoAdres) {
        this.infoAdres = infoAdres;
    }

    /**
     * @return the typAdres
     */
    public String getTypAdres() {
        return typAdres;
    }

    /**
     * @param typAdres the typAdres to set
     */
    public void setTypAdres(String typAdres) {
        this.typAdres = typAdres;
    }

    /**
     * @return the aadres
     */
//    @RequiredStringValidator(message="Adres jest polem wymaganym")
    public adres getAadres() {
        return aadres;
    }

    /**
     * @param aadres the aadres to set
     */
//    @RequiredStringValidator(message = "Adres jest wymaganym polem")
    public void setAadres(adres aadres) {
        this.aadres = aadres;
    }

    /**
     * @return the idadr
     */
    public String getIdadr() {
        return idadr;
    }

    /**
     * @param idadr the idadr to set
     */
    public void setIdadr(String idadr) {
        this.idadr = idadr;
    }
}