package org.quarkus.samples.petclinic.vet;

import org.quarkus.samples.petclinic.model.Specialty;

import java.util.Comparator;

public class SpecialityComparator implements Comparator<Specialty> {

    @Override
    public int compare(Specialty o1, Specialty o2) {
        return o1.name.compareTo(o2.name);
    }
    
}
