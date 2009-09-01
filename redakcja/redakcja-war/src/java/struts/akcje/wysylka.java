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
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import klient.bean.configFacadeLocal;
import klient.encje.numer;
import net.sf.jasperreports.engine.JasperCompileManager;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
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

/**
 *
 * @author arekp
 */
public class wysylka extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {

    private Map session; // zmienne sesyjne
    private String b; // zmienna z urla
    private String akcja = "";
    private numer numer;
    private String tabelkaHTML;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Collection<Klient> daneKlient;
    @EJB
    KlientFacadeLocal klientFac = (KlientFacadeLocal) KlientFacadeLocal();
    @EJB
    configFacadeLocal configFac = (configFacadeLocal) ConfigFacadeLocal();

    @Override
    public String execute() throws Exception {
        setNumer((numer) getSession().get("numer"));

        if (getNumer() == null) {
            addActionMessage("Brak określonego miesiąca");
            return SUCCESS;
        }
        if (getB().equals("ostatni")) //Wysylka klienci z ostatnim miesiacem prenumeraty
        {
            numer _numer = getNumer();

            Calendar cal = Calendar.getInstance();
            cal.setTime(_numer.getData());
            int miesiac = cal.get(Calendar.MONTH) + 1;
            int rok = cal.get(Calendar.YEAR);
            System.out.print(miesiac + " -- " + rok + " all " + cal.toString() + " --> " + getNumer().toString());
            setDaneKlient(klientFac.WysylkaMiesiac(miesiac, rok));//bierzacy miesiac
        } else if (getB().equals("przed")) //Przed ostatnia wysylka
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(getNumer().getData());
            cal.add(Calendar.MONTH, 1);
            int miesiac = cal.get(Calendar.MONTH) + 1;
            int rok = cal.get(Calendar.YEAR);
            System.out.print(miesiac + " -- " + rok + " all " + cal.toString());
            setDaneKlient(klientFac.WysylkaMiesiac(miesiac, rok)); // przed ostatni
        } else if (getB().equals("reszta")) //Wysylka pozostali od numeru plus 1 miesiac
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(getNumer().getData());
            cal.add(Calendar.MONTH, 1);
            setDaneKlient(klientFac.WysylkaALL(cal.getTime()));
            System.out.print("reszta " + getDaneKlient().size());
        } else if (getB().equals("faktura")) {
            //Dopisac zapytanie do katur
        } else if (getB().equals("brak")) {
              Calendar cal = Calendar.getInstance();
            cal.setTime(getNumer().getData());
            cal.set(Calendar.DAY_OF_MONTH,1);// ustawiamy zawsze pierwszy danego miesiaca zaby dzien nie miał wplywu na nieprzedluzonych
            setDaneKlient(klientFac.WysylkaZrezygnowani(cal.getTime())); //nie przedluzyli
        } else {
        }


        if (getAkcja().equals("etykieta")) {
            this.etykieta();
            return "etykieta";
        } else {
            this.tabelka();
            getSession().put("body", "/klient/wysylkaRaport.jsp");
            return "success";
        }

    }

    private void etykieta() {
        try {
            String katalog = configFac.findWartosc("raporty.lokalizacja.etykiety").getWartosc();
                   String katalogjboss = configFac.findWartosc("raporty.lokalizacja.jboss").getWartosc();
            JasperCompileManager.compileReportToFile(
                    katalogjboss + "etykieta.jrxml",
                    katalogjboss + "etykieta.jasper");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tabelka() {
        TableFacade tableFacade = TableFacadeFactory.createStruts2TableFacade("klient_id", getServletRequest());
        tableFacade.setColumnProperties("nazwa", "adresy[0].adress", "adresy[0].miasto", "info", "ilosc", "prenDo", "statusPren");
        tableFacade.setItems(getDaneKlient());
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
        tableFacade.setColumnProperties("nazwa", "adresy[0].adress", "adresy[0].miasto", "ilosc", "prenDo");
        tableFacade.setItems(getDaneKlient());
        Table table = tableFacade.getTable();
        table.setCaption("Raport do wysyłki");
        Row row = table.getRow();
        Column firstName = row.getColumn("nazwa");
        firstName.setTitle("Nazwa usera");
        setTabelkaHTML(tableFacade.render()); // Will write the export data out to the response.
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
     * @return the daneKlient
     */
    public Collection<Klient> getDaneKlient() {
        return daneKlient;
    }

    /**
     * @param daneKlient the daneKlient to set
     */
    public void setDaneKlient(Collection<Klient> DdaneKlient) {
        this.daneKlient = DdaneKlient;
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

    /**
     * @return the akcja
     */
    public String getAkcja() {
        return akcja;
    }

    /**
     * @param akcja the akcja to set
     */
    public void setAkcja(String akcja) {
        this.akcja = akcja;
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
     * @return the numer
     */
    public numer getNumer() {
        return numer;
    }

    /**
     * @param numer the numer to set
     */
    public void setNumer(numer numer) {
        this.numer = numer;
    }
    /**
     * @param daneKlient the daneKlient to set
     */
}