package com.hcl.cloud.cart.config;

import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RabbitmqCartConfig.
 * @author baghelp || shikhar.a
 */
@Configuration
public class RabbitmqCartConfig {
    /**
     * EXCHANGE_NAME.
     */
    public static final String EXCHANGE_NAME_CART = "cartMQ";
    /**
     * ROUTING_KEY.
     */
    public static final String ROUTING_KEY_CART = "cartPOC";
    /**
     * QUEUE_SPECIFIC_NAME.
     */
    public static final String QUEUE_SPECIFIC_NAME_CART = "cartQueue";

    /**
     * mqCartExchange method for TopicExchange.
     * @return TopicExchange object.
     */
    @Bean
    public TopicExchange mqCartExchange() {
        return new TopicExchange(EXCHANGE_NAME_CART);
    }
    /**
     * appCartQueueSpecific method for TopicExchange.
     * @return Queue object.
     */
    @Bean
    public Queue appCartQueueSpecific() {
        return new Queue(QUEUE_SPECIFIC_NAME_CART);
    }
    /**
     * declareBindingSpecificCart method.
     * @return Binding object.
     */
    @Bean
    public Binding declareBindingSpecificCart() {
        return BindingBuilder.bind(appCartQueueSpecific()).to(mqCartExchange()).with(ROUTING_KEY_CART);
    }
    /**
     * rabbitTemplateCart method.
     * @return RabbitTemplate object.
     */
    @Bean
    public RabbitTemplate rabbitTemplateCart(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        return rabbitTemplate;
    }
    /**
     * producerJackson2MessageConverter method - converter method.
     * @return Jackson2JsonMessageConverter object.
     */
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper objectMapper) {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        classMapper.setDefaultType(Map.class);
        Jackson2JsonMessageConverter converter = new ImplicitJsonMessageConverter(objectMapper);
        converter.setClassMapper(classMapper);
        return converter;
    }
    /**
     * Inner class ImplicitJsonMessageConverter.
     */
    public static class ImplicitJsonMessageConverter extends Jackson2JsonMessageConverter {
        public ImplicitJsonMessageConverter(ObjectMapper jsonObjectMapper) {
            super(jsonObjectMapper, "*");
        }
        @Override
        public Object fromMessage(Message message) throws MessageConversionException {
            message.getMessageProperties().setContentType("application/json");
            return super.fromMessage(message);
        }
    }
}
