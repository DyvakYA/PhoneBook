package com.lardi;

import com.lardi.service.ContactServiceFactory;
import com.lardi.utils.PropertiesReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.TimeZone;

@SpringBootApplication
@ImportAutoConfiguration(Application.class)
public class Application {

    private static Logger log = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Primary
    @Profile("prod")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(PropertiesReader.getProperty("hibernate.driver"));
        dataSource.setUrl(PropertiesReader.getProperty("hibernate.url"));
        dataSource.setUsername(PropertiesReader.getProperty("hibernate.username"));
        dataSource.setPassword(PropertiesReader.getProperty("hibernate.password"));
        return dataSource;
    }

    @Bean
    @Primary
    @Profile("prod")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        log.info("EntityManagerFactory");
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{PropertiesReader.getProperty("hibernate.scanPath")});
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Properties properties = additionalProperties();
        em.setJpaProperties(properties);
        return em;
    }

    private Properties additionalProperties() {
        String hbm2ddl = PropertiesReader.getProperty("hibernate.hbm2ddl");
        String dialect = PropertiesReader.getProperty("hibernate.dialect");
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("spring.jpa.show-sql", "true");
        return properties;
    }

    @Bean
    public ServiceLocatorFactoryBean myFactoryServiceLocatorFactoryBean() {
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(ContactServiceFactory.class);
        return bean;
    }

    @Bean
    public ContactServiceFactory contactServiceFactory() {
        return (ContactServiceFactory) myFactoryServiceLocatorFactoryBean().getObject();
    }
}
