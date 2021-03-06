package com.coastcapitalsavings;

import com.coastcapitalsavings.datasource.DatasourceInfo;
import com.coastcapitalsavings.datasource.IDataSourceInfoParser;
import com.coastcapitalsavings.datasource.JSONDataSourceInfoParser;
import com.coastcapitalsavings.datasource.LocalDataSourceInfoParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coastcapitalsavings"})
public class RequisitioningApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication app = new SpringApplication(RequisitioningApplication.class);
		app.run(args);
	}

	@Bean
	@Primary
	public DataSource dataSource() throws Exception {
		IDataSourceInfoParser parser = new JSONDataSourceInfoParser();
//		DatasourceInfo dbInfo = parser.createDatabaseInfo("./src/main/resources/dbproperties.json");
//		IDataSourceInfoParser parser = new LocalDataSourceInfoParser();
		DatasourceInfo dbInfo = parser.createDatabaseInfo("./src/main/resources/dbproperties.json");
		return DataSourceBuilder
				.create()
				.username(dbInfo.getUserName())
				.password(dbInfo.getPassword())
				.url(dbInfo.getAddress())
				.build();
	}



}
