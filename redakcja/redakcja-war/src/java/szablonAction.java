/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author arekp
 */

public class szablonAction extends ActionSupport implements SessionAware{

    private Map session; // zmienne sesyjne
    private String b; // zmienna z urla

    @Override
    public String execute() throws Exception {

        getSession().put("body", "/body.jsp");
            return "success";
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
}