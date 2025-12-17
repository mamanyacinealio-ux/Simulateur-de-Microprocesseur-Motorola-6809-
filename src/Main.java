import Instruction.Syntaxe;
import Memoire.Memoire;
import InterfaceGraphique.FenetreCPU;
import InterfaceGraphique.FenetreEdition;
import CPU.RegistreCPU;
import InterfaceGraphique.FenetrePrincipale;
import Instruction.Instruction;
import Memoire.Memoire;


public class Main {
    public static void main(String[] args) {

                RegistreCPU rcpu = new RegistreCPU();
                FenetreEdition f=new FenetreEdition(rcpu);
                FenetrePrincipale F=new FenetrePrincipale(rcpu);
                FenetreCPU h= new FenetreCPU(rcpu);
                Instruction instruction = new Instruction();
                instruction.setRegistre(rcpu);
                rcpu.initialise(instruction);





            }











}