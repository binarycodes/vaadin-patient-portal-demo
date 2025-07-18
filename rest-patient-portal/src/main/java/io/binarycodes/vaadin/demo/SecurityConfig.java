package io.binarycodes.vaadin.demo;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.stream.Stream;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import io.binarycodes.vaadin.demo.dummy.DummyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/* reference from https://martinelli.ch/angular-15-spring-boot-3-and-jwt/ */

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    public static final String AUTHENTICATION_ENDPOINT = "/rest/auth";

    private final DummyUserService dummyUserService;
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    @Autowired
    public SecurityConfig(DummyUserService dummyUserService,
                          @Value("${jwt.public-key}") RSAPublicKey publicKey,
                          @Value("${jwt.private-key}") RSAPrivateKey privateKey) {
        this.dummyUserService = dummyUserService;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        return dummyUserService.dummyUserDetailsManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(config -> config.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(AUTHENTICATION_ENDPOINT).permitAll()
                            .anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers(AUTHENTICATION_ENDPOINT))
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(config -> config.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        var jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    private CorsConfigurationSource corsConfigurationSource() {
        final var corsSupportedMethods = Stream.of(
                        HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS
                )
                .map(HttpMethod::name)
                .toList();

        final var corsSupportedHeaders = Stream.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
                .toList();

        var cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:4200"));
        cfg.setAllowedMethods(corsSupportedMethods);
        cfg.setAllowedHeaders(corsSupportedHeaders);

        var src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}