package org.kmsg.cv.common;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DaoHandlerObject
{
	@Value("${spring.datasource.driver-class-name}")
	private String dcn;
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String un;
	
	@Value("${spring.datasource.password}")
	private String ps;
	
	@ConfigurationProperties(prefix="spring.datasource")
    @Bean
    public DataSource getDataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(dcn);
	    dataSource.setUrl(url);
	    dataSource.setUsername(un);
	    dataSource.setPassword(ps);
	    
	    Constants.dataSource = dataSource;

	    return dataSource;
    }
	
}
