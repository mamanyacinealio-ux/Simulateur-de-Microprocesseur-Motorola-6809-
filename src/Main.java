import InterfaceGraphique.FenetreCPU;
import InterfaceGraphique.FenetreEdition;
import CPU.RegistreCPU;
import InterfaceGraphique.FenetrePrincipale;

public class Main {
    public static void main(String[] args) {
        new FenetrePrincipale();
        new FenetreEdition();
        RegistreCPU reg = new RegistreCPU();
        new FenetreCPU(reg);

    }
}