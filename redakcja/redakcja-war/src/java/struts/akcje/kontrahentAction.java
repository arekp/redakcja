package struts.akcje;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.opensymphony.xwork2.ActionSupport;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import klient.bean.dokumentFacadeLocal;
import klient.bean.kontrahentFacadeLocal;

import klient.encje.dokument;
import klient.encje.kontrahent;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author arekp
 */
public class kontrahentAction extends ActionSupport implements SessionAware {

    private Map session; // zmienne sesyjne
    private String b; // zmienna z urla
    private String typ;//tablica DIC_TYPflaga(autor,reklama,redaktor,barter)
    private String imie;
    private String nazwisko;
    private String ulica;
    private String miasto;
    private String email;
    private String tel;
    private String nip;
    private String pesel;
    private String info;
    private String urzadSkarbowy;
    private String numerKonta;
//    private double stawka;
    private String stawka;
    private String id;
    private Collection<kontrahent> daneKontrahent;
    private Collection<dokument> listaDokumentow;
    private kontrahent kontrahent;
    private
    @EJB
    kontrahentFacadeLocal kontrahentFac = (kontrahentFacadeLocal) kontrahentFacadeLocal();
    @EJB
    dokumentFacadeLocal dokumentFac = (dokumentFacadeLocal) dokumentFacadeLocal();

    @Override
    public String execute() throws Exception {
        if (getB().equals("addFor")) //Dodajemy numer formularz
        {
            getSession().put("body", "/kontrahent/newKontrahent.jsp");
        } else if (getB().equals("add")) //Dodaje numer
        {
            kontrahent _kontrahent = new kontrahent(getTyp(), getImie(), getNazwisko(), getUlica(), getMiasto(), getEmail(), getTel(),
                    getNip(), getPesel(), getUrzadSkarbowy(), getNumerKonta(), Double.parseDouble(getStawka()));
            // Double.parseDouble(
            kontrahentFac.create(_kontrahent);
            setKontrahent(_kontrahent);
            if (_kontrahent.getTyp().equals("Redaktor")) {
                setListaDokumentow(dokumentFac.ListaDokumentowRedaktora(getKontrahent()));
            } else {
                setListaDokumentow(dokumentFac.ListaDokumentowKontra(getKontrahent()));
            }
            getSession().put("body", "/kontrahent/kontrahentInfo.jsp");

        } else if (getB().equals("info")) //strona informacyjna kontrahenta
        {
            kontrahent _kontrahent = kontrahentFac.find(Long.parseLong(getId()));
            setKontrahent(_kontrahent);
            if (_kontrahent.getTyp().equals("Redaktor")) {
                setListaDokumentow(dokumentFac.ListaDokumentowRedaktora(getKontrahent()));
            } else {
                setListaDokumentow(dokumentFac.ListaDokumentowKontra(getKontrahent()));
            }
            getSession().put("body", "/kontrahent/kontrahentInfo.jsp");
        } else if (getB().equals("editForm")) //edycja danych
        {
            kontrahent _kontrahent = kontrahentFac.find(Long.parseLong(getId()));
            setKontrahent(_kontrahent);
            getSession().put("body", "/kontrahent/kontrahentEdit.jsp");
        } else if (getB().equals("edit")) //edycja danych
        {
            System.out.print("dostalismy " + getId() + "---> " + getStawka());

            kontrahent _kontrahent = kontrahentFac.find(Long.parseLong(getId()));
            _kontrahent.setEmail(getEmail());
            _kontrahent.setImie(getImie());
            _kontrahent.setTyp(getTyp());
            _kontrahent.setNazwisko(getNazwisko());
            _kontrahent.setUlica(getUlica());
            _kontrahent.setMiasto(getMiasto());
            _kontrahent.setTel(getTel());
            _kontrahent.setNip(getNip());
            _kontrahent.setPesel(getPesel());
            _kontrahent.setUrzadSkarbowy(getUrzadSkarbowy());
            _kontrahent.setNumerKonta(getNumerKonta());
            _kontrahent.setStawka(Double.parseDouble(getStawka()));
//            _kontrahent.setStawka(getStawka());
            _kontrahent.setInfo(getInfo());
            kontrahentFac.edit(_kontrahent);
            setKontrahent(_kontrahent);
            setListaDokumentow(dokumentFac.ListaDokumentowKontra(getKontrahent()));
            getSession().put("body", "/kontrahent/kontrahentInfo.jsp");
        }
        return "success";
    }

    private dokumentFacadeLocal dokumentFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (dokumentFacadeLocal) c.lookup("redakcja/dokumentFacade/local");
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
     * @return the imie
     */
    public String getImie() {
        return imie;
    }

    /**
     * @param imie the imie to set
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * @return the nazwisko
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * @param nazwisko the nazwisko to set
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * @return the ulica
     */
    public String getUlica() {
        return ulica;
    }

    /**
     * @param ulica the ulica to set
     */
    public void setUlica(String ulica) {
        this.ulica = ulica;
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
     * @return the pesel
     */
    public String getPesel() {
        return pesel;
    }

    /**
     * @param pesel the pesel to set
     */
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    /**
     * @return the urzadSkarbowy
     */
    public String getUrzadSkarbowy() {
        return urzadSkarbowy;
    }

    /**
     * @param urzadSkarbowy the urzadSkarbowy to set
     */
    public void setUrzadSkarbowy(String urzadSkarbowy) {
        this.urzadSkarbowy = urzadSkarbowy;
    }

    /**
     * @return the numerKonta
     */
    public String getNumerKonta() {
        return numerKonta;
    }

    /**
     * @param numerKonta the numerKonta to set
     */
    public void setNumerKonta(String numerKonta) {
        this.numerKonta = numerKonta;
    }

    /**
     * @return the stawka
     */
    public String getStawka() {
        return stawka;
    }

    /**
     * @param stawka the stawka to set
     */
    public void setStawka(String stawka) {
        System.out.print("w SET " + stawka);
//        this.stawka = Double.parseDouble(stawka);
        this.stawka = stawka;
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
     * @return the kontrahent
     */
    public kontrahent getKontrahent() {
        return kontrahent;
    }

    /**
     * @param kontrahent the kontrahent to set
     */
    public void setKontrahent(kontrahent kontrahent) {
        this.kontrahent = kontrahent;
    }

    /**
     * @return the listaDokumentow
     */
    public Collection<dokument> getListaDokumentow() {
        return listaDokumentow;
    }

    /**
     * @param listaDokumentow the listaDokumentow to set
     */
    public void setListaDokumentow(Collection<dokument> listaDokumentow) {
        this.listaDokumentow = listaDokumentow;
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
}