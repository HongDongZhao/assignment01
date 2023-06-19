package org.quarkus.samples.petclinic.system;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;
import org.jboss.logging.Logger;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;

public class WSAuthFilter
{
    private static final Logger LOG = Logger.getLogger(WSAuthFilter.class);

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String JWT_COOKIE_NAME = "QuarkusUser";
    @RouteFilter(1000)
    void myFilter(RoutingContext rc) {
        io.vertx.core.http.Cookie jwtCookie = rc.request().getCookie(JWT_COOKIE_NAME);
        if (jwtCookie != null && !(jwtCookie.getValue().trim().isEmpty())) {
            String authHeaderValue = BEARER_PREFIX + jwtCookie.getValue();
            LOG.info("zzzz WSAuthFilter authHeaderValue = " + authHeaderValue);
            rc.request().headers().add(HttpHeaders.AUTHORIZATION, authHeaderValue);
//        rc.request().headers().add("Authorization", rc.request().query());
        }
        rc.next();
    }
}
