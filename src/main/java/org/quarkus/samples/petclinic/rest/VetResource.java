package org.quarkus.samples.petclinic.rest;

import io.quarkus.security.Authenticated;
import org.quarkus.samples.petclinic.system.TemplatesLocale;

import io.quarkus.qute.TemplateInstance;
import org.quarkus.samples.petclinic.model.Vet;
import org.quarkus.samples.petclinic.vet.Vets;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Authenticated
@Path("/")
public class VetResource {
    
    @Inject
    TemplatesLocale templates;

    @Authenticated
    @GET
    @Path("/vets.html")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showResourcesVetPage() {
        List<Vet> vets = Vet.listAll();
        System.out.println(vets);
        return templates.vetList(vets);
    }


    @Authenticated
    @GET
//    @RolesAllowed("ROLE_APPLICATION")
    @Path("/vets")
    public Vets showResourcesVetList() {
        Vets vets = new Vets();
		vets.getVetList().addAll(Vet.listAll());
		return vets;
    }

}
