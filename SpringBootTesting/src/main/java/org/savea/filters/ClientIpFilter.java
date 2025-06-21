package org.savea.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that stores the remote address of each request under the
 * {@code CLIENT_IP} attribute before continuing the filter chain.
 */
@Component
public class ClientIpFilter extends OncePerRequestFilter {

    public static final String CLIENT_IP = "CLIENT_IP";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        request.setAttribute(CLIENT_IP, request.getRemoteAddr());
        filterChain.doFilter(request, response);
    }
}
