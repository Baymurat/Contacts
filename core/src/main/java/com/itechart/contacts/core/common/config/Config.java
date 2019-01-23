package com.itechart.contacts.core.common.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(value = "com.itechart.contacts.core")
@EntityScan("com.itechart")
@ComponentScan(value = "com.itechart.contacts")
public class Config {


    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) throws Exception {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath*:db.changelog/master.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }


    @Bean
    public JavaMailSender getJavaMailSender(Environment environment) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        String host = environment.getProperty("spring.mail.host");
        String port = environment.getProperty("spring.mail.port");
        String username = environment.getProperty("spring.mail.username");
        String password = environment.getProperty("spring.mail.password");

        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
