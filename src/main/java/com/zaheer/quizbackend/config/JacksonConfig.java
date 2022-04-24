package com.zaheer.quizbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .setAnnotationIntrospector(new JacksonAnnotationIntrospector())
        .registerModule(new JavaTimeModule())
        .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
        .registerModule(new Hibernate5Module())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }
}
