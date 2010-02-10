/*
 * $Id: HelloWorld.template,v 1.2 2008/03/27 05:47:21 ub3rsold4t Exp $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package example;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.struts2.interceptor.SessionAware;
import util.sendMailLocal;


/**
 * <code>Set welcome message.</code>
 */
public class HelloWorld extends ActionSupport implements SessionAware{

//Map attibutes = ActionContext.getContext().getSession();
 private Map session;
    @EJB
    sendMailLocal sendMailFac = (sendMailLocal) sendMailLocal();

 @Override
public String execute() throws Exception {
     System.out.print("Przed pobieraniem");
     sendMailFac.getNews();
     System.out.print("PO pobieraniu");
    Map attibutes = ActionContext.getContext().getSession();
        setMessage(getText(MESSAGE));
        System.out.print(attibutes.toString());
        attibutes.put("body","/body.jsp");
         System.out.print(attibutes.toString());
        return "success";
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

    /**
     * Provide default valuie for Message property.
     */
    public static final String MESSAGE = "HelloWorld.message";

    /**
     * Field for Message property.
     */
    private String message;

    /**
     * Return Message property.
     *
     * @return Message property
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set Message property.
     *
     * @param message Text to display on HelloWorld page.
     */
    public void setMessage(String message) {
        this.message = message;
    }

  public void setSession(Map session) {
    this.session = session;
  }
      public Map getSession() {
    return session;
  }
}

