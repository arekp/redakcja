/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.ejb.Local;

/**
 *
 * @author arekp
 */
@Local
public interface sendMailLocal {

    public void sendMsg(java.lang.String email,String cc, java.lang.String subject, java.lang.String body,String filename) throws javax.mail.MessagingException, javax.naming.NamingException, javax.naming.NamingException;
    
}
