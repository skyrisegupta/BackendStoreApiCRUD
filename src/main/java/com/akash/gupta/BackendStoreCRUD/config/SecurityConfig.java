package com.akash.gupta.BackendStoreCRUD.config;

import com.akash.gupta.BackendStoreCRUD.security.JwtAuthenticationEntryPoint;
import com.akash.gupta.BackendStoreCRUD.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
//
public class SecurityConfig {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    private final String[] PUBLIC_URLS = {
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-resources/**",
//            "/api-docs/**",
            "/test",
//            "/v2/api-/docs",
//            "/swagger-resources",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/webjars/**",
            "/v3/api-docs/**",
//            "/swagger-ui/**"

//            "/v2/api-docs",


    };

//      @Bean
//      public UserDetailsService UserDetailsService()
//      {
//
//            //USER CREATE
//            //IN Memory User Details Manager
//
//
//          UserDetails user = User.builder()
//                  .username("ankit")
//                  .password(passwordEncoder().encode("ankit"))                  //the password we got is thtough THE ENCODED VERSION
//                  .roles("Normal")
//                  .build();
//
//
//            UserDetails admin = User.builder()
//                    .username("aasim")
//                    .password(passwordEncoder().encode("aasim"))                  //the password we got is thtough THE ENCODED VERSION
//                    .roles("ADMIN")
//                    .build();
//
//            return new InMemoryUserDetailsManager(user , admin);
//
//
//
//      }


    //      Once this method completes execution and you return the SecurityFilterChain instance, the control goes back to the Spring Security configuration.
//      Spring Security will use the returned SecurityFilterChain instance to configure the security filters for your application.
//Typically, this method is called during the initialization of Spring Security configuration, and the resulting SecurityFilterChain is used by the Spring
// Security framework to handle incoming requests according to the defined security rules.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        // Http basic Authentication
        //    http.csrf(csrf -> csrf.disable())
        //            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        //            .httpBasic(Customizer.withDefaults());

        //experesion usr Authorization Configurer
        http.csrf(AbstractHttpConfigurer::disable)
//                    .cors(AbstractHttpConfigurer::disable)


                //This configuration specifies that requests matching "/auth/login" should be allowed without authentication, meaning they are public.
                // It's essentially stating that any request to the "/auth/login" endpoint should not require authentication.
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login").permitAll())  //the request you wnat to make public
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/google").permitAll())  //the request you wnat to make public

                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/users").permitAll())

                // disable for cheking items in the cart
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URLS).permitAll())

//                    .authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-ui/**").permitAll())
//                    .authorizeHttpRequests(auth -> auth.requestMatchers("/webjars/**").permitAll())
//                    .authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-resources/**").permitAll())

//                   This configuration states that any request other than those matching "/auth/login" or
//                   which is in  .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login").permitAll())
//                   should require authentication.
//                    So, any request that doesn't match "/auth/login" will need to be authenticated.
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//                                                                         entry point set
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
//                                                                setting policy on session management
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //                                which filter        , type of the filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //1st this filter       ,, // second this  UsernamePasswordAuthenticationFilter plays a crucial role in processing username/password-based authentication requests,
        // ensuring that users can
        // securely authenticate themselves to access protected resources in your application.

        //                check validation of token and many more ..
        return http.build();

        //return to the spring security

    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;


    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {

        return builder.getAuthenticationManager();

    }


    //CORS Configuration
    @Bean
    public CorsFilter corsFilter() {

        //
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
//            configuration.setAllowCredentials(Arrays.asList("http://domain2.com","http://localhost/4040"));
        configuration.setAllowCredentials(true);
// these are the requests when the preflight comes it all verified first
        //alowed every header
        configuration.addAllowedOriginPattern("*");

        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("OPTIONS");


//           setMaxAge is a method used to set the maximum age (in seconds) that the client is allowed to cache the CORS
//           (Cross-Origin Resource Sharing) preflight response. CORS is a mechanism that allows web servers
//           to specify who can access the resources on the server, based on the origin of the request.
        configuration.setMaxAge(3600L);


        source.registerCorsConfiguration("/**", configuration);
        CorsFilter corsFilter = new CorsFilter(source);
//            filterRegistrationBean.setOrder(0);  // prefrence in which order should it execute

        return corsFilter;

    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>(corsFilter());
        filterRegistrationBean.setOrder(-110); // Set the order to zero
        return filterRegistrationBean;
    }


}
