package com.lardi.filter;

import com.lardi.exception.RequestException;
import org.apache.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationFilter extends GenericFilterBean {

    private static final Logger log = Logger.getLogger(AuthorizationFilter.class);

    static Map<String, Boolean> pathHolder = new HashMap<>();

    static {
        pathHolder.put("/login.html", true);
        pathHolder.put("/js/login.js", true);
        pathHolder.put("/css/login.css", true);

        pathHolder.put("/index.html", true);
        pathHolder.put("/js/index.js", true);
        pathHolder.put("/css/index.css", true);

        pathHolder.put("/favicon.ico", true);

        pathHolder.put("/", true);
        pathHolder.put("/error", true);
        pathHolder.put("/api/users/login", true);
        pathHolder.put("/api/contacts", true);
        pathHolder.put("/api/contacts/search", true);
        pathHolder.put("/api/users/registration", true);
        pathHolder.put("/api/contacts/*", true);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {


        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getHeader("Authorization");
        String path = httpRequest.getServletPath();
        String method = httpRequest.getMethod();

        log.info(path);

        // Pass all requests with correct Authorization header
        if (token != null && token.equals("Access allowed")) {
            log.info("Token = " + token);
            chain.doFilter(request, response);
        } else if (token == null || token.equals("null")) {
            log.info("Token = null");
            // check permissions for url
            if (isAvailable(path)) {
                log.info("contacts");
                chain.doFilter(request, response);
            } else {
                // Custom Exception for bad requests
                throw new RequestException(String.format("RequestException: path - %s, method - %s, token - %s", path, method, token));
            }
        }
    }

    // Method for checking permissions
    private boolean isAvailable(String path) {
        log.info(path);
        Boolean result = pathHolder.get(path);
        if (result != null) {
            return result;
        } else {
            throw new RequestException(String.format("RequestException: path - %s", path));
        }
    }
}


