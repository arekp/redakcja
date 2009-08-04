/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package moduly;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;

/**
 *
 * @author arekp
 */

public class countEgzempl extends ActionSupport {
private Date sysdate;
    private int count;

    @Override
    public String execute() throws Exception {
        setSysdate(new Date());
        setCount(2000);
        System.out.print(getSysdate()+"--"+getCount());
        return "succes";
    }

    /**
     * @return the sysdate
     */
    public Date getSysdate() {
        return sysdate;
    }

    /**
     * @param sysdate the sysdate to set
     */
    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

}