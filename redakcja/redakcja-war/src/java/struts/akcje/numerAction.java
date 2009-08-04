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
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import klient.bean.configFacadeLocal;
import klient.bean.numerFacadeLocal;

//import klient.encje.numer;


import klient.encje.numer;
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
    private String info;
    private String id;
    private Date data;
    private File file;
    private String contentType;
    private String filename;
    private Collection<numer> daneNumer;
    @EJB
    numerFacadeLocal numerFac = (numerFacadeLocal) numerFacadeLocal();
    @EJB
    configFacadeLocal configFac = (configFacadeLocal) ConfigFacadeLocal();

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
//            attibutes.put("body", "/admin/ok.jsp");
        }

        return "success";
    }

    public void ZapiszPlik(File aFile) throws ParseException, FileNotFoundException, IOException {
//        ObjectOutputStream os;
//        try {
//            String katalog= configFac.findWartosc("dokumenty.lokalizacja").getWartosc();
//            String url = katalog+getId()+"\\"+getFilename();
//            System.out.print("URL --> "+url);
//            os = new ObjectOutputStream(new FileOutputStream(url));
//                    os.writeObject(aFile); // zapis danych na dysk
//        os.close();
//        } catch (IOException ex) {
//            Logger.getLogger(numerAction.class.getName()).log(Level.SEVERE, null, ex);
//        }
 String katalog= configFac.findWartosc("dokumenty.lokalizacja").getWartosc();
        String url = katalog+getId()+"\\"+getFilename();
FileInputStream from = null;
    FileOutputStream to = null;
    try {
      from = new FileInputStream(aFile);
      to = new FileOutputStream(url);
      byte[] buffer = new byte[4096];
      int bytesRead;
      while ((bytesRead = from.read(buffer)) != -1)
        to.write(buffer, 0, bytesRead); // write
    } finally {
      if (from != null)
        try {
          from.close();
        } catch (IOException e) {
          ;
        }
      if (to != null)
        try {
          to.close();
        } catch (IOException e) {
          ;
        }
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
}