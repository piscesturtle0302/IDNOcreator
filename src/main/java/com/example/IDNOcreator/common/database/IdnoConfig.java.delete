package com.example.IDNOcreator.common.database;

import jakarta.persistence.SharedCacheMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = { "com.example.IDNOcreator.common.database.dao" },
    entityManagerFactoryRef = "idnoEntityManagerFactory",
    transactionManagerRef = "idnoTransactionManager"
)
public class IdnoConfig {

    @Autowired
    private Environment env;
    
    @Autowired
    @Qualifier("idnoDatasource")
    private DataSource datasource;
 
    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        adapter.setDatabase(Database.SQL_SERVER);
        adapter.setShowSql(false);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.SQLServer2012Dialect");

        return adapter;
    }
    
    /**
     * 載入 Entity
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean idnoEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(datasource);
        emf.setPackagesToScan(new String[] { "com.example.IDNOcreator.common.database.entity" });
        emf.setJpaVendorAdapter(this.jpaVendorAdapter());
        emf.setSharedCacheMode(SharedCacheMode.NONE);

        /*
            https://stackoverflow.com/questions/34222950/set-hibernate-ddl-auto-in-springboot-programmatically
            https://stackoverflow.com/questions/42135114/how-does-spring-jpa-hibernate-ddl-auto-property-exactly-work-in-spring
        */
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        emf.setJpaProperties(properties);

        return emf;
    }
    
    @Bean(name = "idnoTransactionManager")
    public PlatformTransactionManager idnoTransactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(this.idnoEntityManagerFactory().getObject());
        
        return tm;
    }
}