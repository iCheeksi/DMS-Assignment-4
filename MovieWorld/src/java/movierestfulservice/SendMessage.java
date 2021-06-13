/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierestfulservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
public class SendMessage {

    private Connection connection;
    private Session session;
    private MessageProducer producer;

    @Resource(mappedName = "jms/MovieWorldConnectionFactory")
    private static ConnectionFactory cf;
    @Resource(mappedName = "jms/MovieWorldQueue")
    private static Queue queue;

    public SendMessage() {
        try {
            connection = cf.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer((Destination) queue);
        } catch (JMSException ex) {
            System.err.println("Cannot open connection");
        }
    }
    public void send(Collection<String> information){
        try{
            TextMessage message = session.createTextMessage();
            for(String info : information){
                message.setText(info);
                System.out.println("Server sending a session message:" +message);
                producer.send(message);
            }
        }
        catch (JMSException e){
            System.err.println("Message cannot be sent");
        }
    }
    
    public void endConnection(){
        try{
            if(session != null)
                session.close();
            if(connection != null)
                connection.close();
    }
        catch(JMSException e){
            System.err.println("Cannot close connection");
        }
    }
    
    public static void main(String[] args){
        String[] messages = {"message 1", "message 2", "message 3"};
        Collection<String> information = new ArrayList<String>();
        for(String message : messages)
            information.add(message);
        System.out.println("Sending "+information.size()+" messages");
        SendMessage sender = new SendMessage();
        sender.send(information);
        sender.endConnection();
        System.out.println("Sending DONE!!!");
    }
}
