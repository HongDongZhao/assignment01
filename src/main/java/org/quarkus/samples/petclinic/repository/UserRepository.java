package org.quarkus.samples.petclinic.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.quarkus.samples.petclinic.model.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByEmail(final String email){
        return find("email", email).singleResultOptional();
    }
}