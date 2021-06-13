/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierestfulservice;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
@MessageDriven(activationConfig =
{
   @ActivationConfigProperty(propertyName = "destinationLookup",
      propertyValue = "jms/MovieWorldQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
           propertyValue = "javax.jms.Queue")
})
public class MessageBean implements MessageListener{
   // field obtained via dependency injection (not used here)
   @Resource private MessageDrivenContext mdc;

   public MessageBean()
   {
   }

   public void onMessage(Message message)
   {
      try
      {
         if (message instanceof TextMessage)
            System.out.println("MessageBean received text message: "
               + ((TextMessage)message).getText());
         else
            System.out.println
               ("MessageBean received non-text message: " + message);
      }
      catch(JMSException e)
      {
         System.err.println("Exception with incoming message: "+e);
      }
   }
}