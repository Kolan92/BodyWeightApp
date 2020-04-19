package com.bodyweightapp.webapi.backend

import com.okta.spring.boot.oauth.Okta
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import java.util.*

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow
import io.swagger.v3.oas.models.security.OAuthFlows


@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class BackendApplication

@Configuration
class OktaOAuth2WebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var env: Environment

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers(
                        "/index.html",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt()

        // process CORS annotations
        http.cors()
                .configurationSource {
                    CorsConfiguration().apply {
                        allowedMethods = listOf("*")
                        allowedHeaders = listOf("*")
                        allowedOrigins = getOrigins()
                    }
                }

        // force a non-empty response body for 401's to make the response more browser friendly
        Okta.configureResourceServer401ResponseBody(http)
    }

    private fun getOrigins(): List<String> {
        val origins = env["cors.origins"]
                ?.split(";")
                ?.filter { !it.isNullOrEmpty() }

        if (origins.isNullOrEmpty()) {
            throw IllegalStateException("No CORS origins found in application.properties")
        }

        return origins
    }

    @Bean
    fun customOpenAPI(): OpenAPI? {
        return OpenAPI()
                .components(Components().addSecuritySchemes(BearerSecurityName,
                        SecurityScheme().type(SecurityScheme.Type.APIKEY)
                                .`in`(SecurityScheme.In.HEADER)
                                .name("Authorization")
                                .description("please enter valid bearer token")
                                .flows(OAuthFlows().authorizationCode(OAuthFlow()))))
                .info(Info().title("BodyWeight API").version("0.0.1"))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(BackendApplication::class.java, *args)
}