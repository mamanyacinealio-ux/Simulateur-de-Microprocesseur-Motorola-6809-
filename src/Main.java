import CPU.CPU6809;
import Instruction.Syntaxe;
import Memoire.MemoireRam;
import InterfaceGraphique.FenetreCPU;
import InterfaceGraphique.FenetreEdition;
import CPU.RegistreCPU;
import InterfaceGraphique.FenetrePrincipale;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {



                MemoireRam m=new MemoireRam(65536);

                RegistreCPU reg = new RegistreCPU();
                CPU6809 cpu=new CPU6809(reg,m);
                FenetrePrincipale F=new FenetrePrincipale();


                FenetreCPU h= new FenetreCPU(reg);

                FenetreEdition f=new FenetreEdition(reg,cpu);
                Syntaxe syntaxe = new Syntaxe(f);


                //f.setVisible(true);
                //System.out.println(syntaxe);
                //F.setVisible(true);
            }











}