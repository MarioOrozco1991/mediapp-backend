package com.marioorozco.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

//Clase que se utiliza para configurar los mensajes en la aplicación.
@Configuration
public class MessageConfig {

    //Cargar los properties de mensajes
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");//nombre del archivo de mensajes
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    //Para resolver las variables en los messages.properties
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    //Establecer un properties x default, que es el archivo que tiene por nombre messages.properties sin ningún sufijo
    //y que se encuentra en la carpeta src/main/resources, en este caso es el que esta en español
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ROOT); // Establecer el locale por defecto
        return localeResolver;
    }

}