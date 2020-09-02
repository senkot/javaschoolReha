package ru.senkot.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import ru.senkot.entities.Event;

import javax.jms.JMSException;

@Component
public class MessageReceiver {

//    private static final String ORDER_RESPONSE_QUEUE = "super-queue";
//
//    @JmsListener(destination = ORDER_RESPONSE_QUEUE)
//    public void receiveMessage(final Message<Event> message) throws JMSException {
//        MessageHeaders headers =  message.getHeaders();
//    }

}
