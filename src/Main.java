import CPU.MemoireRam;
import InterfaceGraphique.FenetreCPU;
import InterfaceGraphique.FenetreEdition;
import CPU.RegistreCPU;
import InterfaceGraphique.FenetrePrincipale;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FenetrePrincipale F=new FenetrePrincipale();
                new FenetreEdition();
                RegistreCPU reg = new RegistreCPU();
                new FenetreCPU(reg);
            F.setVisible(true);}
            });





    }
}