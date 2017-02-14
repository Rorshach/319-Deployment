package com.coastcapitalsavings;

import com.coastcapitalsavings.database.DatabaseInfo;
import com.coastcapitalsavings.database.IDatabaseInfoParser;
import com.coastcapitalsavings.database.JSONDatabaseInfoParser;
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
		IDatabaseInfoParser parser = new JSONDatabaseInfoParser();
		DatabaseInfo dbInfo = parser.createDatabaseInfo("./src/main/resources/dbproperties.json");
		return DataSourceBuilder
				.create()
				.username(dbInfo.getUserName())
				.password(dbInfo.getPassword())
				.url(dbInfo.getAddress())
				.build();
	}
}
