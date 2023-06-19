package org.quarkus.samples.petclinic.rest;


import io.smallrye.jwt.build.Jwt;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.quarkus.samples.petclinic.exception.AuthenticationPasswordException;
import org.quarkus.samples.petclinic.exception.AuthenticationUsernameException;
import org.quarkus.samples.petclinic.service.UserService;
import org.quarkus.samples.petclinic.system.AuthenticationRequest;
import org.quarkus.samples.petclinic.system.AuthenticationResponse;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.Duration;
import java.util.Set;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor = @__(@Inject))
public class UserResource {
    private final UserService userService;
    private static final Logger LOG = Logger.getLogger(UserResource.class);

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthenticationRequest authRequest)  {
        LOG.info("authRequest.getUsername() = " + authRequest.getUsername());
        try {
            final String token = userService.authenticate(authRequest);
            LOG.info("token = " + token);
            NewCookie jwtCookie = new NewCookie("QuarkusUser", token, "/",
                    "", "", 3600, false);
            URI homePageUri = UriBuilder.fromPath("/").build();
            return Response.seeOther(homePageUri).cookie(jwtCookie).build();
        } catch (Exception authenticationException) {
            LOG.info("Authorization Error.", authenticationException);
            Response res = Response.status(Response.Status.UNAUTHORIZED.getStatusCode(), "Login failed").build();
            LOG.info("res.getStatus() = " + res.getStatus());
            return res;
        }
    }

    @GET
    @Path("/logout")public Response logout() {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }

        // Redirect to the login page after logout
        URI loginUri = UriBuilder.fromPath("/login.html").build();
        NewCookie emptyCookie = new NewCookie("QuarkusUser", "", "/",
                "", "", 3600, false);
        return Response.seeOther(loginUri).cookie(emptyCookie).build();
    }

    @GET
    @Path("roles")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkRolesAllowed(){
        return String.format("User %s has the following roles: %s", jwt.getName(), String.join(", ", jwt.getGroups()));
    }

}
