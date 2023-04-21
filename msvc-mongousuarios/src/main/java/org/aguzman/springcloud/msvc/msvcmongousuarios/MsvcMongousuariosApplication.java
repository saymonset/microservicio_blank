package org.aguzman.springcloud.msvc.msvcmongousuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsvcMongousuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcMongousuariosApplication.class, args);
	}

}
