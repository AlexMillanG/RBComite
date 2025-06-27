package mx.edu.utez.rbbackcomite.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConnection {
    @Value("${db.url}")
    private String DB_URL;
    @Value("${db.password}")
    private String DB_PASSWORD;
    @Value("${db.username}")
    private String DB_USER;

    @Bean
    public DataSource getConnection(){
        try {
            DriverManagerDataSource configuration = new DriverManagerDataSource();
            configuration.setUrl(DB_URL);
            configuration.setPassword(DB_PASSWORD);
            configuration.setUsername(DB_USER);
            configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");

            return configuration;

        } catch (Exception e) {
            System.err.println("error al conectar a la base de datos \n" );
            e.printStackTrace();
            return null;
        }
    }
}
