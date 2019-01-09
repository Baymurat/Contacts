package com.itechart.contacts.core;

import com.itechart.contacts.core.utils.ConnectionPool;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public SpringLiquibase liquibase() throws Exception {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath*:db.changelog/master.xml");
        liquibase.setDataSource(new ConnectionPool().setUpPool());
        return liquibase;
    }
}
