package com.dnd.spaced.global.config;

import com.dnd.spaced.global.config.properties.CorsProperties;
import com.dnd.spaced.global.config.properties.NicknameProperties;
import com.dnd.spaced.global.config.properties.ProfileImageProperties;
import com.dnd.spaced.global.config.properties.TokenProperties;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {
        CorsProperties.class, TokenProperties.class, NicknameProperties.class, ProfileImageProperties.class
})
public class SecurityConfig {

    private final CorsProperties corsProperties;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (webSecurity) -> webSecurity.ignoring()
                                           .requestMatchers(
                                                   PathRequest.toStaticResources()
                                                              .atCommonLocations()
                                           );
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin(corsProperties.allowedOrigin());
        config.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setExposedHeaders(
                List.of(
                        "Authorization",
                        "Origin",
                        "Accept",
                        "Access-Control-Allow-Headers",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers",
                        "Content-Type"
                )
        );
        config.setAllowCredentials(true);
        config.setMaxAge(corsProperties.maxAge());

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
