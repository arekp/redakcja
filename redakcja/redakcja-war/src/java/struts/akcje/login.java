/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package struts.akcje;

/**
 *
 * @author arekp
 */
import com.opensymphony.xwork2.ActionSupport;

public class login extends ActionSupport {
private String error;
    public login() {
    }

    @Override
    public String execute() throws Exception {
        if (error.equals("1")) {
//            bledene logowaniw
       addActionError("Błeden haslo lub login");
      return SUCCESS;
        }
        if (error.equals("2")) {
//            Wylogowanie
            addActionMessage("Dziekujemy za skorzystanie z naszych uslug");
      return SUCCESS;
        }
        if (error.equals("3")) {
//            brak dostepu
            addActionMessage("Brak dostepu do zasobu");
      return SUCCESS;
        }
      return SUCCESS;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

}