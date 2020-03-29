package com.bodyweightapp.webapi.backend

import com.okta.spring.boot.oauth.Okta
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


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

        // force a non-empty response body for 401's to make the response more browser friendly
        Okta.configureResourceServer401ResponseBody(http)
    }
}

@RestController
@CrossOrigin(origins = ["http://localhost:8080"])
class MessageOfTheDayController {
    @GetMapping("/api/userProfile")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    fun getUserDetails(authentication: JwtAuthenticationToken): Map<String, Any> {
        return authentication.tokenAttributes
    }

    @GetMapping("/api/messages")
    @PreAuthorize("hasAuthority('SCOPE_email')")
    fun messages(): Map<String, Any> {
        val result: MutableMap<String, Any> = HashMap()
        result["messages"] = Arrays.asList(
                Message("I am a robot."),
                Message("Hello, world!")
        )
        return result
    }
}

class Message(var text: String) {
    var date: Date = Date()
}

fun main(args: Array<String>) {
    SpringApplication.run(BackendApplication::class.java, *args)
}