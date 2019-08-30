package community.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {
	
		@Bean
		@ConfigurationProperties(prefix="spring.datasource.hikari")
		public HikariConfig hikariConfig() {
			return new HikariConfig();
				}
		
		@Bean
		public DataSource dataSource() throws Exception{
			DataSource dataSource = new HikariDataSource(hikariConfig());
			return dataSource;
		}
		
		@ConfigurationProperties(prefix="spring.jpa")
		public Properties hibernateConfig() {
			return new Properties();
		}
		
		@Bean
		public PlatformTransactionManager transactionManager() throws Exception {
			return new DataSourceTransactionManager(dataSource());
		}
}
