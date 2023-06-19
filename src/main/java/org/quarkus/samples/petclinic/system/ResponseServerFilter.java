package org.quarkus.samples.petclinic.system;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URI;

@Provider
@ApplicationScoped
public class ResponseServerFilter implements ContainerResponseFilter {
    private static final Logger LOG = Logger.getLogger(ResponseServerFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        LOG.info(requestContext);
        LOG.info("responseContext" + responseContext);
        SecurityContext securityContext = requestContext.getSecurityContext();
        //!securityContext.isSecure() ||
        if (!isAuthorized(responseContext)) {

            // Construct the URI for the home.html file
            URI loginUri = UriBuilder.fromPath("/login.html").build();
            LOG.info("redirect to: " + loginUri);

            // Redirect to home.html
            responseContext.setStatus(Response.Status.SEE_OTHER.getStatusCode());
            responseContext.getHeaders().putSingle("Location", loginUri.toString());
        }

//        if (responseContext.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            URI homeUri = UriBuilder.fromPath("/login.html").build();
//            Response.seeOther(homeUri).build();
//        }

//        int statusCode = responseContext.getStatus();
//        if (statusCode >= 300 && statusCode < 402) {
//            // Retrieve the redirection URL from the Location header
//            URI redirectionUri = responseContext.getLocation();
//            if (redirectionUri != null) {
//                // Check if the redirection URL ends with ".html" extension
//                if (redirectionUri.getPath().endsWith(".html")) {
//                    // Construct the URI for the home.html file
//                    URI homeUri = UriBuilder.fromPath("home.html").build();
//                    if (!redirectionUri.equals(homeUri)) {
//                        // Redirect to home.html if the current URL is not already home.html
//                        responseContext.setStatus(Response.Status.SEE_OTHER.getStatusCode());
//                        responseContext.getHeaders().putSingle("Location", homeUri.toString());
//                    }
//                }
//            }
//        }
    }

    private boolean isAuthorized(ContainerResponseContext responseContext) {
        // Check the response status code
        int statusCode = responseContext.getStatus();
        LOG.info("statusCode: " + statusCode);
        return statusCode >= 200 && statusCode < 300; // Assume only 2xx status codes are considered authorized
    }
}
