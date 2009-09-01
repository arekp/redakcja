/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package struts.akcje;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Collection;
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
import klient.bean.kontrahentFacadeLocal;
import klient.bean.zarzadcaFacadeLocal;
import klient.encje.Klient;
import klient.encje.kontrahent;
import klient.encje.zarzadca;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author arekp
 */
public class szukaj extends ActionSupport implements SessionAware {

    private Map session;
    private String ciag;
    private String szuk_akcja;
    private Collection<Klient> daneKlient;
    private Collection<zarzadca> daneZarzadca;
    private Collection<kontrahent> daneKontrahent;
    @EJB
    KlientFacadeLocal klientFac = (KlientFacadeLocal) KlientFacadeLocal();
    @EJB
    zarzadcaFacadeLocal zarzadcaFac = (zarzadcaFacadeLocal) ZarzadcaFacadeLocal();
    @EJB
    kontrahentFacadeLocal kontrahentFac = (kontrahentFacadeLocal) kontrahentFacadeLocal();

    public String execute() throws Exception {
//        System.out.print("jestesmy w szukaj szukaj " + getCiag() + "  " + getSzuk_akcja());

        if (getSzuk_akcja().equals("klient")) //Dodajemy zamowienie
        {
//            System.out.print("jestesmy w szukaj klienta " + getCiag());
//            setDaneKlient(klientFac.findKlient(getCiag()));
////               this.tabelka();
//            getSession().put("body", "/klient/listaZnal.jsp");
            return "klient";
        } else if (getSzuk_akcja().equals("zarzadca")) //Szukamy i wyswietlamy dane konta
        {
            System.out.print("jestesmy w szukaj zarzadca " + getCiag());
            List<zarzadca> dane = zarzadcaFac.szukajZarzadce('%' + getCiag() + '%');
            setDaneZarzadca(dane);
            getSession().put("body", "/zarzadca/lista.jsp");
        } else if (getSzuk_akcja().equals("kontrahent")) //Szukamy i wyswietlamy dane kontrahenta
        {
            System.out.print("jestesmy w szukaj kontrahent " + getCiag());
            List<kontrahent> dane =  kontrahentFac.findKontrahent(getCiag());
            setDaneKontrahent(dane);
            getSession().put("body", "/kontrahent/lista.jsp");
        }
        return "success";
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
     * @return the szuk_akcja
     */
    public String getSzuk_akcja() {
        return szuk_akcja;
    }

    /**
     * @param szuk_akcja the szuk_akcja to set
     */
    public void setSzuk_akcja(String szuk_akcja) {
        this.szuk_akcja = szuk_akcja;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
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
    public void setDaneKlient(Collection<Klient> daneKlient) {
        this.daneKlient = daneKlient;
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
     * @return the daneKontrahent
     */
    public Collection<kontrahent> getDaneKontrahent() {
        return daneKontrahent;
    }

    /**
     * @param daneKontrahent the daneKontrahent to set
     */
    public void setDaneKontrahent(Collection<kontrahent> daneKontrahent) {
        this.daneKontrahent = daneKontrahent;
    }
}