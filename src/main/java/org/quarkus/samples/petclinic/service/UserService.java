package org.quarkus.samples.petclinic.service;


import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.auth.principal.DefaultJWTTokenParser;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jboss.logging.Logger;
import org.quarkus.samples.petclinic.exception.AuthenticationPasswordException;
import org.quarkus.samples.petclinic.exception.AuthenticationUsernameException;
import org.quarkus.samples.petclinic.model.User;
import org.quarkus.samples.petclinic.repository.UserRepository;
import org.quarkus.samples.petclinic.system.AuthenticationRequest;
import org.quarkus.samples.petclinic.system.ErrorExceptionMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;

@ApplicationScoped
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Inject))
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);

    private final UserRepository userRepository;
//    @ConfigProperty(name = "mp.jwt.verify.publickey.issuer") public String issuer;

    public String authenticate(final AuthenticationRequest authRequest)
            throws AuthenticationUsernameException, AuthenticationPasswordException {

        LOG.info("authRequest.getUsername() = " + authRequest.getUsername());
        final User user = userRepository.findByEmail(authRequest.getUsername())
                .orElseThrow(AuthenticationUsernameException::new);
        LOG.info("user.getEmail() = " + user.getEmail());

        if (BcryptUtil.matches(authRequest.getPassword(), user.getPassword())){
            LOG.info("BcryptUtil. password matches = " + true);

            return generateToken(user);
        }
        throw new AuthenticationPasswordException();

    }

    private String generateToken(final User user) {
        String issuerStr = "http://test.bc.ca/issuer";
        return Jwt.issuer(issuerStr)
                .upn(user.getEmail())
                .expiresIn(Duration.ofDays(365))
                .groups(user.getRoles())
                .sign();
    }
}
