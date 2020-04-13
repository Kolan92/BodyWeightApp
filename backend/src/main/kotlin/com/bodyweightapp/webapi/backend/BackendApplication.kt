package com.bodyweightapp.webapi.backend

import com.okta.spring.boot.oauth.Okta
import org.springframework.core.env.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.get
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import java.lang.IllegalStateException
import java.util.*


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
                .antMatchers("/v3/api-docs/**",
                        "/swagger-ui/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt()

        // process CORS annotations
        http.cors()
                .configurationSource {
                    CorsConfiguration().apply {
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
}


class Message(var text: String) {
    var date: Date = Date()
}

fun main(args: Array<String>) {
    SpringApplication.run(BackendApplication::class.java, *args)
}