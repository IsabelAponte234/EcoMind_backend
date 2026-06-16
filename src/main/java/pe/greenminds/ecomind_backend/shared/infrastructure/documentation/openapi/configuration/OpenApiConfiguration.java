package pe.greenminds.ecomind_backend.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI ecomindOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("EcoMind Backend API")
                        .description("EcoMind application REST API documentation.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("GreenMinds Team")
                                .email("support@ecomind.com")
                                .url("https://github.com/greenminds/ecomind-backend"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8092").description("Servidor de desarrollo local")
                ));
    }
}
