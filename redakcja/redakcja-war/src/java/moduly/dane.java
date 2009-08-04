/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package moduly;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import klient.bean.KlientFacadeLocal;

/**
 *
 * @author arekp
 */
public class dane {
    private String iloscPren;
    private Date dataD;
    private    String test;
        @EJB
    KlientFacadeLocal klientFac = (KlientFacadeLocal) KlientFacadeLocal();

    public String getIloscPren(){
         Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
       cal.set(Calendar.DAY_OF_MONTH,1);
        setDataD(cal.getTime());

        iloscPren = klientFac.IloscEgzempl(getDataD());
        System.out.print(iloscPren+" "+dataD);
        return iloscPren;
    }

    public Date getDataD(){
    return dataD;
    }

    private KlientFacadeLocal KlientFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (KlientFacadeLocal) c.lookup("redakcja/KlientFacade/local");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the test
     */
    public String getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(String test) {
        this.test = test;
    }

    /**
     * @param iloscPren the iloscPren to set
     */
    public void setIloscPren(String iloscPren) {
        this.iloscPren = iloscPren;
    }

    /**
     * @param dataD the dataD to set
     */
    public void setDataD(Date dataD) {
        this.dataD = dataD;
    }
}
