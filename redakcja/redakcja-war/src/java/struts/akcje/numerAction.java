/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package struts.akcje;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import klient.bean.configFacadeLocal;
import klient.bean.dokumentFacadeLocal;
import klient.bean.numerFacade;
import klient.bean.numerFacadeLocal;

//import klient.encje.numer;


import klient.bean.plikFacadeLocal;
import klient.encje.dokument;
import klient.encje.numer;
import klient.encje.plik;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author arekp
 */
public class numerAction extends ActionSupport implements SessionAware {

    private Map session;
    private String b;
    private String nazwa;
    private String naklad;
    private String status;
    private String info;
    private String id;
    private String idDokumentu;
    private Date data;
    private File file;
    private String contentType;//  typ pliku jaki mamy
    private String filename;
    private Long wielkoscpliku; // progres bar do wysyłania
//    private String contentDisposition;//nazwa pod jaka widaczny jest dokument pobierany
    private InputStream inputStream;
    private Collection<numer> daneNumer;
    private Collection<plik> danePliki;
    private Collection<dokument> daneDokumenty;
    private Collection<dokument> daneDokumentyWolne;
    @EJB
    numerFacadeLocal numerFac = (numerFacadeLocal) numerFacadeLocal();
    @EJB
    configFacadeLocal configFac = (configFacadeLocal) ConfigFacadeLocal();
    @EJB
    plikFacadeLocal plikFac = (plikFacadeLocal) plikFacadeLocal();
    @EJB
    dokumentFacadeLocal dokumentFac = (dokumentFacadeLocal) dokumentFacadeLocal();

    public String execute() throws Exception {

        if (getB().equals("addFor")) //Dodajemy numer formularz
        {
            getSession().put("body", "/numer/numerAdd.jsp");
        } else if (getB().equals("add")) //Dodaje numer
        {
            numer nu = new numer(getNazwa(), "", getInfo(), "Otwarty", 0, getData());
            numerFac.create(nu);
            setDaneNumer(numerFac.findAll());
            getSession().put("body", "/numer/lista.jsp");

        } else if (getB().equals("lista")) //Dodaje numer
        {
            setDaneNumer(numerFac.findAll());
            getSession().put("body", "/numer/lista.jsp");
        } else if (getB().equals("addFileForm")) //Dodaje plik z numerem
        {
            getSession().put("body", "/numer/addfile.jsp");
        } else if (getB().equals("addFile")) //Dodaje plik z numerem
        {
            System.out.print("pobrało " + file.getTotalSpace());
            System.out.print("nazwa  " + filename);
            System.out.print("nazwa  " + getId());
            System.out.print("contentType  " + getContentType());
            ZapiszPlik(file);
            numer _numer = numerFac.find(Long.valueOf(getId()).longValue());
             this.dane(_numer);
//            setDanePliki(plikFac.findPliki(Long.valueOf(getId()).longValue(), "numer"));
            this.dane(_numer);
            getSession().put("numer", _numer);
            getSession().put("body", "/numer/numerInfo.jsp");
        } else if (getB().equals("sendFile")) //Dodaje plik z numerem
        {
            System.out.print(getId() + " PRZED przekazujemy do wyslania");
            this.sendFile(getId());
//            getSession().put("body", "/numer/addfile.jsp");
            System.out.print(getId() + " przekazujemy do wyslania");
            return "sendFile";
//            return "success";
        } else if (getB().equals("set")) //ustawiamy bierzacy numer
        {
            numer _numer = numerFac.find(Long.valueOf(getId()).longValue());
//            setDanePliki(plikFac.findPliki(Long.valueOf(getId()).longValue(), "numer"));
            this.dane(_numer);
            getSession().put("numer", _numer);
            getSession().put("body", "/numer/numerInfo.jsp");
        } else if (getB().equals("zmien")) //ustawiamy bierzacy numer
        {
            numer _numer = numerFac.find(Long.valueOf(getId()).longValue());
            _numer.setStatus(getStatus());
            _numer.setNaklad(Integer.parseInt(getNaklad()));
            numerFac.edit(_numer);
             this.dane(_numer);
//            setDanePliki(plikFac.findPliki(Long.valueOf(getId()).longValue(), "numer"));
            getSession().put("numer", _numer);
            getSession().put("body", "/numer/numerInfo.jsp");
        } else if (getB().equals("addDok")) //Dodaj dokumentu do numeru
        {
            numer _numer = (numer) getSession().get("numer");
            dokument _dokument = dokumentFac.find(Long.valueOf(getIdDokumentu()).longValue());
            _dokument.setNume(_numer);
            dokumentFac.edit(_dokument);
            boolean a=dokumentFac.ZmienNumer(Long.valueOf(getIdDokumentu()).longValue(), _numer.getId());
            this.dane(_numer);
            getSession().put("body", "/numer/numerInfo.jsp");
        } else if (getB().equals("remDok")) //usunięcie dokumentu z numeru
        {
            numer _numer = (numer) getSession().get("numer");
            dokument _dokument = dokumentFac.find(Long.valueOf(getIdDokumentu()).longValue());
            _dokument.setNume(null);
               boolean a= dokumentFac.ZmienNumer(Long.valueOf(getIdDokumentu()).longValue(), null);
            dokumentFac.edit(_dokument);
            this.dane(_numer);
            getSession().put("body", "/numer/numerInfo.jsp");
        }
        return "success";
    }

    private void dane(numer _numer) {
        setDanePliki(plikFac.findPliki(_numer.getId(), "numer"));
        setDaneDokumenty(dokumentFac.ListaDokumentowNumeru(_numer));
        setDaneDokumentyWolne(dokumentFac.ListaDokumentowWolna());


    }

    public void sendFile(String idPliku) {
        FileInputStream _FileInputStream = null;
        try {
            String katalog = configFac.findWartosc("dokumenty.lokalizacja").getWartosc();
            plik _plik = plikFac.find(Long.valueOf(idPliku).longValue());
            String url = katalog + _plik.getUrl() + "\\" + _plik.getFilename();
            File _file = new File(url);
            System.out.print(url);

            _FileInputStream = new FileInputStream(_file);
            System.out.print("Brak pliku --> " + _file.getPath() + " -- " + _file.getName() + "--" + _FileInputStream.available());

            setInputStream(_FileInputStream);
            setFilename(_plik.getFilename());
//            setContentDisposition(_plik.getFilename());
            setContentType(_plik.getContentType());
            setWielkoscpliku(_file.getTotalSpace());//file.getTotalSpace()

            System.out.print("skonczono sładac dane");
        } catch (Exception ex) {
//            FileNotFound
            addActionMessage("Brak pliku do pobrania na serwerze sprawdz zasoby");
            System.out.print("brak pliku do pobrania");
            Logger.getLogger(numerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ZapiszPlik(File aFile) throws ParseException, FileNotFoundException, IOException {
        String katalog = configFac.findWartosc("dokumenty.lokalizacja").getWartosc();

        String url = "\\numer\\" + getId();

        plik _plik = new plik();
        _plik.setContentType(getContentType());
        _plik.setData(new Date());
        if (new File(katalog + url + "\\" + getFilename()).exists()) {
            System.out.println("Plik istnieje " + katalog + url + "\\" + getFilename());
            String[] temp = getFilename().split("\\.");
            String ile = plikFac.ilePlikow("numer", url, temp[0] + "%");
            String _nazwa = "";
            System.out.print(getFilename() + "--->" + temp.length + "--" + ile + "--" + temp[0] + temp[1]);
            setFilename(temp[0] + "_V" + ile + "." + temp[1]);
        }

        _plik.setFilename(getFilename());
        _plik.setTyp("numer");
        _plik.setUrl(url);
        _plik.setWielkoscpliku(aFile.getTotalSpace());//aFile.getTotalSpace()
        _plik.setId_opisu(Long.valueOf(getId()).longValue());
        plikFac.create(_plik);

        FileUtils.copyFile(aFile, new File(katalog + url + "\\" + getFilename()));

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

    private numerFacadeLocal numerFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (numerFacadeLocal) c.lookup("redakcja/numerFacade/local");
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
     * @return the naklad
     */
    public String getNaklad() {
        return naklad;
    }

    /**
     * @param naklad the naklad to set
     */
    public void setNaklad(String naklad) {
        this.naklad = naklad;
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
     * @return the daneNumer
     */
    public Collection<numer> getDaneNumer() {
        return daneNumer;
    }

    /**
     * @param daneNumer the daneNumer to set
     */
    public void setDaneNumer(Collection<numer> daneNumer) {
        this.daneNumer = daneNumer;
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

    public void setUpload(File file) {
        this.setFile(file);
    }

    public void setUploadContentType(String contentType) {
        this.setContentType(contentType);
    }

    public void setUploadFileName(String filename) {
        this.setFilename(filename);
    }

    public String getMyDocFileName() {
        return getFilename();
    }

    public Long getSizeFile() {
        return getFile().getTotalSpace();
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
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
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
     * @return the contentLength
     */
    public Long getWielkoscpliku() {
        return wielkoscpliku;
    }

    /**
     * @param contentLength the contentLength to set
     */
    public void setWielkoscpliku(Long wielkoscpliku) {
        this.wielkoscpliku = wielkoscpliku;
    }

    /**
     * @return the danePliki
     */
    public Collection<plik> getDanePliki() {
        return danePliki;
    }

    /**
     * @param danePliki the danePliki to set
     */
    public void setDanePliki(Collection<plik> danePliki) {
        this.danePliki = danePliki;
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
     * @return the daneDokumenty
     */
    public Collection<dokument> getDaneDokumenty() {
        return daneDokumenty;
    }

    /**
     * @param daneDokumenty the daneDokumenty to set
     */
    public void setDaneDokumenty(Collection<dokument> daneDokumenty) {
        this.daneDokumenty = daneDokumenty;
    }

    /**
     * @return the daneDokumentyWolne
     */
    public Collection<dokument> getDaneDokumentyWolne() {
        return daneDokumentyWolne;
    }

    /**
     * @param daneDokumentyWolne the daneDokumentyWolne to set
     */
    public void setDaneDokumentyWolne(Collection<dokument> daneDokumentyWolne) {
        this.daneDokumentyWolne = daneDokumentyWolne;
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
     * @param daneNumer the daneNumer to set
     */
//    public void setDaneNumer(Collection<numer> daneNumer) {
//        this.setDaneNumer(daneNumer);
//    }
//
//    /**
//     * @param daneNumer the daneNumer to set
//     */
//    public void setDaneNumer(Collection<numer> daneNumer) {
//        this.daneNumer = daneNumer;
//    }
}