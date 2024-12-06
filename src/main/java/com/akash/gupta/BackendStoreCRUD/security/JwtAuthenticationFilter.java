package com.akash.gupta.BackendStoreCRUD.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //check if the header is coming or not
        // if header is not available theN it's a problem

        //Authorization          // if this header is present then we can fetch the value if it's not then we can't
        String requestHeader = request.getHeader("Authorization");
        //Bearer 23432329048589325340534903458538dfjkgfdjklg
//
        logger.info("Header : {}", requestHeader);
        String username = null;
        String token = null;
//        jwt token that we set is always start with bearer
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {


            // lets fetch the token from header
            token = requestHeader.substring(7);
            try {
                //here we fetch the username from the token
                username = jwtHelper.getUsernameFromToken(token);

            } catch (IllegalArgumentException e) {

                logger.info("Illegal Argument while fetching the username");
                e.printStackTrace();

            } catch (ExpiredJwtException e) {
                logger.info("The token you Gave is Expired");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some Changes has done in Token , Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                logger.info("exception Occur , Invalid Token");
                e.printStackTrace();
            }
        } else {
            logger.info("Inavlid Header Value");

        }
        //

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //fetch details from  username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //so here we can tell spring that is bnde ka authentication kam hai
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                logger.info("Validation Fails");

            }


        }
        filterChain.doFilter(request, response);
        //return to rhe next filter
//        filterChain.doFilter(request, response) ensures the sequential execution of filters in the chain and allows each
//        filter to perform its designated tasks before passing the request and response to the next filter or servlet for further
//        processing.
//             doFilterInternal(request,response);

    }

}

//For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com

//<             ---------------------------------------------ADDITIONAL DETAILS AS JWT AUTHENTICATION A COMPLEX TOPIC---------------------------------->


// this is a Java class named JwtAuthenticationFilter which extends OncePerRequestFilter from the Spring Framework. This class is used to authenticate requests using JSON Web Tokens (JWTs). Here’s a breakdown of what it does:
//
//doFilterInternal Method: This method is overridden from OncePerRequestFilter and is called for every request to the server. It takes in an HttpServletRequest, HttpServletResponse, and a FilterChain as parameters.
//Authorization Header: The method first checks if the incoming request has an “Authorization” header. If the header is not present, it logs an “Invalid Header Value” message.
//Token Extraction: If the header is present and starts with “Bearer”, it extracts the JWT from the header. The JWT is the substring following "Bearer " in the header.
//Username Extraction: It then tries to extract the username from the token using the jwtHelper.getUsernameFromToken(token) method. If any exceptions occur during this process (like IllegalArgumentException, ExpiredJwtException, MalformedJwtException), they are caught and logged.
//User Authentication: If the username is successfully extracted and the current user is not authenticated (SecurityContextHolder.getContext().getAuthentication() == null), it fetches the UserDetails for the username using userDetailsService.loadUserByUsername(username). It then validates the token using jwtHelper.validateToken(token, userDetails). If the token is valid, it creates a UsernamePasswordAuthenticationToken and sets it in the SecurityContextHolder.
//Filter Chain: Finally, it calls filterChain.doFilter(request, response) to continue the filter chain. This means that the request is forwarded to the next filter in the chain.
//This class is annotated with @Component, which means it’s a Spring Bean and will be automatically detected and registered by Spring’s component scanning. The @Autowired annotations are used to automatically inject JwtHelper and UserDetailsService beans into this class.
//
//The logger is used to log information about the process, such as the received header and any exceptions that occur.
//
//This class is part of the security configuration of a Spring application and is crucial for securing the application using JWTs. It ensures that only authenticated requests with valid JWTs are processed by the server.

//
//what if SecurityContextHolder.getContext().getAuthentication() != null
//If SecurityContextHolder.getContext().getAuthentication() != null, it means that the user is already authenticated for the current session.
// In this case, the filter does not attempt to authenticate the user again using the JWT token from the request header.
//The SecurityContextHolder holds the SecurityContext, which contains details about the security context of the current thread of execution,
// including details about the authenticated user. If getAuthentication() returns a non-null value, it means that the user is already authenticated
// and their authentication details are stored in the security context.
//So, in the JwtAuthenticationFilter, if SecurityContextHolder.getContext().getAuthentication() != null, the filter simply skips the JWT authentication
// process and forwards the request to the next filter in the chain by calling filterChain.doFilter(request, response). This is because the user is already
// authenticated, so there’s no need to authenticate them again.
//
//This check helps to avoid unnecessary database calls to load user details and unnecessary computation to validate the
// JWT token if the user is already authenticated. It’s a common practice in stateless authentication mechanisms like JWT to improve performance.