package com.dnd.spaced.global.config;

import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.account.domain.repository.NicknameMetadataRepository;
import com.dnd.spaced.domain.auth.domain.TokenDecoder;
import com.dnd.spaced.domain.auth.domain.TokenEncoder;
import com.dnd.spaced.global.config.properties.CorsProperties;
import com.dnd.spaced.global.config.properties.NicknameProperties;
import com.dnd.spaced.global.config.properties.ProfileImageProperties;
import com.dnd.spaced.global.config.properties.TokenProperties;
import com.dnd.spaced.global.security.core.OAuth2UserDetailsService;
import com.dnd.spaced.global.security.core.OAuth2UserService;
import com.dnd.spaced.global.security.filter.OAuth2AuthenticationFilter;
import com.dnd.spaced.global.security.filter.OAuth2RegistrationValidateFilter;
import com.dnd.spaced.global.security.handler.OAuth2AccessDeniedHandler;
import com.dnd.spaced.global.security.handler.OAuth2AuthenticationEntryPoint;
import com.dnd.spaced.global.security.handler.OAuth2AuthenticationFailureHandler;
import com.dnd.spaced.global.security.handler.OAuth2SuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {
        CorsProperties.class, TokenProperties.class, NicknameProperties.class, ProfileImageProperties.class
})
public class SecurityConfig {

    private final TokenEncoder tokenEncoder;
    private final TokenDecoder tokenDecoder;
    private final ObjectMapper objectMapper;
    private final TokenProperties tokenProperties;
    private final NicknameProperties nicknameProperties;
    private final ProfileImageProperties profileImageProperties;
    private final AccountRepository accountRepository;
    private final NicknameMetadataRepository nicknameMetadataRepository;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (webSecurity) -> webSecurity.ignoring()
                                           .requestMatchers(
                                                   PathRequest.toStaticResources()
                                                              .atCommonLocations()
                                           );
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.GET,"/admin/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/refresh-token").permitAll()
                    .requestMatchers(HttpMethod.GET, "/comments/popular").permitAll()
                    .requestMatchers(HttpMethod.GET, "/words/{wordId}/comments").permitAll()
                    .requestMatchers(HttpMethod.GET, "/images/{name}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/learnings/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/learnings/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/words/**").permitAll()
                    .anyRequest().authenticated()
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .exceptionHandling(handler -> handler
                    .authenticationEntryPoint(oAuth2AuthenticationEntryPoint())
                    .accessDeniedHandler(oAuth2AccessDeniedHandler())
            )
            .oauth2Login(oauth -> oauth
                    .authorizationEndpoint(endPoint -> endPoint.baseUri("/login"))
                    .userInfoEndpoint(endPoint -> endPoint.userService(oAuth2UserService()))
                    .successHandler(oAuth2SuccessHandler())
                    .failureHandler(oAuth2AuthenticationFailureHandler())
            )
            .addFilterBefore(oAuth2AuthenticationFilter(), OAuth2LoginAuthenticationFilter.class)
            .addFilterBefore(oAuth2RegistrationValidateFilter(), OAuth2AuthorizationRequestRedirectFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint() {
        return new OAuth2AuthenticationEntryPoint(objectMapper);
    }

    @Bean
    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler(objectMapper);
    }

    @Bean
    public OAuth2UserService oAuth2UserService() {
        return new OAuth2UserService();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(
                tokenEncoder,
                objectMapper,
                tokenProperties,
                nicknameProperties,
                profileImageProperties,
                accountRepository,
                nicknameMetadataRepository
        );
    }

    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(objectMapper);
    }

    @Bean
    public OAuth2AuthenticationFilter oAuth2AuthenticationFilter() {
        return new OAuth2AuthenticationFilter(oAuth2UserDetailsService());
    }

    @Bean
    public OAuth2UserDetailsService oAuth2UserDetailsService() {
        return new OAuth2UserDetailsService(tokenDecoder);
    }

    @Bean
    public OAuth2RegistrationValidateFilter oAuth2RegistrationValidateFilter() {
        return new OAuth2RegistrationValidateFilter(objectMapper, handlerExceptionResolver);
    }
}
