package ru.senkot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@SuppressWarnings("DuplicatedCode")
@Configuration
public class MessagingConfiguration {

    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
    private static final String ORDER_QUEUE = "jms/queue/myQueue";

    private static final String DEFAULT_MESSAGE = "Hello, World!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/myQueue";
    private static final String DEFAULT_MESSAGE_COUNT = "1";
    private static final String DEFAULT_USERNAME = "senkot";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";


    @Bean
    public ConnectionFactory connectionFactory() throws NamingException {

        String userName = System.getProperty("username", DEFAULT_USERNAME);
        String password = System.getProperty("password", DEFAULT_PASSWORD);

        final Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        properties.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
        properties.put(Context.SECURITY_PRINCIPAL, userName);
        properties.put(Context.SECURITY_CREDENTIALS, password);
        Context namingContext = new InitialContext(properties);

        String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
        ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);

        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws NamingException {
        String userName = System.getProperty("username", DEFAULT_USERNAME);
        String password = System.getProperty("password", DEFAULT_PASSWORD);

        final Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        properties.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
        properties.put(Context.SECURITY_PRINCIPAL, userName);
        properties.put(Context.SECURITY_CREDENTIALS, password);
        Context namingContext = new InitialContext(properties);

        String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
        Destination destination = (Destination) namingContext.lookup(destinationString);

        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(destinationString);
        template.setDefaultDestination(destination);
        return template;
    }

}
