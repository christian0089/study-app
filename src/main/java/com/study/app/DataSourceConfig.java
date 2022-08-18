package com.study.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Bean( name = "dataSource" )
	@ConfigurationProperties( prefix = "spring.datasource.hikari" )
	public DataSource DataSource( ConfigurableEnvironment environment ) throws Exception {
		HikariDataSource hds = new HikariDataSource();
		// password using property for decryption
		hds.addDataSourceProperty( "password", environment.getProperty( "db.pwd" ) );
		return hds;
	}
	
	@Bean( name = "sqlSessionFactory" )
	public SqlSessionFactory sqlSessionFactory( @Qualifier( "dataSource" ) DataSource dataSource )throws Exception{
		SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
		ssfb.setDataSource( dataSource );
		
		ssfb.setMapperLocations( new PathMatchingResourcePatternResolver().getResources("classpath:mappers/**/*.xml") );
		ssfb.setConfigLocation( new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml") );
		
		return ssfb.getObject();
	}
}