package co.edu.unbosque.ddeli.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;

	private final UserDetailsService userDetailsService;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {

		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(auth -> auth

						.requestMatchers("/auth/login", "/auth/register",

								"/usuario/login", "/usuario/crear", "/usuario/createjson", "/usuario/verificar",

								"/swagger-ui/**", "/v3/api-docs/**")
						.permitAll()

						.requestMatchers("/producto/getall", "/producto/getbyid/**", "/producto/disponibles",
								"/producto/obtenerPorTipo/**", "/producto/obtenerPorCategoria/**", "/producto/buscar")
						.permitAll()

						.requestMatchers("/api/carrito/**").hasAnyRole("CLIENTE", "ADMIN")

						.requestMatchers("/pedido/**").hasAnyRole("CLIENTE", "ADMIN")

						.requestMatchers("/direccion/**").hasAnyRole("CLIENTE", "ADMIN")

						.requestMatchers("/evento/**").hasAnyRole("CLIENTE", "ADMIN")

						.requestMatchers("/api/detalles/**").hasAnyRole("CLIENTE", "ADMIN")

						.requestMatchers("/pago/**", "/envio/**").hasAnyRole("CLIENTE", "ADMIN")

						.requestMatchers("/tipo-personalizacion/getall", "/tipo-personalizacion/getbyid/**",
								"/opcion/getall", "/opcion/obtenerPorTipo/**")
						.hasAnyRole("CLIENTE", "ADMIN")

						.requestMatchers("/tipo-personalizacion/**", "/opcion/**").hasRole("ADMIN")

						.requestMatchers("/producto/crear", "/producto/createjson", "/producto/actualizar/**",
								"/producto/deletebyid/**")
						.hasRole("ADMIN")

						.anyRequest().permitAll())

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authenticationProvider(authenticationProvider())

				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:4200"));

		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
}