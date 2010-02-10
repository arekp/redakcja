package struts.akcje;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.opensymphony.xwork2.ActionSupport;

import freemarker.core.ParseException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

import javax.mail.MessagingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import klient.bean.configFacadeLocal;
import klient.bean.dokumentFacadeLocal;
import klient.bean.grupaDokFacadeLocal;
import klient.bean.kontrahentFacadeLocal;
import klient.bean.plikFacadeLocal;
import klient.encje.dokument;
import klient.encje.grupaDok;
import klient.encje.kontrahent;
import klient.encje.plik;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.SessionAware;

import util.sendMailLocal;

/**
 *
 * @author arekp
 */
public class dokumentAction extends ActionSupport implements SessionAware {

    /**
     * Założenia: dokument posiada tylko jeden plik. redagowanie polega na zrobieniu kopi redagowanego dokumentu,
     * dopiero po zredagowaniu zmieniamy scieszkę i dodajemy poprawiony plik. Pliki przenosimy do katalogu idRodzica
     *
     */
    private Map session; // zmienne sesyjne
    private String b; // zmienna z urla
    private Long idRodzica;
    private String idDokumentu;
    private String idDokumentReda;
    private String idGrupy;
    private kontrahent kontrahent;
    private kontrahent redaktor;// id z tablicy autor z flaga redaktor osoba przypisana do redagowania tekstu
    private String typ;//tablica DIC_FLAGA Flaga(Artukul,reklama,michalki)
    private String tytul;
    private String status;//Status(nowy,redagowany,zlozony_artukuł,zamknięty)
    private int iloscZnakow;
    private String nrStrony;
    private String powierzchnia;
    private String id_kontrah;
    private double cena;
    private Date data;
    private File file;
    private String fileContentType;
    private String fileFileName;
    private InputStream inputStream;
    private Collection<dokument> listaDokumentow;
    private Collection<dokument> listaDokumentowDzieci;
    private Collection<kontrahent> listaRedaktorow;
    private Collection<grupaDok> listaGrup;
    private dokument dokument;
    private dokument dokumentred;
    private plik plik;
    @EJB
    dokumentFacadeLocal dokumentFac = (dokumentFacadeLocal) dokumentFacadeLocal();
    @EJB
    configFacadeLocal configFac = (configFacadeLocal) ConfigFacadeLocal();
    @EJB
    plikFacadeLocal plikFac = (plikFacadeLocal) plikFacadeLocal();
    @EJB
    kontrahentFacadeLocal kontrahentFac = (kontrahentFacadeLocal) kontrahentFacadeLocal();
    @EJB
    sendMailLocal sendMailFac = (sendMailLocal) sendMailLocal();
    @EJB
    grupaDokFacadeLocal grupaDokFac = (grupaDokFacadeLocal) grupaDokFacadeLocal();

    @Override
    public String execute() throws Exception {
        System.out.print("Mamy dane  " + getTyp() + " " + getId_kontrah() + " " + getIdDokumentu() + " " + getIdDokumentReda() + " B " + getB() + " --> " + getIdGrupy());
        if (getB().equals("addFor")) //Dodajemy numer formularz
        {
            kontrahent _kontrahent = kontrahentFac.find(Long.parseLong(getId_kontrah()));
            setListaGrup(grupaDokFac.findAll());
//            System.out.print("Wysyłamu " + _kontrahent.getId());
            setKontrahent(_kontrahent);
            getSession().put("body", "/dokument/dokAdd.jsp");
        } else if (getB().equals("add")) //Dodaje dokument
        {
            boolean blad = false;

            if (getTytul().length() == 0) {
                addFieldError("tytul", "Tytuł jest wymaganym polem");
                blad = true;
            }
            if (getFile() == null) {
                addFieldError("file", "Musisz załączyć plik");
                blad = true;
            }
            if ((getIloscZnakow() == 0) && (!getTyp().equals("Reklama"))) {
                addFieldError("iloscZnakow", "Musisz podać ilość znaków");

                blad = true;
            }
            if (blad) {
                setId_kontrah(getId_kontrah());
                setListaGrup(grupaDokFac.findAll());
                return "success";
            }

            dokument _dokument = new dokument();
            _dokument.setData(new Date());

            _dokument.setCena(getCena());
            _dokument.setTyp(getTyp());
            _dokument.setTytul(getTytul());
            _dokument.setNrStrony("0");
            kontrahent _kontrahent = kontrahentFac.find(Long.parseLong(getId_kontrah()));
            String znakiPDF = configFac.findWartosc("ilosc.zankow.strona.skladu").getWartosc();
//sprawdzanie czy kontahent jest reklamodawca 
//            _dokument.setPowierzchnia(Double.parseDouble(getPowierzchnia()));
            System.out.print("Przed dane do reklamy");
            if (getTyp().equals("Reklama")) {
                System.out.print("START dane do reklamy");
                _dokument.setStatus("Zamkniete");
                _dokument.setIloscZnakow(0);
                _dokument.setPowierzchnia(new BigDecimal(getPowierzchnia()).setScale(2, BigDecimal.ROUND_UP).doubleValue());
//                _dokument.setGrupa(null);
                System.out.print("mamy dane do reklamy");
                dokumentFac.create(_dokument);
            } else {
                _dokument.setStatus("Nowy");
                _dokument.setIloscZnakow(getIloscZnakow());
                double pow = getIloscZnakow() / Double.parseDouble(znakiPDF);
                System.out.print("pow " + pow);
//            _dokument.setPowierzchnia(new DecimalFormat("#,##0.00").format(pow));
                _dokument.setPowierzchnia(new BigDecimal(pow).setScale(2, BigDecimal.ROUND_UP).doubleValue());
                System.out.print("pow " + _dokument.getPowierzchnia());
                grupaDok _grupaDok = grupaDokFac.find(Long.parseLong(getIdGrupy()));
                System.out.print("mamy dane do grupa " + _grupaDok.getNazwa());
                dokumentFac.create(_dokument);
                _dokument.setGrupa(_grupaDok);
            }

            System.out.print("Mamay kontrache" + _kontrahent.getId());
            _dokument.setKont(_kontrahent);
            System.out.print("PO kontrache");
            dokumentFac.edit(_dokument);
            System.out.print("PO zapisaniu dokumentu");
            System.out.print("mamy dokument" + _dokument.getId() + " --> " + getId_kontrah());
            if (getFile().exists()) {
                System.out.print("mamy plik");
                System.out.print("pobrało " + getFile().getUsableSpace());
                System.out.print("nazwa  " + file.getName() + "--> " + getFileFileName());
                System.out.print("contentType  " + getFileContentType());
                ZapiszPlik(getFile(), _dokument.getId());
            } else {
                System.out.print("Nie mamy pliku");
            }
            setKontrahent(kontrahentFac.find(Long.parseLong(getId_kontrah())));
            setListaDokumentow(dokumentFac.ListaDokumentowKontra(kontrahentFac.find(Long.parseLong(getId_kontrah()))));

            getSession().put("body", "/kontrahent/kontrahentInfo.jsp");

        } else if (getB().equals("info")) {

            this.info();
            getSession().put("body", "/dokument/dokumentInfo.jsp");
        } else if (getB().equals("send")) {
            this.sendFile(getIdDokumentu());
            return "sendFile";
        } else if (getB().equals("redaguj")) {
            this.redaguj();
            getSession().put("body", "/dokument/dokumentInfo.jsp");

        } else if (getB().equals("zamknij")) {
            this.zamknij();
            this.info();
            getSession().put("body", "/dokument/dokumentInfo.jsp");

        } else if (getB().equals("sklad")) {
            this.sklad();
            this.info();
            getSession().put("body", "/dokument/dokumentInfo.jsp");

        } else if (getB().equals("zamknijDok")) {

            boolean blad = false;
            if (getFile() == null) {
                addFieldError("file", "Musisz załączyć plik");
                blad = true;
            }
            if (getIloscZnakow() == 0) {
                addFieldError("iloscZnakow", "Musisz podać ilość znaków po składzie");
                blad = true;
            }
            if (blad) {
                this.info();
                return "success";
            }

            this.zamknijDok();
            this.info();
            getSession().put("body", "/dokument/dokumentInfo.jsp");

        } else if (getB().equals("modifForm")) {

            getSession().put("body", "/dokument/dokumentInfo.jsp");
        } else if (getB().equals("strona")) {
            this.strona();
            this.info();
        } else if (getB().equals("wyslij")) {
            dokument _dokument = dokumentFac.find(Long.parseLong(getIdDokumentReda()));
            setIdDokumentu(_dokument.getIdRodzica().toString());
            String katalog = configFac.findWartosc("dokumenty.lokalizacja").getWartosc();
            String cc = configFac.findWartosc("mail.cc").getWartosc();
            plik _plik = plikFac.findPlik(_dokument.getId(), "dokument");
            String url = katalog + _plik.getUrl() + "\\" + _plik.getFilename();
            String body = "\t Redakcja Administratora zlecił ci zredagowanie dokumentu " + _dokument.getTytul() + " \n Po skończeniu pracy proszę o odesłanie poprawionego dokumetu na adres zwrotny \n" +
                    "\n \n Pozdrawiam \n Sekretarz Redakcji";
            sendMailFac.sendMsg(_dokument.getRedaktor().getEmail(), cc, "Do Zredagowania: " + _dokument.getTytul(), body, url);

            this.info();
            getSession().put("body", "/dokument/dokumentInfo.jsp");
        }
//  getSession().put("body", "/dokument/dokumentInfo.jsp");
//        getSession().put("body", "/body.jsp");
        return "success";
    }

    public void zamknijDok() {

        this.zamknij();
        String znakiNaStrone = configFac.findWartosc("ilosc.znakow.strona.maszynopisu").getWartosc();
        String znakiPDF = configFac.findWartosc("ilosc.zankow.strona.skladu").getWartosc();
        dokument _dokument = dokumentFac.find(Long.parseLong(getIdDokumentu()));
        System.out.print("zaribil autor " + getIloscZnakow());
        _dokument.setStatus("Zamkniete");
        _dokument.setIloscZnakow(getIloscZnakow());
        double iloscZnak = Double.parseDouble(znakiNaStrone);
        System.out.print("zaribil autor " + iloscZnak);
        double iloscStron = _dokument.getIloscZnakow() / iloscZnak;
        System.out.print("zaribil autor " + iloscStron + " ---> " + _dokument.getKont().getStawka());
        double zarobil = iloscStron * _dokument.getKont().getStawka();
        System.out.print("zaribil autor " + zarobil);
        _dokument.setCena(new BigDecimal(zarobil).setScale(2, BigDecimal.ROUND_UP).doubleValue());
        System.out.print("zajmuje  " + getIloscZnakow() + " --> " + Integer.parseInt(znakiPDF));
        double pow = getIloscZnakow() / Double.parseDouble(znakiPDF);
        System.out.print("zajmuje  " + pow);
        System.out.print("zajmuje  2 " + new DecimalFormat("#,##0.00").format(pow));
//        _dokument.setPowierzchnia(new DecimalFormat("#,##0.00").format(pow));
        _dokument.setPowierzchnia(new BigDecimal(pow).setScale(2, BigDecimal.ROUND_UP).doubleValue());

        dokumentFac.edit(_dokument);
    }

    public void sklad() {
        System.out.print("jestesmy w sklad" + getIdDokumentReda());
        //metoda wysłania maila do skladu razem z załącznikum
        dokument _dokument = dokumentFac.find(Long.parseLong(getIdDokumentReda()));
        plik _plik = plikFac.findPlik(Long.parseLong(getIdDokumentReda()), "dokument");
        String idSkladu = configFac.findWartosc("sklad.id").getWartosc();
        String katalog = configFac.findWartosc("dokumenty.lokalizacja").getWartosc();
        String cc = configFac.findWartosc("mail.cc").getWartosc();
        kontrahent _redaktor = kontrahentFac.find(Long.parseLong(idSkladu));//komu przypisaliśmy
        //Mamy kopie dokumentu
        dokument _dokumentNew = new dokument(_dokument.getTytul(), _dokument.getCena(), _dokument.getIdRodzica(), _dokument.getIloscZnakow(), _dokument.getNrStrony(), _dokument.getPowierzchnia(), _dokument.getStatus(), _dokument.getTyp());
        System.out.print("mamy NOWY  dokument " + _dokumentNew.getId() + " " + _dokumentNew.getStatus());

        _dokumentNew.setStatus("W składzie");
        _dokumentNew.setData(new Date());
        _dokumentNew.setCena(0);

        dokumentFac.create(_dokumentNew);

        _dokumentNew.setKont(_dokument.getKont());
        _dokumentNew.setNume(_dokument.getNume());
        _dokumentNew.setRedaktor(_redaktor);
        dokumentFac.edit(_dokumentNew);
        System.out.print("update redaktora " + _dokumentNew.getRedaktor().getNazwisko());

        plik _plikNew = new plik(_plik.getContentType(), _plik.getData(), _plik.getFilename(), _dokumentNew.getId(), _plik.getTyp(), _plik.getUrl(), _plik.getWielkoscpliku());
        plikFac.create(_plikNew);

        dokument _dokumentRodzic = dokumentFac.find(_dokument.getIdRodzica());
        _dokumentRodzic.setStatus("W składzie");
        dokumentFac.edit(_dokumentRodzic);

        setIdDokumentu(_dokumentNew.getIdRodzica().toString());//Wracamy z dokumentem rodzicem
        setIdDokumentReda(null);

//        Wysyłamy maila z dokumentem do składu

        String url = katalog + _plikNew.getUrl() + "\\" + _plikNew.getFilename();
        String body = "\t Proszę o złożenie dokumentu \" " + _dokumentNew.getTytul() + " \" i odesłanie gotowego pdf'a na adres zwrotny .\n \n \n Pozdrawiam \n Sekretarz Redakcji";
        try {
            sendMailFac.sendMsg(_dokumentNew.getRedaktor().getEmail(), cc, "Dokument do składu " + _dokumentNew.getTytul(), body, url);
        } 
//        catch (MessagingException ex) {
//            addActionError("Nie wysłano maila do składu !! Sprawdz skrzynke i wyslij ręcznie");
//             System.out.print("bład przy wysłaniu maila");
//            Logger.getLogger(dokumentAction.class.getName()).log(Level.SEVERE, null, ex);
//        }
        catch (Exception ex) {
            addActionError("Problemy z wysłaniem maila do składu.");
            Logger.getLogger(dokumentAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.info();
    }

    public void redaguj() {
        dokument _dokument;
        plik _plik;

        System.out.print("Mamy dane  " + getId_kontrah() + " " + getIdDokumentu() + " " + getIdDokumentReda());
        if (getIdDokumentReda() != null) {
            setIdDokumentu(getIdDokumentReda());
            _dokument = dokumentFac.find(Long.parseLong(getIdDokumentu()));
            _plik = plikFac.findPlik(Long.parseLong(getIdDokumentu()), "dokument");
        } else {//Ten przypadek wystepuje tylko dla nowego artykułu
            _dokument = dokumentFac.find(Long.parseLong(getIdDokumentu()));
            System.out.print("mamy dokument " + _dokument.getId());
            //Przyjmujemy zmiane dokumentu żrudłowego
            _dokument.setStatus("Redagowany");
            dokumentFac.edit(_dokument);
            _plik = plikFac.findPlik(Long.parseLong(getIdDokumentu()), "dokument");
        }

        kontrahent _redaktor = kontrahentFac.find(Long.parseLong(getId_kontrah()));//komu przypisaliśmy
        System.out.print("mamy redaktora " + _redaktor.getNazwisko());
//Mamy kopie dokumentu
        dokument _dokumentNew = new dokument(_dokument.getTytul(), _dokument.getCena(), _dokument.getIdRodzica(), _dokument.getIloscZnakow(), _dokument.getNrStrony(), _dokument.getPowierzchnia(), _dokument.getStatus(), _dokument.getTyp());
        System.out.print("mamy NOWY  dokument " + _dokumentNew.getId() + " " + _dokumentNew.getStatus());

        if (_dokumentNew.getIdRodzica() == null) {
            //tylko dal dokumentu nowy
            System.out.print("jest null");
            _dokumentNew.setIdRodzica(Long.parseLong(getIdDokumentu()));
        }
        _dokumentNew.setStatus("Redagowany");
        _dokumentNew.setData(new Date());
        _dokumentNew.setCena(0);
        System.out.print("Przed tworzeniem " + _dokumentNew.getId() + " " + _dokumentNew.getStatus());
        dokumentFac.create(_dokumentNew);
        System.out.print("mamy NOWY  dokument " + _dokumentNew.getId() + " " + _dokumentNew.getStatus());
        _dokumentNew.setKont(_dokument.getKont());
        _dokumentNew.setNume(_dokument.getNume());
        _dokumentNew.setRedaktor(_redaktor);
        dokumentFac.edit(_dokumentNew);
        System.out.print("update redaktora " + _dokumentNew.getRedaktor().getNazwisko());
        plik _plikNew = new plik(_plik.getContentType(), _plik.getData(), _plik.getFilename(), _dokumentNew.getId(), _plik.getTyp(), _plik.getUrl(), _plik.getWielkoscpliku());

        plikFac.create(_plikNew);
        setIdDokumentu(_dokumentNew.getIdRodzica().toString());//Wracamy z dokumentem rodzicem
        setIdDokumentReda(null);
        this.info();

    }

    public void zamknij() {
        String katalog = configFac.findWartosc("dokumenty.lokalizacja").getWartosc();
        String znakiNaStrone = configFac.findWartosc("ilosc.znakow.strona.maszynopisu").getWartosc();
        dokument _dokument = dokumentFac.find(Long.parseLong(getIdDokumentReda()));
        plik _plik = plikFac.findPlik(Long.parseLong(getIdDokumentReda()), "dokument");
        //trzeba wstawić poprawiony plik i podmienic sciezke do tego pliku. Znajdowac się bedzie w katalogu rodzica
        String urlNew = _plik.getUrl() + "//" + _plik.getId_opisu().toString();
        _plik.setContentType(getFileContentType());
        _plik.setData(new Date());


        if (new File(katalog + urlNew + "\\" + getFileFileName()).exists()) {
            System.out.println("Plik istnieje " + katalog + urlNew + "\\" + getFileFileName());
            String[] temp = getFileFileName().split("\\.");
            String ile = plikFac.ilePlikow("numer", urlNew, temp[0] + "%");
            String _nazwa = "";
            System.out.print(getFileFileName() + "--->" + temp.length + "--" + ile + "--" + temp[0] + temp[1]);
            setFileFileName(temp[0] + "_V" + ile + "." + temp[1]);
        }
        _plik.setFilename(getFileFileName());
        _plik.setUrl(urlNew);
        try {
            FileUtils.copyFile(getFile(), new File(katalog + urlNew + "\\" + getFileFileName()));
        } catch (IOException ex) {
            addActionError("Problemy z kopiowanie plików");
            Logger.getLogger(dokumentAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        plikFac.edit(_plik);
        _dokument.setStatus("Zamkniete");
        //Dopisac wyliczanie kwoty za redagowanie
        double iloscZnak = Double.parseDouble(znakiNaStrone);
        double iloscStron = _dokument.getIloscZnakow() / iloscZnak;
        double zarobil = iloscStron * _dokument.getRedaktor().getStawka();
        System.out.print("zaribil " + zarobil);
        _dokument.setCena(new BigDecimal(zarobil).setScale(2, BigDecimal.ROUND_UP).doubleValue());
        dokumentFac.edit(_dokument);
        setIdDokumentu(_dokument.getIdRodzica().toString());
        setIdDokumentReda(null);
    }

    public void info() {
        //dane do poprawnego prezentowania

        setListaRedaktorow(kontrahentFac.findTypKontrah("Redaktor"));
        setDokument(dokumentFac.find(Long.parseLong(getIdDokumentu())));
        setListaDokumentowDzieci(dokumentFac.ListaDokumentowDzieci(Long.parseLong(getIdDokumentu())));
        setListaGrup(grupaDokFac.findAll());
        Long i = dokumentFac.MAXIdDokumentuDziecka(Long.parseLong(getIdDokumentu()));
        System.out.print("numer dok do redagowania " + i + " i grup " + getListaGrup().size());
        try {
            if (i == null) {
                setIdDokumentReda("0");
            } else {
                setIdDokumentReda(i.toString());
            }
        } catch (Exception ex) {
//                setIdDokumentReda("0");
            ex.printStackTrace();
        }
        System.out.print("numer dok do redagowania " + getIdDokumentReda());

        if ((getIdDokumentReda() != null) || (!getIdDokumentReda().equals("0"))) {
            setDokumentred(dokumentFac.find(Long.parseLong(getIdDokumentReda())));
        }

    }

    public void sendFile(String idPliku) {
        FileInputStream _FileInputStream = null;
        try {
            String katalog = configFac.findWartosc("dokumenty.lokalizacja").getWartosc();
            plik _plik = plikFac.findPlik(Long.valueOf(idPliku).longValue(), "dokument");
            String url = katalog + _plik.getUrl() + "\\" + _plik.getFilename();
            File _file = new File(url);
            System.out.print(url);

            _FileInputStream = new FileInputStream(_file);
            System.out.print("Brak pliku --> " + _file.getPath() + " -- " + _file.getName() + "--" + _FileInputStream.available());

            setInputStream(_FileInputStream);
            setFileFileName(_plik.getFilename());

//            setContentDisposition(_plik.getFilename());
            setFileContentType(_plik.getContentType());
//            setWielkoscpliku(_file.getTotalSpace());//file.getTotalSpace()

            System.out.print("skonczono sładac dane");
        } catch (Exception ex) {
//            FileNotFound
            addActionMessage("Brak pliku do pobrania na serwerze sprawdz zasoby");
            System.out.print("brak pliku do pobrania");
            Logger.getLogger(numerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ZapiszPlik(File aFile, Long id) throws ParseException, FileNotFoundException, IOException {
        String katalog = configFac.findWartosc("dokumenty.lokalizacja").getWartosc();

        String url = "\\dokument\\" + id.toString();

        plik _plik = new plik();
        _plik.setContentType(getFileContentType());
        _plik.setData(new Date());
        if (new File(katalog + url + "\\" + getFileFileName()).exists()) {
            System.out.println("Plik istnieje " + katalog + url + "\\" + getFileFileName());
            String[] temp = getFileFileName().split("\\.");
            String ile = plikFac.ilePlikow("numer", url, temp[0] + "%");
            String _nazwa = "";
            System.out.print(getFileFileName() + "--->" + temp.length + "--" + ile + "--" + temp[0] + temp[1]);
            setFileFileName(temp[0] + "_V" + ile + "." + temp[1]);
        }

        _plik.setFilename(getFileFileName());
        _plik.setTyp("dokument");
        _plik.setUrl(url);
        _plik.setWielkoscpliku(aFile.getTotalSpace());//aFile.getTotalSpace()
        _plik.setId_opisu(id);
        plikFac.create(_plik);

        FileUtils.copyFile(aFile, new File(katalog + url + "\\" + getFileFileName()));

    }

    private sendMailLocal sendMailLocal() {
        try {
            Context c = new InitialContext();
            return (sendMailLocal) c.lookup("redakcja/sendMailBean/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private void strona() {
        dokument _dokument = dokumentFac.find(Long.parseLong(getIdDokumentu()));
        grupaDok _grupaDok = grupaDokFac.find(Long.parseLong(getIdGrupy()));
        _dokument.setNrStrony(getNrStrony());
        _dokument.setGrupa(_grupaDok);
        dokumentFac.edit(_dokument);
    }

    private grupaDokFacadeLocal grupaDokFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (grupaDokFacadeLocal) c.lookup("redakcja/grupaDokFacade/local");
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

    private configFacadeLocal ConfigFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (configFacadeLocal) c.lookup("redakcja/configFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private plikFacadeLocal plikFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (plikFacadeLocal) c.lookup("redakcja/plikFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
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
     * @return the id_kontrah
     */
    public String getId_kontrah() {
        return id_kontrah;
    }

    /**
     * @param id_kontrah the id_kontrah to set
     */
    public void setId_kontrah(String id_kontrah) {
        this.id_kontrah = id_kontrah;
    }

    /**
     * @return the idRodzica
     */
    public Long getIdRodzica() {
        return idRodzica;
    }

    /**
     * @param idRodzica the idRodzica to set
     */
    public void setIdRodzica(Long idRodzica) {
        this.idRodzica = idRodzica;
    }

    /**
     * @return the redaktor
     */
    public kontrahent getRedaktor() {
        return redaktor;
    }

    /**
     * @param redaktor the redaktor to set
     */
    public void setRedaktor(kontrahent redaktor) {
        this.redaktor = redaktor;
    }

    /**
     * @return the powierzchnia
     */
    public String getPowierzchnia() {
        return powierzchnia;
    }

    /**
     * @param powierzchnia the powierzchnia to set
     */
    public void setPowierzchnia(String powierzchnia) {
        this.powierzchnia = powierzchnia;
    }

    /**
     * @return the cena
     */
    public double getCena() {
        return cena;
    }

    /**
     * @param cena the cena to set
     */
    public void setCena(double cena) {
        this.cena = cena;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
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
     * @return the tytul
     */
    public String getTytul() {
        return tytul;
    }

    /**
     * @param tytul the tytul to set
     */
    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the iloscZnakow
     */
    public int getIloscZnakow() {
        return iloscZnakow;
    }

    /**
     * @param iloscZnakow the iloscZnakow to set
     */
    public void setIloscZnakow(int iloscZnakow) {
        this.iloscZnakow = iloscZnakow;
    }

    /**
     * @return the nrStrony
     */
    public String getNrStrony() {
        return nrStrony;
    }

    /**
     * @param nrStrony the nrStrony to set
     */
    public void setNrStrony(String nrStrony) {
        this.nrStrony = nrStrony;
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
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the fileContentType
     */
    public String getFileContentType() {
        return fileContentType;
    }

    /**
     * @param fileContentType the fileContentType to set
     */
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    /**
     * @return the fileFileName
     */
    public String getFileFileName() {
        return fileFileName;
    }

    /**
     * @param fileFileName the fileFileName to set
     */
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    /**
     * @return the idDokumentu
     */
    public String getIdDokumentu() {
        return idDokumentu;
    }

    /**
     * @param idDokumentu the idDokumentu to set
     */
    public void setIdDokumentu(String idDokumentu) {
        this.idDokumentu = idDokumentu;
    }

    /**
     * @return the listaDokumentowDzieci
     */
    public Collection<dokument> getListaDokumentowDzieci() {
        return listaDokumentowDzieci;
    }

    /**
     * @param listaDokumentowDzieci the listaDokumentowDzieci to set
     */
    public void setListaDokumentowDzieci(Collection<dokument> listaDokumentowDzieci) {
        this.listaDokumentowDzieci = listaDokumentowDzieci;
    }

    /**
     * @return the dokument
     */
    public dokument getDokument() {
        return dokument;
    }

    /**
     * @param dokument the dokument to set
     */
    public void setDokument(dokument dokument) {
        this.dokument = dokument;
    }

    /**
     * @return the listaRedaktorow
     */
    public Collection<kontrahent> getListaRedaktorow() {
        return listaRedaktorow;
    }

    /**
     * @param listaRedaktorow the listaRedaktorow to set
     */
    public void setListaRedaktorow(Collection<kontrahent> listaRedaktorow) {
        this.listaRedaktorow = listaRedaktorow;
    }

    /**
     * @return the plik
     */
    public plik getPlik() {
        return plik;
    }

    /**
     * @param plik the plik to set
     */
    public void setPlik(plik plik) {
        this.plik = plik;
    }

    /**
     * @return the idDokumentuDziecko
     */
    public String getIdDokumentReda() {
        return idDokumentReda;
    }

    /**
     * @param idDokumentuDziecko the idDokumentuDziecko to set
     */
    public void setIdDokumentReda(String idDokumentReda) {
        this.idDokumentReda = idDokumentReda;
    }

    /**
     * @return the dokumentred
     */
    public dokument getDokumentred() {
        return dokumentred;
    }

    /**
     * @param dokumentred the dokumentred to set
     */
    public void setDokumentred(dokument dokumentred) {
        this.dokumentred = dokumentred;
    }

    /**
     * @return the idGrupy
     */
    public String getIdGrupy() {
        return idGrupy;
    }

    /**
     * @param idGrupy the idGrupy to set
     */
    public void setIdGrupy(String idGrupy) {
        this.idGrupy = idGrupy;
    }

    /**
     * @return the listaGrup
     */
    public Collection<grupaDok> getListaGrup() {
        return listaGrup;
    }

    /**
     * @param listaGrup the listaGrup to set
     */
    public void setListaGrup(Collection<grupaDok> listaGrup) {
        this.listaGrup = listaGrup;
    }
}