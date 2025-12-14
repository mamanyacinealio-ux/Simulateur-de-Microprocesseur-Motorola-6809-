package Instruction;

import CPU.RegistreCPU;

public class Instruction {


    private RegistreCPU registre;

    public void setRegistre(RegistreCPU registre) {
        this.registre = registre;
    }

    public void lda(String mode,String operande) {
        if ("IMMEDIAT".equals(mode)) {
            int value = Integer.parseInt(operande.substring(2));
            registre.setA(value);
            System.out.println("lda");
        }
        System.out.println("lda");
    }



}




