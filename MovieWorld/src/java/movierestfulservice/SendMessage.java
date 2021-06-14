/*
 * Class to set server messaging using MoviewMessageBean.java
 */
package movierestfulservice;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;


/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 * */
 
public class SendMessage {

    //Injecting JNDI
   @Resource(mappedName = "jms/MovieWorldConnectionFactory")
   private static ConnectionFactory cf;
   
   //Injecting JNDI
   @Resource(mappedName = "jms/MovieWorldQueue")
   private static javax.jms.Queue queue;
   
   public static void main(String[] args){
       //Setting a context and producer to send messages to JMS
       JMSContext context = cf.createContext();
       JMSProducer producer = context.createProducer();
       
       String message = "Sending a message to JMS! ";
       System.out.println("Sending: ");
       
       producer.send(queue, message);;
       
       System.out.println("Message sent");
   }
}
