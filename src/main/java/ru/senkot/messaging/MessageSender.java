package ru.senkot.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import ru.senkot.entities.Event;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.Serializable;

@Component
public class MessageSender {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(String text) {

            MessageCreator messageCreator = new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage1 = session.createTextMessage(text);
                    return textMessage1;
                }
            };
            jmsTemplate.send(messageCreator);


//    public void sendMessage(final Event event) {
//        jmsTemplate.send(new MessageCreator(){
//            @Override
//            public Message createMessage(Session session) throws JMSException{
//                ObjectMessage objectMessage = session.createObjectMessage((Serializable) event);
//                return objectMessage;
//            }
//        });
    }

}
