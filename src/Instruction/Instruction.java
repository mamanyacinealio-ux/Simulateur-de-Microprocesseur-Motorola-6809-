package Instruction;

import CPU.RegistreCPU;
import Memoire.Memoire;

public class Instruction {
    private Memoire memoire;



    private RegistreCPU registre;

    public void setRegistre(RegistreCPU registre) {
        this.registre = registre;
    }

    public void setMemoire(Memoire memoire) {
        this.memoire = memoire;
    }


    public void lda(String mode,String operande) {
        if ("IMMEDIAT".equals(mode)) {
            int value = Integer.parseInt(operande.substring(2));
            registre.setA(value);
            System.out.println("lda");
        }
        System.out.println("lda");
    }

    public void sta(String mode, String operande) {
        if ("DIRECT".equals(mode)) {
            int adresse = Integer.parseInt(operande, 16);
            memoire.write(adresse, (byte) registre.getA());
            System.out.println("STA");
        }
    }



}




