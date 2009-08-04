/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package klient.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 *
 * @author ArekP
 */
@Stateless
public class ImportBeanBean implements ImportBeanLocal {
     final public static String CASH = "CASH";
   final public static String CREDIT = "CREDIT";
   final public static String CHECK = "CHECK";

   @Resource(mappedName="java:/jdbc/redakcjaMs") DataSource dataSource;

   @Resource(name="min") int minCheckNumber = 100;

   public boolean wstawKlienta(String nazwa,String ulica,String miasto,String wojewodztwo,String typ,String info,
           String prenOd,String prenDo,String ilosc,String dataPrzedl,String typ_pren) throws Exception
//                           throws PaymentException
   {

      Connection con = null;

      PreparedStatement ps = null;

      try
      {
         con = dataSource.getConnection();
         ps = con.prepareStatement
            ("INSERT INTO payment (customer_id, amount, type,"+
             "check_bar_code,check_number,credit_number,"+
             "credit_exp_date) VALUES (?,?,?,?,?,?,?)");
//         ps.setInt(1,customerID);
//         ps.setDouble(2,amount);
//         ps.setString(3,type);
//         ps.setString(4,checkBarCode);
//         ps.setInt(5,checkNumber);
//         ps.setString(6,creditNumber);
//         ps.setDate(7,creditExpDate);
         int retVal = ps.executeUpdate();
         if (retVal!=1)
         {System.out.print("Wstawiernie nie powiodlo sie");
//            throw new EJBException("Payment insert failed");
         }
         return true;
      }
      catch(SQLException sql)
      {
//         throw new EJBException(sql);
      }
      finally
      {
         try
         {
            if (ps != null) ps.close();
            if (con!= null) con.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }
      }
               return true;

   }
 
}
