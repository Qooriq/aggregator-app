package com.java.akdev.reviewservice.config;

import jakarta.jms.JMSException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Map;

@Configuration
@EnableJms
public class ArtemisConfiguration {

    @Value("${spring.artemis.broker-url}")
    private String brokerUrl;
    @Value("${spring.artemis.user}")
    private String user;
    @Value("${spring.artemis.password}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUser(user);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public JmsTransactionManager transactionManager() throws JMSException {
        return new JmsTransactionManager(connectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setMessageConverter(messageConverter());
        jmsTemplate.setDefaultDestinationName("review-drivers-update");
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setTransactionManager(transactionManager());
        factory.setSessionTransacted(true);
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdMappings(Map.of(
                "ReviewMessage", com.java.akdev.reviewservice.dto.ReviewMessage.class
        ));
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
