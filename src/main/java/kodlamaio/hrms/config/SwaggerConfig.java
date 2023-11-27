package kodlamaio.hrms.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("kodlamaio.hrms")).build()
				.securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
	}
	
	private ApiKey apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, "Bearer", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }
    
    private List<springfox.documentation.service.SecurityReference> defaultAuth() {
        return Collections.singletonList(
                new springfox.documentation.service.SecurityReference(
                        HttpHeaders.AUTHORIZATION,
                        new springfox.documentation.service.AuthorizationScope[]{
                                new springfox.documentation.service.AuthorizationScope("global", "accessEverything")
                        }
                )
        );
    }

}
