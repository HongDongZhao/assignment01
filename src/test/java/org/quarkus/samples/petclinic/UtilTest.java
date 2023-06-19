package org.quarkus.samples.petclinic;

import io.quarkus.elytron.security.common.BcryptUtil;

public class UtilTest {
    public static void main(String[] args) {
        String password = "changeme";
        String password2 = "changeme";

        String hashcode = BcryptUtil.bcryptHash(password);
        System.out.println("p1 = " +hashcode);
        System.out.println("p2 = " +BcryptUtil.bcryptHash(password2));
        boolean same = BcryptUtil.bcryptHash(password2).equals(hashcode);

        System.out.println("same = " + same);
        System.out.println("match = " + BcryptUtil.matches(password2, hashcode));


    }
}
