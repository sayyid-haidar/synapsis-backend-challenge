package com.synapsis.shop.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "Authorization", in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "Synapsis Backend Challenge", version = "1.0.0"), security = {
                @SecurityRequirement(name = "Authorization") })
public class OpenAPIConfig {

}
