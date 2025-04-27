package com.java.akdev.driverservice.configuration;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Map;


@Configuration
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
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                      MessageConverter converter) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(converter);
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionAcknowledgeMode(jakarta.jms.Session.AUTO_ACKNOWLEDGE);
        factory.setConcurrency("1-1");
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdMappings(Map.of(
                "ReviewMessage", com.java.akdev.driverservice.dto.ReviewMessage.class
        ));
        converter.setTypeIdPropertyName("_type");
        return converter;
    }


}
