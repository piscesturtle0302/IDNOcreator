package com.example.IDNOcreator.common.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
//@PropertySource("${com.tmnewa.fps.conf.database.propsource}")
public class MultipleDataSourceConfig {
    @Autowired
    private Environment env;
 
    @Bean(name = "idnoDatasource")
    @ConfigurationProperties(prefix = "idno.datasource")
    public DataSource fpsDataSource() {
        return DataSourceBuilder.create()
           .driverClassName(env.getProperty("idno.datasource.driver-class-name"))
           .url(env.getProperty("idno.datasource.url"))
           .username(env.getProperty("idno.datasource.username"))
           .password(env.getProperty("idno.datasource.password"))
           .build();
    }    
}