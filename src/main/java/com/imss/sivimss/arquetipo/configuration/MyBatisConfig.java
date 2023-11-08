package com.imss.sivimss.arquetipo.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@MapperScan("com.imss.sivimss.arquetipo.configuration.mapper")
@ComponentScan({"com.imss.sivimss.arquetipo.configuration"})
public class MyBatisConfig {
	
	//@Value("${spring.datasource.url}") 
	private String url ="jdbc:mysql://10.102.44.18:3306/SIVIBDDS";
	
	// @Value("${spring.datasource.username}")
	private String envUser = "SIVI_USER";
	
	//@Value("${spring.datasource.password}")
	private String envPass = "51V1_US3R";

	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(envUser);
        dataSource.setPassword(envPass);
		
		factoryBean.setDataSource(dataSource);
		
		return factoryBean.getObject();
	}
}
