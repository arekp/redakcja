/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package struts.akcje;

import com.opensymphony.xwork2.ActionSupport;
import google.geocodingservice.geocoder.Kml;
import google.geocodingservice.geocoder.Kml.Response.Status;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import klient.bean.adresFacadeLocal;
import klient.encje.adres;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
//import org.jboss.beans.metadata.plugins.MapEntry;
import org.apache.xerces.parsers.DOMParser;
import org.netbeans.saas.google.GoogleGeocodingService;
import org.netbeans.saas.RestResponse;
import org.netbeans.saas.google.GoogleMapService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


//import com.google.gdata.client.maps.*;
//
//import com.google.gdata.data.maps.*;
//import java.io.IOException;
//import java.net.URL;
//import javax.xml.rpc.ServiceException;
/**
 *
 * @author arekp
 */
public class mapa extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {

    private Map session; // zmienne sesyjne
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String b;
    private String mapar;
    private String tekst;
    @EJB
    adresFacadeLocal adrFac = (adresFacadeLocal) adresFacadeLocal();

    public String execute() throws Exception {
        setTekst("Test działania aplikacji ");
        // throw new UnsupportedOperationException("Not supported yet.");
         if (getB().equals("pobierz")) //Wysylka klienci z ostatnim miesiacem prenumeraty
        {
        this.getPolozenie(getB());
         }else if (getB().equals("wyswietl")){
         setMapar(this.rysujMape());
         }
        // rysowanieMapy(getB());
        getSession().put("body", "mapa/mapa.jsp");
//         System.out.println("-----" +getMapar()+"" +                " ------");
        return "success";
    }
public String rysujMape(){
    String script="<script type=\"text/javascript\">" +
            "    function initialize1() {" +
            "      if (GBrowserIsCompatible()) {" +
            "        var map = new GMap2(document.getElementById(\"map1\"));" +
            "map.setCenter(new GLatLng(52.229676,21.012229), 4);" +
            "map.addControl(new GSmallMapControl()); map.addControl(new GMapTypeControl());";
List<adres> adresy = adrFac.findAll();
    for (Iterator<adres> it = adresy.iterator(); it.hasNext();) {
        adres dane = it.next();
        if (dane.getLat() != null){
    script+=" var latlng = new GLatLng(" + dane.getLat() +
            "," + dane.getLon() + ");" +
            "  map.addOverlay(new GMarker(latlng));";
        }
    }
    

    script+=" } } </script>";
 System.out.print("------------- "+script+" --------");
    return script;

}
    public void rysowanieMapy(String adres) {

        try {
            String address = adres;
            java.lang.Integer zoom = 15;
            String iframe = "false";

            RestResponse result1 = GoogleMapService.getGoogleMap(address, zoom, iframe);
            setMapar(result1.getDataAsString());
            //TODO - Uncomment the print Statement below to print result.
            System.out.println("The SaasService returned: " + result1.getDataAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void getPolozenie(String adres) {

        try {
            String q = adres;
            String output = "xml";
            //String output = "kml";
            //  String key = "ABQIAAAAnfs7bKE82qgb3Zc2YyS-oBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxSySz_REpPq-4WZA27OwgbtyR3VcA";

            List<adres> adresy = adrFac.findAll();
            System.out.print("mamy " + adresy.size());
            for (Iterator<adres> it = adresy.iterator(); it.hasNext();) {
                adres dane = it.next();

                String szukam = dane.getMiasto() + "," + dane.getAdress();

                System.out.print("--- > " + szukam);
                if (dane.getLat() == null) {

                    System.out.print("Szukamy !!!!");

                    RestResponse result = GoogleGeocodingService.geocode(szukam, output);

                    System.out.print(result.getDataAsString());
                    DOMParser parser = new DOMParser();
                    System.out.print("Zaczynamy");
                    parser.parse(new InputSource(new StringReader(result.getDataAsString())));
                    System.out.print("Pobrano dokument");
                    Document doc = parser.getDocument();
                    System.out.print("Wczytano");
                    NodeList codeList = doc.getElementsByTagName("code");
                    String code = "";
                    if (codeList != null && codeList.getLength() > 0) {
                        Element el = (Element) codeList.item(0);
                        code = el.getFirstChild().getNodeValue();
                        System.out.print(" ---> " + code);
                    }
                    if (code.equals("200")) {
                        NodeList nl = doc.getElementsByTagName("coordinates");
                        String[] l = null;
                        if (nl != null && nl.getLength() > 0) {
                            Element el = (Element) nl.item(0);
                            String textVal = el.getFirstChild().getNodeValue();
                            l = textVal.split(",");
                            System.out.print(" ---> lon " + l[0] + " lat " + l[1]);
                        }
                        adres pop = adrFac.find(dane.getId());
                        pop.setLat(l[1]);
                        pop.setLon(l[0]);
                        adrFac.edit(pop);
                    }
                }
            }
        //System.out.println("The SaasService returned: "+result.getDataAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * @return the session
     */
    public Map getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * @return the request
     */
    public HttpServletRequest getServletRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @return the response
     */
    public HttpServletResponse getServletResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
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
     * @return the mapa
     */
    public String getMapar() {
        return mapar;
    }

    /**
     * @param mapa the mapa to set
     */
    public void setMapar(String mapar) {
        this.mapar = mapar;
    }

    private adresFacadeLocal adresFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (adresFacadeLocal) c.lookup("redakcja/adresFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the tekst
     */
    public String getTekst() {
        return tekst;
    }

    /**
     * @param tekst the tekst to set
     */
    public void setTekst(String tekst) {
        this.tekst = tekst;
    }
}