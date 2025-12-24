

import CPU.RegistreCPU;
import InterfaceGraphique.*;
import Memoire.Memoire;
import Instruction.Instruction;
import javax.swing.SwingUtilities;

public class Main {

    private static final int MEMORY_SIZE = 0x10000;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Memoire m = new Memoire(MEMORY_SIZE);

            Instruction instruction = new Instruction(null, m);



             RegistreCPU rcpu = new RegistreCPU(m, instruction);

            instruction.setRegistreCPU(rcpu);

            FenetreROM ROM=new FenetreROM(m);

            FenetreRAM RAM=new FenetreRAM(m);
            RAM.setVisible(true);
            FenetreCPU h = new FenetreCPU(rcpu);
            FenetrePrincipale F = new FenetrePrincipale(rcpu,m,instruction);



            //f.setVisible(true);
            h.setVisible(true);
        });
        System.out.println(FenetrePrincipale.class.getResource("/image/pas.png"));

    }

}