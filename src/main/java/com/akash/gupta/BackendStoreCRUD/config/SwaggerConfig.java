package com.akash.gupta.BackendStoreCRUD.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@SecurityScheme(
        name = "scheme1",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(

        info = @Info(
                title = "Unlocking Potential with SpringBOOT-Powered Electronic Store API",
                description = "A Comprehensive Backend Project featuring multiple RestAPIs, meticulously crafted in SpringBoot 3.2.2, leveraging the prowess of Spring Data JPA, Spring Security, and Spring MVC. This robust solution is powered by Java 21, seamlessly integrating with MySQL Database, Docker, and AWS services for unparalleled performance and scalability.",
//                version = "1.0V",
                contact = @Contact(
                        name = "    Aasim Ahsan",
                        email = "   akashguptaworks@gmail.com or skyrisegupta@gmail.com"
                        , url = "https://www.linkedin.com/in/aasimahsan-a7a4b3194/"
                )

        ))
public class SwaggerConfig {


}
