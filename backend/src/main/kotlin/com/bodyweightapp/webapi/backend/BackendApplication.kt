package com.bodyweightapp.webapi.backend

import com.okta.spring.boot.oauth.Okta
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import java.util.*
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class BackendApplication

@Configuration
class OktaOAuth2WebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt()

        // process CORS annotations
        http.cors()
                .configurationSource(object : CorsConfigurationSource {
                    override fun getCorsConfiguration(request: HttpServletRequest): CorsConfiguration? {
                        return CorsConfiguration().apply {
                            allowedOrigins = listOf("http://localhost:8080")
                        }
                    }
                }
            )


        // force a non-empty response body for 401's to make the response more browser friendly
        Okta.configureResourceServer401ResponseBody(http)
    }
}


class Message(var text: String) {
    var date: Date = Date()
}

fun main(args: Array<String>) {
    SpringApplication.run(BackendApplication::class.java, *args)
}