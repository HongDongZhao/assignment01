package org.quarkus.samples.petclinic.system;

import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;
import org.quarkus.samples.petclinic.rest.UserResource;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.Interceptor;
import javax.ws.rs.Path;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.eclipse.microprofile.jwt.JsonWebToken;
//@Provider
//@PreMatching
//@Priority(value = 20)
////@Priority(Interceptor.Priority.PLATFORM_BEFORE)

//@ApplicationScoped
//@Priority(900jwtCookie)
//public class JwtCookieAuthorizationFilter implements ContainerRequestFilter {
//
public class JwtCookieAuthorizationFilter  {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String JWT_COOKIE_NAME = "QuarkusUser";
    private static final Logger LOG = Logger.getLogger(JwtCookieAuthorizationFilter.class);

//    @ServerRequestFilter(priority = 100)
    public void filter(ContainerRequestContext requestContext) {
        Cookie jwtCookie = requestContext.getCookies().get(JWT_COOKIE_NAME);

        LOG.info("zzzz ServerRequestFilter Request URI jwtCookie = " + jwtCookie);

        LOG.info("zzzz ServerRequestFilter Request URI" + requestContext.getUriInfo().getRequestUri().getPath());
        String pathStr = requestContext.getUriInfo().getRequestUri().getPath();
        if (pathStr != null && (pathStr.equals("/login.html") || pathStr.equals("/api/login") )) {
            LOG.info("open pathStr" + pathStr);
//            return;
        }


//
//        if (requestContext.getUriInfo().getRequestUri().equals("api/login")) {
//            return;
//        }
        if (jwtCookie != null) {
            String token = jwtCookie.getValue();

            try {
//                JWTCallerPrincipal callerPrincipal = validateToken(token);
                String authHeaderValue = BEARER_PREFIX + token;
                LOG.info("zzzz ServerRequestFilter authHeaderValue = " + authHeaderValue);

                requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, authHeaderValue);
//                requestContext.setSecurityContext(new JwtSecurityContext(callerPrincipal, new HashSet<>(Arrays.asList("user"))));
                return;
            } catch (Exception e) {
                // Token validation failed
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }
        }

//         No or invalid JWT cookie
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

//    private JWTCallerPrincipal validateToken(String token) {
//        // Implement token validation logic here
//        // You can use a JWT library or custom validation logic
//        // For simplicity, we'll skip the actual validation and return a mocked JWTCallerPrincipal
//        Jwt.
//        // Assuming token validation succeeds
//        return new JWTCallerPrincipal.Builder("quarkus-login")
//                .withSubject("john.doe")
//                .withClaim(Claims.ROLES.name(), "user").
//                .build();
//    }

//    @ServerRequestFilter( preMatching = true)
//    public void filter(ContainerRequestContext requestContext) {
//        LOG.info("Testing extension interceptor");
//        LOG.info("zzzz Request URI" + requestContext.getUriInfo().getRequestUri());
//
//        List<String> strs = requestContext.getHeaders().get("Authorization");
//        if (strs != null) {
//            String str = strs.get(0);
//            LOG.info("zzzz Authorization = " + str);
//        }
//                //.toString();// .getCookies().get(JWT_COOKIE_NAME);
////        LOG.debug("zzzz Request URI" + requestContext.getUriInfo().getRequestUri());
//
//    }
}

