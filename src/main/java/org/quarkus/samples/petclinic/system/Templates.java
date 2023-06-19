package org.quarkus.samples.petclinic.system;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.quarkus.samples.petclinic.model.Owner;
import org.quarkus.samples.petclinic.model.Pet;
import org.quarkus.samples.petclinic.model.PetType;
import org.quarkus.samples.petclinic.model.Vet;
import org.quarkus.samples.petclinic.model.visit.Visit;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@CheckedTemplate
public class Templates {
    public static native TemplateInstance welcome();

    public static native TemplateInstance error(String message);

    public static native TemplateInstance vetList(List<Vet> vets);

    public static native TemplateInstance findOwners(List<String> errors);

    public static native TemplateInstance ownerDetails(Owner owner);

    public static native TemplateInstance createOrUpdateOwnerForm(Owner owner, Map<String, String> errors);

    public static native TemplateInstance ownersList(Collection<Owner> owners);

    public static native TemplateInstance createOrUpdatePetForm(Owner owner, Pet pet, List<PetType> petTypes, Map<String, String> errors);

    public static native TemplateInstance createOrUpdateVisitForm(Pet pet, Visit visit, Map<String, String> errors);
}
