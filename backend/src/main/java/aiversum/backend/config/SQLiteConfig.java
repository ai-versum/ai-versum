package aiversum.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SQLiteConfig {
    @Value("${spring.jpa.driver-class-name}")
    private String driverClassName;
    @Value("${spring.jpa.url}")
    private String url;
    @Value("${spring.jpa.username}")
    private String username;
    @Value("${spring.jpa.password}")
    private String password;


    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
