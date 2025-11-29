package InterfaceGraphique;
import CPU.MemoireRam;

import javax.swing.SwingUtilities;



public class Main {
    public static void main(String[] args){






                // Pour le  instance de la memoire
                MemoireRam minhaMemoria = new MemoireRam(65536);

                // 2. Criar e Exibir a Janela Principal (a GUI)
                // Isso deve ser feito na Thread de Eventos do Swing para segurança.
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // Cria a janela principal, passando o objeto de memória
                        FenetrePrincipale janelaPrincipal = new FenetrePrincipale(minhaMemoria);
                        System.out.println(new java.io.File("C:/Image/new.png").exists());

                        janelaPrincipal.setVisible(true);
                        new FenetreCPU();
                    }
                });



        //FenetrePrincipale f=new FenetrePrincipale();



       // MemoireRam R=new MemoireRam();

    }
}