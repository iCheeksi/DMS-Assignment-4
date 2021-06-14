/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierestfulservice.Message;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author User
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/MovieWorldQueue")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MoviesMessageBean implements MessageListener {
    
    public MoviesMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try{
            if(message instanceof TextMessage){
                System.out.println("MessageBean received text message: "
                +((TextMessage)message).getText());
            }
            else{
                System.out.println("Non-text message says: "+message+" to MessageBean");
            }
        } catch (JMSException ex) {
            Logger.getLogger(MoviesMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
